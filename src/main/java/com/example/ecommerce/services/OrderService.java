package com.example.ecommerce.services;

import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.enums.ProductStatus;
import com.example.ecommerce.exceptions.NoAuthenticationException;
import com.example.ecommerce.models.Order;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.User;
import com.example.ecommerce.persistence.OrderRepo;
import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.responses.DataResponse;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final ProductService productService;
    private final EmailService emailService;
    public void createOrder(Order order) {
        orderRepo.save(order);
    }


    public DataResponse<?> confirmOrder(Authentication authentication){
        Order previous = getOrderByAuthentication(authentication);
        checkOrderStatus(previous);
        previous.setStatus(OrderStatus.CONFIRMED);
        if (previous.getProducts().isEmpty())
            throw new IllegalStateException("Cart is empty");
        if (previous
                .getProducts()
                .stream()
                .anyMatch(product -> product.getStatus()==ProductStatus.PURCHASED))
            throw new IllegalStateException("Some products arent available");
        previous.getProducts().forEach(
                product -> product.setStatus(ProductStatus.PURCHASED)
        );
        orderRepo.save(previous);

        try {
            User user = (User) authentication.getPrincipal();
            emailService.sendOrderConfirmMail(user.getEmail(),previous);
            emailService.sendProductOwnerMail(previous);
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
        createCurrentUserCart(authentication);
        return DataResponse.ok(previous);
    }

    private void createCurrentUserCart(Authentication authentication){
        if (authentication == null)
            throw new NoAuthenticationException();
        User user = (User) authentication.getPrincipal();
        createCartByUser(user);
    }

    public void createCartByUser(User user){
        createOrder(Order.builder()
                .status(OrderStatus.CART)
                .total(BigDecimal.ZERO)
                .products(Collections.emptySet())
                .user(user)
                .build());
    }

    public DataResponse<?> getCart(Authentication authentication){
        return DataResponse.ok(getOrderByAuthentication(authentication));
    }

    public DataResponse<?> addProduct(UUID productId, Authentication authentication){
        Order order = getOrderByAuthentication(authentication);
        checkOrderStatus(order);
        Product product = productService.getProduct(productId);
        if (product.getStatus() == ProductStatus.PURCHASED)
            throw new IllegalStateException("Product already purchased");
        if (product.getOwner().getEmail().equals(authentication.getName()))
            throw new IllegalArgumentException("You are already owner of the product");
        if (order.getProducts().add(product))
            order.setTotal(order.getTotal().add(product.getPrice()));
        orderRepo.save(order);
        return DataResponse.ok(order);
    }

    public BasicResponse removeProduct(UUID productId, Authentication authentication){
        Order order = (Order) getCart(authentication).getData();
        checkOrderStatus(order);
        Product product = productService.getProduct(productId);
        if (!order.getProducts().remove(product))
            throw new IllegalArgumentException("No product with id "+productId+" in cart");
        order.setTotal(order.getTotal().subtract(product.getPrice()));
        orderRepo.save(order);
        return DataResponse.ok(order);
    }

    private Order getOrder(UUID uuid){
        return orderRepo
                .findById(uuid)
                .orElseThrow(()->
                        new EntityNotFoundException("Order with given id "+uuid+" doesnt exist")
                );
    }

    private Order getOrderByAuthentication(Authentication authentication){
        if (authentication == null)
            throw new NoAuthenticationException();
        User user = (User) authentication.getPrincipal();
        return orderRepo.getCart(user)
                .orElseThrow(()->
                        new EntityNotFoundException("Error finding users cart")
                );
    }

    private void checkOrderStatus(Order order){
        if (OrderStatus.CONFIRMED == order.getStatus())
            throw new IllegalStateException("Order is already confirmed");
    }

    public DataResponse<?> getAll() {
        return DataResponse.ok(orderRepo.findAll());
    }

    public DataResponse<?> getAllCurrentUserOrders(Authentication authentication) {
        if (authentication == null)
            throw new NoAuthenticationException();
        User user = (User) authentication.getPrincipal();
        return DataResponse.ok(orderRepo.getOrderByUserId(user.getId()));
    }
}

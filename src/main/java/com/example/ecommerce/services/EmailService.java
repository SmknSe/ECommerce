package com.example.ecommerce.services;

import com.example.ecommerce.models.Order;
import com.example.ecommerce.models.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String username;
    String subject = "Your order in Furniture Store";

    @Async
    public void sendOrderConfirmMail(String receiver, Order order) throws MessagingException {
        StringBuilder sb = new StringBuilder();
        sb
                .append("<div style=\"display: flex; align-content: center; flex-direction: column; gap: 0.5rem; width: 100%\">")
                .append("<h2>Congratulations, you've confirmed your order!</h2>")
                .append("<table style=\"sp\">")
                .append("<thead><tr>")
                .append("<th colspan=\"4\"><h3>Ordered products</h3></th>")
                .append("</tr></thead>");

        for (var product : order.getProducts()) {;
            sb
                    .append("<tr>")
                    .append("<td style=\"padding: 0; width: 50%\"><h4 style=\"margin: 8px 0\">").append(product.getTitle()).append("</h4></td>")
                    .append("<td style=\"padding: 0\"><h4 style=\"margin: 8px 0\">").append(product.getCategory().getName().toUpperCase()).append("</h4></td>")
                    .append("<td style=\"padding: 0\"><h4 style=\"margin: 8px 0\">USD $ ").append(product.getPrice()).append("</h4></td>")
                    .append("</tr>");
        }
        sb.append("</tbody></table>")
                .append("<h3>Total: USD $ ").append(order.getTotal()).append("</h3>")
                .append("<h4>Best regards,<br/>Furniture store</h4></div>");
        sendEmail(receiver, sb.toString());
    }

    @Async
    public void sendProductOwnerMail(Order order) throws MessagingException {
        Map<String, Set<Product>> orderedProducts = order.getProducts()
                .stream()
                .collect(Collectors.groupingBy(product -> product.getOwner().getEmail(),Collectors.toSet()));
        for (var entry : orderedProducts.entrySet()){
            String receiver = entry.getKey();
            StringBuilder sb = new StringBuilder();
            sb
                    .append("<div style=\"display: flex; align-content: center; flex-direction: column; gap: 0.5rem; width: 100%\">")
                    .append("<h2>Congratulations, your products was ordered!</h2>")
                    .append("<table style=\"sp\">")
                    .append("<thead><tr>")
                    .append("<th colspan=\"4\"><h3>Ordered products</h3></th>")
                    .append("</tr></thead>");
            for (var product : entry.getValue()) {
                sb
                        .append("<tr>")
                        .append("<td style=\"padding: 0; width: 50%\"><h4 style=\"margin: 8px 0\">").append(product.getTitle()).append("</h4></td>")
                        .append("<td style=\"padding: 0\"><h4 style=\"margin: 8px 0\">").append(product.getCategory().getName().toUpperCase()).append("</h4></td>")
                        .append("<td style=\"padding: 0\"><h4 style=\"margin: 8px 0\">USD $ ").append(product.getPrice()).append("</h4></td>")
                        .append("</tr>");
            }
            sb.append("</tbody></table>")
                    .append("<h3>Total: USD $ ").append(order.getTotal()).append("</h3>")
                    .append("<h4>Best regards,<br/>Furniture store</h4></div>");
            sendEmail(receiver, sb.toString());
        }
    }

    @Async
    public void sendEmail(String receiver, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(username);
        helper.setSubject(subject);
        helper.setTo(receiver);
        helper.setText(body, true);

        javaMailSender.send(message);
    }

}

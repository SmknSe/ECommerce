FROM maven:3.8.4-openjdk-21-slim

WORKDIR /app

COPY target/ECommerce-0.0.1-SNAPSHOT.jar /app/
COPY src/main/resources/images /app/images

CMD ["java", "-jar", "ECommerce-0.0.1-SNAPSHOT.jar"]

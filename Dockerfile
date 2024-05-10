FROM openjdk:21-jdk

WORKDIR /app

RUN mkdir -p /app/images
COPY target/ECommerce-0.0.1-SNAPSHOT.jar /app/

CMD ["java", "-jar", "ECommerce-0.0.1-SNAPSHOT.jar"]

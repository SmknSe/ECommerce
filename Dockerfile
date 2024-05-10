FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

COPY target/kr_backend-0.0.1-SNAPSHOT.jar /app/

CMD ["java", "-jar", "kr_backend-0.0.1-SNAPSHOT.jar"]

FROM amazoncorretto:21.0.4-al2023-headless
WORKDIR /project
COPY target/CarShop-Service-1.0-SNAPSHOT-shaded.jar /project/CarShop-Service-1.0-SNAPSHOT-shaded.jar
CMD ["java", "-jar", "CarShop-Service-1.0-SNAPSHOT-shaded.jar"]


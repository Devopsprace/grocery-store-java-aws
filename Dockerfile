FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/grocery-store-java-aws-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

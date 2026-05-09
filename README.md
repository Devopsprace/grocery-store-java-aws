# Grocery Store Java AWS Project

A small Maven + Spring Boot online grocery buying project.

## Features
- Product listing page
- Add to cart
- Remove from cart
- Checkout form
- Success page
- Ready for AWS EC2 / Docker deployment

## Project structure
- `pom.xml`
- `src/main/java`
- `src/main/resources/templates`
- `src/main/resources/static/css`
- `src/test/java`
- `Dockerfile`

## Run locally
```bash
mvn clean package
mvn spring-boot:run
```

Open:
```text
http://localhost:8080
```

## Build JAR
```bash
mvn clean package
java -jar target/grocery-store-java-aws-1.0.0.jar
```

## Docker build
```bash
mvn clean package
docker build -t grocery-store-java-aws .
docker run -p 8080:8080 grocery-store-java-aws
```

## Deploy on AWS EC2
1. Launch Amazon Linux / Ubuntu EC2 instance.
2. Install Java 17 and Maven.
3. Upload project or clone from git.
4. Run:
```bash
mvn clean package
java -jar target/grocery-store-java-aws-1.0.0.jar
```
5. Open EC2 security group inbound port `8080`.

## Deploy with Docker on AWS EC2
```bash
mvn clean package
docker build -t grocery-store-java-aws .
docker run -d -p 8080:8080 --name grocery-app grocery-store-java-aws
```

## Notes
- Uses in-memory demo data, so no database setup is required.
- Good starter project for testing deployment, CI/CD, reverse proxy, and monitoring.

# Use lightweight Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy Maven wrapper + pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Copy source code
COPY src ./src

# Build the application (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/dump4pass-0.0.1-SNAPSHOT.jar"]

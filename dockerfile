# Use OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy all files into container
COPY . .

# Make mvnw executable (fix for exit code 126)
RUN chmod +x mvnw

# Build the application (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot's default port
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "target/dump4pass-0.0.1-SNAPSHOT.jar"]

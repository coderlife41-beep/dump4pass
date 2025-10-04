# Use OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven files and build
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the app port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/dump4pass-0.0.1-SNAPSHOT.jar"]

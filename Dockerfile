FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Install Maven
RUN apk add --no-cache maven

# Copy all project files
COPY . .

# Build the app
RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/dump4pass-0.0.1-SNAPSHOT.jar"]

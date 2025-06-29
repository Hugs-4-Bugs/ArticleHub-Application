FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy source code
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Run the app
ENTRYPOINT ["java","-jar","target/*.jar"]

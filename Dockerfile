# Stage 1: Build với Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run với JDK nhẹ hơn
FROM amazoncorretto:17
WORKDIR /app

# Copy file jar từ stage 1
COPY --from=build /app/target/*.jar app.jar

# Mặc định Render sẽ set PORT env
ENV PORT=8080
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]
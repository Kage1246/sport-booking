FROM amazoncorretto:17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
FROM eclipse-temurin:21-jdk AS builder

COPY . .

RUN ./mvnw clean package spring-boot:repackage


FROM eclipse-temurin:21

COPY --from=builder target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
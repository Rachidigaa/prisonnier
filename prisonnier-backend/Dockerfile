FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml ./

COPY libs/dilemma_g2_12-1.0-SNAPSHOT.jar /app/libs/
RUN mvn install:install-file \
  -Dfile=/app/libs/dilemma_g2_12-1.0-SNAPSHOT.jar \
  -DgroupId=org.example \
  -DartifactId=dilemma_g2_12 \
  -Dversion=1.0-SNAPSHOT \
  -Dpackaging=jar

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/my_projet_g1_10-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

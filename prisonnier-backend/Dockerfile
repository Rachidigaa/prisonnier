# Use Maven to build the application
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml and dependencies (without src yet) to cache dependencies
COPY pom.xml ./
COPY libs ./libs

# Install local dependencies first
RUN mvn install:install-file -Dfile=libs/dilemma_g2_12-1.0-SNAPSHOT.jar -DgroupId=org.example -DartifactId=dilemma_g2_12 -Dversion=1.0-SNAPSHOT -Dpackaging=jar

# Download all dependencies
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests && ls -R /app/target
# Use a slim OpenJDK runtime for the final image
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the built JAR file from the build stage
COPY --from=build /app/target/my_projet_g1_10-0.0.1-SNAPSHOT.jar my_projet_g1_10-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "my_projet_g1_10-0.0.1-SNAPSHOT.jar"]

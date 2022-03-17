FROM maven:3.8.4-openjdk-17-slim as build-env

# Set the working directory to /app
WORKDIR /app

# Copy the working directory contents into the container
COPY . ./

# download dependencies as specified in pom.xml
# building dependency layer early will speed up compile time when pom is unchanged
RUN mvn verify --fail-never -Djdk.tls.client.protocols=TLSv1.2 -s ci_settings.xml

FROM openjdk:17-slim-buster
COPY --from=build-env app/target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

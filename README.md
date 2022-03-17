# topology-management-service

topology-management-service based on Spring Boot.

## Swagger UI

SpringFox Swagger 3.0 can be reached at /swagger-ui/index.html

It creates REST services documentation for all the services with @ RequestMapping

## Docker build & push

1) Install Docker on your env
2) Install Google Cloud SDK Installer on your env
3) Run "*gcloud init*" and configure it with the Google account which is enabled for the Google Cloud env
4) Run "*gcloud components install docker-credential-gcr*" on your local env
5) [Patch for Windows: in the installation path, *C:\Program Files (x86)\Google\Cloud SDK\google-cloud-sdk\bin*, a **docker-credential-gcr.exe** will be created. Copy it with the following name:]

        docker-credential-gcloud.cmd

Thanks to Spotify's dockerfile Maven plugin, configured in pom.xml, the following command builds and push a Docker image into a cloud registry:

    mvn clean package -DskipTests dockerfile:build dockerfile:push
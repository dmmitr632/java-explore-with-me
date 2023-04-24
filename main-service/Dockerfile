FROM azul/zulu-openjdk-alpine:11-latest
COPY target/*.jar main-service.jar
ENTRYPOINT ["java","-jar","/main-service.jar"]
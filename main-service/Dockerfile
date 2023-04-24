FROM azul/zulu-openjdk-alpine:11-latest
COPY main-service/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
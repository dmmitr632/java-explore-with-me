FROM azul/zulu-openjdk-alpine:11-latest
COPY stats-service/stats-server/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
FROM azul/zulu-openjdk-alpine:11-latest
COPY target/*.jar stats-server.jar
ENTRYPOINT ["java","-jar","/stats-server.jar"]
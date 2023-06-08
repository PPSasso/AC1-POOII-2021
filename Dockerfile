FROM openjdk:11-jre
WORKDIR /sistema
COPY target/*.jar /sistema/sistema-0.0.1-SNAPSHOT.jar
EXPOSE 8081
CMD java -XX:+UseContainerSupport -Xmx512m  -Dserver.port=8081 -jar sistema-0.0.1-SNAPSHOT.jar

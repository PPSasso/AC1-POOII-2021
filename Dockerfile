FROM openjdk:11-jre
WORKDIR /sistema
COPY target/*.jar /sistema/sistema-0.0.1-SNAPSHOT.jar
EXPOSE 9090
CMD java -XX:+UseContainerSupport -Xmx512m  -Dserver.port=9090 -jar sistema-0.0.1-SNAPSHOT.jar

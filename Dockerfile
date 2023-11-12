FROM openjdk:11-jdk
ARG JAR_URL
EXPOSE 8089
ADD $JAR_URL waelhcine-5erpbi6-g4-gestion-station-ski.jar
ENTRYPOINT ["java","-jar","/waelhcine-5erpbi6-g4-gestion-station-ski.jar"]

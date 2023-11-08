FROM openjdk:11-jdk
EXPOSE 8089
ADD ./target/5erpbi6-g4-gestion-station-ski.jar waelhcine-5erpbi6-g4-gestion-station-ski.jar
ENTRYPOINT ["java","-jar","/waelhcine-5erpbi1-g4-gestion-station-ski.jar"]

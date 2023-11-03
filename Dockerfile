FROM openjdk:11-jdk
EXPOSE 8089
ADD ./target/5ERPBI1-G4-gestion-station-ski.jar WaelHcine-5ERPBI1-G4-gestion-station-ski.jar
ENTRYPOINT ["java","-jar","/WaelHcine-5ERPBI1-G4-gestion-station-ski.jar"]
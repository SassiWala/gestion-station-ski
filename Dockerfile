FROM openjdk:11-jdk
EXPOSE 8089
ADD ./target/5ERP-BI6-gestion-station-ski.jar 5ERP-BI6-gestion-station-ski.jar
ENTRYPOINT ["java","-jar","/5ERP-BI6-gestion-station-ski.jar"]
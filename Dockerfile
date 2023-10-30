FROM openjdk:11-jdk-alpine
EXPOSE 8089
ADD ./target/5ERP-BI6-gestion-station-ski.jar 5ERP-BI6-gestion-station-ski.jar
ENTRYPOINT ["java","-jar","/5ERP-BI6-gestion-station-ski.jar"]
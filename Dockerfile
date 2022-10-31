FROM openjdk:17-jdk
EXPOSE 8080
add target/rso-data-aggregation.jar rso-data-aggregation.jar
ENTRYPOINT ["java","-jar","/rso-data-aggregation.jar"]
FROM java:8

EXPOSE 8080

COPY build/libs/shop-0.0.1-SNAPSHOT-boot.jar /tmp/app.jar

ENTRYPOINT ["java", "-jar", "/tmp/app.jar"]
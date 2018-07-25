FROM java:8

EXPOSE 8090

COPY build/libs/product-0.0.1-SNAPSHOT-boot.jar /tmp/app.jar

ENTRYPOINT ["java", "-jar", "/tmp/app.jar"]
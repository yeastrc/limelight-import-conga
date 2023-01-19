FROM amazoncorretto:11-alpine-jdk

COPY build/libs/congaToLimelightXML.jar  /usr/local/bin/congaToLimelightXML.jar

ENTRYPOINT ["java", "-jar", "/usr/local/bin/congaToLimelightXML.jar"]
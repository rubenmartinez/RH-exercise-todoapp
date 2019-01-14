FROM openjdk:8-jre-alpine

EXPOSE 8080

WORKDIR "/app"

ARG VERSION
ADD target/rhe-fe-${VERSION}.jar /app/rhe-fe.jar

# TODO: Dual layer approach for docker optimization (not so important for the exercise)
ENTRYPOINT ["/usr/bin/java", "-jar", "/app/rhe-fe.jar"]


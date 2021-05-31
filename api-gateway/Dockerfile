FROM openjdk:11-jre-slim
ENV FILE_NAME=api-gateway.jar
ARG JAR_FILE=api-gateway/build/libs/${FILE_NAME}
COPY $JAR_FILE .
COPY ../docker-run.sh .
ENTRYPOINT ["bash", "docker-run.sh"]
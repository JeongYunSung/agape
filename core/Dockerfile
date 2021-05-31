FROM openjdk:11-jre-slim
ENV FILE_NAME=core.jar
ARG JAR_FILE=core/build/libs/${FILE_NAME}
COPY $JAR_FILE .
COPY ../docker-run.sh .
ENTRYPOINT ["bash", "docker-run.sh"]
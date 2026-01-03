FROM eclipse-temurin:25 AS jre-build
COPY . /app/codehorn/.

WORKDIR /app/codehorn

RUN chmod +x ./gradlew \
    && ./gradlew :evaluation-service:build -x test \
    && mv ./evaluation-service/build/libs/evaluation-service-1.0.0.jar ./evaluation-service.jar \
    && apt update \
    && apt install unzip -y \
    && unzip ./evaluation-service.jar -d temp

RUN $JAVA_HOME/bin/jdeps \
      --print-module-deps \
      --ignore-missing-deps \
      --recursive \
      --multi-release 25 \
      --class-path="./temp/BOOT-INF/lib/*" \
      --module-path="./temp/BOOT-INF/lib/*" \
      ./evaluation-service.jar > ./jre-modules.txt \
    && $JAVA_HOME/bin/jlink \
      --verbose \
      --add-modules "$(cat ./jre-modules.txt)" \
      --strip-debug \
      --no-man-pages \
      --no-header-files \
      --compress=2 \
      --output /tmp/jre \
    && rm -rf temp

FROM debian:buster-slim
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-build /tmp/jre $JAVA_HOME
COPY --from=jre-build /app/codehorn/evaluation-service.jar /app/evaluation-service.jar

EXPOSE 80

CMD ["java", "-Dserver.port=80", "-jar", "/app/evaluation-service.jar"]
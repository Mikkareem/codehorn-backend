FROM eclipse-temurin:25 AS jre-build
COPY . /app/codehorn/.

WORKDIR /app/codehorn

RUN chmod +x ./gradlew \
    && ./gradlew :gateway-service:build -x test \
    && mv ./gateway-service/build/libs/gateway-service-1.0.0.jar ./gateway-service.jar \
    && apt update \
    && apt install unzip -y \
    && unzip ./gateway-service.jar -d temp

RUN $JAVA_HOME/bin/jdeps \
      --print-module-deps \
      --ignore-missing-deps \
      --recursive \
      --multi-release 25 \
      --class-path="./temp/BOOT-INF/lib/*" \
      --module-path="./temp/BOOT-INF/lib/*" \
      ./gateway-service.jar > ./jre-modules.txt \
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
COPY --from=jre-build /app/codehorn/gateway-service.jar /app/gateway-service.jar

EXPOSE 80

CMD ["java", "-Dserver.port=80", "-jar", "/app/gateway-service.jar"]
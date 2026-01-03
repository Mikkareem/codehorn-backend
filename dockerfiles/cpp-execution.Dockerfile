FROM eclipse-temurin:25 AS jre-build
COPY . /app/codehorn/.

WORKDIR /app/codehorn

RUN chmod +x ./gradlew \
    && ./gradlew :cpp-execution-service:build -x test \
    && mv ./cpp-execution-service/build/libs/cpp-execution-service-1.0.0.jar ./cpp-execution-service.jar \
    && apt update \
    && apt install unzip -y \
    && unzip ./cpp-execution-service.jar -d temp

RUN $JAVA_HOME/bin/jdeps \
      --print-module-deps \
      --ignore-missing-deps \
      --recursive \
      --multi-release 25 \
      --class-path="./temp/BOOT-INF/lib/*" \
      --module-path="./temp/BOOT-INF/lib/*" \
      ./cpp-execution-service.jar > ./jre-modules.txt \
    && $JAVA_HOME/bin/jlink \
      --verbose \
      --add-modules "$(cat ./jre-modules.txt)" \
      --strip-debug \
      --no-man-pages \
      --no-header-files \
      --compress=2 \
      --output /tmp/jre \
    && rm -rf temp

FROM debian:bookworm-slim
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-build /tmp/jre $JAVA_HOME
COPY --from=jre-build /app/codehorn/cpp-execution-service.jar /app/cpp-execution-service.jar

RUN apt-get update \
 && apt-get install -y --no-install-recommends ca-certificates curl \
 && install -m 0755 -d /etc/apt/keyrings \
 && curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc \
 && chmod a+r /etc/apt/keyrings/docker.asc \
 && echo "Types: deb" > /etc/apt/sources.list.d/docker.sources \
 && echo "URIs: https://download.docker.com/linux/debian" >> /etc/apt/sources.list.d/docker.sources \
 && echo "Suites: bookworm" >> /etc/apt/sources.list.d/docker.sources \
 && echo "Components: stable" >> /etc/apt/sources.list.d/docker.sources \
 && echo "Signed-By: /etc/apt/keyrings/docker.asc" >> /etc/apt/sources.list.d/docker.sources \
 && apt-get update \
 && apt-get install -y --no-install-recommends docker-ce-cli \
 && rm -rf /var/lib/apt/lists/*

EXPOSE 80

CMD ["java", "-Dserver.port=80", "-jar", "/app/cpp-execution-service.jar"]
FROM openjdk:21-jdk-slim

ENV MAVEN_VERSION=3.9.1 \
    MAVEN_HOME=/opt/maven \
    M2_HOME=/opt/maven \
    FLYWAY_VERSION=9.16.0 \
    FLYWAY_HOME=/opt/flyway

RUN apt-get update && \
    apt-get install -y wget libfreetype6 libfontconfig1 && \
    wget -O apache-maven-$MAVEN_VERSION-bin.tar.gz https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    tar -xzf apache-maven-$MAVEN_VERSION-bin.tar.gz -C /opt && \
    mv /opt/apache-maven-$MAVEN_VERSION /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    wget -O flyway-commandline-$FLYWAY_VERSION-linux-x64.tar.gz https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/$FLYWAY_VERSION/flyway-commandline-$FLYWAY_VERSION-linux-x64.tar.gz && \
    tar -xzf flyway-commandline-$FLYWAY_VERSION-linux-x64.tar.gz -C /opt && \
    mv /opt/flyway-$FLYWAY_VERSION $FLYWAY_HOME && \
    ln -s $FLYWAY_HOME/flyway /usr/local/bin/flyway && \
    rm flyway-commandline-$FLYWAY_VERSION-linux-x64.tar.gz

WORKDIR /app

# Copia tudo da raiz do projeto, incluindo o pom.xml pai e todos os módulos
COPY . .

# Build do módulo registrations
RUN mvn clean install -U -DskipTests -pl registrations -am

WORKDIR /app/registrations

# Variáveis de ambiente
ENV APPLICATION_ENVIRONMENT=local
ENV ENV_FILE=docker-NBQFC-3S0L1M3
ENV ENV_PATH=/app/registrations/env

ENV KEYCLOAK_URL=http://keycloak:8080
ENV KEYCLOAK_REALM=fixyourealm

EXPOSE 8080 8081 8082 8083 5005

CMD ["java", "-jar", "target/registrations-1.0.0.jar"]

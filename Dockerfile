FROM openjdk:21-jdk-slim

ENV MAVEN_VERSION=3.9.1 \
    MAVEN_HOME=/opt/maven \
    M2_HOME=/opt/maven \
    FLYWAY_VERSION=9.16.0 \
    FLYWAY_HOME=/opt/flyway

# Instala Maven, Flyway e dependências necessárias
RUN apt-get update && \
    apt-get install -y wget libfreetype6 libfontconfig1 && \
    # Instala Maven
    wget -O apache-maven.tar.gz https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar -xzf apache-maven.tar.gz -C /opt && \
    mv /opt/apache-maven-${MAVEN_VERSION} ${MAVEN_HOME} && \
    ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn && \
    rm apache-maven.tar.gz && \
    # Instala Flyway
    wget -O flyway.tar.gz https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/${FLYWAY_VERSION}/flyway-commandline-${FLYWAY_VERSION}-linux-x64.tar.gz && \
    tar -xzf flyway.tar.gz -C /opt && \
    mv /opt/flyway-${FLYWAY_VERSION} ${FLYWAY_HOME} && \
    ln -s ${FLYWAY_HOME}/flyway /usr/local/bin/flyway && \
    rm flyway.tar.gz && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Define diretório da aplicação
WORKDIR /app

# Copia arquivos necessários para o build
COPY pom.xml .
COPY core /app/core
COPY registrations /app/registrations
COPY scales /app/scales

# Compila o projeto e ignora os testes
RUN mvn clean install -U -DskipTests

# Copia o JAR gerado para a raiz
COPY registrations/target/registrations-1.0.0.jar /app/registrations.jar

# Copia o .env para o local esperado
RUN mkdir -p /app/env
COPY .env /app/env/.env

# Define variáveis de ambiente padrão (pode ser sobrescrito no compose)
ENV APPLICATION_ENVIRONMENT=local \
    ENV_FILE=.env \
    ENV_PATH=/app/env \
    KEYCLOAK_URL=http://keycloak:8080 \
    KEYCLOAK_REALM=fixyourealm

EXPOSE 8080 8081 8082 8083 5005

# Comando padrão
CMD ["java", "-jar", "/app/registrations.jar"]

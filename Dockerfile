# Use a imagem base com OpenJDK 21
FROM openjdk:21-jdk-slim

# Variáveis de ambiente para Maven e Flyway
ENV MAVEN_VERSION=3.9.1 \
    MAVEN_HOME=/opt/maven \
    M2_HOME=/opt/maven \
    FLYWAY_VERSION=9.16.0 \
    FLYWAY_HOME=/opt/flyway

# Instalar Maven, Flyway CLI e dependências
RUN apt-get update && \
    apt-get install -y wget libfreetype6 libfontconfig1 && \
    wget -O apache-maven-${MAVEN_VERSION}-bin.tar.gz https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar -xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt && \
    mv /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    wget -O flyway-commandline-${FLYWAY_VERSION}-linux-x64.tar.gz https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/${FLYWAY_VERSION}/flyway-commandline-${FLYWAY_VERSION}-linux-x64.tar.gz && \
    tar -xzf flyway-commandline-${FLYWAY_VERSION}-linux-x64.tar.gz -C /opt && \
    mv /opt/flyway-${FLYWAY_VERSION} /opt/flyway && \
    ln -s /opt/flyway/flyway /usr/local/bin/flyway && \
    rm flyway-commandline-${FLYWAY_VERSION}-linux-x64.tar.gz

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR e o .env da raiz do projeto para dentro do contêiner
COPY api-fixyou.jar /app/api-fixyou.jar
COPY .env /app/.env

# Variáveis de ambiente
ENV APPLICATION_ENVIRONMENT=local
ENV ENV_FILE=docker-MBP-de-Samuel
ENV ENV_PATH=/app/.env

# Keycloak e portas
ENV KEYCLOAK_URL=http://keycloak:8080
ENV KEYCLOAK_REALM=fixyourealm

EXPOSE 8080 8081 8082 8083 5005

# Comando para iniciar a aplicação
CMD ["java", "-jar", "/app/api-fixyou.jar"]

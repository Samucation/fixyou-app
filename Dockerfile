FROM openjdk:21-jdk-slim

ENV MAVEN_VERSION=3.9.1 \
    MAVEN_HOME=/opt/maven \
    M2_HOME=/opt/maven \
    FLYWAY_VERSION=9.16.0 \
    FLYWAY_HOME=/opt/flyway

# Instala Maven e Flyway
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

# Define a pasta de trabalho final onde rodará o JAR
WORKDIR /app

# Copia o projeto
COPY pom.xml ./pom.xml
COPY core ./core
COPY registrations ./registrations
COPY scales ./scales
COPY fixyou-orchestrator ./fixyou-orchestrator

# Build do projeto (ignora testes)
RUN mvn clean install -U -DskipTests

# Copia o JAR gerado e o .env para o mesmo diretório
COPY fixyou-orchestrator/target/api-fixyou.jar ./api-fixyou.jar
COPY .env .env

EXPOSE 8080 8082

CMD ["java", "-jar", "api-fixyou.jar"]

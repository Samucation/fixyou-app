#!/bin/bash
set -e

# Aguarda o Keycloak estar pronto
echo "Aguardando Keycloak iniciar..."
until curl -s http://localhost:8080/health/ready | grep "UP"; do
  sleep 2
done

echo "Keycloak iniciado!"

# Login no Keycloak
/opt/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080 --realm master --user $KEYCLOAK_ADMIN --password $KEYCLOAK_ADMIN_PASSWORD

# Criar o Realm se não existir
if ! /opt/keycloak/bin/kcadm.sh get realms/amicred-realm > /dev/null 2>&1; then
  echo "Criando Realm 'amicred-realm'..."
  /opt/keycloak/bin/kcadm.sh create realms -s realm=amicred-realm -s enabled=true
else
  echo "Realm 'amicred-realm' já existe, ignorando..."
fi

# Criar o Client se não existir
if ! /opt/keycloak/bin/kcadm.sh get clients --realm amicred-realm | grep -q amicred-client-ext; then
  echo "Criando Client 'amicred-client-ext'..."
  /opt/keycloak/bin/kcadm.sh create clients --realm amicred-realm -s clientId=amicred-client-ext -s enabled=true \
    -s publicClient=true -s directAccessGrantsEnabled=true \
    -s 'redirectUris=["http://localhost:8083/secure-data-1", "http://localhost:8083/secure-data-2", "http://localhost:8083/swagger-ui/index.html"]'
else
  echo "Client 'amicred-client-ext' já existe, ignorando..."
fi

# Criar o Usuário se não existir
if ! /opt/keycloak/bin/kcadm.sh get users --realm amicred-realm | grep -q samucation; then
  echo "Criando Usuário 'samucation'..."
  /opt/keycloak/bin/kcadm.sh create users --realm amicred-realm -s username=samucation -s enabled=true
  USER_ID=$(/opt/keycloak/bin/kcadm.sh get users --realm amicred-realm --query username=samucation --fields id --format csv | tail -n1 | tr -d '"')

  # Definir senha para o usuário
  /opt/keycloak/bin/kcadm.sh set-password --realm amicred-realm --userid $USER_ID --new-password 123Mudar
else
  echo "Usuário 'samucation' já existe, ignorando..."
fi

echo "Configuração do Keycloak concluída!"

#!/bin/bash

APP_NAME="fixyou-keycloak"
PLAN_NAME="fixyou-linux-plan"
RESOURCE_GROUP="fixyou-rg"
IMAGE_NAME="fixyouregistry.azurecr.io/keycloak-m33:latest"
STORAGE_ACCOUNT="fixyoustorageXXXX"  # Substitua com o real
SHARE_NAME="keycloakdata"

az appservice plan create \
  --name $PLAN_NAME \
  --resource-group $RESOURCE_GROUP \
  --sku B1 \
  --is-linux

az webapp create \
  --resource-group $RESOURCE_GROUP \
  --plan $PLAN_NAME \
  --name $APP_NAME \
  --deployment-container-image-name $IMAGE_NAME

# Configurar variáveis de ambiente
az webapp config appsettings set \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --settings \
  KEYCLOAK_ADMIN=admin \
  KEYCLOAK_ADMIN_PASSWORD=admin \
  KC_DB=postgres \
  KC_DB_URL="jdbc:postgresql://<seu-db>:5432/m33_db" \
  KC_DB_USERNAME=postgres \
  KC_DB_PASSWORD=SUASENHA \
  KC_HOSTNAME=auth.seudominio.com \
  KC_HOSTNAME_STRICT=false

# Montar o File Share como volume
az webapp config storage-account add \
  --resource-group $RESOURCE_GROUP \
  --name $APP_NAME \
  --custom-id realmvolume \
  --storage-type AzureFiles \
  --account-name $STORAGE_ACCOUNT \
  --share-name $SHARE_NAME \
  --access-key "$(az storage account keys list --resource-group $RESOURCE_GROUP --account-name $STORAGE_ACCOUNT --query "[0].value" -o tsv)" \
  --mount-path /opt/keycloak/data

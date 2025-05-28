#!/bin/bash

RESOURCE_GROUP="fixyou-rg"
STORAGE_ACCOUNT="fixyoustorage$(date +%s)"
SHARE_NAME="keycloakdata"
REALM_FILE="../fixyourealm-realm.json"

# 1. Criar Storage Account
az storage account create \
  --name $STORAGE_ACCOUNT \
  --resource-group $RESOURCE_GROUP \
  --location brazilsouth \
  --sku Standard_LRS

# 2. Obter chave de acesso
ACCOUNT_KEY=$(az storage account keys list \
  --resource-group $RESOURCE_GROUP \
  --account-name $STORAGE_ACCOUNT \
  --query "[0].value" -o tsv)

# 3. Criar File Share
az storage share create \
  --name $SHARE_NAME \
  --account-name $STORAGE_ACCOUNT \
  --account-key $ACCOUNT_KEY

# 4. Enviar fixyourealm-realm.json
az storage file upload \
  --account-name $STORAGE_ACCOUNT \
  --account-key $ACCOUNT_KEY \
  --share-name $SHARE_NAME \
  --source "$REALM_FILE" \
  --path "import/fixyourealm-realm.json"

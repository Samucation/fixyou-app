#!/bin/bash

# Sempre executar no diretório do próprio script
scriptDir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$scriptDir"

clear

echo "==========================================="
echo "========= SETUP PROJETO FIXYOU ============"
echo "==========================================="

# Nome da máquina
envName=$(hostname)
echo "Nome detectado do ambiente: $envName"

# Obter IP local
ip=$(hostname -I | awk '{print $1}')

if [ -z "$ip" ]; then
  echo "Nao foi possivel detectar o IP automaticamente."
  read -p "Informe manualmente o IP da sua maquina: " ip
fi

echo "IP detectado: $ip"

# Perguntar se deseja substituir os marcadores
read -p "Deseja substituir <local-ip> pelo IP $ip e gerar os arquivos? (Y/N) " replace

if [[ "$replace" =~ ^[YySs]$ ]]; then

    echo "Copiando arquivos originais para trabalho..."

    cp "$scriptDir/scripts-files/docker-compose.yml" "$scriptDir/docker-compose.yml"
    cp "$scriptDir/scripts-files/Dockerfile" "$scriptDir/Dockerfile"

    echo "Substituindo <local-ip> nos arquivos docker.env e local.env..."

    # Verificar e criar pasta env dentro de registrations
    envFolder="$scriptDir/registrations/env"
    if [ ! -d "$envFolder" ]; then
      mkdir -p "$envFolder"
      echo "Criada pasta registrations/env"
    fi

    # Nome dos arquivos sem extensão no Dockerfile e Compose
    dockerFileName="docker-$envName"
    localFileName="local-$envName"

    dockerPath="$envFolder/$dockerFileName.env"
    localPath="$envFolder/$localFileName.env"

    # Gerar docker-nome.env
    dockerBase=$(<"$envFolder/docker.env")
    dockerNovo=${dockerBase//<local-ip>/$ip}
    echo -n "$dockerNovo" > "$dockerPath"
    echo "Criado: $dockerPath"

    # Gerar local-nome.env
    localBase=$(<"$envFolder/local.env")
    localNovo=${localBase//<local-ip>/$ip}
    echo -n "$localNovo" > "$localPath"
    echo "Criado: $localPath"

    # Alterar docker-compose.yml
    echo "Alterando docker-compose.yml..."

    sed -i.bak \
      -e "s/KC_HOSTNAME: *<local-ip>/KC_HOSTNAME: $ip/g" \
      -e "s/<local-ip>/$ip/g" \
      -e "s/ENV_FILE: *<docker-env-file>/ENV_FILE: $dockerFileName/g" \
      "$scriptDir/docker-compose.yml"
    rm "$scriptDir/docker-compose.yml.bak"

    # Alterar Dockerfile na linha do ENV_FILE e ENV_PATH
    echo "Alterando Dockerfile..."

    sed -i.bak \
      -e "s/^ENV ENV_FILE=.*/ENV ENV_FILE=$dockerFileName/" \
      -e "s/^ENV ENV_PATH=.*/ENV ENV_PATH=\/app\/env/" \
      "$scriptDir/Dockerfile"
    rm "$scriptDir/Dockerfile.bak"

else
    echo "Pulando substituição dos IPs e geração dos arquivos."
fi

# Verificar e criar a network 'fixyou-network' se não existir
echo ""
echo "Verificando se a rede Docker 'fixyou-network' existe..."
if ! docker network ls --format '{{.Name}}' | grep -q '^fixyou-network$'; then
  echo "Rede 'fixyou-network' não existe. Criando..."
  docker network create fixyou-network > /dev/null
  echo "Rede 'fixyou-network' criada com sucesso."
else
  echo "Rede 'fixyou-network' já existe. Nenhuma ação necessária."
fi

echo "==========================================="
echo "SETUP FINALIZADO COM SUCESSO"
echo "==========================================="
echo ""
echo "Agora execute:"
echo "docker-compose up --build"
echo ""
echo "Acesse o projeto via Docker na porta: http://localhost:8082"
echo "Acesse o projeto via IntelliJ na porta: http://localhost:8083"
echo ""

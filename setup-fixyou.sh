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

# Detectar IP no Linux (pega IP da rota padrão)
ip=$(ip route get 1.1.1.1 | awk '{print $7; exit}')

# Se não achou, solicita manualmente
if [ -z "$ip" ]; then
  echo "Nao foi possivel detectar o IP automaticamente."
  read -p "Informe manualmente o IP da sua maquina: " ip
fi

echo "IP detectado: $ip"

# Perguntar se deseja substituir os marcadores
read -p "Deseja substituir <local-ip> pelo IP $ip e gerar os arquivos .env e local.env? (Y/N) " replace

if [[ "$replace" =~ ^[YySs]$ ]]; then

    echo "Gerando arquivos .env e local.env na raiz do projeto a partir dos templates..."

    envTemplate="$scriptDir/application-docs/scripts-files/.env"
    localEnvTemplate="$scriptDir/application-docs/scripts-files/local.env"

    if [ ! -f "$envTemplate" ]; then
      echo "❌ Template .env não encontrado: $envTemplate"
      exit 1
    fi

    if [ ! -f "$localEnvTemplate" ]; then
      echo "❌ Template local.env não encontrado: $localEnvTemplate"
      exit 1
    fi

    envContent=$(<"$envTemplate")
    envFinal=${envContent//<local-ip>/$ip}
    echo -n "$envFinal" > "$scriptDir/.env"
    echo "✅ Arquivo .env criado com sucesso em: $scriptDir/.env"

    localEnvContent=$(<"$localEnvTemplate")
    localEnvFinal=${localEnvContent//<local-ip>/$ip}
    echo -n "$localEnvFinal" > "$scriptDir/local.env"
    echo "✅ Arquivo local.env criado com sucesso em: $scriptDir/local.env"

else
    echo "⚠️  Pulando substituição dos IPs e geração dos arquivos."
fi

# Verificar e criar a network 'fixyou-network' se não existir
echo ""
echo "Verificando se a rede Docker 'fixyou-network' existe..."
if ! docker network ls --format '{{.Name}}' | grep -q '^fixyou-network$'; then
  echo "Rede 'fixyou-network' não existe. Criando..."
  docker network create fixyou-network > /dev/null
  echo "✅ Rede 'fixyou-network' criada com sucesso."
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

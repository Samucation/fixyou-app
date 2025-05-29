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

# Detectar IP no macOS (Wi-Fi geralmente é en0, ethernet pode ser en1)
ip=$(ipconfig getifaddr en0 2>/dev/null)

# Se não achou no en0, tenta en1
if [ -z "$ip" ]; then
  ip=$(ipconfig getifaddr en1 2>/dev/null)
fi

# Se ainda não achou, solicita manualmente
if [ -z "$ip" ]; then
  echo "Nao foi possivel detectar o IP automaticamente."
  read -p "Informe manualmente o IP da sua maquina: " ip
fi

echo "IP detectado: $ip"

# Perguntar se deseja substituir os marcadores
read -p "Deseja substituir <local-ip> pelo IP $ip e gerar o arquivo .env? (Y/N) " replace

if [[ "$replace" =~ ^[YySs]$ ]]; then

    echo "Gerando arquivo .env na raiz do projeto a partir do template..."

    # Caminho do template base
    templatePath="$scriptDir/application-docs/scripts-files/.env"

    if [ ! -f "$templatePath" ]; then
      echo "❌ Template .env não encontrado em: $templatePath"
      exit 1
    fi

    # Substituir <local-ip> pelo IP real
    envContent=$(<"$templatePath")
    envFinal=${envContent//<local-ip>/$ip}

    # Salvar na raiz do projeto
    echo -n "$envFinal" > "$scriptDir/.env"
    echo "✅ Arquivo .env criado com sucesso em: $scriptDir/.env"

else
    echo "⚠️  Pulando substituição dos IPs e geração do arquivo .env."
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

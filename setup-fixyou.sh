#!/bin/bash

# Sempre executar no diretório do próprio script
scriptDir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$scriptDir"

# Caminho do arquivo base com <local-ip>
baseEnvPath="$scriptDir/../application-docs/scripts-files/.env"
# Caminho do arquivo .env final que será gerado na raiz do projeto
finalEnvPath="$scriptDir/../.env"

clear
echo "==========================================="
echo "======== SETUP .env DO FIXYOU ============="
echo "==========================================="

# Detecta o IP local
ip=$(hostname -I | awk '{print $1}')

if [ -z "$ip" ]; then
  echo "❌ Não foi possível detectar o IP automaticamente."
  read -p "Informe manualmente o IP da sua máquina: " ip
fi

echo "✅ IP detectado: $ip"

# Confirmação
read -p "Deseja substituir <local-ip> pelo IP $ip e gerar o arquivo .env? (Y/N) " confirm

if [[ "$confirm" =~ ^[YySs]$ ]]; then
  echo "🔄 Gerando novo .env..."

  # Faz a substituição e escreve o arquivo final
  content=$(<"$baseEnvPath")
  updated=${content//<local-ip>/$ip}

  echo "$updated" > "$finalEnvPath"

  echo "✅ Arquivo .env gerado em: $finalEnvPath"
else
  echo "⚠️ Operação cancelada. Nenhum arquivo foi alterado."
fi

echo ""
echo "==========================================="
echo "SETUP FINALIZADO"
echo "==========================================="

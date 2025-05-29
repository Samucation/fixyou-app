# Caminho do diretório onde está este script
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $scriptDir

Write-Host "==========================================="
Write-Host "======= SETUP .env DO FIXYOU (Windows) ====="
Write-Host "==========================================="

# Caminho do arquivo base com <local-ip>
$baseEnvPath = Join-Path $scriptDir "..\application-docs\scripts-files\.env"
# Caminho final onde será gerado o novo .env
$finalEnvPath = Join-Path $scriptDir "..\.env"

# Obter IP local da máquina
$ip = (Get-NetIPAddress -AddressFamily IPv4 `
      | Where-Object { $_.IPAddress -notmatch '^(127|169\.254)\.' -and $_.InterfaceAlias -notmatch 'Loopback' } `
      | Select-Object -First 1 -ExpandProperty IPAddress)

if (-not $ip) {
    Write-Warning "❌ Não foi possível detectar o IP automaticamente."
    $ip = Read-Host "Informe manualmente o IP da sua máquina"
}

Write-Host "✅ IP detectado: $ip"

# Confirma substituição
$confirm = Read-Host "Deseja substituir <local-ip> pelo IP $ip e gerar o arquivo .env? (Y/N)"

if ($confirm -match '^[YySs]$') {
    Write-Host "🔄 Gerando novo .env..."

    # Lê conteúdo do arquivo base
    $content = Get-Content $baseEnvPath -Raw
    $updated = $content -replace '<local-ip>', $ip

    # Grava o novo .env na raiz
    $updated | Set-Content $finalEnvPath -Encoding UTF8

    Write-Host "✅ Arquivo .env gerado em: $finalEnvPath"
} else {
    Write-Host "⚠️ Operação cancelada. Nenhum arquivo foi alterado."
}

Write-Host ""
Write-Host "==========================================="
Write-Host "SETUP FINALIZADO"
Write-Host "==========================================="

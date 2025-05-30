# Sempre executar no diretório do próprio script
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location -Path $scriptDir

Clear-Host

Write-Host "==========================================="
Write-Host "========= SETUP PROJETO FIXYOU ============"
Write-Host "==========================================="

# Função para salvar arquivo como UTF-8 sem BOM
function Save-FileUtf8NoBom {
    param (
        [string]$Path,
        [string]$Content
    )

    $utf8NoBom = New-Object System.Text.UTF8Encoding $false
    [System.IO.File]::WriteAllText($Path, $Content, $utf8NoBom)
}

# Obter IP local
$ip = (Get-NetIPConfiguration | Where-Object { $_.IPv4DefaultGateway -ne $null } | Select-Object -First 1).IPv4Address.IPAddress

if (-not $ip) {
    Write-Host "Nao foi possivel detectar o IP automaticamente."
    $ip = Read-Host "Informe manualmente o IP da sua maquina"
}

Write-Host "IP detectado: $ip"

# Perguntar se deseja substituir os marcadores
$replace = Read-Host "Deseja substituir <local-ip> pelo IP $ip e gerar os arquivos? (Y/N)"

if ($replace -match "^(Y|y|S|s)$") {
    Write-Host "Copiando arquivos de template para a raiz do projeto..."
    Write-Host "Substituindo <local-ip> nos arquivos e copiando para a raiz..."

    $envTemplate = "$scriptDir\application-docs\scripts-files\.env"
    $localEnvTemplate = "$scriptDir\application-docs\scripts-files\local.env"
    $dockerComposeTemplate = "$scriptDir\application-docs\scripts-files\docker-compose.yml"
    $dockerfileTemplate = "$scriptDir\application-docs\scripts-files\Dockerfile"

    $envDest = "$scriptDir\.env"
    $localEnvDest = "$scriptDir\local.env"
    $dockerComposeDest = "$scriptDir\docker-compose.yml"
    $dockerfileDest = "$scriptDir\Dockerfile"

    # Gera .env e local.env substituindo <local-ip>
    Save-FileUtf8NoBom -Path $envDest -Content ((Get-Content $envTemplate -Raw) -replace '<local-ip>', "$ip")
    Save-FileUtf8NoBom -Path $localEnvDest -Content ((Get-Content $localEnvTemplate -Raw) -replace '<local-ip>', "$ip")
    Save-FileUtf8NoBom -Path $dockerComposeDest -Content ((Get-Content $dockerComposeTemplate -Raw) -replace '<local-ip>', "$ip")
    Save-FileUtf8NoBom -Path $dockerfileDest -Content ((Get-Content $dockerfileTemplate -Raw) -replace '<local-ip>', "$ip")

    Write-Host "Arquivos .env e local.env gerados com sucesso na raiz do projeto."
} else {
    Write-Host "Pulando substituição dos IPs."
}

# Verificar e criar a network 'fixyou-network' se não existir
Write-Host ""
Write-Host "Verificando se a rede Docker 'fixyou-network' existe..."

$networkExists = docker network ls --format '{{.Name}}' | Where-Object { $_ -eq 'fixyou-network' }

if (-not $networkExists) {
    Write-Host "Rede 'fixyou-network' não existe. Criando..."
    docker network create fixyou-network | Out-Null
    Write-Host "Rede 'fixyou-network' criada com sucesso."
} else {
    Write-Host "Rede 'fixyou-network' já existe. Nenhuma ação necessária."
}

Write-Host "==========================================="
Write-Host "SETUP FINALIZADO COM SUCESSO"
Write-Host "==========================================="
Write-Host ""
Write-Host "Agora execute:"
Write-Host "docker-compose up --build"
Write-Host ""
Write-Host "Acesse o projeto via Docker na porta: http://localhost:8082"
Write-Host "Acesse o projeto via IntelliJ na porta: http://localhost:8083"
Write-Host ""
Pause

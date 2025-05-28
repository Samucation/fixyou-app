# Sempre executar no diretório do próprio script
$scriptDir = (Split-Path -Parent $MyInvocation.MyCommand.Definition)
Set-Location -Path $scriptDir

Clear-Host

Write-Host "==========================================="
Write-Host "========= SETUP PROJETO FIXYOU ============"
Write-Host "==========================================="

# Nome da máquina
$envName = $env:COMPUTERNAME
Write-Host "Nome detectado do ambiente: $envName"

# Obter IP local
$ip = (Get-NetIPConfiguration | Where-Object {
    $_.IPv4DefaultGateway -ne $null
} | Select-Object -First 1).IPv4Address.IPAddress

if (!$ip) {
    Write-Host "Nao foi possivel detectar o IP automaticamente."
    $ip = Read-Host "Informe manualmente o IP da sua maquina"
}

Write-Host "IP detectado: $ip"

# Perguntar se deseja substituir os marcadores
$ipPergunta = "Deseja substituir <local-ip> pelo IP $ip e gerar os arquivos? (Y/N)"
$replace = Read-Host $ipPergunta

if ($replace -match "^(Y|y|S|s)$") {

    Write-Host "Copiando arquivos originais para trabalho..."

    Copy-Item -Path "$scriptDir\scripts-files\docker-compose.yml" -Destination "$scriptDir\docker-compose.yml" -Force
    Copy-Item -Path "$scriptDir\scripts-files\Dockerfile" -Destination "$scriptDir\Dockerfile" -Force

    Write-Host "Substituindo <local-ip> nos arquivos docker.env e local.env..."

    # Verificar e criar pasta env dentro de registrations
    $envFolder = Join-Path $scriptDir "registrations\env"
    if (!(Test-Path -Path $envFolder)) {
        New-Item -ItemType Directory -Path $envFolder | Out-Null
        Write-Host "Criada pasta registrations/env"
    }

    # Nome dos arquivos sem extensão no Dockerfile e no Compose
    $dockerFileName = "docker-$envName"
    $localFileName = "local-$envName"

    # Caminhos absolutos para escrita
    $dockerPath = Join-Path $envFolder "$dockerFileName.env"
    $localPath = Join-Path $envFolder "$localFileName.env"

    # Gerar docker-nome.env
    $dockerBase = Get-Content "$envFolder\docker.env" -Raw
    $dockerNovo = $dockerBase -replace '<local-ip>', $ip
    [System.IO.File]::WriteAllText($dockerPath, $dockerNovo, (New-Object System.Text.UTF8Encoding($false)))
    Write-Host "Criado: $dockerPath"

    # Gerar local-nome.env
    $localBase = Get-Content "$envFolder\local.env" -Raw
    $localNovo = $localBase -replace '<local-ip>', $ip
    [System.IO.File]::WriteAllText($localPath, $localNovo, (New-Object System.Text.UTF8Encoding($false)))
    Write-Host "Criado: $localPath"

    # Alterar docker-compose.yml
    Write-Host "Alterando docker-compose.yml..."

    (Get-Content "$scriptDir\docker-compose.yml" -Raw) `
    -replace 'KC_HOSTNAME:\s*<local-ip>', "KC_HOSTNAME: $ip" `
    -replace '<local-ip>', "$ip" `
    -replace 'ENV_FILE:\s*<docker-env-file>', "ENV_FILE: $dockerFileName" | `
    Set-Content "$scriptDir\docker-compose.yml" -Encoding UTF8

    # Alterar Dockerfile na linha do ENV_FILE
    Write-Host "Alterando ENV_FILE no Dockerfile..."

    $dockerfileLines = Get-Content "$scriptDir\Dockerfile"
    $dockerfileLines = $dockerfileLines | ForEach-Object {
        if ($_ -match '^ENV ENV_FILE=' -or $_ -match '^ENV ENV_FILE=<docker-env-file>') {
            "ENV ENV_FILE=$dockerFileName"
        } elseif ($_ -match '^ENV ENV_PATH=') {
            "ENV ENV_PATH=/app/env"
        } else {
            $_
        }
    }
    $dockerfileLines | Set-Content "$scriptDir\Dockerfile" -Encoding UTF8

} else {
    Write-Host "Pulando substituição dos IPs e geração dos arquivos."
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

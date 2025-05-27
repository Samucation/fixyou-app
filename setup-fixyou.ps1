Clear-Host

Write-Host "==========================================="
Write-Host "========= SETUP PROJETO FIXYOU ============"
Write-Host "==========================================="

# Nome da maquina
$envName = $env:COMPUTERNAME
Write-Host "Nome detectado do ambiente: $envName"

# Obter IP local da interface com Gateway
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

    Copy-Item -Path ".\scripts-files\docker-compose.yml" -Destination ".\docker-compose.yml" -Force
    Copy-Item -Path ".\scripts-files\Dockerfile" -Destination ".\Dockerfile" -Force

    Write-Host "Substituindo <local-ip> nos arquivos docker.env e local.env..."

    # Verificar pasta env
    if (!(Test-Path -Path ".\env")) {
        New-Item -ItemType Directory -Path ".\env" | Out-Null
    }

    # Gerar docker-nome.env
    $dockerBase = Get-Content .\env\docker.env -Raw
    $dockerNovo = $dockerBase -replace '<local-ip>', $ip
    $dockerFileName = "docker-$envName.env"
    $dockerNovo | Out-File -FilePath ".\env\$dockerFileName" -Encoding UTF8 -Force
    Write-Host "Criado: env\$dockerFileName"

    # Gerar local-nome.env
    $localBase = Get-Content .\env\local.env -Raw
    $localNovo = $localBase -replace '<local-ip>', $ip
    $localFileName = "local-$envName.env"
    $localNovo | Out-File -FilePath ".\env\$localFileName" -Encoding UTF8 -Force
    Write-Host "Criado: env\$localFileName"

    # Alterar docker-compose.yml
    Write-Host "Alterando docker-compose.yml..."

    (Get-Content docker-compose.yml -Raw) `
    -replace 'KC_HOSTNAME:\s*<local-ip>', "KC_HOSTNAME: $ip" `
    -replace '<local-ip>', "$ip" `
    -replace 'ENV_FILE:\s*<docker-env-file>', "ENV_FILE: $dockerFileName" | `
    Set-Content docker-compose.yml -Encoding UTF8

    # Alterar Dockerfile SOMENTE NA LINHA DO ENV_FILE
    Write-Host "Alterando ENV_FILE no Dockerfile..."

    $dockerfileLines = Get-Content Dockerfile
    $dockerfileLines = $dockerfileLines | ForEach-Object {
        if ($_ -match '^ENV ENV_FILE=' -or $_ -match '^ENV ENV_FILE=<docker-env-file>') {
            "ENV ENV_FILE=$dockerFileName"
        } else {
            $_
        }
    }
    $dockerfileLines | Set-Content Dockerfile -Encoding UTF8

} else {
    Write-Host "Pulando substituição dos IPs e geração dos arquivos."
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

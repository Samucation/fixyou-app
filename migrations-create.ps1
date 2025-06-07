# Caminho para o arquivo .env na raiz do projeto
$envPath = "$PSScriptRoot\local.env"

# Verifica se o arquivo existe
if (-Not (Test-Path $envPath)) {
    Write-Error "Arquivo .env não encontrado em $envPath"
    exit 1
}

# Carrega e exporta as variáveis do .env
Get-Content $envPath | ForEach-Object {
    if ($_ -match '^\s*#') { return }  # Ignora comentários
    if ($_ -match '^\s*$') { return } # Ignora linhas vazias

    $pair = $_ -split '=', 2
    if ($pair.Length -eq 2) {
        $key = $pair[0].Trim()
        $value = $pair[1].Trim()
        Set-Item -Path "Env:$key" -Value $value
    }
}

# Executa a migration com Maven no módulo orchestrator
Write-Host "Executando Flyway migrations no módulo fixyou-orchestrator..."
Push-Location "$PSScriptRoot\fixyou-orchestrator"
mvn flyway:migrate
Pop-Location

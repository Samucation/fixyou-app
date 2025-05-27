
# 🚀 Setup do Projeto FixYou

## 🛠️ Sobre o Script de Setup

Este projeto possui um script chamado `setup-fixyou.ps1` que automatiza toda a configuração do ambiente local.  
Ele faz:

- Detecta automaticamente o **nome da sua máquina** e o **seu IP local**.
- Substitui o placeholder `<local-ip>` nos arquivos:
    - `docker-compose.yml`
    - `Dockerfile`
    - Arquivos `.env`
- Cria arquivos de ambiente personalizados:
    - `env/docker-NOMEDAMAQUINA.env` (para rodar no Docker)
    - `env/local-NOMEDAMAQUINA.env` (para rodar local via IntelliJ ou qualquer IDE)
- Atualiza automaticamente:
    - `KC_HOSTNAME`
    - `KEYCLOAK_URL`
    - `ENV_FILE` (no Dockerfile e docker-compose)

✅ Sempre usa como base os arquivos originais que estão na pasta `/scripts-files`.

---

## ⚙️ Como Rodar o Script

### ✔️ Passo 1 – Permitir execução de scripts PowerShell

Abra o PowerShell como **Administrador** e execute:

```powershell
Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned
```

👉 Isso libera a execução de scripts no seu ambiente local.

---

### ✔️ Passo 2 – Executar o script de setup

Navegue até a pasta do projeto:

```powershell
cd C:\CAMINHO\DO\SEU\PROJETO
```

Execute o script:

```powershell
.\setup-fixyou.ps1
```

---

## 🔥 O que acontece quando você executa:

- Os arquivos `docker-compose.yml` e `Dockerfile` são **gerados** com base nos arquivos da pasta `/scripts-files`.
- Placeholders como `<local-ip>` e `<docker-env-file>` são substituídos automaticamente.
- Arquivos `.env` são gerados automaticamente:

```
env/docker-NOMEDAMAQUINA.env
env/local-NOMEDAMAQUINA.env
```

- As variáveis de IP e nome do ambiente são atualizadas.

---

## 🐳 Subindo os Containers

Após rodar o setup, execute:

```powershell create network of project
 docker network create fixyou-network
 ```

```powershell create project images
docker-compose up --build
```

---

## 🌐 Acessos do Projeto

| Serviço      | URL                                    |
| -------------|----------------------------------------|
| Aplicação    | http://localhost:8082                 |
| Aplicação IDE| http://localhost:8083                 |
| Keycloak     | http://SEU_IP_LOCAL:8080              |

---

## ♻️ Resetando o ambiente

Se quiser redefinir tudo:

- Apenas rode novamente o script:

```powershell
.\setup-fixyou.ps1
```

- Ele vai sobrescrever:
    - `docker-compose.yml`
    - `Dockerfile`
    - Arquivos `.env`

**Baseando-se sempre nos arquivos limpos da pasta `/scripts-files`.**

---

## ✅ Observação Importante

✔️ O script **NÃO ALTERA PERMANENTEMENTE** os templates originais.  
✔️ Você pode rodar quantas vezes quiser, sempre partindo de arquivos limpos da pasta `/scripts-files`.

---

Feito com ❤️ para automação total do ambiente FixYou.

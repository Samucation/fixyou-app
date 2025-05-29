
# 🐳 Guia Docker - Projeto FixYou

Passos para rodar todo o ambiente via Docker.

---

## ⚙️ Primeiro passo: Executar o Setup do Ambiente

Antes de subir os containers, é necessário gerar os arquivos de variáveis de ambiente.

Execute no PowerShell, na raiz do projeto:

```powershell
Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned
./setup-fixyou.ps1
```

Esse script irá criar na pasta `/env` um arquivo com o nome:

```
local-NOME-DA-SUA-MAQUINA.env
```

Por exemplo:

```
local-DESKTOP-1234.env
```

Esse arquivo contém as configurações necessárias para o funcionamento da aplicação, banco de dados e Keycloak.

---

## 🚀 Subindo os containers Docker

Na raiz do projeto, execute:

```bash
docker compose up
```

O Docker irá subir automaticamente:

- Aplicação Spring Boot (FixYou)
- Banco de Dados PostgreSQL
- Keycloak com Realm e configurações pré-carregadas

> 🔥 O ambiente estará pronto sem necessidade de configurar manualmente o Keycloak ou o banco.

Acesse o Keycloak para validação, se desejar:

- URL: [http://localhost:8080](http://localhost:8080)
- **Usuário:** `admin`
- **Senha:** `admin`

---

## 🔑 Usuários padrão do ambiente

O ambiente já possui dois usuários configurados para geração de token no Keycloak:

- **admfixyou** – Permissão total sobre todos os endpoints.
- **userfixyou** – Permissão restrita, pode apenas buscar usuários por nome.

**Senha de ambos:** `123Mudar`

---

## 🔗 Obtendo o Token de Acesso (Bearer Token)

Você pode gerar seu token de duas maneiras:

### ✔️ Usando o Postman

Na pasta `/external-util` existe um arquivo de backup do Postman com as collections prontas para:

- Gerar tokens no Keycloak;
- Testar os endpoints da aplicação.

> ⚠️ **Importante:**  
Após importar a collection no Postman, ajuste o **endereço IP nas variáveis ou requests**, pois ele precisa ser o **IP local (IPv4)** da sua máquina, o mesmo que foi usado para gerar o arquivo `.env`.

Para descobrir seu IP no Windows:

```powershell
ipconfig
```

Use o valor de **Endereço IPv4**.

### ✔️ Usando cURL

Exemplo de geração de token com cURL:

```bash
curl --location --request POST 'http://localhost:8080/realms/fixyourealm/protocol/openid-connect/token' --header 'Content-Type: application/x-www-form-urlencoded' --data-urlencode 'client_id=immotus-app' --data-urlencode 'client_secret=SEU_CLIENT_SECRET' --data-urlencode 'grant_type=password' --data-urlencode 'username=admfixyou' --data-urlencode 'password=123Mudar'
```

A resposta conterá o campo `access_token`, que deve ser usado nos endpoints protegidos da aplicação.

---

## 🏃‍♂️ Verificando se a aplicação está no ar

Acesse a URL:

[http://localhost:8083/swagger-ui/index.html#/user-controller/isServerLive](http://localhost:8083/swagger-ui/index.html#/user-controller/isServerLive)

Se retornar "true", a aplicação está rodando corretamente.

---

## 🚦 Para desligar os containers

Execute:

```bash
docker compose down
```

---

## ✅ Ambiente Docker operacional! 🎉

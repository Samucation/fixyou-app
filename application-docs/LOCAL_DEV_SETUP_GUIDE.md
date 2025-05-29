
# 💻 Guia de Ambiente Local (IntelliJ + Docker DB/Keycloak)

## ⚙️ Primeiro passo: Executar o Setup do Ambiente

Antes de qualquer coisa, **execute o script de configuração do ambiente**. Esse script cria automaticamente os arquivos de variáveis de ambiente necessários, incluindo o arquivo `.env` específico da sua máquina.

### ⚠️ Permissão para executar scripts no PowerShell

Para conseguir executar o script no PowerShell, é necessário garantir que sua política de execução permita rodar scripts locais.

Execute o seguinte comando no PowerShell **antes de rodar o setup**:

```powershell
Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned
```

> 🔐 Esse comando permite que você execute scripts locais que são assinados ou que você mesmo criou. Não se preocupe, ele só altera a política para o seu usuário atual.

### Executando o setup:

Na raiz do projeto, execute:

```powershell
./setup-fixyou.ps1
```

O script irá gerar dentro da pasta `env` um arquivo com o seguinte padrão de nome:

```
local-NOME-DA-SUA-MAQUINA.env
```

Por exemplo, se sua máquina se chama `DESKTOP-1234`, o arquivo gerado será:

```
local-DESKTOP-1234.env
```

Esse arquivo contém as configurações do banco de dados, Keycloak e outras variáveis de ambiente necessárias para rodar a aplicação corretamente.

---

## 🚀 Rodando a aplicação inteira via Docker

Se você deseja rodar a aplicação apenas para testes, é possível executá-la totalmente via Docker. Isso significa que o banco de dados, a aplicação e o Keycloak estarão todos rodando em containers.

### ✅ Pré-requisitos
- Docker instalado na sua máquina (Windows, Linux ou Mac).
- Docker Compose instalado (ou utilize `docker compose` diretamente se sua versão suportar).

### 🔧 Executando a aplicação
Na raiz do projeto, execute o seguinte comando:

```bash
docker compose up
```

Se tudo ocorrer corretamente, a aplicação, o banco de dados e o Keycloak serão configurados automaticamente pelos containers.

> ⚠️ **Observação:** O Keycloak é restaurado automaticamente a partir de um backup que já inclui o Realm, Clients, Usuários e Roles necessários. Portanto, você não precisa configurar o Keycloak manualmente.

Caso queira acessar o painel administrativo do Keycloak, utilize:

- URL: [http://localhost:8080](http://localhost:8080)
- **Usuário:** `admin`
- **Senha:** `admin`

---

## 🔧 Configurando o IntelliJ

Para rodar a aplicação localmente pelo IntelliJ, é necessário configurar as seguintes variáveis de ambiente na configuração de execução (**Run/Debug Configurations**):

```
APPLICATION_ENVIRONMENT=local
ENV_PATH=env
ENV_FILE=local-NOME-DA-SUA-MAQUINA
```

> ⚠️ **Importante:**  
O valor de `ENV_FILE` corresponde ao nome do arquivo `.env` que foi gerado na pasta `/env` após rodar o script `setup-fixyou.ps1`.

---

## 🔑 Obtendo o Token de Acesso (Bearer Token)

Para acessar os endpoints da aplicação protegidos, você precisará de um token de acesso. Utilize uma ferramenta como o **Postman** ou cURL para gerar esse token.

### 👥 Usuários já cadastrados no Keycloak

O ambiente já vem configurado com dois usuários padrão para geração de token:

- **admfixyou** – Permissão total para todos os endpoints.
- **userfixyou** – Permissão restrita, apenas para buscar usuários por nome.

**Senha de ambos:** `123Mudar`

### 📦 Backup do Postman
Na pasta `/external-util` na raiz do projeto, existe um arquivo de backup do Postman contendo todas as collections prontas para:

- Gerar o token do Keycloak;
- Testar os endpoints da aplicação.

Basta importar esse arquivo no Postman para facilitar suas requisições.

> ⚠️ **Atenção:**  
Após importar a collection, será necessário **ajustar o endereço IP** nas variáveis ou nos requests do Postman.  
As collections utilizam, por padrão, o **IP local da máquina (seu IPv4)**. Esse IP é o mesmo utilizado automaticamente na geração do arquivo `.env` após rodar o script `setup-fixyou.ps1`.

Para descobrir seu IP local (IPv4) no Windows, execute no terminal:

```powershell
ipconfig
```

E utilize o valor que aparece em **"Endereço IPv4"**.

### 🧠 Exemplo de requisição via cURL:

```bash
curl --location --request POST 'http://localhost:8080/realms/fixyourealm/protocol/openid-connect/token' --header 'Content-Type: application/x-www-form-urlencoded' --data-urlencode 'client_id=immotus-app' --data-urlencode 'client_secret=SEU_CLIENT_SECRET' --data-urlencode 'grant_type=password' --data-urlencode 'username=admfixyou' --data-urlencode 'password=123Mudar'
```

A resposta conterá um campo `access_token`. Utilize esse token para autenticação nos endpoints da aplicação.

---

## 🔗 Acessando a aplicação

Após iniciar os containers e obter o token, acesse a URL abaixo para verificar se a aplicação está rodando:

[http://localhost:8083/swagger-ui/index.html#/user-controller/isServerLive](http://localhost:8083/swagger-ui/index.html#/user-controller/isServerLive)

Se esse endpoint responder corretamente, sua aplicação está no ar! 🎉

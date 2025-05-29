
# ⚙️ Guia de Configuração de Ambiente

Resumo das configurações necessárias para o ambiente do projeto **FixYou**, incluindo:

- **docker-compose.yml**
- **Dockerfile**
- **Arquivos `.env`**
- **Configuração de ambiente no IntelliJ**

---

## 📦 docker-compose.yml

O arquivo `docker-compose.yml` na raiz do projeto é responsável por subir:

- A aplicação Spring Boot (FixYou)
- Banco de Dados PostgreSQL
- Keycloak (com backup automático de Realm, Clients e Usuários)

> ✅ O Keycloak é restaurado automaticamente utilizando o volume com o backup incluído no projeto.

### 🚪 Portas utilizadas:

| Serviço     | Porta |
|--------------|-------|
| Aplicação    | 8080  |
| Keycloak     | 8081  |
| PostgreSQL   | 5432  |

Se rodar localmente via IntelliJ (modo desenvolvimento), a aplicação usará a porta `8083`.

---

## 🐳 Dockerfile

O `Dockerfile` configura a imagem da aplicação Spring Boot.

### 🔧 Principais etapas do Dockerfile:

- Instala o JDK.
- Copia o arquivo `.war` da aplicação.
- Define as variáveis de ambiente necessárias.
- Executa o serviço com:

```bash
java -jar target/fixyou-app.war
```

> 🎯 O Dockerfile espera que o build da aplicação tenha sido feito antes (**`mvn clean install`**).

---

## 🗂️ Arquivos .env

Os arquivos `.env` são fundamentais para configurar o ambiente.

- A pasta `/env` na raiz do projeto armazena esses arquivos.
- Eles são gerados automaticamente ao executar o script:

```powershell
./setup-fixyou.ps1
```

### 🏷️ Formato do arquivo gerado:

```
local-NOME-DA-SUA-MAQUINA.env
```

> ⚠️ Esse arquivo define variáveis como IP da máquina, URL do banco de dados, URL do Keycloak, usuários, senhas e configurações necessárias para que a aplicação funcione tanto no modo Docker quanto no modo desenvolvimento (IntelliJ).

### 🔑 Variáveis importantes:

| Variável                  | Descrição                                           |
|---------------------------|-----------------------------------------------------|
| APPLICATION_ENVIRONMENT    | Define o ambiente (`local`)                        |
| ENV_PATH                   | Caminho da pasta `env`                             |
| ENV_FILE                   | Nome do arquivo `.env` gerado                      |
| JDBC_DATABASE_URL          | URL de conexão com o banco                         |
| JDBC_DATABASE_USERNAME     | Usuário do banco                                   |
| JDBC_DATABASE_PASSWORD     | Senha do banco                                     |
| KEYCLOAK_AUTH_SERVER_URL   | URL do servidor Keycloak                           |
| KEYCLOAK_REALM             | Realm configurado (`Fixyou-realm`)                 |
| KEYCLOAK_CLIENT_ID         | Client ID (`Fixyou-client-ext`)                    |
| KEYCLOAK_CLIENT_SECRET     | Client Secret gerado no Keycloak                   |

---

## 🔧 Configurando as Variáveis de Ambiente no IntelliJ

Para rodar a aplicação localmente pelo IntelliJ, é necessário adicionar as seguintes variáveis na configuração de execução (**Run/Debug Configurations**):

```
APPLICATION_ENVIRONMENT=local
ENV_PATH=env
ENV_FILE=local-NOME-DA-SUA-MAQUINA
```

### ⚠️ **Muito Importante:**

Se você **não definir `APPLICATION_ENVIRONMENT=local`**, o sistema **ignora completamente o carregamento do arquivo `.env` via dotenv**. Nesse caso, a aplicação tentará buscar as variáveis diretamente do ambiente do sistema operacional onde ela estiver rodando.

Portanto, **sempre defina `APPLICATION_ENVIRONMENT=local`** no IntelliJ ou qualquer ambiente de desenvolvimento para garantir que o carregamento do `.env` funcione corretamente.

---

## 🚀 Conclusão

- Execute sempre o script `setup-fixyou.ps1` antes de subir o ambiente.
- Verifique o IP utilizado nas variáveis, na aplicação e no Postman.
- O Docker, IntelliJ e os arquivos `.env` trabalham juntos para fornecer um ambiente 100% funcional tanto para desenvolvimento quanto para testes.

✅ Ambiente configurado e pronto para rodar!

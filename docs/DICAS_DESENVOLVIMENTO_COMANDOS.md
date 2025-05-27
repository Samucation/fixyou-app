
# 💡 Dicas de Desenvolvimento (Comandos Importantes)

Comandos úteis e essenciais para o desenvolvimento e manutenção do projeto **FixYou**.

---

## 🛠️ Migrações Flyway

### ➕ Rodar migrações do banco de dados:

Na raiz do projeto, execute:

```bash
mvn flyway:migrate
```

### 🔄 Resetar ou reparar migrações:

```bash
mvn flyway:repair
```

> ✅ Útil para corrigir problemas em versões quebradas de migrações no banco de dados.

---

## ⚙️ Instalação de Dependências (Sem rodar testes)

Para instalar as dependências e gerar o build do projeto **sem executar os testes**, execute:

```bash
mvn clean install -DskipTests
```

> 🚀 Economiza tempo no desenvolvimento quando você não precisa executar os testes.

---

## 🐳 Comandos Docker Essenciais

### 🔄 Recriar a imagem Docker da aplicação:

Se você alterou o código ou fez mudanças na aplicação, execute:

```bash
docker compose up --build
```

> Isso garante que a imagem da aplicação será reconstruída com as alterações mais recentes.

### 📦 Subindo a aplicação pela primeira vez via Docker:

```bash
docker compose up
```

---

## 🔐 Backup do Realm do Keycloak

Para gerar um backup local do Realm configurado no Keycloak, execute os seguintes comandos:

### ➤ Exportar o Realm do container Keycloak:

```bash
docker exec keycloak /opt/keycloak/bin/kc.sh export --dir=/opt/keycloak/data/export --realm=fixyourealm --users=realm_file
```

### ➤ Copiar o arquivo do container para sua máquina local:

```bash
docker cp keycloak:/opt/keycloak/data/export/fixyourealm-realm.json ./keycloak-data/import/
```

> 🔥 Esse backup pode ser usado para restaurar o Keycloak em outros ambientes ou após resetar o container.

---

## 🚀 Dica Extra

Se fizer mudanças nas configurações do Keycloak (Realms, Clients, Usuários, Roles), lembre-se sempre de gerar um novo backup usando os comandos acima para evitar perda de configuração.

---

✅ Com esses comandos você mantém seu ambiente de desenvolvimento saudável, atualizado e funcional! 🚀

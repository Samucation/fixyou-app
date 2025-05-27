
# 🛠️ Guia de Solução de Problemas

Resolução dos erros mais comuns no uso do ambiente Docker, Keycloak, IP e configurações da aplicação.

---

## 🚫 Problema: Keycloak não acessa via localhost

### 🔍 Causa:

- O Keycloak e a aplicação estão rodando em containers Docker.
- O Docker não entende `localhost` como sendo sua máquina, ele entende `localhost` como o próprio container.

### ✔️ Solução:

- Utilize o **IP local (IPv4)** da sua máquina.
- Para descobrir seu IP no Windows:

```powershell
ipconfig
```

Use o valor de **Endereço IPv4**.

> ⚠️ Esse é o mesmo IP que foi capturado pelo script `setup-fixyou.ps1`.

---

## 🚫 Problema: Postman não gera token

### 🔍 Causa:

- IP incorreto no endpoint.
- Client Secret errado.
- Realm, Client ID ou Credenciais incorretas.

### ✔️ Solução:

- Verifique se o IP utilizado na collection é o **IP local (IPv4)**.
- Verifique se o **Client Secret** usado é o mesmo gerado no Keycloak (Clients ➝ Credentials).
- Confirme que está usando os dados corretos:

```
Realm: Fixyou-realm
Client ID: Fixyou-client-ext
Usuário: admfixyou ou userfixyou
Senha: 123Mudar
```

---

## 🚫 Problema: Porta da aplicação não funciona

### 🔍 Causa:

- Confusão entre ambientes.

### ✔️ Verifique:

- Se está rodando via **Docker**, a aplicação está na porta:

```
http://localhost:8080
```

- Se está rodando via **IntelliJ (modo desenvolvimento)**, a aplicação está na porta:

```
http://localhost:8083
```

> ⚠️ O Keycloak **sempre** estará na porta `8081` quando rodando via Docker.

---

## 🚫 Problema: Erro ao executar script PowerShell (`setup-fixyou.ps1`)

### 🔍 Mensagem:

```
File cannot be loaded because running scripts is disabled on this system.
```

### ✔️ Solução:

Execute no PowerShell:

```powershell
Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned
```

Isso libera a execução de scripts no seu usuário.

---

## 🚫 Problema: Container não sobe ou dá erro de porta em uso

### ✔️ Solução:

- Verifique se você não tem outra aplicação rodando na mesma porta (`8080`, `8081` ou `8083`).
- Pare os containers existentes:

```bash
docker compose down
```

- Se necessário, mate processos na porta manualmente:

```powershell
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

---

## 🚫 Problema: Token funciona no Postman mas não no Swagger

### ✔️ Causas mais comuns:

- Token gerado para IP incorreto (verifique o IP utilizado).
- Não adicionou o token no Swagger corretamente.

### ✔️ Como adicionar o token no Swagger:

1. Clique em **Authorize** no topo do Swagger.
2. Insira no campo:

```
Bearer SEU_TOKEN
```

3. Clique em **Authorize**.

> Se funcionar no Postman, obrigatoriamente funciona no Swagger se o token e IP forem corretos.

---

## ✅ Dicas Gerais

- Sempre confira se o IP utilizado é o mesmo que aparece em `ipconfig`.
- Rode o script `setup-fixyou.ps1` sempre que trocar de rede Wi-Fi ou se mudar o IP local.
- Verifique se o Docker Desktop está em execução corretamente.

---

Se após esses passos o problema persistir, reinicie sua máquina e tente novamente. 🚀

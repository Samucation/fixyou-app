
# 🔑 Guia de Geração de Token no Keycloak

Passo a passo para gerar tokens via **Postman** ou **cURL**.

---

## ⚙️ Atenção ao Endereço IP!

O endereço `localhost` pode não funcionar em alguns casos, especialmente quando você está rodando a aplicação ou Keycloak via Docker.

> ⚠️ **Utilize sempre o IP local (IPv4) da sua máquina**, o mesmo que foi capturado e configurado automaticamente quando você executou o script:

```powershell
./setup-fixyou.ps1
```

Para descobrir seu IP local, rode no PowerShell:

```powershell
ipconfig
```

Use o valor que aparece em **"Endereço IPv4"**, exemplo:

```
192.168.1.100
```

Substitua `localhost` por esse IP nas URLs das requisições.

---

## ✔️ Gerando Token via Postman

> 🗂️ **Backup disponível:** Na pasta `/external-util` do projeto, existe um arquivo de backup do Postman contendo todas as collections prontas para gerar tokens e testar os endpoints.

> ⚠️ **Importante:**  
Após importar a collection, você **precisa atualizar o endereço IP** nas variáveis `{{host}}` ou diretamente nos endpoints.  
Use sempre o **IP local (IPv4)** da sua máquina, o mesmo que foi utilizado na geração do arquivo `.env`.

### ✔️ Passos:

1. Importe o backup do Postman da pasta `/external-util`.
2. Acesse a collection de geração de token.
3. Atualize o IP nos endpoints para seu IP local, exemplo:

```
http://192.168.1.100:8081
```

4. Preencha os dados do body:

- **client_id:** `Fixyou-client-ext`
- **client_secret:** (pegue no Keycloak em Clients > Credentials)
- **grant_type:** `password`
- **username:** (ex.: `admfixyou` ou `userfixyou`)
- **password:** `123Mudar`
- **scope:** `openid offline_access profile`

5. Execute a request.

O token estará disponível na resposta no campo:

```
access_token
```

---

## ✔️ Gerando Token via cURL

Exemplo de comando **cURL** para gerar token:

```bash
curl --location 'http://192.168.1.100:8081/realms/Fixyou-realm/protocol/openid-connect/token' --header 'Content-Type: application/x-www-form-urlencoded' --data-urlencode 'client_id=Fixyou-client-ext' --data-urlencode 'client_secret=SEU_CLIENT_SECRET' --data-urlencode 'grant_type=password' --data-urlencode 'username=admfixyou' --data-urlencode 'password=123Mudar' --data-urlencode 'scope=openid offline_access profile'
```

Substitua:

- `192.168.1.100` ➝ Pelo **IP local da sua máquina**.
- `SEU_CLIENT_SECRET` ➝ Pelo **Client Secret** do seu Client configurado no Keycloak.

---

## 👥 Usuários Disponíveis para Token

- **admfixyou** ➝ Acesso completo a todos os endpoints.
- **userfixyou** ➝ Acesso restrito, apenas para busca de usuários por nome.

**Senha de ambos:** `123Mudar`

---

## 📜 O que fazer com o Token?

Copie o valor do campo:

```
access_token
```

E utilize no Swagger ou nas suas requisições HTTP, adicionando no header:

```
Authorization: Bearer SEU_TOKEN_AQUI
```

---

✅ Token gerado com sucesso e pronto para uso na aplicação! 🚀

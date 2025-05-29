
# 🔐 Guia de Configuração do Keycloak

Como configurar manualmente o Keycloak, criar Realms, Clients e Usuários.

---

## 🚪 Acessando o Keycloak

Acesse o painel administrativo do Keycloak através da URL:

```
http://localhost:8081
```

> 🔥 **Importante:**  
O Keycloak só estará disponível na porta `8081` se você estiver rodando através do comando:

```bash
docker compose up
```

### 📌 Atenção às portas:

- No **modo Docker**, a aplicação roda na porta `8080`.
- No **modo desenvolvimento local (IntelliJ)**, a aplicação roda na porta `8083`.

> Ambos os ambientes compartilham o **mesmo Keycloak**, portanto não é necessário criar duas instâncias.

### 🔑 Credenciais do administrador do Keycloak:

- **Usuário:** `admin`
- **Senha:** `admin`

---

## 🏗️ Criando o Realm

1. No canto superior esquerdo, clique no dropdown ao lado de `Keycloak`.
2. Clique em **"Create Realm"**.
3. Informe o nome:

```
Fixyou-realm
```

4. Clique em **Save**.

---

## 🏛️ Criando o Client

1. No menu lateral, acesse **Clients**.
2. Clique em **Create Client**.
3. Preencha os campos:

- **Client ID:** `Fixyou-client-ext`
- **Name:** `Fixyou-client`
- **Always display in UI:** `ON` (ativado)

4. Em **Valid Redirect URIs**, adicione os seguintes valores:

```
http://localhost:8083/secure-data-1
http://localhost:8083/secure-data-2
http://localhost:8083/swagger-ui/index.html
http://localhost:8080/secure-data-1
http://localhost:8080/secure-data-2
http://localhost:8080/swagger-ui/index.html
```

> 🔥 **Atenção:**  
Caso você adicione um novo endpoint, ele também precisa ser registrado aqui!

5. Em **Web Origins**, adicione:

```
http://localhost:8083
http://localhost:8080
```

> Isso garante funcionamento tanto no modo desenvolvimento quanto no modo Docker.

6. Na aba **Capability Config**, configure:

- **Client authentication:** `ON`
- **Authorization:** `OFF`

7. Na seção **Authentication flow**, marque:

- **Standard Flow:** ✅
- **Direct Access Grants:** ✅
- **Service Accounts Roles:** ✅
- **OAuth 2.0 Device Authorization Grant:** ✅
- **Logout settings:**
    - **Front Channel Logout:** ✅
    - **Backchannel Logout Session Required:** ✅

8. Vá até a aba **Credentials**:

- **Client Authenticator:** `Client ID and Secret`
- Clique em **Regenerate Secret** ou copie o **Client Secret** gerado.

> ⚠️ Esse **Client Secret** será utilizado na geração do token de autenticação.

9. Clique em **Save**.

---

## 👥 Criando Usuários

1. No menu lateral, acesse **Users** dentro do realm `Fixyou-realm`.
2. Clique em **Add User**.
3. Preencha os campos:

- **Username:** `admfixyou` (para admin) ou `userfixyou` (para usuário comum).

4. Clique em **Save**.
5. Na aba **Credentials**, configure:

- Defina uma senha: `123Mudar`
- Marque **Temporary:** `OFF`

6. Vá em **Role Mappings** e atribua os papéis necessários:

- Para `admfixyou`: atribuir as roles que garantem acesso total.
- Para `userfixyou`: atribuir roles que permitam **apenas busca de usuários por nome**.

> ⚙️ As roles devem estar previamente criadas no seu Realm.

---

## 📝 Observações importantes

- Todo esse processo foi feito dentro do Realm `Fixyou-realm`.
- Caso perceba que seu Client está com o nome `Fixyou-realm-realm` (erro padrão do Keycloak), você pode editar e alterar o nome para algo mais adequado, como `Fixyou-client-ext`.

---

✅ **Keycloak configurado manualmente com sucesso!**

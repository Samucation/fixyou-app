# **Manual de integração da aplicação Fixyou**

## **Empresa: M3 Consultoria**

A aplicação está previamente configurada para rodar localmente e também via docker, para cada
ambiente será necessário o manuseio e criação de algumas variáveis de ambiente especificas que serão aboradas nesse documento
podendo ser que essas variáveis sejam criadas no próprio intellij "Em contextos aonde você está rodando local e sem dockerizar a aplicão
( contexto de desenvolvimento ) ou setando as enviroments diretamente no sistema operacional ou cloud que a aplicação
será executada. ( Modo de produção ) Abaixo existe a explicação de como rodar a aplicação."

### **01 - Rodando a aplicação inteira via docker:** ###

- Caso queira rodar a aplicação apenas para testes é possível rodar ela totalmente via docker, ou seja o banco de dados e a aplicação estarão dentro
- de um container docker assim como o keycloak, nesses casos você precisará de poucos passos para rodar a aplicação.
- - Com o docker instalado na sua máquina windows ou linux execute os seguintes comandos na raiz do projeto:
- - ```docker composer up ``` Se tudo ser certo a aplicação será configurada automáticamente pelo docker:
- - mas antes de acessar a aplicação será necessário configurar o keycloak que subiu no docker para isso siga os passos **04 - Acessando e configurando o keycloak para funcionar a aplicação** para configurar o keycloak
- - e o passo: **05 - Recuperando o token do keycloak para ter acesso aos endpoints da aplicação:** para saber como recuperar o Barer token da aplicação para usar no swagger, acesse o passo:
- - **06 - Autenticando via keycloak** para saber como inserir o token na aplicação, após configurar o keycloak com os passos 04,05 e 06
- - Acesse a url **http://localhost:8080/swagger-ui/index.html#/user-controller/isServerLive** para saber se a aplicação está rodando.
- - você poderá usar a aplicação normalmente sem mais configurações, porém isso é um modo docker local, não tem muita serventia para desenvolvimento de backend apenas para consumo do frontend e testes.

### **02 - Usar a aplicação localmente para desenvolvimento backend sem docker:** ###

- Execute o comando ```docker composer up``` para subir o keycloak e o banco de dados da aplicação, note que usamos a mesma imagem docker que no passo 01
- porém aqui nós não iremos usar a aplicação de fato, apenas usaremos o keycloak e o banco de dados postgres, pois iremos rodar a aplicação a patir do intellij
- e não a partir do target gerado e compilado para virar aplicação container do docker, após rodar o comando ```docker-compose up``` continue para o próximo passo.
- Para isso siga os seguintes comandos:

- **Inserindo variáveis de ambiente, definindo o ambiente local de desenvolvimento**
- A aplicação sempre irá procurar no seu arquivo application.class ( **FixyouApplication** ) qual é o ambiente
- setado nas variáveis de ambiente, para rodar localmente no **intellij** vá em:
- - Edit Configurations
- - New configuration
- - Escolha a opção do menu esquerdo no simbolo de + para adicionar uma aplicação.
- - Especifique o **JAVA 22** para a aplicação.
- - E em seguida coloque o pacote da aplicação (**com.fcamara.FixYouApplication**) O Intellij busca automático se clicar no botão.
- - Em variáveis de ambiente coloque ( <span style="color: red;">**APPLICATION_ENVIRONMENT=local;ENV_PATH=env;ENV_FILE=local** </span>) sem os parenteses.
- - Em **Work Directory** tenha certeza que está no diretório da aplicação.
- - Caso não tenha ainda configurado o keycloak, será necessário configurar o keycloak caso não tenha ainda usado o comando docker-compose up na raiz do projeto via cmd
- - você precisará fazer para subir a imagem do keycloak, para saber como configurar o keycloak
- - siga os passos **04 - Acessando e configurando o keycloak para funcionar a aplicação** para configurar o keycloak
- - e o passo: **05 - Recuperando o token do keycloak para ter acesso aos endpoints da aplicação:** para saber como recuperar o Barer token da aplicação para usar no swagger, acesse o passo:
- - **06 - Autenticando via keycloak** para saber como inserir o token na aplicação, após configurar o keycloak com os passos 04,05 e 06
- - Agora com o keycloak configurado, clique em executar a aplicação em modo ( **Debug** ) ou modo sem debug, fica a sua escolha.
- - Acesse a url **http://localhost:8083/swagger-ui/index.html#/user-controller/isServerLive** para saber se a aplicação está rodando.

### **03 - Dicas de desenvolvimento (Comandos importantes)** ###
- - Rodar migrations: Na raiz do projeto executar via cmd ou terminal o comando ```mvn flyway:migrate```
- - Resetar migrações ou reparar migrações do banco de dados, via cmd ou terminal rodar o comando: ```mvn flyway:repair```
- - Executar a instalação de dependencias do projeto dando skip dos testes, via cmd ou terminal rodar o comando: ``` mvn clean install -DskipTests```
- - Recriar a imagem docker da aplicação, caso esteja rodando a aplicação no modo 01 ```docker compose up --build``` ou por qualquer outro motivo.

### **04 - Acessando e configurando o keycloak para funcionar a aplicação** ###
- - Para acessar o keycloak use o url local http://localhost:8081 <strong style="color: darkred"> <strong>NOTA:</strong> O keycloak só estará na porta 8081 caso seja criado pelos comandos docker-compose up rodando na raiz da aplicação. </strong>
- - nesse caso é importante entender que também a aplicação será criada e acessada pela porta 8080 ou seja usando o acesso da aplicação via docker a aplicação roda na porta 8080 já no modo dev mod a aplicação por default irá rodar na porta 8083
- - cada tipo de ambiente precisa ser acessado em sua devida porta, os dois ambientes consomem o mesmo keycloak criado ao executar docker-compose up na raiz do projeto, não havendo a necessidade de criar dois keycloaks para acessar dev mod e docker mode.
- - Para acessar o portal administrador do keycloak use as credencias:
- - - Usuário: **admin**
- - - Senha: **admin**
- - - Crie um novo reaml, clicando logo abaixo do nome keycloak no canto superior esquerdo clique o droopdown e em seguida no botão <strong style="background-color: darkblue; color: #f0f0f0">Create Realm</strong>
- - - de o nome de <strong style="darkred">Fixyou-realm</strong>
- - - Agora vá até <strong style="color: darkred">Clients</strong> e crie um novo client e em <strong style="color:blue">Client ID</strong> de nome de <strong style="color: darkgreen">Fixyou-client-ext</strong> e em <strong style="color:blue">Name</strong> de o nome de <strong style="color:darkgreen">Fixyou-client</strong>
- - - Mais abaixo ainda em client verifique se <strong>Always display in UI </strong> está como ON
- - - Em <strong style="color: darkred">Valid redirect URIs</strong> adicione as seguintes urls para que o keycloak tenha acesso a essas urls, sem isso ele não vai reconhecer a aplicação:
- - - http://localhost:8083/secure-data-1
- - - http://localhost:8083/secure-data-2
- - - http://localhost:8083/swagger-ui/index.html
- - - Lembre-se que caso adicione um novo endpoint com um novo /nova-request ela precisará ser cadastrada em <strong style="color: darkred">Valid redirect URIs</strong> também!
- - - Em <strong>Web origins</strong> verifique se o caminho está válido para a aplicação que irá rodar no caso nosso backend está rodando em: http://localhost:8083 mas adicione também http://localhost:8080 para funcionar na imagem docker
- - - ou seja 8083 para modo dev mod e 8080 para modo apenas docker. adicione as duas em Web origins.
- - - Em <strong style="color: darkred">Capability config</strong> verique se as seguintes configurações estão corretas:
- - - <strong style="color: darkred">Client authentication</strong> <strong style="background-color:blue;color:white">ON</strong>
- - - <strong style="backgrund-color: white;color:grey">Authorization deixe OFF</strong>
- - - Em <strong style="color: darkred">Authentication flow</strong> deixe os seguintes valores como <strong style="background-color: white;color: blue">V</strong>
- - - Standard flow: <strong style="background-color: white;color: blue">V</strong>
- - - Direct access grants <strong style="background-color: white;color: blue">V</strong>
- - - Service accounts roles: <strong style="background-color: white;color: blue">V</strong>
- - - OAuth 2.0 Device Authorization Grant <strong style="background-color: white;color: blue">V</strong>
- - - Logout settings -> Front channel logout: <strong style="background-color: white;color: blue">V</strong>
- - - Backchannel logout session required <strong style="background-color: white;color: blue">V</strong>
- - - Agora na aba <strong style="color:darkblue">Credentials</strong> adicione as seguintes configurações para credentials:
- - - Em <strong style="color:darkgreen">Client Authenticator</strong> adicione o valor de <strong style="color:darkgreen">Clientid and Secret</strong>
- - - Logo abaixo gere uma nova ou copie a <strong style="color:darkgreen">Client Secret</strong> você usará ela no passo **05 - Recuperando o token do keycloak para ter acesso aos endpoints da aplicação:** para
- - - colocar no parametro client_secret ao buscar o token de autenticação da aplicação.
- - - Aperte save e deixe todo o resto com a configuração padrão.
- - - <strong style="color: darkred">NOTA: toda a configuração anterior foi feita em cima do realm Fixyou-realm, porém em algum momento foi trocado para o realm master e em Clients como master o nome do client ID do Fixyou-realm foi modificado de Fixyou-realm-realm para apenas Fixyou-realm
- - - caso exista algum problema na configuração, considerar essa nota para ajuste no nome do realm que por default fica sempre realm-realm e foi trocado manualmente na master para Client ID Fixyou-realm em Clients </strong>

- - - Vá agora em Users do Fixyou-realm e clique em <strong>Add User</strong> para acionar um novo usuário, adicione usuário e senha.

### **05 - Recuperando o token do keycloak para ter acesso aos endpoints da aplicação:**
- - Para ter acesso aos endpoints da aplicação, será necessário antes recuperar o bearer token, então tenha certeza que o seu keycloak está rodando e configurado com o Fixyou-realm e o client Fixyou-client-ext
- - No meu caso eu criei um usuário de nome <strong>samucation</strong> com senha: <strong>123Mudar</strong> mas o código abaixo precisará da sua alteração nesses parametros caso você tenha mudado o nome do usuário ou senha ou do realm ou client.
- - Use o CURL abaixo com as devidas alterações para recuperar o bearer token:
```
curl --location 'http://localhost:8081/realms/Fixyou-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=Fixyou-client-ext' \
--data-urlencode 'client_secret=admin' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=samucation' \
--data-urlencode 'password=123Mudar' \
--data-urlencode 'scope=openid offline_access profile'
```
Troque os valores do CURL em:
- - username= <strong style="color:darkred">samucation</strong>
- - password= <strong style="color:darkred">123Mudar</strong>
- - client_secret=<strong style="color:darkred">admin</strong>
- - Por um valores válidos para você de usuário e senha e secret anteriormente configurados no passo **04 - Acessando e configurando o keycloak para funcionar a aplicação**.

### **06 - Autenticando via keycloak** ###
- - Após passar pelo passo 04 e 05 e obter o token válido do keycloak, use esse token para desbloquear o cadeado do swagger.
- - Para isso acesse a url a seguir e em seguida coloque o token no cadeado a direita.
- - Com o token em mãos, acesse a uri do swagger: http://localhost:8083/swagger-ui/index.html caso esteja em dev mod, ou http://localhost:8080/swagger-ui/index.html caso esteja em docker mod.
- - No cadeado insira o valor recuperado em code e aperte autorize para liberar os endpoints bloqueados que só tem acesso via token.
- - agora acesse os endpoints, caso o token expire, peça um novo token via CURL do passo 05.

## **Keycloak notas bonus, Novos endpoins precisarão de configuração**
- - Caso queira incluir novos endpoints será necessário na sessão client do Keycloak adicionar essas novas URLS, caso contrário o keycloak não terá acesso as urls e o sistema não funcionará.


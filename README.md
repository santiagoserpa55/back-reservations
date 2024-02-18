
# Backend App Reservas

Este proyecto se basa en una implementación de seguridad utilizando la librería [OpenSSL](https://www.openssl.org/) que explico el usuario [Daniel Espanadero](https://github.com/DanielEspanadero).

El proyecto original lo pueden encontrar [aquí](https://github.com/DanielEspanadero/spring-security)

Utilizaremos las bases para seguridad y autenticación de usuarios ya que es un proyecto que está muy bien explicado y considero que tiene valor agregado en el uso de la librería [OpenSSL](https://www.openssl.org/) y [JWT](https://jwt.io/).


####Si deseas clonar este repositorio y probar su funcionamiento deberás tener en cuenta lo siguiente:

#### ¿Cómo generar private_key.pem y public_key.pem con OpenSSL?

Recuerda guardad las llaves privadas y públicas dentro de la carpeta **resources/jwtKeys**

Generación de la llave privada:

```
openssl genrsa -out private_key.pem 2048
```

Generación de la llave pública a través de la llave privada:

```
openssl rsa -pubout -in private_key.pem -out public_key.pem
```

### application.yml

```
spring:
  application:
    name: security-app
datasource:
  customs:
    query-timeout: 20
  my-connection:
    jdbc-url: jdbc:mysql://localhost:3306/appsecurity?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrival=true
    username: tuusuario
    password: tupassword
    maximum-pool-size: 10
    pool-name: "security-pool"
    auto-commit: true

jwtKeys:
  privateKeyPath: jwtKeys/private.key.pem
  publicKeyPath: jwtKeys/public.key.pem

```

### application.properties

Para poder arrancar la aplicación correctamente, recuerda tener creada una BBDD llamada security y cambiar el password por el que tengas configurado en tu sistema.

```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/security?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrival=true
spring.datasource.username=tuusuario
spring.datasource.password=tupassword
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
jwtKeys.privateKeyPath=jwtKeys/private.key.pem
jwtKeys.publicKeyPath=jwtKeys/public.key.pem
```

#####Nuestra implementación será para un Sistema web de gestión de reservas. Las principales funcionalidades se describen a continuación:

#### Funcionalidades 
- Registro de clientes.
- Inicio de sesión de clientes
- Registro de reservas
- Revisión de reservas
- Modificación de reservas (fecha, cantidad de personas, tipo)

#### Validaciones
- Datos vacios (Nombres y apellidos, etc)
- Verificación de email ya existente
- Verificación de identificacion ya existente
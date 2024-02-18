
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

De igual manera debes crear la base de datos en mi caso estoy usando [MySQL](https://dev.mysql.com/). El script es el siguiente: 

``` 
-- ***** TABLE ROL ***** --
CREATE TABLE IF NOT EXISTS rol (
	rol_id TINYINT(1) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(10) NOT NULL
);

INSERT INTO rol (name) VALUES ("ROLE_USER");

-- ***** TABLE CUSTOMER ***** --
CREATE TABLE IF NOT EXISTS customer (
    customer_id INTEGER UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    tipo_document ENUM ('CC','PASSPORT','VISA') NOT NULL,
    document VARCHAR(15) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    birthdate DATE,
    rol TINYINT(1) UNSIGNED NULL DEFAULT 1 COMMENT '1 = ROLE_ADMIN 2 = ROLE_USER',
    active TINYINT(1) UNSIGNED DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (rol) REFERENCES rol (rol_id)
  );
-- ***** INSERT ***** --
INSERT INTO customer (tipo_document,document,first_name,last_name,phone,email,password,birthdate)
VALUES("CC", "101075173", "SANTIAGO","SERPA","32180283", "SqS5G@GMAIL.COM","PASS1","1992-01-28" );

INSERT INTO customer (tipo_document,document,first_name,last_name,phone,email,password,birthdate)
VALUES("CC", "201075173", "SANTIAGO","SERPA","33180283", "SS5G@GMAIL.COM","PASS1","1992-01-28" );

UPDATE `customer` SET `active` = 0 WHERE `customer_id` = 1;

-- *****TABLE RESERVATIONS*****--
CREATE TABLE IF NOT EXISTS reservations (
  reservation_id INTEGER UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  date_reserva DATETIME(6) NOT NULL,
  tipo_reserva ENUM ('CENA','ALMUERZO','ONCES','CUMPLEAÑOS', 'OCASION ESPECIAL','OTRO') NOT NULL,
  quantity_persons TINYINT(3) UNSIGNED NOT NULL,
  observations TEXT,
  active TINYINT(1) UNSIGNED DEFAULT 1,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  customer_id_reserva INTEGER UNSIGNED,
  FOREIGN KEY (customer_id_reserva) REFERENCES customer (customer_id)
);

-- ***** EDIT TABLE RESERVAS ***** --
ALTER TABLE reservations ADD status_reserv ENUM('NO CONFIRMADA','CONFIRMADA', 'CANCELADA') NOT NULL
	AFTER observations;

INSERT INTO reservations (date_reserva, tipo_reserva, quantity_persons, customer_id_reserva)
VALUES ('2024-01-05', 'CENA', 20, 2);

SELECT u.first_name, u.last_name, u.tipo_document, u.document,
      u.email, r.date_reserva, r.tipo_reserva, r.quantity_persons, r.active FROM reservations AS r
      INNER JOIN customer AS u ON r.customer_id_reserva = u.customer_id ORDER BY r.date_reserva DESC;
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
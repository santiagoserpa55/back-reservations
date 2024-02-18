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
 

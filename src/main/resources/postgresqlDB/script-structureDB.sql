\CONNECT appsecurity;
-- line for docker
USE bookingapp
-- ROL TABLE --
CREATE SEQUENCE rol_id_seq START WITH 1;
--conexion psql -U dev -h 127.0.0.1 -p 5432 -d dev
CREATE TABLE IF NOT EXISTS public.rol
(
    rol_id integer NOT NULL default nextval('rol_id_seq'::regclass),
    name character VARYING(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT rol_pkey PRIMARY KEY (rol_id)
);

ALTER SEQUENCE public.rol_id_seq OWNED BY public.rol.rol_id;

-- CUSTOMER TABLE --
CREATE SEQUENCE IF NOT EXISTS customer_id_seq START WITH 1;
-- seq for auto_incrememnt
create table if not exists public.customer (
	customer_id integer not null default nextval('customer_id_seq'::regclass),
    document_type character varying(10) collate pg_catalog."default" not null,
    document_number character varying(15) collate pg_catalog."default" not null,
    first_name character varying(50) collate pg_catalog."default" not null,
    last_name character varying(50) collate pg_catalog."default" not null,
    phone character varying(15) collate pg_catalog."default" not null,
    email character varying(100) collate pg_catalog."default" not null,
    password_cust character varying(255) collate pg_catalog."default" not null,
    birthdate date not null,
    rol smallint default 1,
    active smallint default 1,
    created_at timestamp without time zone default CURRENT_TIMESTAMP,
    updated_at timestamp without time zone default CURRENT_TIMESTAMP,
    constraint customer_pkey primary key (customer_id),
    constraint customer_document_key unique (document_number),
    constraint customer_email_key unique (email),
    constraint customer_rol_fkey foreign key (rol)
        references public.rol (rol_id) match simple
        on
update
	no action
        on
	delete
		no action,
		constraint customer_tipo_document_check check (document_type::text = any (array['CC'::character varying,
		'PASSPORT'::character varying,
		'VISA'::character varying]::text[])),
		constraint customer_rol_check check (rol = any (array[1,
		2]))
);
-- Reservations Table --
CREATE SEQUENCE IF NOT EXISTS reserv_id_seq START WITH
1;
-- seq for auto_incrememnt
create table if not exists public.reservations
(
    reservation_id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    date_reserva timestamp(6) without time ZONE NOT NULL,
    tipo_reserva character VARYING(20) COLLATE pg_catalog."default" NOT NULL,
    quantity_persons smallint NOT NULL,
    observations text COLLATE pg_catalog."default",
    hour_start time without time ZONE,
	  hour_finish time without time ZONE,
    status_reserva character VARYING(20) COLLATE pg_catalog."default" NOT NULL,
    active smallint default 1,
    created_at timestamp without time ZONE NOT NULL default CURRENT_TIMESTAMP,
    updated_at timestamp without time ZONE NOT NULL default CURRENT_TIMESTAMP,
    customer_id_reserva integer,
    CONSTRAINT reservations_pkey PRIMARY KEY (reservation_id),
    CONSTRAINT reservations_customer_id_reserva_fkey FOREIGN KEY (customer_id_reserva)
        REFERENCES public.customer (customer_id) MATCH SIMPLE
        ON
UPDATE
	NO ACTION
        ON
	DELETE
		NO ACTION,
		CONSTRAINT reservations_status_reserva_check 
check (status_reserva = any (array[
    'SOLICITADA',
		'CONFIRMADA',
		'CANCELADA'
]::text[])),
		CONSTRAINT reservations_tipo_reserva_check check (tipo_reserva::text = any (array['CENA'::character VARYING,
		'ALMUERZO'::character VARYING,
		'ONCES'::character VARYING,
		'CUMPLEAÑOS'::character VARYING,
		'OCASION ESPECIAL'::character VARYING,
		'OTRO'::character VARYING]::text[])),
		CONSTRAINT reservations_quantity_persons_check check (quantity_persons > 0)
);

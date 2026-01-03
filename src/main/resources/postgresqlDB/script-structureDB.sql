\connect appsecurity; -- line for docker
-- ROL TABLE --
CREATE SEQUENCE rol_id_seq START WITH 1;
CREATE TABLE IF NOT EXISTS public.rol
(
    rol_id integer NOT NULL DEFAULT nextval('rol_id_seq'::regclass),
    name character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT rol_pkey PRIMARY KEY (rol_id)
);

-- CUSTOMER TABLE --
CREATE sequence if not exists customer_id_seq START WITH 1; -- seq for auto_incrememnt
CREATE TABLE IF NOT EXISTS public.customer (
	customer_id integer NOT NULL DEFAULT nextval('customer_id_seq'::regclass),
    tipo_document character varying(10) collate pg_catalog."default" not null,
    document character varying(15) collate pg_catalog."default" not null,
    first_name character varying(50) collate pg_catalog."default" not null,
    last_name character varying(50) collate pg_catalog."default" not null,
    phone character varying(15) collate pg_catalog."default" not null,
    email character varying(100) collate pg_catalog."default" not null,
    password character varying(255) collate pg_catalog."default" not null,
    birthdate date not null,
    rol smallint default 1,
    active smallint default 1,
    created_at timestamp without time zone default CURRENT_TIMESTAMP,
    updated_at timestamp without time zone default CURRENT_TIMESTAMP,
    constraint customer_pkey primary key (customer_id),
    constraint customer_document_key unique (document),
    constraint customer_email_key unique (email),
    constraint customer_rol_fkey foreign key (rol)
        references public.rol (rol_id) match simple
        on
update
	no action
        on
	delete
		no action,
		constraint customer_tipo_document_check check (tipo_document::text = any (array['CC'::character varying,
		'PASSPORT'::character varying,
		'VISA'::character varying]::text[])),
		constraint customer_rol_check check (rol = any (array[1,
		2]))
);

-- Reservations Table --
CREATE sequence if not exists reserv_id_seq START WITH 1; -- seq for auto_incrememnt
CREATE TABLE IF NOT EXISTS public.reservations
(
    reservation_id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    date_reserva timestamp(6) without time zone NOT NULL,
    tipo_reserva character varying(20) COLLATE pg_catalog."default" NOT NULL,
    quantity_persons smallint NOT NULL,
    observations text COLLATE pg_catalog."default",
    hour_start time without time zone,
	  hour_finish time without time zone,
    status_reserva character varying(20) COLLATE pg_catalog."default" NOT NULL,
    active smallint DEFAULT 1,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    customer_id_reserva integer,
    CONSTRAINT reservations_pkey PRIMARY KEY (reservation_id),
    CONSTRAINT reservations_customer_id_reserva_fkey FOREIGN KEY (customer_id_reserva)
        REFERENCES public.customer (customer_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
        CONSTRAINT reservations_status_reserva_check 
CHECK (status_reserva = ANY (ARRAY[
    'SOLICITADA', 
    'CONFIRMADA',
    'CANCELADA'
]::text[])),
    CONSTRAINT reservations_tipo_reserva_check CHECK (tipo_reserva::text = ANY (ARRAY['CENA'::character varying, 'ALMUERZO'::character varying,
    'ONCES'::character varying, 'CUMPLEAÑOS'::character varying, 'OCASION ESPECIAL'::character varying, 'OTRO'::character varying]::text[])),
    CONSTRAINT reservations_quantity_persons_check CHECK (quantity_persons > 0)
);





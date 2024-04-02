-- PostgreSQL Querys --
-- ROL TABLE --
CREATE SEQUENCE rol_rol_id_seq START WITH 1; -- seq for auto_incrememnt
CREATE TABLE IF NOT EXISTS public.rol
(
    rol_id integer NOT NULL DEFAULT nextval('rol_rol_id_seq'::regclass),
    name character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT rol_pkey PRIMARY KEY (rol_id)
)
-- CUSTOMER TABLE --
CREATE sequence if not exists customer_customer_id_seq START WITH 1; -- seq for auto_incrememnt

create table if not exists public.customer (
	customer_id integer NOT NULL DEFAULT nextval('customer_customer_id_seq'::regclass),
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
)
-- DELETING CUSTOMER TABLE --
DROP TABLE public.customer CASCADE;

-- FUNCTION FOR UDATE FIELDS CREATED, ACTIVE, ROL AND UPDATED, --
CREATE OR REPLACE FUNCTION fill_customer()
RETURNS TRIGGER AS $$
BEGIN
	NEW.created_at = CURRENT_TIMESTAMP;
	NEW.active = 1;
	NEW.rol = 1;
	NEW.updated_at = CURRENT_TIMESTAMP;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;
-- TRIGGER FOR UDATE FIELDS CREATED, ACTIVE, ROL AND UPDATED --
-- LAS FUNCIONES NO SE ELIMINAN CUANDO ELIMINAS LA TABLA --
-- LOS TRIGGER SI SE ELIMINAN CUANDO ELIMINAS LA TABLA --
CREATE TRIGGER trigger_fill_customer
BEFORE INSERT ON customer
FOR EACH ROW
EXECUTE FUNCTION fill_customer();
-- FUNCTION FOR UPDATE UPDATED AT FIELD --
CREATE OR REPLACE FUNCTION update_customer_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
-- TRIGGER FOR FIELD UPDATET AT -- 
CREATE TRIGGER customer_updated_at_trigger
BEFORE UPDATE ON customer
FOR EACH ROW
EXECUTE FUNCTION update_customer_updated_at();


-- querys --
UPDATE public.customer
	SET tipo_document='CC'
	WHERE document = '1007517386';
	
SELECT * FROM public.customer
ORDER BY customer_id ASC

DELETE FROM public.customer
	WHERE customer_id = 27;

-- CALCULAR EDAD --
SELECT first_name, AGE(CURRENT_TIMESTAMP, birthdate) age FROM customer;

SELECT
	first_name,
	YEAR(CURDATE())-YEAR(birthdate) + IF(DATE_FORMAT(CURDATE(), '%m-%d') > DATE_FORMAT(birthdate, '%m-%d'),
	0 ,
	-1 ) AS EDAD_ACTUAL
FROM
	customer
WHERE
	first_name = 'SANTIAGO';

-- RESERVATIONS TABLE  --

CREATE TABLE IF NOT EXISTS public.reservations
(
    reservation_id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    date_reserva timestamp(6) without time zone NOT NULL,
    tipo_reserva character varying(20) COLLATE pg_catalog."default" NOT NULL,
    quantity_persons smallint NOT NULL,
    observations text COLLATE pg_catalog."default",
    active smallint DEFAULT 1,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    customer_id_reserva integer,
    CONSTRAINT reservations_pkey PRIMARY KEY (reservation_id),
    CONSTRAINT reservations_customer_id_reserva_fkey FOREIGN KEY (customer_id_reserva)
        REFERENCES public.customer (customer_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT reservations_tipo_reserva_check CHECK (tipo_reserva::text = ANY (ARRAY['CENA'::character varying, 'ALMUERZO'::character varying,
    'ONCES'::character varying, 'CUMPLEAÑOS'::character varying, 'OCASION ESPECIAL'::character varying, 'OTRO'::character varying]::text[])),
    CONSTRAINT reservations_quantity_persons_check CHECK (quantity_persons > 0)
)


-- FUNCTION: public.getMaxDate()

-- DROP FUNCTION IF EXISTS public."getMaxDate"();

CREATE OR REPLACE FUNCTION public."getMaxDate"()
    RETURNS date
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE getMaxDate DATE;
BEGIN
SELECT MIN(birthdate) INTO getMaxDate FROM customer;
RETURN getMaxDate;
END;
$BODY$;

ALTER FUNCTION public."getMaxDate"()
    OWNER TO santiago;
------ GET MAX DATE FIN

-- FUNCTION: public.fu_obtener_edad(date, date)

-- DROP FUNCTION IF EXISTS public.fu_obtener_edad(date, date);

CREATE OR REPLACE FUNCTION public.fu_obtener_edad(
	pd_fecha_ini date,
	pd_fecha_fin date,
	OUT pn_edad integer)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
BEGIN
pn_edad := FLOOR(((DATE_PART('YEAR',pd_fecha_fin) - DATE_PART('YEAR',pd_fecha_ini)) * 372 +
(DATE_PART('MONTH',pd_fecha_fin) - DATE_PART('MONTH',pd_fecha_ini))*31 +
(DATE_PART('DAY',pd_fecha_fin)-DATE_PART('DAY',pd_fecha_ini)))/372);
END;
$BODY$;

ALTER FUNCTION public.fu_obtener_edad(date, date)
    OWNER TO santiago;

-- FIN OBTENER EDAD --

-- Trigger: customer_insert_field_created_at

-- DROP TRIGGER IF EXISTS customer_insert_field_created_at ON public.customer;

CREATE OR REPLACE TRIGGER customer_insert_field_created_at
    BEFORE INSERT
    ON public.customer
    FOR EACH ROW
    EXECUTE FUNCTION public.insert_field_created_at_customer();

-- Trigger: customer_updated_at_trigger

-- DROP TRIGGER IF EXISTS customer_updated_at_trigger ON public.customer;

CREATE OR REPLACE TRIGGER customer_updated_at_trigger
    BEFORE UPDATE 
    ON public.customer
    FOR EACH ROW
    EXECUTE FUNCTION public.update_customer_updated_at();
-- FIN --












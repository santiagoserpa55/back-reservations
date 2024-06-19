-- querys --

-- DELETING CUSTOMER TABLE --

DROP TABLE public.rol CASCADE;
DROP TABLE public.customer CASCADE;
DROP TABLE public.reservations CASCADE;

UPDATE public.customer
	SET last_name ='Smith'
	WHERE document = '1007517385';
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
	first_name = 'SANTIAGO';*/
-- RESERVATIONS TABLE  --
INSERT INTO public.reservations
(date_reserva, tipo_reserva, status_reserva, quantity_persons, observations, active, created_at, updated_at, customer_id_reserva, hour_start, hour_finish)
VALUES('2023-06-01 20:00', 'ALMUERZO', 'SOLICITADA', 10, 'NO', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, '08:10:00', '09:10:00');

ALTER TABLE public.reservations ADD hour_start time without time zone;
ALTER TABLE public.reservations ADD hour_finish time without time zone;
ALTER TABLE public.reservations ALTER COLUMN date_reserva TYPE date USING date_reserva::date;


DROP TYPE statusReserv;

CREATE TYPE statusReserv AS ENUM ('SOLICITADA', 'CONFIRMADA', 'CANCELADA');

ALTER TABLE public.reservations ADD status_reserva statusReserv default 'SOLICITADA';

alter table reservations drop status_reserv;

select * from reservations;



/*SELECT COUNT(reservation_id) FROM reservations where customer_id_reserva = ; 
SELECT COUNT(customer_id)
FROM customers
WHERE city = 'London'; 
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
*/
--***  LAS FUNCIONES NO SE ELIMINAN CUANDO ELIMINAS LA TABLA  ***--
--***  LOS TRIGGER SI SE ELIMINAN CUANDO ELIMINAS LA TABLA  ***--

-- ********** customer table ********** 
CREATE OR REPLACE FUNCTION fill_table_customer()
RETURNS TRIGGER AS $$
BEGIN
	NEW.created_at = CURRENT_TIMESTAMP;
	NEW.active = 1;
	NEW.rol = 1;
	NEW.updated_at = CURRENT_TIMESTAMP;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;
-- trigger ********** customer table ********** 
CREATE TRIGGER fill_table_customer_trigger
BEFORE INSERT ON customer
FOR EACH ROW
EXECUTE FUNCTION fill_table_customer();

-- function ********** customer table ********** 
CREATE OR REPLACE FUNCTION update_field_customer_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- TRIGGER FOR FIELD UPDATET AT -- 
CREATE TRIGGER update_customer_updated_at_trigger
BEFORE UPDATE ON customer
FOR EACH ROW
EXECUTE FUNCTION update_field_customer_updated_at();
-- function ********** reservations table ********** 
create or replace function fill_table_reserva()
returns trigger as $$
begin
	new.created_at = current_timestamp;
	new.updated_at = current_timestamp;
	new.active = 1;
	return new;
end;
$$ language plpgsql;
-- *** trigger
create trigger fill_reservat_trigger
before insert on reservations
for each row
execute function fill_table_reserva();
-- FUNCTION
CREATE OR REPLACE FUNCTION update_reserva_updatet_at()
	RETURNS trigger as $$
	begin
		new.updated_at = current_timestamp;
		return new;
	END;
$$ LANGUAGE plpgsql;
-- trigger
create trigger upate_field_updated_at_reserva_trigger
before update on reservations
for each row execute function update_reserva_updatet_at();
-- function set status
create or replace function set_status_reserva()
	returns trigger as $$
	begin
		new.status_reserva = 'SOLICITADA';
		return new;
	end;
$$ language plpgsql;
-- trigger
create trigger set_status_reserva_trigger
before insert on reservations
for each row execute function set_status_reserva();







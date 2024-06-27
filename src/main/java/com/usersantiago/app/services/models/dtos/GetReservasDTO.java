package com.usersantiago.app.services.models.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record GetReservasDTO(
        LocalDate dateReserva,
        String tipoReserva,
        LocalTime hourStart,
        LocalTime hourFinish,
        short quantityPersons,
        String observations,
        String statusReserva
) {

    public static ReservasDTOBuilder builder() {
        return new ReservasDTOBuilder();
    }

    public static class ReservasDTOBuilder {
        private LocalDate dateReserva;
        private String tipoReserva;
        private LocalTime hourStart;
        private LocalTime hourFinish;
        private short quantityPersons;
        private String observations;
        private String statusReserva;

        ReservasDTOBuilder() {
        }

        public ReservasDTOBuilder dateReserva(LocalDate dateReserva) {
            this.dateReserva = dateReserva;
            return this;
        }

        public ReservasDTOBuilder tipoReserva(String tipoReserva) {
            this.tipoReserva = tipoReserva;
            return this;
        }

        public ReservasDTOBuilder hourStart(LocalTime hourStart) {
            this.hourStart = hourStart;
            return this;
        }

        public ReservasDTOBuilder hourFinish(LocalTime hourFinish) {
            this.hourFinish = hourFinish;
            return this;
        }

        public ReservasDTOBuilder quantityPersons(short quantityPersons) {
            this.quantityPersons = quantityPersons;
            return this;
        }

        public ReservasDTOBuilder observations(String observations) {
            this.observations = observations;
            return this;
        }

        public ReservasDTOBuilder statusReserva(String statusReserva) {
            this.statusReserva = statusReserva;
            return this;
        }

        public GetReservasDTO build() {
            return new GetReservasDTO(
                this.dateReserva,
                tipoReserva,
                hourStart,
                hourFinish,
                quantityPersons,
                observations,
                statusReserva
            );
        }
    }
}

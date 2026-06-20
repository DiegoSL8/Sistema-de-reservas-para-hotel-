package modelo;

import java.time.LocalDate;

/**
 * Entidad base que representa una transacción de reserva en el sistema.
 */
public class Reserva {
    private int idReserva;
    private LocalDate fechaReserva;
    private Cliente huesped;
    private Habitacion habitacion;
    private String estado;

    public Reserva(int idReserva, LocalDate fechaReserva, Cliente huesped, Habitacion habitacion) {
        this.idReserva = idReserva;
        this.fechaReserva = fechaReserva;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.estado = "Pendiente";
    }

    /**
     * Confirma la reserva, cambiando su estado de forma oficial.
     */
    public void confirmarReserva() {
        this.estado = "Confirmada";
    }

    // --- Getters y Setters ---
    public int getIdReserva() {
        return idReserva;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public Cliente getHuesped() {
        return huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
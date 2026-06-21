package modelo;

import java.time.LocalDate;

/**
 * Entidad base que representa una transacción de reserva en el sistema.
 */
public class Reserva {
    private int idReserva;
    
    // CORRECCIÓN INTEGRACIÓN 1: Renombrado a "fecha" para estandarizar
    private LocalDate fecha; 
    
    // CORRECCIÓN INTEGRACIÓN 2: Renombrado a "cliente" para coincidir con el modelo
    private Cliente cliente; 
    
    private Habitacion habitacion;
    private String estado;

    public Reserva(int idReserva, LocalDate fecha, Cliente cliente, Habitacion habitacion) {
        this.idReserva = idReserva;
        this.fecha = fecha;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.estado = "Pendiente";
    }

    /**
     * Confirma la reserva, cambiando su estado de forma oficial.
     */
    public void confirmarReserva() {
        this.estado = "Confirmada";
    }

    // --- Getters y Setters estandarizados ---
    public int getIdReserva() {
        return idReserva;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
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
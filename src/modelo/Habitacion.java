package modelo;

/**
 * Entidad que representa una habitación física dentro del hotel.
 * Cumple con la regla de negocio RN-01: Solo posee estados "Disponible" u "Ocupada".
 */
public class Habitacion {
    private int idHabitacion;
    private String numero;
    private String estado; 

    public Habitacion(int idHabitacion, String numero) {
        this.idHabitacion = idHabitacion;
        this.numero = numero;
        this.estado = "Disponible"; // Estado por defecto
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public String getNumero() {
        return numero;
    }

    /**
     * Obtiene el estado actual de la habitación.
     * @return String con el estado ("Disponible" u "Ocupada").
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Modifica el estado de la habitación (RN-01).
     * @param estado El nuevo estado a asignar.
     */
    public void setEstado(String estado) {
        // Validación defensiva para asegurar el cumplimiento de RN-01
        if (estado.equalsIgnoreCase("Disponible") || estado.equalsIgnoreCase("Ocupada")) {
            this.estado = estado;
        } else {
            throw new IllegalArgumentException("Estado no válido según RN-01. Use 'Disponible' u 'Ocupada'.");
        }
    }

    @Override
    public String toString() {
        return "Habitación " + numero + " | Estado: " + estado;
    }
}
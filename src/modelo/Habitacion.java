package modelo;

/**
 * Entidad que representa una habitación física dentro del hotel.
 * Cumple con la regla de negocio RN-01: Solo posee estados "Disponible" u "Ocupada".
 */
public class Habitacion {
    private int idHabitacion;
    private String numeroString; // Renombrado internamente para evitar confusión
    private String estado; 

    // Constructor original (Intacto)
    public Habitacion(int idHabitacion, String numeroString) {
        this.idHabitacion = idHabitacion;
        this.numeroString = numeroString;
        this.estado = "Disponible"; // Estado por defecto
    }

    // CORRECCIÓN INTEGRACIÓN 1: Constructor sobrecargado para el GestorReservas
    public Habitacion(int numero) {
        this.idHabitacion = numero;
        this.numeroString = String.valueOf(numero);
        this.estado = "Disponible";
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    // CORRECCIÓN INTEGRACIÓN 2: Ahora devuelve un int para que el Gestor pueda buscarla matemáticamente
    public int getNumero() {
        return idHabitacion;
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
        // Validación defensiva de Daniel (Intacta)
        if (estado.equalsIgnoreCase("Disponible") || estado.equalsIgnoreCase("Ocupada")) {
            this.estado = estado;
        } else {
            throw new IllegalArgumentException("Estado no válido según RN-01. Use 'Disponible' u 'Ocupada'.");
        }
    }

    // =========================================================================
    // CORRECCIÓN INTEGRACIÓN 3: "Métodos Puente" para el Facade
    // Estos métodos traducen los booleanos (true/false) para la validación
    // =========================================================================
    
    public boolean isOcupada() {
        return this.estado.equalsIgnoreCase("Ocupada");
    }

    public void setOcupada(boolean ocupada) {
        if (ocupada) {
            this.setEstado("Ocupada"); // Llama a la validación
        } else {
            this.setEstado("Disponible"); // Llama a la validación
        }
    }

    @Override
    public String toString() {
        return "Habitación " + numeroString + " | Estado: " + estado;
    }
}
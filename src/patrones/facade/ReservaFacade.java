package patrones.facade;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import modelo.Habitacion;
import modelo.Cliente;
import patrones.facade.subsistemas.ControladorEstado;
import patrones.facade.subsistemas.RegistroHuesped;
import patrones.facade.subsistemas.ValidadorInventario;

public class ReservaFacade {

    // Las 3 clases que llamaremos desde acá.
    private ValidadorInventario validador;
    private RegistroHuesped registro;
    private ControladorEstado controlador;

    public ReservaFacade() {
        this.validador = new ValidadorInventario();
        this.registro = new RegistroHuesped();
        this.controlador = new ControladorEstado();
    }

    // Método principal--
    public void realizarReserva(String rut, String nombre, String fechaStr, int numHabitacion) {
        System.out.println("\n--- [Facade] Iniciando solicitud de reserva ---");

        try {
            // Transformamos el local date a String.
            LocalDate fecha = LocalDate.parse(fechaStr); // Formato esperado: YYYY-MM-DD

            // PASO 1: Validamos el inventario
            Habitacion habitacion = validador.verificarDisponibilidad(fecha, numHabitacion);

            if (habitacion != null) {
                // PASO 2: Registrar al huésped
                Cliente cliente = registro.registrarCliente(rut, nombre);

                // PASO 3: Confirmar reserva y actualizar estado
                controlador.procesarConfirmacion(fecha, cliente, habitacion);
            } else {
                // En caso de que la habitación este ocupada, rechazo.
                controlador.procesarRechazo(nombre);
            }

        } catch (DateTimeParseException e) {
            System.out.println("[Facade] Error: El formato de la fecha debe ser YYYY-MM-DD (Ej: 2024-12-01).");
        }
    }

    // Método Anulación--
    public void anularReserva(int idReserva) {
        System.out.println("\n--- [Facade] Iniciando solicitud de anulación ---");
        controlador.procesarAnulacion(idReserva);
    }
}
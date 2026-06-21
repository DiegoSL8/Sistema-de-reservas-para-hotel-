package patrones.facade.subsistemas;

import java.time.LocalDate;
import modelo.Cliente;
import modelo.Habitacion;
import modelo.Reserva;
import patrones.singleton.GestorReservas;

public class ControladorEstado {

    public void procesarConfirmacion(LocalDate fecha, Cliente cliente, Habitacion habitacion) {
        System.out.println("[Subsistema: Controlador] Generando reserva oficial y aplicando reglas de negocio...");
        
        // CORRECCIÓN 2: Quitamos el Random(). Usaremos el número de habitación como ID de reserva 
        // para que nuestro Main.java pueda predecirlo y anularlo correctamente.
        int idGenerado = habitacion.getIdHabitacion(); 
        Reserva nuevaReserva = new Reserva(idGenerado, fecha, cliente, habitacion);

        GestorReservas.getInstancia().registrarReserva(nuevaReserva);
    }

    public void procesarRechazo(String nombreHuesped) {
        System.out.println("[Subsistema: Controlador] Gestionando el rechazo de la solicitud...");
        GestorReservas.getInstancia().notificarObservadores(
            "NOTIFICACIÓN: Lo sentimos " + nombreHuesped + ", la habitación no cumple con disponibilidad."
        );
    }

    public void procesarAnulacion(int idReserva) {
        System.out.println("[Subsistema: Controlador] Procesando anulación para la reserva N°" + idReserva);
        GestorReservas.getInstancia().cambiarEstadoReserva(idReserva, "Anulada");
    }
}
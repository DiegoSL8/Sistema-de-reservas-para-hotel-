package patrones.facade.subsistemas;

import java.time.LocalDate;
import java.util.Random;
import modelo.Cliente;
import modelo.Habitacion;
import modelo.Reserva;
import patrones.singleton.GestorReservas;

public class ControladorEstado {

    public void procesarConfirmacion(LocalDate fecha, Cliente cliente, Habitacion habitacion) {
        System.out.println("[Subsistema: Controlador] Generando reserva oficial y aplicando reglas de negocio...");
        //Para que nosotros sepamos si esta funcionando correctamente.
        
        // Se genera una ID de reserva.
        int idGenerado = new Random().nextInt(1000) + 1; 
        Reserva nuevaReserva = new Reserva(idGenerado, fecha, cliente, habitacion);

        // Acá el Singleton debería encargarse de cambiar la reserva a "ocupada" y despertar al Observer.
        GestorReservas.getInstancia().registrarReserva(nuevaReserva);
    }

    public void procesarRechazo(String nombreHuesped) {
        System.out.println("[Subsistema: Controlador] Gestionando el rechazo de la solicitud...");
        
        // Disparamos notificación directa al Observer de rechazo.
        GestorReservas.getInstancia().notificarObservadores(
            "✉️ SMS ENVIADO: Lo sentimos " + nombreHuesped + ", la habitación no cumple con disponibilidad."
        );
    }

    public void procesarAnulacion(int idReserva) {
        System.out.println("[Subsistema: Controlador] Procesando anulación para la reserva N°" + idReserva);
        
        GestorReservas.getInstancia().cambiarEstadoReserva(idReserva, "Anulada");
    }
}
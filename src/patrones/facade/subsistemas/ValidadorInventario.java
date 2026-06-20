package patrones.facade.subsistemas;

import java.time.LocalDate;
import java.util.List;
import modelo.Habitacion;
import patrones.singleton.GestorReservas;

public class ValidadorInventario {
    
    public Habitacion verificarDisponibilidad(LocalDate fecha, int numHabitacion) {
        System.out.println("[Subsistema: Validador] Buscando habitación " + numHabitacion + " para la fecha " + fecha);
        
        GestorReservas gestor = GestorReservas.getInstancia();
        
        List<Habitacion> disponibles = gestor.consultarDisponibilidad(fecha);

        for (Habitacion h : disponibles) {
            if (h.getIdHabitacion() == numHabitacion) {
                System.out.println("[Subsistema: Validador] ¡Habitación disponible y validada!");
                return h; // Retorna la habitación si está libre
            }
        }
        
        System.out.println("[Subsistema: Validador] La habitación " + numHabitacion + " NO está disponible o no existe.");
        return null;
    }
}
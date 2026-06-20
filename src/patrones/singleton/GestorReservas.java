package patrones.singleton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import modelo.Habitacion;
import modelo.Reserva;
import patrones.observer.IObservador;
import patrones.observer.ISujetoObservable;

/**
 * Patrón Creacional: Singleton.
 * Actúa como la única fuente de verdad para el inventario y las reservas
 * (RN-03).
 * Implementa control de concurrencia centralizado (RNF-01).
 */
public class GestorReservas implements ISujetoObservable {

    // Uso de 'volatile' previene problemas de memoria caché en entornos multi-hilo
    private static volatile GestorReservas instancia;

    private List<Habitacion> inventarioHabitaciones;
    private List<Reserva> reservasActivas;
    private List<IObservador> listaObservadores;

    private GestorReservas() {
        this.inventarioHabitaciones = new ArrayList<>();
        this.reservasActivas = new ArrayList<>();
        this.listaObservadores = new ArrayList<>();

        // Inicialización base de datos simulada
        this.inventarioHabitaciones.add(new Habitacion(101, "101"));
        this.inventarioHabitaciones.add(new Habitacion(102, "102"));
        this.inventarioHabitaciones.add(new Habitacion(103, "103"));
    }

    /**
     * Obtiene la instancia única mediante Double-Checked Locking.
     * Garantiza seguridad matemática (RNF-01) sin bloquear la lectura constante.
     */
    public static GestorReservas getInstancia() {
        if (instancia == null) {
            synchronized (GestorReservas.class) {
                if (instancia == null) {
                    instancia = new GestorReservas();
                }
            }
        }
        return instancia;
    }

    /**
     * Consulta las habitaciones que están "Disponibles" para una fecha determinada.
     * Retorna una lista según lo especificado en el diagrama de clases del
     * Singleton.
     */
    public List<Habitacion> consultarDisponibilidad(LocalDate fecha) {
        // Filtra las habitaciones ocupadas en esa fecha
        List<Habitacion> ocupadas = reservasActivas.stream()
                .filter(r -> r.getFechaReserva().equals(fecha) && r.getEstado().equals("Confirmada"))
                .map(Reserva::getHabitacion)
                .collect(Collectors.toList());

        // Retorna las que NO están en la lista de ocupadas
        return inventarioHabitaciones.stream()
                .filter(h -> !ocupadas.contains(h))
                .collect(Collectors.toList());
    }

    /**
     * Registra una reserva asegurando bloqueo de concurrencia.
     * 
     * @param reserva La reserva a procesar.
     */
    public synchronized void registrarReserva(Reserva reserva) {
        // Se confirma la reserva y se actualiza la habitación a "Ocupada" (RN-01)
        reserva.confirmarReserva();
        reserva.getHabitacion().setEstado("Ocupada");
        reservasActivas.add(reserva);

        // Notificación mediante el patrón Observer (RF-06)
        notificarObservadores("NOTIFICACIÓN: Reserva N°" + reserva.getIdReserva() +
                " confirmada con éxito para el huésped " + reserva.getHuesped().getNombre() + ".");
    }

    /**
     * Modifica el estado de una reserva (Ej: para anularla según RF-05).
     */
    public synchronized void cambiarEstadoReserva(int idReserva, String nuevoEstado) {
        for (Reserva r : reservasActivas) {
            if (r.getIdReserva() == idReserva) {
                r.setEstado(nuevoEstado);

                // Si se cancela, se libera la habitación
                if (nuevoEstado.equalsIgnoreCase("Cancelada") || nuevoEstado.equalsIgnoreCase("Anulada")) {
                    r.getHabitacion().setEstado("Disponible");
                }

                notificarObservadores("SISTEMA: El estado de la reserva N°" + idReserva +
                        " ha cambiado a " + nuevoEstado.toUpperCase());
                break;
            }
        }
    }

    // --- Implementación de ISujetoObservable (Patrón Observer) ---
    @Override
    public void agregarObservador(IObservador observador) {
        if (!listaObservadores.contains(observador)) {
            listaObservadores.add(observador);
        }
    }

    @Override
    public void quitarObservador(IObservador observador) {
        listaObservadores.remove(observador);
    }

    @Override
    public void notificarObservadores(String mensaje) {
        for (IObservador obs : listaObservadores) {
            obs.actualizar(mensaje); // Delega la impresión/vista al observador (Cliente/Recepción)
        }
    }
}
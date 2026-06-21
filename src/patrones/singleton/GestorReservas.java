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
 * Actúa como la única fuente de verdad para el inventario y las reservas (RN-03).
 * Implementa control de concurrencia centralizado (RNF-01).
 */
public class GestorReservas implements ISujetoObservable {

    private static volatile GestorReservas instancia;

    private List<Habitacion> inventarioHabitaciones;
    private List<Reserva> reservasActivas;
    private List<IObservador> listaObservadores;

    private GestorReservas() {
        this.inventarioHabitaciones = new ArrayList<>();
        this.reservasActivas = new ArrayList<>();
        this.listaObservadores = new ArrayList<>();

        // CORRECCIÓN INTEGRACIÓN 1: Se ajusta al constructor que solo recibe un int
        this.inventarioHabitaciones.add(new Habitacion(101));
        this.inventarioHabitaciones.add(new Habitacion(102));
        this.inventarioHabitaciones.add(new Habitacion(103));
    }

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

    // CORRECCIÓN INTEGRACIÓN 2: Se agrega el método que necesita el Facade
    public Habitacion buscarHabitacion(int numero) {
        for (Habitacion h : inventarioHabitaciones) {
            if (h.getNumero() == numero) return h;
        }
        return null;
    }

    public List<Habitacion> consultarDisponibilidad(LocalDate fecha) {
        // Filtra las habitaciones ocupadas en esa fecha
        List<Habitacion> ocupadas = reservasActivas.stream()
                .filter(r -> r.getFecha().equals(fecha) && r.getEstado().equals("Confirmada")) // Cambiado getFechaReserva a getFecha
                .map(Reserva::getHabitacion)
                .collect(Collectors.toList());

        // Retorna las que NO están en la lista de ocupadas
        return inventarioHabitaciones.stream()
                .filter(h -> !ocupadas.contains(h))
                .collect(Collectors.toList());
    }

    public synchronized void registrarReserva(Reserva reserva) {
        // Se confirma la reserva y se actualiza la habitación a "Ocupada" (RN-01)
        reserva.setEstado("Confirmada"); // Cambiado confirmarReserva a setEstado para estandarizar
        reserva.getHabitacion().setOcupada(true); // Cambiado setEstado("Ocupada") a setOcupada(true)
        reservasActivas.add(reserva);

        // CORRECCIÓN INTEGRACIÓN 3: Cambiado getHuesped a getCliente para coincidir con nuestro modelo
        notificarObservadores("NOTIFICACIÓN: Reserva N°" + reserva.getIdReserva() +
                " confirmada con éxito para el huésped " + reserva.getCliente().getNombre() + ".");
    }

    public synchronized void cambiarEstadoReserva(int idReserva, String nuevoEstado) {
        for (Reserva r : reservasActivas) {
            if (r.getIdReserva() == idReserva) {
                r.setEstado(nuevoEstado);

                if (nuevoEstado.equalsIgnoreCase("Cancelada") || nuevoEstado.equalsIgnoreCase("Anulada")) {
                    r.getHabitacion().setOcupada(false); 
                }

                // CORRECCIÓN 3: Agregamos el nombre del cliente al mensaje para que el Observer lo escuche
                notificarObservadores("SISTEMA: El estado de la reserva N°" + idReserva +
                        " de " + r.getCliente().getNombre() + " ha cambiado a " + nuevoEstado.toUpperCase());
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
            obs.actualizar(mensaje); 
        }
    }
}
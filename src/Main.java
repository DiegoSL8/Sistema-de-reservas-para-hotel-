import modelo.Cliente;
import patrones.facade.ReservaFacade;
import patrones.singleton.GestorReservas;

public class Main {
    public static void main(String[] args) {
        System.out.println("=====================================================");
        System.out.println("    INICIANDO SISTEMA DE RESERVAS DE HOTEL    ");
        System.out.println("=====================================================\n");

        // 1. INICIALIZACIÓN DEL SISTEMA (Singleton & Observer)
        GestorReservas sistemaCentral = GestorReservas.getInstancia();
        
        // Creamos a nuestros actores (Huéspedes)
        Cliente huesped1 = new Cliente("Chupete Suazo", "10.123.456-7");
        Cliente huesped2 = new Cliente("Juan Pérez", "20.987.654-3");
        
        // Los suscribimos al sistema de notificaciones
        sistemaCentral.agregarObservador(huesped1);
        sistemaCentral.agregarObservador(huesped2);

        // 2. ORQUESTACIÓN (Instanciamos la Fachada)
        ReservaFacade recepcion = new ReservaFacade();

        // ---------------------------------------------------------
        // EJECUCIÓN DE ESCENARIOS (Mapeo directo del BPMN)
        // ---------------------------------------------------------

        System.out.println("\n▶ ESCENARIO 1: Creación de Reserva Exitosa (Camino Feliz)");
        System.out.println("---------------------------------------------------------");
        // CORRECCIÓN: Usamos el formato YYYY-MM-DD que exige Seba
        recepcion.realizarReserva(huesped1.getRut(), huesped1.getNombre(), "2026-06-15", 101);

        System.out.println("\n▶ ESCENARIO 2: Reserva Rechazada (Compuerta BPMN/ disponible? R:NO)' )");
        System.out.println("---------------------------------------------------------");
        // CORRECCIÓN: Usamos el formato YYYY-MM-DD que exige Seba
        recepcion.realizarReserva(huesped2.getRut(), huesped2.getNombre(), "2026-06-15", 101);

        System.out.println("\n▶ ESCENARIO 3: Subproceso de Anulación (Camino A - RN-02)");
        System.out.println("---------------------------------------------------------");
        recepcion.anularReserva(101);

        System.out.println("\n=====================================================");
        System.out.println("              SIMULACIÓN FINALIZADA              ");
        System.out.println("=====================================================");
    }
}
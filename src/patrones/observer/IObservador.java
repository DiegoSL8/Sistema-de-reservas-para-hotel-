package patrones.observer;

/**
 * Interfaz que deben implementar todos los objetos que deseen 
 * recibir notificaciones del sistema (Ej: Los Clientes).
 */
public interface IObservador {
    void actualizar(String mensaje);
}
package patrones.observer;

public interface ISujetoObservable {
    void agregarObservador(IObservador o);
    void quitarObservador(IObservador o); //  Se agrego la regla faltante
    void notificarObservadores(String mensaje);
}
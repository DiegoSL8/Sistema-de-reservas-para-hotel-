package modelo;

import patrones.observer.IObservador;

public class Cliente implements IObservador {
    private String nombre;
    private String rut;

    public Cliente(String nombre, String rut) {
        this.nombre = nombre;
        this.rut = rut;
    }

    public String getNombre() { return nombre; }
    public String getRut() { return rut; } 

    @Override
    public void actualizar(String mensaje) {
        // CORRECCIÓN: El cliente solo reacciona si el mensaje menciona su nombre o su RUT
        if (mensaje.contains(this.nombre) || mensaje.contains(this.rut)) {
            System.out.println("   [Observer] 📱 Notificación entrante para " + this.nombre + ":");
            System.out.println("   -> " + mensaje);
        }
    }
}
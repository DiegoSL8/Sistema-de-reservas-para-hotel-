package patrones.facade.subsistemas;

import modelo.Cliente;

public class RegistroHuesped {
    
    public Cliente registrarCliente(String rut, String nombre) {
        System.out.println("[Subsistema: Registro] Procesando datos del huésped: " + nombre + " (RUT: " + rut + ")");
        
        // Creamos la instancia del cliente. 
        // (Cambiar al momento de crear el modelo Cliente).
        return new Cliente(rut, nombre); 
    }
}
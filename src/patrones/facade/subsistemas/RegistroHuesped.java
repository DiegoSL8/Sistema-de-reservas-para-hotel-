package patrones.facade.subsistemas;

import modelo.Cliente;

public class RegistroHuesped {
    
    public Cliente registrarCliente(String rut, String nombre) {
        System.out.println("[Subsistema: Registro] Procesando datos del huésped: " + nombre + " (RUT: " + rut + ")");
        
        // CORRECCIÓN 1: Invertimos el orden para respetar el constructor (nombre, rut)
        return new Cliente(nombre, rut); 
    }
}
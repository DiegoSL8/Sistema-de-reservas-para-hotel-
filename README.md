# Sistema de Reservas de Hotel - Implementación de Patrones de Diseño

## Descripción del Proyecto
Sistema de simulación de reservas hoteleras por consola desarrollado en Java. 
Este proyecto tiene como objetivo principal demostrar la correcta implementación, justificación y orquestación de patrones de diseño de software para resolver problemas de arquitectura, escalabilidad y cohesión. 

El sistema valida reglas de negocio a través de un script de pruebas automatizado, el cual representa el flujo exacto de un modelo de procesos de negocio (BPMN) previamente diseñado.

## Arquitectura y Patrones de Diseño Utilizados

El sistema está construido bajo una Arquitectura por Capas, separando estrictamente el modelo de dominio, la lógica de negocio y la capa de presentación/ejecución. Se implementaron los siguientes patrones de diseño:

1. **Singleton (Patrón Creacional)**
   - **Implementación:** Clase `GestorReservas`.
   - **Justificación:** Garantiza una única fuente de la verdad para el inventario de habitaciones y las reservas activas en memoria. Previene la duplicación de datos y centraliza el control de concurrencia para evitar conflictos de asignación.

2. **Facade (Patrón Estructural)**
   - **Implementación:** Clase `ReservaFacade`.
   - **Justificación:** Oculta la complejidad de la lógica de negocio. Actúa como una interfaz unificada que orquesta el trabajo de tres subsistemas independientes (`ValidadorInventario`, `RegistroHuesped` y `ControladorEstado`), logrando un bajo acoplamiento entre el cliente y las reglas de validación interna.

3. **Observer (Patrón de Comportamiento)**
   - **Implementación:** Interfaces `IObservador`, `ISujetoObservable` y la entidad `Cliente`.
   - **Justificación:** Permite una arquitectura orientada a eventos. Desacopla la lógica de reservas del sistema de notificaciones. Cuando ocurre un cambio de estado crítico (confirmación, rechazo o anulación), el sistema central notifica automáticamente a las entidades suscritas correspondientes.

## Estructura de Directorios

```text
/
├── docs/                             
│   └── (Diagramas UML y BPMN)
│   └── (Informe y Requerimientos)
├── src/
│   ├── Main.java                     
│   │
│   ├── modelo/                       
│   │   ├── Cliente.java              
│   │   ├── Habitacion.java           
│   │   └── Reserva.java              
│   │
│   └── patrones/
│       ├── facade/                   
│       │   ├── ReservaFacade.java    
│       │   └── subsistemas/          
│       │       ├── ControladorEstado.java   
│       │       ├── RegistroHuesped.java     
│       │       └── ValidadorInventario.java 
│       │
│       ├── observer/                 
│       │   ├── IObservador.java       
│       │   └── ISujetoObservable.java 
│       │
│       └── singleton/                
│           └── GestorReservas.java

Escenarios de Prueba Automatizados
La ejecución del sistema no requiere ingreso de datos por teclado. 
El archivo Main.java contiene un script automatizado que simula tres flujos de negocio basados en el BPMN:

Escenario 1 (Camino Normal): Solicitud exitosa. El sistema valida fechas e inventario, crea la reserva, ocupa la habitación y notifica la confirmación.

Escenario 2 (Compuerta Alternativa): Solicitud rechazada. Un segundo usuario intenta reservar una habitación ya ocupada. El sistema bloquea la transacción y notifica el rechazo.

Escenario 3 (Regla de Anulación): Cancelación de reserva. El sistema localiza la reserva existente, libera el inventario de la habitación y notifica la anulación exitosa.

Instrucciones de Ejecución
Requisitos Previos:

Java Development Kit (JDK) 8 o superior.

IDE recomendado: Visual Studio Code (con Extension Pack for Java) o NetBeans.

Pasos:

Clonar este repositorio en la máquina local.

Abrir la carpeta raíz del proyecto en el IDE de preferencia.

Navegar hasta la ruta src/Main.java.

Ejecutar el archivo Main.java. La simulación de los escenarios se imprimirá de manera secuencial a través de la consola.

Autores del Proyecto
Daniel Ledesma: Desarrollo del núcleo de datos, entidades base y patrón Singleton.

Sebastian Cofre: Desarrollo de la lógica de negocio, reglas de validación y patrón Facade.

Diego Sereño: Definición de arquitectura base, patrón Observer, script automatizado e integración general.

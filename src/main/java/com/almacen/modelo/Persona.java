package com.almacen.modelo;

/**
 * Clase abstracta que representa a una persona genérica dentro del sistema.
 *
 * PILAR APLICADO: ABSTRACCIÓN
 * Persona modela el concepto general "persona" quedándose solo con los
 * atributos esenciales que cualquier persona del sistema debe tener
 * (nombre, apellido). No se puede instanciar directamente: es una base
 * conceptual para clases más concretas como Usuario.
 *
 * PILAR APLICADO: ENCAPSULAMIENTO
 * Los atributos son privados y solo se exponen mediante getters/setters,
 * protegiendo el estado interno del objeto de modificaciones directas.
 */
public abstract class Persona {

    private String nombre;
    private String apellido;

    public Persona() {
    }

    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Nombre completo. Método concreto compartido por todas las subclases,
     * demuestra reutilización de código gracias a la herencia.
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}

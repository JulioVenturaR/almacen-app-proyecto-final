package com.almacen.modelo;

import com.almacen.modelo.interfaces.Validable;

/**
 * Representa un producto del almacén.
 *
 * PILAR APLICADO: ENCAPSULAMIENTO
 * Todos los atributos son privados y se acceden mediante getters/setters.
 *
 * PILAR APLICADO: POLIMORFISMO
 * Implementa Validable con una lógica de validación propia y distinta a
 * la de Usuario (ver Usuario.validar()), demostrando que un mismo método
 * de la interfaz se comporta de forma diferente según la clase concreta.
 */
public class Producto implements Validable {

    private int idProducto;
    private String nombre;
    private String marca;
    private String categoria;
    private double precio;
    private int cantidadDisponible;

    public Producto() {
    }

    public Producto(String nombre, String marca, String categoria,
                     double precio, int cantidadDisponible) {
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    /**
     * Implementación específica de Producto para Validable
     * (POLIMORFISMO: distinta a Usuario.validar()).
     */
    @Override
    public String validar() {
        if (nombre == null || nombre.trim().isEmpty()) return "El nombre del producto es obligatorio.";
        if (marca == null || marca.trim().isEmpty()) return "La marca es obligatoria.";
        if (categoria == null || categoria.trim().isEmpty()) return "La categoría es obligatoria.";
        if (precio < 0) return "El precio no puede ser negativo.";
        if (cantidadDisponible < 0) return "La cantidad disponible no puede ser negativa.";
        return null;
    }

    @Override
    public String toString() {
        return nombre + " - " + marca;
    }
}

package com.almacen.modelo.interfaces;

/**
 * Interfaz que define el contrato de validación.
 *
 * PATRÓN / PILAR APLICADO: POLIMORFISMO
 * Tanto Usuario como Producto implementan esta interfaz, pero cada uno
 * define su propia lógica de validación (validar() se comporta distinto
 * según la clase concreta que lo implemente). Esto es polimorfismo por
 * subtipo: el mismo mensaje "validar()" produce comportamientos distintos.
 */
public interface Validable {

    /**
     * Valida los datos del objeto.
     * @return null si es válido, o un mensaje de error describiendo el
     *         primer campo inválido encontrado.
     */
    String validar();
}

package com.almacen.modelo;

import com.almacen.modelo.interfaces.Validable;

/**
 * Representa un usuario registrado en el sistema.
 *
 * PILAR APLICADO: HERENCIA
 * Usuario extiende de Persona, heredando nombre/apellido y reutilizando
 * getNombreCompleto() sin tener que reescribirlo.
 *
 * PILAR APLICADO: ENCAPSULAMIENTO
 * idUser, userName y password son privados; solo accesibles vía getters
 * y setters, evitando que otras clases manipulen el estado directamente.
 *
 * PILAR APLICADO: POLIMORFISMO
 * Implementa Validable con su propia versión de validar(), distinta a la
 * de Producto (ver Producto.validar()).
 */
public class Usuario extends Persona implements Validable {

    private int idUser;
    private String userName;
    private String telefono;
    private String email;
    private String password;

    public Usuario() {
        super();
    }

    public Usuario(String userName, String nombre, String apellido,
                    String telefono, String email, String password) {
        super(nombre, apellido);
        this.userName = userName;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Implementación específica de Usuario para la interfaz Validable
     * (POLIMORFISMO: distinta a Producto.validar()).
     */
    @Override
    public String validar() {
        if (isVacio(getNombre())) return "El nombre es obligatorio.";
        if (isVacio(getApellido())) return "El apellido es obligatorio.";
        if (isVacio(userName)) return "El nombre de usuario es obligatorio.";
        if (isVacio(telefono)) return "El número de teléfono es obligatorio.";
        if (isVacio(email)) return "El correo electrónico es obligatorio.";
        if (isVacio(password)) return "La contraseña es obligatoria.";
        return null;
    }

    private boolean isVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    @Override
    public String toString() {
        return getNombreCompleto() + " (" + userName + ")";
    }
}

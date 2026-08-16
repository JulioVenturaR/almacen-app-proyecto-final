package com.almacen.dao;

import java.util.List;

/**
 * PATRÓN DE DISEÑO APLICADO: DAO (Data Access Object)
 *
 * Define un contrato genérico para el acceso a datos, separando por
 * completo la lógica de persistencia (SQL / JDBC) de la lógica de negocio
 * y de la interfaz gráfica. UsuarioDAO y ProductoDAO implementan esta
 * interfaz, cada uno encapsulando las consultas propias de su tabla.
 *
 * @param <T> tipo de entidad que maneja el DAO (Usuario, Producto, etc.)
 */
public interface IDAO<T> {
    boolean insertar(T entidad);
    boolean actualizar(T entidad);
    boolean eliminar(int id);
    List<T> listarTodos();
}

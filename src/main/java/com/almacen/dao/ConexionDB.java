package com.almacen.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * PATRÓN DE DISEÑO APLICADO: SINGLETON
 *
 * Garantiza que exista una única instancia de la conexión a la base de
 * datos en toda la aplicación, y provee un punto de acceso global a ella
 * mediante getInstancia(). Esto evita abrir conexiones innecesarias y
 * centraliza la configuración de la base de datos en un solo lugar.
 *
 * Implementación "lazy": la conexión solo se crea la primera vez que se
 * solicita (getInstancia()), no al cargar la clase.
 */
public class ConexionDB {

    // --- Datos de conexión a la base de datos local (MySQL en localhost) ---
    private static final String URL =
            "jdbc:mysql://localhost:3306/almacenitlafinal" +
                    "?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    // Única instancia de la clase (núcleo del patrón Singleton)
    private static ConexionDB instancia;

    private Connection conexion;

    // Constructor privado: nadie fuera de esta clase puede hacer "new ConexionDB()"
    private ConexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("No se pudo conectar a la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Punto de acceso global al Singleton. Si la conexión se cerró o
     * nunca se abrió, crea una nueva; si ya existe y está viva, la reutiliza.
     */
    public static Connection getInstancia() {
        try {
            if (instancia == null) {
                instancia = new ConexionDB();
            } else if (instancia.conexion == null || instancia.conexion.isClosed()) {
                instancia = new ConexionDB();
            }
        } catch (SQLException e) {
            instancia = new ConexionDB();
        }
        return instancia.conexion;
    }
}

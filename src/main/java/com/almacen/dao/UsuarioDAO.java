package com.almacen.dao;

import com.almacen.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PATRÓN DE DISEÑO APLICADO: DAO
 * Encapsula todo el acceso SQL relacionado a la tabla "usuarios".
 * Columnas reales en la BD: idUser, UserName, Nombre, Apellido, Telefono, Email, Password.
 */
public class UsuarioDAO implements IDAO<Usuario> {

    @Override
    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO usuarios (UserName, Nombre, Apellido, Telefono, Email, Password) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexionDB.getInstancia().prepareStatement(sql)) {
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuarios SET UserName = ?, Nombre = ?, Apellido = ?, Telefono = ?, " +
                     "Email = ?, Password = ? WHERE idUser = ?";
        try (PreparedStatement ps = ConexionDB.getInstancia().prepareStatement(sql)) {
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getPassword());
            ps.setInt(7, u.getIdUser());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int idUser) {
        String sql = "DELETE FROM usuarios WHERE idUser = ?";
        try (PreparedStatement ps = ConexionDB.getInstancia().prepareStatement(sql)) {
            ps.setInt(1, idUser);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT idUser, UserName, Nombre, Apellido, Telefono, Email, Password FROM usuarios";
        try (PreparedStatement ps = ConexionDB.getInstancia().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /** Busca un usuario por su nombre de usuario (para el login). */
    public Usuario buscarPorUserName(String userName) {
        String sql = "SELECT idUser, UserName, Nombre, Apellido, Telefono, Email, Password " +
                     "FROM usuarios WHERE UserName = ?";
        try (PreparedStatement ps = ConexionDB.getInstancia().prepareStatement(sql)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUser(rs.getInt("idUser"));
        u.setUserName(rs.getString("UserName"));
        u.setNombre(rs.getString("Nombre"));
        u.setApellido(rs.getString("Apellido"));
        u.setTelefono(rs.getString("Telefono"));
        u.setEmail(rs.getString("Email"));
        u.setPassword(rs.getString("Password"));
        return u;
    }
}

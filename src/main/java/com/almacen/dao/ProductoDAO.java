package com.almacen.dao;

import com.almacen.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PATRÓN DE DISEÑO APLICADO: DAO
 * Encapsula todo el acceso SQL relacionado a la tabla "productos".
 * Columnas reales en la BD: idProducto, NombreProducto, MarcaProducto,
 * CategoriaProducto, PrecioProducto, StockProducto.
 */
public class ProductoDAO implements IDAO<Producto> {

    @Override
    public boolean insertar(Producto p) {
        String sql = "INSERT INTO productos (NombreProducto, MarcaProducto, CategoriaProducto, " +
                     "PrecioProducto, StockProducto) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexionDB.getInstancia().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getCantidadDisponible());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Producto p) {
        String sql = "UPDATE productos SET NombreProducto = ?, MarcaProducto = ?, CategoriaProducto = ?, " +
                     "PrecioProducto = ?, StockProducto = ? WHERE idProducto = ?";
        try (PreparedStatement ps = ConexionDB.getInstancia().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getCantidadDisponible());
            ps.setInt(6, p.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int idProducto) {
        String sql = "DELETE FROM productos WHERE idProducto = ?";
        try (PreparedStatement ps = ConexionDB.getInstancia().prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT idProducto, NombreProducto, MarcaProducto, CategoriaProducto, " +
                     "PrecioProducto, StockProducto FROM productos";
        try (PreparedStatement ps = ConexionDB.getInstancia().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("NombreProducto"));
                p.setMarca(rs.getString("MarcaProducto"));
                p.setCategoria(rs.getString("CategoriaProducto"));
                p.setPrecio(rs.getDouble("PrecioProducto"));
                p.setCantidadDisponible(rs.getInt("StockProducto"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

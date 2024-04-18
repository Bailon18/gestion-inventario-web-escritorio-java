package com.micompania.desktop.inventario.negocio.DAO;

import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.Proveedor;
import com.micompania.desktop.inventario.interfaces.ProveedorInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO implements ProveedorInterface{

    @Override
    public void agregarProveedor(Proveedor proveedor) {
        String sql = "INSERT INTO Proveedor (nombre, direccion, telefono, correo) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConeccion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getDireccion());
            pstmt.setString(3, proveedor.getTelefono());
            pstmt.setString(4, proveedor.getCorreo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    
    @Override
    public void actualizarProveedor(Proveedor proveedor) {
        String sql = "UPDATE Proveedor SET nombre=?, direccion=?, telefono=?, correo=? WHERE idProveedor=?";
        try (Connection conn = Conexion.getConeccion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getDireccion());
            pstmt.setString(3, proveedor.getTelefono());
            pstmt.setString(4, proveedor.getCorreo());
            pstmt.setInt(5, proveedor.getIdProveedor());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void eliminarProveedor(int idProveedor) {
        String sql = "DELETE FROM Proveedor WHERE idProveedor=?";
        try (Connection conn = Conexion.getConeccion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProveedor);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public Proveedor buscarProveedor(int idProveedor) {
        String sql = "SELECT * FROM Proveedor WHERE idProveedor=?";
        try (Connection conn = Conexion.getConeccion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProveedor);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return obtenerProveedorDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Proveedor> obtenerTodosLosProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor";
        try (Connection conn = Conexion.getConeccion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                proveedores.add(obtenerProveedorDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return proveedores;
    }

    
    private Proveedor obtenerProveedorDesdeResultSet(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(rs.getInt("idProveedor"));
        proveedor.setNombre(rs.getString("nombre"));
        proveedor.setDireccion(rs.getString("direccion"));
        proveedor.setTelefono(rs.getString("telefono"));
        proveedor.setCorreo(rs.getString("correo"));
        return proveedor;
    }
}

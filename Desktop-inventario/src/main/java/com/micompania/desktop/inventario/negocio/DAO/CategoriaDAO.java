package com.micompania.desktop.inventario.negocio.DAO;

import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.Categoria;
import com.micompania.desktop.inventario.interfaces.CategoriaInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements CategoriaInterface {

    @Override
    public Categoria buscarCategoriaPorId(int idCategoria) {
        Categoria categoria = null;
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Categoria WHERE idCategoria = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idCategoria);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        categoria = obtenerCategoriaDesdeResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return categoria;
    }

    @Override
    public List<Categoria> obtenerTodasLasCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Categoria";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        categorias.add(obtenerCategoriaDesdeResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return categorias;
    }

    private Categoria obtenerCategoriaDesdeResultSet(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(rs.getInt("idCategoria"));
        categoria.setNombre(rs.getString("nombre"));
        categoria.setDescripcion(rs.getString("descripcion"));
        return categoria;
    }
}

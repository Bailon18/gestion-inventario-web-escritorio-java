package com.micompania.desktop.inventario.negocio.DAO;

import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.Inventario;
import com.micompania.desktop.inventario.entidad.Producto;
import com.micompania.desktop.inventario.interfaces.InventarioInterface;
import com.micompania.desktop.inventario.negocio.Services.ProductoService;
import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO implements InventarioInterface {

    private final ProductoService productoService;

    public InventarioDAO(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    @Override
    public void agregarInventario(Inventario inventario) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "INSERT INTO Inventario (idProducto, producto, salida, ingreso, stockActual) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, inventario.getProducto().getIdProducto());
                pstmt.setString(2, inventario.getProductonombre());
                pstmt.setInt(3, inventario.getSalida());
                pstmt.setInt(4, inventario.getIngreso());
                pstmt.setInt(5, inventario.getStockActual());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void actualizarInventario(Inventario inventario) {

    }

    @Override
    public void eliminarInventario(int idProducto) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "DELETE FROM Inventario WHERE idProducto = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idProducto);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public Inventario buscarInventario(int idProducto) {
        Inventario inventario = null;
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Inventario WHERE idProducto = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idProducto);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        inventario = obtenerInventarioDesdeResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return inventario;
    }

    @Override
    public List<Inventario> obtenerTodosLosInventarios() {
        List<Inventario> inventarios = new ArrayList<>();
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Inventario ORDER BY idInventario DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        inventarios.add(obtenerInventarioDesdeResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return inventarios;
    }

    private Inventario obtenerInventarioDesdeResultSet(ResultSet rs) throws SQLException {
        Inventario inventario = new Inventario();

        // Obtener el producto asociado al inventario
        Producto producto = productoService.buscarProducto(rs.getInt("idProducto"));
        //System.out.println("PRODUCTO: "+ producto);

        // Establecer los valores del inventario
        inventario.setIdInventario(rs.getInt("idInventario"));
        inventario.setProducto(producto);
        inventario.setProductonombre(producto.getNombre());
        inventario.setIngreso(rs.getInt("ingreso"));
        inventario.setSalida(rs.getInt("salida"));
        inventario.setStockActual(rs.getInt("stockActual"));

        return inventario;
    }

    @Override
    public List<Inventario> buscarInventarioPorCriterio(String valorBusqueda) {
       
        List<Inventario> inventarios = new ArrayList<>();

        try (Connection conn = Conexion.getConeccion()) {
            String sql = "{CALL BuscarInventario(?)}";
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, valorBusqueda);
                try (ResultSet rs = cstmt.executeQuery()) {
                    while (rs.next()) {
                        inventarios.add(obtenerInventarioDesdeResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return inventarios;
        
    }

}

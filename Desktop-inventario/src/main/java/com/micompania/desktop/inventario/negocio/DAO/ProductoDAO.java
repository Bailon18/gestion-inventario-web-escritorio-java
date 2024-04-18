package com.micompania.desktop.inventario.negocio.DAO;

import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.Categoria;
import com.micompania.desktop.inventario.entidad.Producto;
import com.micompania.desktop.inventario.entidad.Proveedor;
import com.micompania.desktop.inventario.interfaces.ProductoInterface;
import com.micompania.desktop.inventario.negocio.DTO.EstadisticasIngresoSalidaDTO;
import com.micompania.desktop.inventario.negocio.DTO.MovimientoProductoDTO;
import com.micompania.desktop.inventario.negocio.Services.CategoriaService;
import com.micompania.desktop.inventario.negocio.Services.ProveedorService;
import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductoDAO implements ProductoInterface {

    private final CategoriaService categoriaService;
    private final ProveedorService proveedorService;

    public ProductoDAO(CategoriaService categoriaService, ProveedorService proveedorService) {
        this.categoriaService = categoriaService;
        this.proveedorService = proveedorService;
    }

    @Override
    public void agregarProducto(Producto producto) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "INSERT INTO Producto (nombre, descripcion, precio, idCategoria, idProveedor, stockMinimo, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, producto.getNombre());
                pstmt.setString(2, producto.getDescripcion());
                pstmt.setDouble(3, producto.getPrecio());
                pstmt.setInt(4, producto.getCategoria().getIdCategoria());
                pstmt.setInt(5, producto.getProveedor().getIdProveedor());
                pstmt.setInt(6, producto.getStockMinimo());
                pstmt.setBoolean(7, producto.isActivo());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void actualizarProducto(Producto producto) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "UPDATE Producto SET nombre = ?, descripcion = ?, precio = ?, idCategoria = ?, idProveedor = ?, stockMinimo = ?, activo = ? WHERE idProducto = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, producto.getNombre());
                pstmt.setString(2, producto.getDescripcion());
                pstmt.setDouble(3, producto.getPrecio());
                pstmt.setInt(4, producto.getCategoria().getIdCategoria());
                pstmt.setInt(5, producto.getProveedor().getIdProveedor());
                pstmt.setInt(6, producto.getStockMinimo());
                pstmt.setBoolean(7, producto.isActivo());
                pstmt.setInt(8, producto.getIdProducto());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void cambiarEstadoProducto(int idProducto, boolean activo) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "UPDATE Producto SET activo = ? WHERE idProducto = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBoolean(1, activo);
                pstmt.setInt(2, idProducto);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public Producto buscarProducto(int idProducto) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Producto WHERE idProducto = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idProducto);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return obtenerProductoDesdeResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Producto WHERE activo = 1  ORDER BY idProducto DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        productos.add(obtenerProductoDesdeResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return productos;
    }

    private Producto obtenerProductoDesdeResultSet(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setIdProducto(rs.getInt("idProducto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setEstado(rs.getString("estado"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getDouble("precio"));

        // Obtener detalles adicionales de la categoría y proveedor
        int idCategoria = rs.getInt("idCategoria");
        int idProveedor = rs.getInt("idProveedor");
        boolean activo = rs.getBoolean("activo");

        // Utilizar el CategoriaService para obtener la categoría
        Categoria categoria = categoriaService.buscarCategoriaPorId(idCategoria);
        producto.setCategoria(categoria);

        // Utilizar el ProveedorService para obtener el proveedor
        Proveedor proveedor = proveedorService.buscarProveedor(idProveedor);
        producto.setProveedor(proveedor);

        int stockMinimo = rs.getInt("stockMinimo");
        producto.setStockMinimo(stockMinimo);
        producto.setActivo(activo);

        return producto;
    }

    @Override
    public List<Producto> buscarProductoPorCriterio(String valorBusqueda) {
        List<Producto> productos = new ArrayList<>();

        try (Connection conn = Conexion.getConeccion()) {
            String sql = "{CALL BuscarProducto(?)}";
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, valorBusqueda);
                try (ResultSet rs = cstmt.executeQuery()) {
                    while (rs.next()) {
                        productos.add(obtenerProductoDesdeResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return productos;
    }

    @Override
    public List<MovimientoProductoDTO> obtenerMovimientosGenerales() {
        List<MovimientoProductoDTO> movimientos = new ArrayList<>();

        try (Connection conn = Conexion.getConeccion(); CallableStatement stmt = conn.prepareCall("CALL movimientoproductogeneral()"); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MovimientoProductoDTO movimiento = obtenerMovimiento(rs);
                movimientos.add(movimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return movimientos;
    }

    @Override
    public List<MovimientoProductoDTO> obtenerMovimientosPorProductoNombre(String campo_busqueda) {
        List<MovimientoProductoDTO> movimientos = new ArrayList<>();

        try (Connection conn = Conexion.getConeccion(); CallableStatement stmt = conn.prepareCall("CALL movimientoporproductonombre(?)")) {
            stmt.setString(1, campo_busqueda);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MovimientoProductoDTO movimiento = obtenerMovimiento(rs);
                    movimientos.add(movimiento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return movimientos;
    }

    @Override
    public List<MovimientoProductoDTO> obtenerMovimientosPorProductoNombreTipoMovimiento(String campo_busqueda, String tipo) {
        List<MovimientoProductoDTO> movimientos = new ArrayList<>();

        try (Connection conn = Conexion.getConeccion(); CallableStatement stmt = conn.prepareCall("CALL movimientoporproductonombreymovimiento(?, ?)")) {
            stmt.setString(1, campo_busqueda);
            stmt.setString(2, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MovimientoProductoDTO movimiento = obtenerMovimiento(rs);
                    movimientos.add(movimiento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return movimientos;
    }

    private MovimientoProductoDTO obtenerMovimiento(ResultSet rs) throws SQLException {
        int idProducto = rs.getInt("ID_Producto");
        Date fecha = rs.getDate("Fecha");
        String nombreProducto = rs.getString("NombreProducto");
        int cantidad = rs.getInt("Cantidad");
        String clienteProveedor = rs.getString("Cliente / Proveedor");
        String tipoMovimiento = rs.getString("TipoMovimiento");
        return new MovimientoProductoDTO(idProducto, fecha, nombreProducto, cantidad, clienteProveedor, tipoMovimiento);
    }

    @Override
    public List<EstadisticasIngresoSalidaDTO> obtenerEstadisticasIngresoSalida() {
        List<EstadisticasIngresoSalidaDTO> estadisticas = new ArrayList<>();

        try (Connection conn = Conexion.getConeccion(); CallableStatement stmt = conn.prepareCall("{CALL obtener_estadisticas_ingreso_salida()}")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String mes = rs.getString("mes");
                    int totalIngreso = rs.getInt("total_ingreso");
                    int totalSalida = rs.getInt("total_salida");
                    estadisticas.add(new EstadisticasIngresoSalidaDTO(mes, totalIngreso, totalSalida));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return estadisticas;
    }

}

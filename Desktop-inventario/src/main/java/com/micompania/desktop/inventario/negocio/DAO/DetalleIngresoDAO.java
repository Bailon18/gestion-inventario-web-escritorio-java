package com.micompania.desktop.inventario.negocio.DAO;

import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.DetalleIngreso;
import com.micompania.desktop.inventario.entidad.Producto;
import com.micompania.desktop.inventario.interfaces.DetalleIngresoInterface;
import com.micompania.desktop.inventario.negocio.Services.CategoriaService;
import com.micompania.desktop.inventario.negocio.Services.ProductoService;
import com.micompania.desktop.inventario.negocio.Services.ProveedorService;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetalleIngresoDAO implements DetalleIngresoInterface {

    private ProductoService productoService = new ProductoService(new ProductoDAO(new CategoriaService(), new ProveedorService()));

    @Override
    public void insertarDetallesIngreso(List<DetalleIngreso> detallesIngreso, int idIngreso) {
        try (Connection connection = Conexion.getConeccion()) {
            String sql = "INSERT INTO detalle_ingreso (cantidad, idProducto, idIngreso) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (DetalleIngreso detalle : detallesIngreso) {
                    preparedStatement.setInt(1, detalle.getCantidad());
                    preparedStatement.setInt(2, detalle.getProducto().getIdProducto());
                    preparedStatement.setInt(3, idIngreso);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public boolean insertarDetallesIngreso(List<DetalleIngreso> detallesIngreso) {
        boolean insercionExitosa = false;
        try (Connection connection = Conexion.getConeccion()) {
            String sql = "INSERT INTO detalle_ingreso (cantidad, idProducto, idIngreso) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (DetalleIngreso detalle : detallesIngreso) {
                    preparedStatement.setInt(1, detalle.getCantidad());
                    preparedStatement.setInt(2, detalle.getProducto().getIdProducto());
                    preparedStatement.setInt(3, detalle.getIngreso().getIdIngreso());
                    preparedStatement.addBatch();
                }
                int[] resultados = preparedStatement.executeBatch();
                // Verificar si todos los registros se insertaron exitosamente
                insercionExitosa = Arrays.stream(resultados).allMatch(result -> result == 1);
            }
        } catch (SQLException e) {
            // Manejar la excepción
        }
        return insercionExitosa;
    }

    public List<DetalleIngreso> obtenerDetallesIngreso(int idIngreso) {

        List<DetalleIngreso> detallesIngreso = new ArrayList<>();

        try (Connection connection = Conexion.getConeccion()) {
            String sql = "{CALL ObtenerDetallesIngreso(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(sql)) {
                callableStatement.setInt(1, idIngreso);

                try (ResultSet resultSet = callableStatement.executeQuery()) {
                    while (resultSet.next()) {
                        DetalleIngreso detalle = convertirResultSetADetalleIngreso(resultSet);
                        detallesIngreso.add(detalle);
                    }
                }
            }
        } catch (SQLException e) {
        }

        return detallesIngreso;
    }

    private DetalleIngreso convertirResultSetADetalleIngreso(ResultSet resultSet) throws SQLException {
        DetalleIngreso detalle = new DetalleIngreso();

        detalle.setIdDetalleIngreso(resultSet.getInt("idDetalleIngreso"));
        detalle.setCantidad(resultSet.getInt("cantidad"));

        int idProducto = resultSet.getInt("idProducto");
        if (idProducto != 0) {
            Producto producto = this.productoService.buscarProducto(idProducto);
            if (producto != null) {
                detalle.setProducto(producto);
                detalle.getProducto().setNombre(resultSet.getString("nombre_producto"));
            }
        }

        return detalle;
    }

}

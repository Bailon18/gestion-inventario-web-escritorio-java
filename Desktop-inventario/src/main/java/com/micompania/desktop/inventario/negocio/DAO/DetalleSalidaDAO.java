
package com.micompania.desktop.inventario.negocio.DAO;

import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.DetalleSalida;
import com.micompania.desktop.inventario.entidad.Producto;
import com.micompania.desktop.inventario.interfaces.DetalleSalidaInterface;
import com.micompania.desktop.inventario.negocio.Services.CategoriaService;
import com.micompania.desktop.inventario.negocio.Services.ProductoService;
import com.micompania.desktop.inventario.negocio.Services.ProveedorService;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DetalleSalidaDAO implements DetalleSalidaInterface{
    
    private ProductoService productoService = new ProductoService(new ProductoDAO(new CategoriaService(), new ProveedorService()));

    @Override
    public void insertarDetallesSalida(List<DetalleSalida> detallesSalida, int idSalida) {
        try (Connection connection = Conexion.getConeccion()) {
            String sql = "INSERT INTO detalle_salida (cantidad, idProducto, idSalida) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (DetalleSalida detalle : detallesSalida) {
                    preparedStatement.setInt(1, detalle.getCantidad());
                    preparedStatement.setInt(2, detalle.getProducto().getIdProducto());
                    preparedStatement.setInt(3, idSalida);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
  

        }
    }
    
    
    @Override
    public void insertarDetallesSalida(List<DetalleSalida> detallesSalida) {
        try (Connection connection = Conexion.getConeccion()) {
            String sql = "INSERT INTO detalle_salida (cantidad, idProducto, idSalida) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (DetalleSalida detalle : detallesSalida) {
                    preparedStatement.setInt(1, detalle.getCantidad());
                    preparedStatement.setInt(2, detalle.getProducto().getIdProducto());
                    preparedStatement.setInt(3, detalle.getSalida().getIdSalida());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
  

        }
    }
    
    public List<DetalleSalida> obtenerDetallesSalida(int idSalida) {
        List<DetalleSalida> detallesSalida = new ArrayList<>();

        try (Connection connection = Conexion.getConeccion()) {
            String sql = "{CALL ObtenerDetallesSalida(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(sql)) {
                callableStatement.setInt(1, idSalida);

                try (ResultSet resultSet = callableStatement.executeQuery()) {
                    while (resultSet.next()) {
                        DetalleSalida detalle = convertirResultSetADetalleSalida(resultSet);
                        detallesSalida.add(detalle);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detallesSalida;
    }


    
    private DetalleSalida convertirResultSetADetalleSalida(ResultSet resultSet) throws SQLException {
        DetalleSalida detalle = new DetalleSalida();

        detalle.setIdDetalleSalida(resultSet.getInt("idDetalle_salida"));
        detalle.setCantidad(resultSet.getInt("cantidad"));

        int idProducto = resultSet.getInt("idProducto");
        if (idProducto != 0) {  
            Producto producto = this.productoService.buscarProducto(idProducto);
            if (producto != null) {

                detalle.setProducto(producto);
                String nombreProducto = resultSet.getString("nombre_producto");
                detalle.getProducto().setNombre(nombreProducto);
            } 
        }

        return detalle;
    }

    

}

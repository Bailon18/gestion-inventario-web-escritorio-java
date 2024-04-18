package com.micompania.desktop.inventario.negocio.DAO;

import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.ENUM.TipoMovimiento;
import com.micompania.desktop.inventario.entidad.IngresoInventario;
import com.micompania.desktop.inventario.interfaces.IngresoInventarioInterface;
import com.micompania.desktop.inventario.negocio.Services.ProveedorService;
import com.micompania.desktop.inventario.negocio.Services.UsuarioService;
import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngresoInventarioDAO implements IngresoInventarioInterface {

    
    private UsuarioService usuariservice = new UsuarioService(new UsuarioDAO());
    private ProveedorService proveedorService = new ProveedorService();
    
    @Override
    public IngresoInventario realizarIngresoInventario(IngresoInventario ingresoInventario) {
        try (Connection connection = Conexion.getConeccion()) {
            String sql = "INSERT INTO ingresoinventario (fecha, idUsuario, tipo, idProveedor) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, ingresoInventario.getFecha());
                preparedStatement.setInt(2, ingresoInventario.getUsuario().getIdUsuario());
                preparedStatement.setString(3, ingresoInventario.getTipoMovimiento().name());
                preparedStatement.setInt(4, ingresoInventario.getProveedor().getIdProveedor());

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ingresoInventario.setIdIngreso(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("No se generó la clave primaria para el ingreso.");
                    }
                }
            }
        } catch (SQLException e) {
          
        }
        return ingresoInventario;
    }


    @Override
    public IngresoInventario buscarIngresoInventario(int idIngreso) {
        IngresoInventario ingresoInventario = null;
        try (Connection connection = Conexion.getConeccion()) {
            String sql = "SELECT * FROM ingresoinventario WHERE idIngreso = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idIngreso);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        ingresoInventario = obtenerIngresoInventaruiDesdeResultSet(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
        }
        return ingresoInventario;
    }

    @Override
    public List<IngresoInventario> obtenerTodosLosIngresos() {
        List<IngresoInventario> ingresos = new ArrayList<>();
        try (Connection connection = Conexion.getConeccion()) {
            String sql = "SELECT * FROM ingresoinventario ORDER BY fecha DESC";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ingresos.add(obtenerIngresoInventaruiDesdeResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
        }
        return ingresos;
    }
    
    @Override
    public List<IngresoInventario> buscarIngresosPorFecha(String fechaInicio, String fechaFin) {
        List<IngresoInventario> ingresos = new ArrayList<>();

        try (Connection connection = Conexion.getConeccion()) {
            String sql = "SELECT * FROM ingresoinventario WHERE fecha BETWEEN ? AND ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, fechaInicio);
                preparedStatement.setString(2, fechaFin);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        ingresos.add(obtenerIngresoInventaruiDesdeResultSet(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingresos;
    }





    private IngresoInventario obtenerIngresoInventaruiDesdeResultSet(ResultSet rs) throws SQLException {
        
 
        IngresoInventario ingreso = new IngresoInventario();
        ingreso.setIdIngreso(rs.getInt("idIngreso"));
        ingreso.setFecha(rs.getString("fecha"));
        
        ingreso.setUsuario( usuariservice.buscarUsuario(rs.getInt("idUsuario")));
        ingreso.setTipoMovimiento(TipoMovimiento.valueOf(rs.getString("tipo")));
        ingreso.setProveedor(proveedorService.buscarProveedor(rs.getInt("idProveedor")));
        
        return ingreso;
    }



}

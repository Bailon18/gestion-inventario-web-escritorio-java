package com.micompania.desktop.inventario.negocio.DAO;

import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.ENUM.TipoMovimiento;
import com.micompania.desktop.inventario.entidad.SalidaInventario;
import com.micompania.desktop.inventario.interfaces.SalidaInventarioInterface;
import com.micompania.desktop.inventario.negocio.Services.ClienteService;
import com.micompania.desktop.inventario.negocio.Services.UsuarioService;
import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalidaInventarioDAO implements SalidaInventarioInterface {

    
    private UsuarioService usuariservice = new UsuarioService(new UsuarioDAO());
    private ClienteService clienteService = new ClienteService();
    
    @Override
    public SalidaInventario realizarSalidaInventario(SalidaInventario salidaInventario) {
        
        try (Connection connection = Conexion.getConeccion()) {
            String sql = "INSERT INTO salidainventario (fecha, idUsuario, tipo, idCliente) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, salidaInventario.getFecha());
                preparedStatement.setInt(2, salidaInventario.getUsuario().getIdUsuario());
                preparedStatement.setString(3, salidaInventario.getTipoMovimiento().name());
                preparedStatement.setInt(4, salidaInventario.getCliente().getIdCliente());

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        salidaInventario.setIdSalida(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("No se generó la clave primaria para el ingreso.");
                    }
                }
            }
        } catch (SQLException e) {
          
        }
        return salidaInventario;
    }

    @Override
    public List<SalidaInventario> buscarSalidasPorFecha(String fechaInicio, String fechaFin) {
        List<SalidaInventario> salidas = new ArrayList<>();

        try (Connection connection = Conexion.getConeccion()) {
            String sql = "SELECT * FROM salidainventario WHERE fecha BETWEEN ? AND ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, fechaInicio);
                preparedStatement.setString(2, fechaFin);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        salidas.add(obtenerSalidaInventaruiDesdeResultSet(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }

        return salidas;
    }


    @Override
    public SalidaInventario buscarSalidaInventario(int idSalida) {
        SalidaInventario salidaInventario = null;
        try (Connection connection = Conexion.getConeccion()) {
            String sql = "SELECT * FROM salidainventario WHERE idSalida = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idSalida);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        salidaInventario = obtenerSalidaInventaruiDesdeResultSet(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
        return salidaInventario;
    }

    @Override
    public List<SalidaInventario> obtenerTodasLasSalidas() {
        List<SalidaInventario> salidas = new ArrayList<>();
        try (Connection connection = Conexion.getConeccion()) {
            String sql = "SELECT * FROM salidainventario ORDER BY fecha DESC";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    salidas.add(obtenerSalidaInventaruiDesdeResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
        return salidas;
    }

    private SalidaInventario obtenerSalidaInventaruiDesdeResultSet(ResultSet rs) throws SQLException {
        
 
        SalidaInventario salida = new SalidaInventario();
        salida.setIdSalida(rs.getInt("idSalida"));
        salida.setFecha(rs.getString("fecha"));
        salida.setUsuario( usuariservice.buscarUsuario(rs.getInt("idUsuario")));
        salida.setTipoMovimiento(TipoMovimiento.valueOf(rs.getString("tipo")));
        salida.setCliente(clienteService.buscarClientec(rs.getInt("idCliente")));
        
        return salida;
    }



}

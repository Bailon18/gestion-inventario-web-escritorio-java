
package com.micompania.desktop.inventario.negocio.DAO;

import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.Cliente;
import com.micompania.desktop.inventario.entidad.ENUM.TipoCliente;
import com.micompania.desktop.inventario.interfaces.ClienteInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO implements ClienteInterface{

    @Override
    public Cliente buscarClientec(int idCliente) {
        Cliente cliente = null;

        try {
            String sql = "SELECT * FROM Cliente WHERE idCliente = ?";
            try (Connection connection = Conexion.getConeccion();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idCliente);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        cliente = obtenerClienteDesdeResultSet(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
        }
        return cliente;
    }


    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Cliente";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        clientes.add(obtenerClienteDesdeResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return clientes;
    }
    
    private Cliente obtenerClienteDesdeResultSet(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setIdCliente(rs.getInt("idCliente"));
        cliente.setNombre(rs.getString("nombre"));
        String tipoClienteString = rs.getString("tipoCliente");
        TipoCliente tipoCliente = TipoCliente.valueOf(tipoClienteString); // enum
        cliente.setTipoCliente(tipoCliente);
        cliente.setIdentificacion(rs.getString("identificacion"));

        return cliente;
    }

}

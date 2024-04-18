
package com.micompania.desktop.inventario.negocio.Services;

import com.micompania.desktop.inventario.entidad.Cliente;
import com.micompania.desktop.inventario.interfaces.ClienteInterface;
import com.micompania.desktop.inventario.negocio.DAO.ClienteDAO;
import java.util.List;


public class ClienteService implements ClienteInterface{

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }
    
    @Override
    public Cliente buscarClientec(int idCliente) {
        return this.clienteDAO.buscarClientec(idCliente);
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return this.clienteDAO.obtenerTodosLosClientes();
    }
    
}

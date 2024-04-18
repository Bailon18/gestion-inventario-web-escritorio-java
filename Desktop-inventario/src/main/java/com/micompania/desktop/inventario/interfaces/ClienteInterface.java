package com.micompania.desktop.inventario.interfaces;

import com.micompania.desktop.inventario.entidad.Cliente;
import java.util.List;


public interface ClienteInterface {
    
    Cliente buscarClientec (int idCliente);
    List<Cliente> obtenerTodosLosClientes();
}

package com.micompania.desktop.inventario.negocio.Services;

import com.micompania.desktop.inventario.entidad.Inventario;
import com.micompania.desktop.inventario.interfaces.InventarioInterface;

import java.util.List;

public class InventarioService implements InventarioInterface {

    private final InventarioInterface inventarioDAO;

    public InventarioService(InventarioInterface inventarioDAO) {
        this.inventarioDAO = inventarioDAO;
    }

    @Override
    public void agregarInventario(Inventario inventario) {
        inventarioDAO.agregarInventario(inventario);
    }

    @Override
    public void actualizarInventario(Inventario inventario) {
        inventarioDAO.actualizarInventario(inventario);
    }

    @Override
    public void eliminarInventario(int idProducto) {
        inventarioDAO.eliminarInventario(idProducto);
    }

    @Override
    public Inventario buscarInventario(int idProducto) {
        return inventarioDAO.buscarInventario(idProducto);
    }

    @Override
    public List<Inventario> obtenerTodosLosInventarios() {
        return inventarioDAO.obtenerTodosLosInventarios();
    }

    @Override
    public List<Inventario> buscarInventarioPorCriterio(String valorBusqueda) {
        return inventarioDAO.buscarInventarioPorCriterio(valorBusqueda);
    }
}

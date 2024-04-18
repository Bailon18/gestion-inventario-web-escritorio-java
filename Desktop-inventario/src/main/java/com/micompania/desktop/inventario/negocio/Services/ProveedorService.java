package com.micompania.desktop.inventario.negocio.Services;

import com.micompania.desktop.inventario.negocio.DAO.ProveedorDAO;
import com.micompania.desktop.inventario.entidad.Proveedor;
import com.micompania.desktop.inventario.interfaces.ProveedorInterface;

import java.util.List;

public class ProveedorService implements ProveedorInterface {

    private final ProveedorDAO proveedorDAO;

    public ProveedorService() {
        this.proveedorDAO = new ProveedorDAO();
    }

    @Override
    public void agregarProveedor(Proveedor proveedor) {
        proveedorDAO.agregarProveedor(proveedor);
    }

    @Override
    public void actualizarProveedor(Proveedor proveedor) {
        proveedorDAO.actualizarProveedor(proveedor);
    }

    @Override
    public void eliminarProveedor(int idProveedor) {
        proveedorDAO.eliminarProveedor(idProveedor);
    }

    @Override
    public Proveedor buscarProveedor(int idProveedor) {
        return proveedorDAO.buscarProveedor(idProveedor);
    }

    @Override
    public List<Proveedor> obtenerTodosLosProveedores() {
        return proveedorDAO.obtenerTodosLosProveedores();
    }
}

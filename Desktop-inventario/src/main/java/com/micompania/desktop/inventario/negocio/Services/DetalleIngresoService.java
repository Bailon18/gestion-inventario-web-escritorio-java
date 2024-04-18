
package com.micompania.desktop.inventario.negocio.Services;

import com.micompania.desktop.inventario.entidad.DetalleIngreso;
import com.micompania.desktop.inventario.interfaces.DetalleIngresoInterface;
import com.micompania.desktop.inventario.negocio.DAO.DetalleIngresoDAO;
import java.util.List;


public class DetalleIngresoService implements DetalleIngresoInterface{

    
    private final DetalleIngresoDAO ingresoDAO;

   
    public DetalleIngresoService(DetalleIngresoDAO ingresoDAO) {
        this.ingresoDAO = ingresoDAO;
    }
    
    @Override
    public void insertarDetallesIngreso(List<DetalleIngreso> detallesIngreso, int idIngreso) {
        this.ingresoDAO.insertarDetallesIngreso(detallesIngreso, idIngreso);
    }

    @Override
    public List<DetalleIngreso> obtenerDetallesIngreso(int idIngreso) {
        return this.ingresoDAO.obtenerDetallesIngreso(idIngreso);
    }

    @Override
    public boolean insertarDetallesIngreso(List<DetalleIngreso> detallesIngreso) {
        return this.ingresoDAO.insertarDetallesIngreso(detallesIngreso);
    }
    
}

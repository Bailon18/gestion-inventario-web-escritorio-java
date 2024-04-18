
package com.micompania.desktop.inventario.negocio.Services;

import com.micompania.desktop.inventario.entidad.DetalleSalida;
import com.micompania.desktop.inventario.interfaces.DetalleSalidaInterface;
import com.micompania.desktop.inventario.negocio.DAO.DetalleSalidaDAO;
import java.util.List;


public class DetalleSalidaService implements DetalleSalidaInterface{

    
    private final DetalleSalidaDAO salidaDAO = new DetalleSalidaDAO();

   
    @Override
    public void insertarDetallesSalida(List<DetalleSalida> detallesSalida, int idSalida) {
        this.salidaDAO.insertarDetallesSalida(detallesSalida, idSalida);
    }

    @Override
    public List<DetalleSalida> obtenerDetallesSalida(int idSalida) {
        return salidaDAO.obtenerDetallesSalida(idSalida);
    }

    @Override
    public void insertarDetallesSalida(List<DetalleSalida> detallesSalida) {
        salidaDAO.insertarDetallesSalida(detallesSalida);
    }
    
}

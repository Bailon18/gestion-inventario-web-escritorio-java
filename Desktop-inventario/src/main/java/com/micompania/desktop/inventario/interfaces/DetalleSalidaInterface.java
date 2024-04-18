
package com.micompania.desktop.inventario.interfaces;


import com.micompania.desktop.inventario.entidad.DetalleSalida;
import java.util.List;


public interface  DetalleSalidaInterface {
    
    void insertarDetallesSalida(List<DetalleSalida> detallesSalida, int idSalida);
    List<DetalleSalida> obtenerDetallesSalida(int idSalida);
    void insertarDetallesSalida(List<DetalleSalida> detallesSalida);
}

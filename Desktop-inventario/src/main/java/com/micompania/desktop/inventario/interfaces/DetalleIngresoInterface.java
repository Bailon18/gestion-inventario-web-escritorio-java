
package com.micompania.desktop.inventario.interfaces;

import com.micompania.desktop.inventario.entidad.DetalleIngreso;
import java.util.List;

public interface DetalleIngresoInterface {
    
    void insertarDetallesIngreso(List<DetalleIngreso> detallesIngreso, int idIngreso);
    List<DetalleIngreso> obtenerDetallesIngreso(int idIngreso);
    boolean insertarDetallesIngreso(List<DetalleIngreso> detallesIngreso);
}

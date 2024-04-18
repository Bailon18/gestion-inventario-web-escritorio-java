package com.micompania.desktop.inventario.interfaces;

import com.micompania.desktop.inventario.entidad.IngresoInventario;
import java.util.Date;
import java.util.List;

public interface IngresoInventarioInterface {

    IngresoInventario realizarIngresoInventario(IngresoInventario ingresoInventario);
    IngresoInventario buscarIngresoInventario(int idIngreso);
    List<IngresoInventario> obtenerTodosLosIngresos();
    List<IngresoInventario> buscarIngresosPorFecha(String fechaInicio, String fechaFin);

}

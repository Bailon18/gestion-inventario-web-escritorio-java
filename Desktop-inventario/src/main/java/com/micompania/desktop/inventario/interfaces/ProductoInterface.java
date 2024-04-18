package com.micompania.desktop.inventario.interfaces;

import com.micompania.desktop.inventario.entidad.Producto;
import com.micompania.desktop.inventario.negocio.DTO.EstadisticasIngresoSalidaDTO;
import com.micompania.desktop.inventario.negocio.DTO.MovimientoProductoDTO;
import java.util.List;

public interface ProductoInterface {

    void agregarProducto(Producto producto);

    void actualizarProducto(Producto producto);

    Producto buscarProducto(int idProducto);

    List<Producto> obtenerTodosLosProductos();

    List<Producto> buscarProductoPorCriterio(String valorBusqueda);

    void cambiarEstadoProducto(int idProducto, boolean activo);

    List<MovimientoProductoDTO> obtenerMovimientosGenerales();

    List<MovimientoProductoDTO> obtenerMovimientosPorProductoNombre(String campo_busqueda);

    List<MovimientoProductoDTO> obtenerMovimientosPorProductoNombreTipoMovimiento(String campo_busqueda, String tipo);

    List<EstadisticasIngresoSalidaDTO> obtenerEstadisticasIngresoSalida();
}

package com.micompania.desktop.inventario.negocio.Services;


import com.micompania.desktop.inventario.negocio.DAO.ProductoDAO;
import com.micompania.desktop.inventario.entidad.Producto;
import com.micompania.desktop.inventario.interfaces.ProductoInterface;
import com.micompania.desktop.inventario.negocio.DTO.EstadisticasIngresoSalidaDTO;
import com.micompania.desktop.inventario.negocio.DTO.MovimientoProductoDTO;
import java.util.List;

public class ProductoService implements ProductoInterface{

    private final ProductoDAO productoDAO;

   
    public ProductoService(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }
    
    @Override
    public void agregarProducto(Producto producto) {
        productoDAO.agregarProducto(producto);
    }

    @Override
    public void actualizarProducto(Producto producto) {
        productoDAO.actualizarProducto(producto);
    }
       
    @Override
    public Producto buscarProducto(int idProducto) {
        return productoDAO.buscarProducto(idProducto);
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return productoDAO.obtenerTodosLosProductos();
    }

    @Override
    public List<Producto> buscarProductoPorCriterio(String valorBusqueda) {
        return productoDAO.buscarProductoPorCriterio(valorBusqueda);
    }

    @Override
    public void cambiarEstadoProducto(int idProducto, boolean activo) {
        productoDAO.cambiarEstadoProducto(idProducto, activo);
    }

    @Override
    public List<MovimientoProductoDTO> obtenerMovimientosGenerales() {
        return productoDAO.obtenerMovimientosGenerales();
    }

    @Override
    public List<MovimientoProductoDTO> obtenerMovimientosPorProductoNombre(String campo_busqueda) {
        return productoDAO.obtenerMovimientosPorProductoNombre(campo_busqueda);
    }

    @Override
    public List<MovimientoProductoDTO> obtenerMovimientosPorProductoNombreTipoMovimiento(String campo_busqueda, String tipo) {
        return productoDAO.obtenerMovimientosPorProductoNombreTipoMovimiento(campo_busqueda, tipo);
    }

    @Override
    public List<EstadisticasIngresoSalidaDTO> obtenerEstadisticasIngresoSalida() {
        return productoDAO.obtenerEstadisticasIngresoSalida();
    }
}

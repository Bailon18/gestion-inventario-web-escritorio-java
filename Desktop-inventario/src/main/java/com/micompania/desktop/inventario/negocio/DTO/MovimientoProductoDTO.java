package com.micompania.desktop.inventario.negocio.DTO;

import java.util.Date;

public class MovimientoProductoDTO {
    private int idProducto;
    private Date fecha;
    private String nombreProducto;
    private int cantidad;
    private String clienteProveedor;
    private String tipoMovimiento;

    public MovimientoProductoDTO(int idProducto, Date fecha, String nombreProducto, int cantidad, String clienteProveedor, String tipoMovimiento) {
        this.idProducto = idProducto;
        this.fecha = fecha;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.clienteProveedor = clienteProveedor;
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getClienteProveedor() {
        return clienteProveedor;
    }

    public void setClienteProveedor(String clienteProveedor) {
        this.clienteProveedor = clienteProveedor;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    // Método toString para imprimir el objeto
    @Override
    public String toString() {
        return "MovimientoProductoDTO{" +
                "idProducto=" + idProducto +
                ", fecha=" + fecha +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", cantidad=" + cantidad +
                ", clienteProveedor='" + clienteProveedor + '\'' +
                ", tipoMovimiento='" + tipoMovimiento + '\'' +
                '}';
    }
}

package com.micompania.desktop.inventario.entidad;

public class DetalleIngreso {

    private int idDetalleIngreso;
    private int cantidad;
    private Producto producto;
    private IngresoInventario ingreso;

    public DetalleIngreso() {
    }

    public DetalleIngreso(int idDetalleIngreso, int cantidad, Producto producto, IngresoInventario ingreso) {
        this.idDetalleIngreso = idDetalleIngreso;
        this.cantidad = cantidad;
        this.producto = producto;
        this.ingreso = ingreso;
    }

    public int getIdDetalleIngreso() {
        return idDetalleIngreso;
    }

    public void setIdDetalleIngreso(int idDetalleIngreso) {
        this.idDetalleIngreso = idDetalleIngreso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public IngresoInventario getIngreso() {
        return ingreso;
    }

    public void setIngreso(IngresoInventario ingreso) {
        this.ingreso = ingreso;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DetalleIngreso{");
        sb.append("idDetalleIngreso=").append(idDetalleIngreso);
        sb.append(", cantidad=").append(cantidad);
        sb.append(", producto=").append(producto);
        sb.append(", ingreso=").append(ingreso);
        sb.append('}');
        return sb.toString();
    }

}

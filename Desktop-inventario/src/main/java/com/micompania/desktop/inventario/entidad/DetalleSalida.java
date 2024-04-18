package com.micompania.desktop.inventario.entidad;

public class DetalleSalida {

    private int idDetalleSalida;
    private int cantidad;
    private Producto producto;
    private SalidaInventario salida;

    public DetalleSalida() {
    }

    public DetalleSalida(int idDetalleSalida, int cantidad, Producto producto, SalidaInventario salida) {
        this.idDetalleSalida = idDetalleSalida;
        this.cantidad = cantidad;
        this.producto = producto;
        this.salida = salida;
    }

    public int getIdDetalleSalida() {
        return idDetalleSalida;
    }

    public void setIdDetalleSalida(int idDetalleSalida) {
        this.idDetalleSalida = idDetalleSalida;
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

    public SalidaInventario getSalida() {
        return salida;
    }

    public void setSalida(SalidaInventario salida) {
        this.salida = salida;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DetalleSalida{");
        sb.append("idDetalleSalida=").append(idDetalleSalida);
        sb.append(", cantidad=").append(cantidad);
        sb.append(", producto=").append(producto);
        sb.append(", salida=").append(salida);
        sb.append('}');
        return sb.toString();
    }

}

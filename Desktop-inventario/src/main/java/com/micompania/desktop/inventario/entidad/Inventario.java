package com.micompania.desktop.inventario.entidad;

public class Inventario {

    private int idInventario;
    private Producto producto;
    private String productonombre;
    private int salida;
    private int ingreso;
    private int stockActual;

    public Inventario() {
    }

    public Inventario(int idInventario, Producto producto, String productonombre, int salida, int ingreso, int stockActual) {
        this.idInventario = idInventario;
        this.producto = producto;
        this.productonombre = productonombre;
        this.salida = salida;
        this.ingreso = ingreso;
        this.stockActual = stockActual;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getProductonombre() {
        return productonombre;
    }

    public void setProductonombre(String productonombre) {
        this.productonombre = productonombre;
    }


    public int getSalida() {
        return salida;
    }

    public void setSalida(int salida) {
        this.salida = salida;
    }

    public int getIngreso() {
        return ingreso;
    }

    public void setIngreso(int ingreso) {
        this.ingreso = ingreso;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventario{");
        sb.append("idInventario=").append(idInventario);
        sb.append(", producto=").append(producto);
        sb.append(", productonombre=").append(productonombre);
        sb.append(", salida=").append(salida);
        sb.append(", ingreso=").append(ingreso);
        sb.append(", stockActual=").append(stockActual);
        sb.append('}');
        return sb.toString();
    }



}

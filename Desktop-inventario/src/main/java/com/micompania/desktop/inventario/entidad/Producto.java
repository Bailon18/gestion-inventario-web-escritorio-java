package com.micompania.desktop.inventario.entidad;

public class Producto {

    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private Categoria categoria;
    private Proveedor proveedor;
    private int stockMinimo;
    private boolean activo;
    private String estado;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String descripcion,
            double precio, Categoria categoria, Proveedor proveedor,
            int stockMinimo, String estado) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.stockMinimo = stockMinimo;
        this.activo = true;
        this.estado = estado;
    }
    
    public Producto(String nombre, String descripcion,
            double precio, Categoria categoria, Proveedor proveedor,
            int stockMinimo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.stockMinimo = stockMinimo;
        this.activo = true;
    }
    
    public Producto(String nombre, String descripcion,
            double precio, Categoria categoria, Proveedor proveedor,
            int stockMinimo, boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
    }



    public void activarProducto() {
        this.activo = true;
    }

    public void desactivarProducto() {
        this.activo = false;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Producto{");
        sb.append("idProducto=").append(idProducto);
        sb.append(", nombre=").append(nombre);
        sb.append(", descripcion=").append(descripcion);
        sb.append(", precio=").append(precio);
        sb.append(", categoria=").append(categoria);
        sb.append(", proveedor=").append(proveedor);
        sb.append(", stockMinimo=").append(stockMinimo);
        sb.append(", activo=").append(activo);
        sb.append(", estado=").append(estado);
        sb.append('}');
        return sb.toString();
    }
    
    
    


}

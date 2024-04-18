
package com.micompania.desktop.inventario.negocio.DTO;


public class InventarioDTO {

    private int idProducto;
    private int stockActual;

    public InventarioDTO() {
    }

    public InventarioDTO(int idProducto, int stockActual) {
        this.idProducto = idProducto;
        this.stockActual = stockActual;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }
}
package com.micompania.desktop.inventario.entidad;

import com.micompania.desktop.inventario.entidad.ENUM.TipoMovimiento;
import java.util.Date;

public class IngresoInventario {

    private int idIngreso;
    private String fecha;
    private Proveedor proveedor;
    private Usuario usuario;
    private TipoMovimiento tipoMovimiento;

    public IngresoInventario() {
        this.tipoMovimiento = TipoMovimiento.INGRESO;
    }

    public IngresoInventario(int idIngreso, String fecha,
            Proveedor proveedor, Usuario usuario) {
        this.idIngreso = idIngreso;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.usuario = usuario;
        this.tipoMovimiento = TipoMovimiento.INGRESO;
    }
    
    public IngresoInventario(String fecha,
            Proveedor proveedor, Usuario usuario) {
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.usuario = usuario;
        this.tipoMovimiento = TipoMovimiento.INGRESO;
    }

    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IngresoInventario{");
        sb.append("idIngreso=").append(idIngreso);
        sb.append(", fecha=").append(fecha);
        sb.append(", proveedor=").append(proveedor);
        sb.append(", usuario=").append(usuario);
        sb.append(", tipoMovimiento=").append(tipoMovimiento);
        sb.append('}');
        return sb.toString();
    }
    
    


}

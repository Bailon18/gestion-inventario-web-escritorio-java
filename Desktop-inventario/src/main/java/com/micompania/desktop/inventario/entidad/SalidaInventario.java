package com.micompania.desktop.inventario.entidad;

import com.micompania.desktop.inventario.entidad.ENUM.TipoMovimiento;
import java.util.Date;

public class SalidaInventario {

    private int idSalida;
    private String fecha;
    private Usuario usuario;
    private Cliente cliente;
    private TipoMovimiento tipoMovimiento;

    public SalidaInventario() {
        this.tipoMovimiento = TipoMovimiento.SALIDA;
    }

    public SalidaInventario(int idSalida, String fecha, Cliente cliente, Usuario usuario) {
        this.idSalida = idSalida;
        this.fecha = fecha;
        this.cliente = cliente;
        this.usuario = usuario;
        this.tipoMovimiento = TipoMovimiento.SALIDA;
    }

    public int getIdSalida() {
        return idSalida;
    }

    public void setIdSalida(int idSalida) {
        this.idSalida = idSalida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        sb.append("SalidaInventario{");
        sb.append("idSalida=").append(idSalida);
        sb.append(", fecha=").append(fecha);
        sb.append(", usuario=").append(usuario);
        sb.append(", cliente=").append(cliente);
        sb.append(", tipoMovimiento=").append(tipoMovimiento);
        sb.append('}');
        return sb.toString();
    }



}

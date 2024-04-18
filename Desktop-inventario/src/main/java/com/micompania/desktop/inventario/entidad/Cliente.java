package com.micompania.desktop.inventario.entidad;

import com.micompania.desktop.inventario.entidad.ENUM.TipoCliente;

public class Cliente {

    private int idCliente;
    private String nombre;
    private TipoCliente tipoCliente;
    private String identificacion;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombre, TipoCliente tipoCliente, String identificacion) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
        this.identificacion = identificacion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente{");
        sb.append("idCliente=").append(idCliente);
        sb.append(", nombre=").append(nombre);
        sb.append(", tipoCliente=").append(tipoCliente);
        sb.append(", identificacion=").append(identificacion);
        sb.append('}');
        return sb.toString();
    }

}

package com.micompania.desktop.inventario.negocio.DTO;

public class EstadisticasIngresoSalidaDTO {
    private String mes;
    private int totalIngreso;
    private int totalSalida;

    public EstadisticasIngresoSalidaDTO(String mes, int totalIngreso, int totalSalida) {
        this.mes = mes;
        this.totalIngreso = totalIngreso;
        this.totalSalida = totalSalida;
    }

    // Getters y Setters
    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getTotalIngreso() {
        return totalIngreso;
    }

    public void setTotalIngreso(int totalIngreso) {
        this.totalIngreso = totalIngreso;
    }

    public int getTotalSalida() {
        return totalSalida;
    }

    public void setTotalSalida(int totalSalida) {
        this.totalSalida = totalSalida;
    }
}

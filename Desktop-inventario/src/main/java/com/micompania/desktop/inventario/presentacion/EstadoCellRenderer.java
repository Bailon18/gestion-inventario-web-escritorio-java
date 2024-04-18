package com.micompania.desktop.inventario.presentacion;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class EstadoCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String estado = (String) value;

        Color colorDisponible = new Color(0, 153, 51);
        Color colorSinStock = new Color(255, 153, 51); 
        Color colorPorAgotarse = new Color(255, 51, 51);

        switch (estado) {
            case "Disponible":
                cellComponent.setForeground(colorDisponible);
                break;
            case "Sin Stock":
                cellComponent.setForeground(colorSinStock);
                break;
            case "Por agotarse":
                cellComponent.setForeground(colorPorAgotarse);
                break;
            default:
                break;
        }

        return cellComponent;
    }
}

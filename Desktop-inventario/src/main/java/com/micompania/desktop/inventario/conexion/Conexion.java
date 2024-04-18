package com.micompania.desktop.inventario.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion {
    
    private static final String USER = "root"; 
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:mysql://localhost:3306/inventariobd?zeroDateTimeBehavior=CONVERT_TO_NULL";

    public static Connection getConeccion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}

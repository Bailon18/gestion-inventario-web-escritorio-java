package com.micompania.desktop.inventario.entidad;

import com.micompania.desktop.inventario.entidad.ENUM.Rol;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String username;
    private String correo;
    private String password;
    private Rol tipo;
    private boolean activo;

    public Usuario() {
        this.activo = true;
    }
    
    

    public Usuario(String nombre, String apellido,
            String username,String correo,  String password, Rol tipo, boolean  estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
        this.correo = correo;
        this.tipo = tipo;
        this.activo = estado;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

   
    public boolean isActivo() {
        return activo;
    }

    public void activarUsuario() {
        this.activo = true;
    }

    public void desactivarUsuario() {
        this.activo = false;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getTipo() {
        return tipo;
    }

    public void setTipo(Rol tipo) {
        this.tipo = tipo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    

    @Override
    public String toString() {
        return "Usuario{"
                + "idUsuario=" + idUsuario
                + ", nombre='" + nombre + '\''
                + ", apellido='" + apellido + '\''
                + ", username='" + username + '\''
                + ", password='" + password + '\''
                + ", tipo=" + tipo
                + ", activo=" + activo
                + // Otros atributos...
                '}';
    }
}

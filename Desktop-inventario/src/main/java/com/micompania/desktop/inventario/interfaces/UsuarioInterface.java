package com.micompania.desktop.inventario.interfaces;

import com.micompania.desktop.inventario.entidad.Usuario;
import java.util.List;

public interface UsuarioInterface {

    void agregarUsuario(Usuario usuario);
    void actualizarUsuario(Usuario usuario);
    void cambiarEstadoUsuario(int idUsuario, boolean activo);
    Usuario buscarUsuario(int idUsuario);
    List<Usuario> obtenerTodosLosUsuarios();
    Usuario validarInicioSesion(String correo , String contrasena);
    List<Usuario> buscarUsuariosPorFiltro(String filtro);

}


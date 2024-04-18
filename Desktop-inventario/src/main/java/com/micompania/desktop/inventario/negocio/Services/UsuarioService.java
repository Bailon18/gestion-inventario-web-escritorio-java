package com.micompania.desktop.inventario.negocio.Services;

import com.micompania.desktop.inventario.entidad.Usuario;
import com.micompania.desktop.inventario.interfaces.UsuarioInterface;


import java.util.List;

public class UsuarioService implements UsuarioInterface {

    private final UsuarioInterface usuarioDAO;

    public UsuarioService(UsuarioInterface usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public void agregarUsuario(Usuario usuario) {
        usuarioDAO.agregarUsuario(usuario);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        usuarioDAO.actualizarUsuario(usuario);
    }

    @Override
    public void cambiarEstadoUsuario(int idUsuario, boolean activo) {
        usuarioDAO.cambiarEstadoUsuario(idUsuario, activo);
    }

    @Override
    public Usuario buscarUsuario(int idUsuario) {
        return usuarioDAO.buscarUsuario(idUsuario);
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.obtenerTodosLosUsuarios();
    }

    @Override
    public Usuario validarInicioSesion(String correo, String contrasena) {
        return usuarioDAO.validarInicioSesion(correo, contrasena);
    }

    @Override
    public List<Usuario> buscarUsuariosPorFiltro(String filtro) {
        return usuarioDAO.buscarUsuariosPorFiltro(filtro);
    }
    


}

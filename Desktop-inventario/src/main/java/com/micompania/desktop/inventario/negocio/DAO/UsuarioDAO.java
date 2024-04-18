package com.micompania.desktop.inventario.negocio.DAO;

import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.ENUM.Rol;
import com.micompania.desktop.inventario.entidad.Usuario;
import com.micompania.desktop.inventario.interfaces.UsuarioInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements UsuarioInterface {

    @Override
    public void agregarUsuario(Usuario usuario) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "INSERT INTO Usuario (nombre, apellido, correo,  username, password, tipo, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, usuario.getNombre());
                pstmt.setString(2, usuario.getApellido());
                pstmt.setString(3, usuario.getCorreo());
                pstmt.setString(4, usuario.getUsername());
                pstmt.setString(5, usuario.getPassword());
                pstmt.setString(6, usuario.getTipo().toString());
                pstmt.setBoolean(7, usuario.isActivo());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "UPDATE Usuario SET nombre = ?, apellido = ?, correo = ?, username = ?, password = ?, tipo = ?, activo = ? WHERE idUsuario = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, usuario.getNombre());
                pstmt.setString(2, usuario.getApellido());
                pstmt.setString(3, usuario.getCorreo());
                pstmt.setString(4, usuario.getUsername());
                pstmt.setString(5, usuario.getPassword());
                pstmt.setString(6, usuario.getTipo().toString());
                pstmt.setBoolean(7, usuario.isActivo());
                pstmt.setInt(8, usuario.getIdUsuario()); // aqui esta 7
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void cambiarEstadoUsuario(int idUsuario, boolean activo) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "UPDATE Usuario SET activo = ? WHERE idUsuario = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBoolean(1, activo);
                pstmt.setInt(2, idUsuario);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public Usuario buscarUsuario(int idUsuario) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Usuario WHERE idUsuario = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idUsuario);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return obtenerUsuarioDesdeResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Usuario ORDER BY idUsuario DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        usuarios.add(obtenerUsuarioDesdeResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return usuarios;
    }

    private Usuario obtenerUsuarioDesdeResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("idUsuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setTipo(Rol.valueOf(rs.getString("tipo")));
        usuario.setActivo(rs.getBoolean("activo"));
        return usuario;
    }
    
    @Override
    public Usuario validarInicioSesion(String correo, String contrasena) {
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Usuario WHERE correo = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, correo);
                pstmt.setString(2, contrasena);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return obtenerUsuarioDesdeResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null; 
    }
    
    @Override
    public List<Usuario> buscarUsuariosPorFiltro(String filtro) {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = Conexion.getConeccion()) {
            String sql = "SELECT * FROM Usuario " +
                         "WHERE LOWER(nombre) LIKE ? OR " +
                         "LOWER(apellido) LIKE ? OR " +
                         "LOWER(correo) LIKE ? OR " +
                         "LOWER(username) LIKE ? OR " +
                         "LOWER(tipo) LIKE ? " +
                         "ORDER BY idUsuario DESC";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 1; i <= 5; i++) {
                    pstmt.setString(i, "%" + filtro + "%");
                }

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        usuarios.add(obtenerUsuarioDesdeResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return usuarios;
    }

}

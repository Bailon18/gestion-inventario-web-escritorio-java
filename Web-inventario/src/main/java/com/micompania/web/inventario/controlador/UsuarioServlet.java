package com.micompania.web.inventario.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.micompania.desktop.inventario.entidad.ENUM.Rol;
import com.micompania.desktop.inventario.entidad.Usuario;
import com.micompania.desktop.inventario.negocio.DAO.ProductoDAO;
import com.micompania.desktop.inventario.negocio.DAO.UsuarioDAO;
import com.micompania.desktop.inventario.negocio.DTO.EstadisticasIngresoSalidaDTO;
import com.micompania.desktop.inventario.negocio.Services.*;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsuarioServlet extends HttpServlet {

    private final UsuarioService usuarioService = new UsuarioService(new UsuarioDAO());
    private final ProductoService productoService = new ProductoService(new ProductoDAO(new CategoriaService(), new ProveedorService()));

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");

        try {
            if (accion != null) {
                switch (accion) {

                    case "validarLogeo":
                        validarLogeo(request, response);
                        break;
                    case "cerrar":
                        cerrarSesion(request, response);
                        break;
                    case "inicio":
                        inicio(request, response);
                        break;
                    case "listar":
                        listarUsuarios(request, response);
                        break;
                    case "agregar":
                        guardarUsuario(request, response);
                        break;
                    case "buscar":
                        buscarUsuario(request, response);
                        break;
                    case "eliminar":
                        eliminarUsuario(request, response);
                        break;
                    case "obtenerUsuarioSession":
                        obtenerUsuarioSession(request, response);
                        break;

                    case "datoestadistico":
                        enviarEstadisticasPorJSON(request, response);
                        break;

                    default:
                        response.sendRedirect("login.jsp");
                }

            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void validarLogeo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        Usuario usuario = usuarioService.validarInicioSesion(correo, contrasena);

        if (usuario != null) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);

            request.setAttribute("mensaje", "Bienvenido al sistema " + usuario.getNombre());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.setAttribute("mensaje", "Credenciales incorrectas");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void enviarEstadisticasPorJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtener las estadísticas de la solicitud
        List<EstadisticasIngresoSalidaDTO> estadisticas = productoService.obtenerEstadisticasIngresoSalida();
        
        System.out.println("Estadistica "+ estadisticas.size());

        // Convertir las estadísticas a JSON
        String estadisticasJson = new Gson().toJson(estadisticas);

        // Establecer el tipo de contenido de la respuesta como application/json
        response.setContentType("application/json");

        // Escribir las estadísticas como JSON en la respuesta
        response.getWriter().write(estadisticasJson);
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login.jsp");

    }

    private void inicio(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("vista/usuario/usuarios.jsp").forward(request, response);
    }

    private void guardarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String json = request.getReader().lines().collect(Collectors.joining());

        JsonObject jsonObject = null;
        try {
            jsonObject = JsonParser.parseString(json).getAsJsonObject();
        } catch (JsonParseException e) {
            e.printStackTrace();
            return;
        }

        try {
            int idUsuario = jsonObject.has("idUsuario") && !jsonObject.get("idUsuario").getAsString().isEmpty() ? jsonObject.get("idUsuario").getAsInt() : -1;
            String nombre = jsonObject.get("nombre").getAsString();
            String apellido = jsonObject.get("apellido").getAsString();
            String correo = jsonObject.get("correo").getAsString();
            String contrasena = jsonObject.get("contrasena").getAsString();
            String username = jsonObject.get("username").getAsString();
            String rol = jsonObject.get("rol").getAsString();
            boolean estado = jsonObject.get("estado").getAsBoolean();

            Rol rol_nombre = Rol.valueOf(rol);
            // Crear instancia de Usuario
            Usuario usuario = new Usuario(nombre, apellido, correo, contrasena, username, rol_nombre, estado);

            System.out.println("Usuario: " + usuario);

            if (idUsuario > 0) {
                usuario.setIdUsuario(idUsuario);
            }

            if (idUsuario > 0) {
                usuarioService.actualizarUsuario(usuario);
                System.out.println("Usuario actualizado con éxito.");
            } else {
                usuarioService.agregarUsuario(usuario);
                System.out.println("Usuario agregado exitosamente.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al procesar el JSON: " + e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"mensaje\": \"Usuario agregado exitosamente\"}");

    }

    private void buscarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idUsuario = Integer.parseInt(request.getParameter("id"));

        JsonObject jsonResponse = new JsonObject();

        Usuario usuario = usuarioService.buscarUsuario(idUsuario);

        if (usuario != null) {
            // Si se encuentra el usuario, agregar sus detalles al objeto JSON de respuesta
            jsonResponse.addProperty("idUsuario", usuario.getIdUsuario());
            jsonResponse.addProperty("nombre", usuario.getNombre());
            jsonResponse.addProperty("apellido", usuario.getApellido());
            jsonResponse.addProperty("correo", usuario.getCorreo());
            jsonResponse.addProperty("contrasena", usuario.getPassword());
            jsonResponse.addProperty("username", usuario.getUsername());
            jsonResponse.addProperty("rol", usuario.getTipo().toString());
            jsonResponse.addProperty("estado", usuario.isActivo());
        }

        // Configurar la respuesta HTTP con el contenido JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Escribir el objeto JSON de respuesta en el cuerpo de la respuesta
        java.io.PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idUsuario = Integer.parseInt(request.getParameter("id"));

        try {

            usuarioService.cambiarEstadoUsuario(idUsuario, false);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"mensaje\": \"Producto eliminado exitosamente\"}");
        } catch (IOException e) {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    private void obtenerUsuarioSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Gson gson = new Gson();
        String usuarioJson = gson.toJson(usuario);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(usuarioJson);
        out.flush();
    }

}

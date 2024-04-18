package com.micompania.web.inventario.controlador;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.micompania.desktop.inventario.entidad.Categoria;
import com.micompania.desktop.inventario.entidad.Producto;
import com.micompania.desktop.inventario.entidad.Proveedor;
import com.micompania.desktop.inventario.negocio.DAO.ProductoDAO;
import com.micompania.desktop.inventario.negocio.Services.CategoriaService;
import com.micompania.desktop.inventario.negocio.Services.ProductoService;
import com.micompania.desktop.inventario.negocio.Services.ProveedorService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductoServlet extends HttpServlet {

    private final ProveedorService proveedorService = new ProveedorService();
    private final CategoriaService categoriaService = new CategoriaService();
    private final ProductoService productoService = new ProductoService(new ProductoDAO(categoriaService, proveedorService));


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");

        try {
            if (accion != null) {
                switch (accion) {

                    case "listar":
                        listarProductos(request, response);
                        break;

                    case "agregar":
                        agregarProducto(request, response);
                        break;

                    case "buscar":
                        buscarProducto(request, response);
                        break;

                    case "eliminar":
                        eliminar(request, response);
                        break;
                    default:
                        response.sendRedirect("productos.jsp");
                }

            } else {
                response.sendRedirect("productos.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("productos.jsp");
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

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        List<Proveedor> proveedores = proveedorService.obtenerTodosLosProveedores();
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();

        request.setAttribute("productos", productos);
        request.setAttribute("proveedores", proveedores);
        request.setAttribute("categorias", categorias);

        request.getRequestDispatcher("vista/producto/productos.jsp").forward(request, response);
    }

    private void agregarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String json = request.getReader().lines().collect(Collectors.joining());

        JsonObject jsonObject = null;
        try {
            jsonObject = JsonParser.parseString(json).getAsJsonObject();
        } catch (JsonParseException e) {
            e.printStackTrace();
            return;
        }

        try {
    
            int idProducto = jsonObject.has("idProducto") && !jsonObject.get("idProducto").getAsString().isEmpty() ? jsonObject.get("idProducto").getAsInt() : -1;
            String nombre = jsonObject.get("nombre").getAsString();
            String descripcion = jsonObject.get("descripcion").getAsString();
            int categoriaId = jsonObject.get("categoriaId").getAsInt();
            int proveedorId = jsonObject.get("proveedorId").getAsInt();
            String estadoString = jsonObject.get("estado").getAsString();
            boolean activo = estadoString.equals("activo");
            int stockMinimo = jsonObject.get("stockMinimo").getAsInt();
            double precio = jsonObject.get("precio").getAsDouble();

            // Crear instancias de Categoria y Proveedor
            Categoria categoria = new Categoria(categoriaId);
            Proveedor proveedor = new Proveedor(proveedorId);


            Producto producto = new Producto(nombre, descripcion, precio, categoria, proveedor, stockMinimo, activo);

            System.out.println("LLEGO3: " + producto);

            // Establecer el ID del producto si es mayor que cero
            if (idProducto > 0) {
                producto.setIdProducto(idProducto);
            }

            // Agregar o actualizar el producto según el ID
            if (idProducto > 0) {
                productoService.actualizarProducto(producto);
                System.out.println("Producto actualizado con éxito.");
            } else {
                productoService.agregarProducto(producto);
                System.out.println("Producto agregado exitosamente.");
            }

        } catch (Exception e) {
            // Captura cualquier excepción y muestra un mensaje de error
            e.printStackTrace();
            System.out.println("Error al procesar el JSON: " + e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"mensaje\": \"Producto agregado exitosamente\"}");

    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idProducto = Integer.parseInt(request.getParameter("id"));

        try {

            productoService.cambiarEstadoProducto(idProducto, false);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"mensaje\": \"Producto eliminado exitosamente\"}");
        } catch (Exception e) {
            // En caso de error, enviar una respuesta con el mensaje de error al cliente
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    private void buscarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID del producto de los parámetros de la solicitud
        int idProducto = Integer.parseInt(request.getParameter("id"));

        // Crear un objeto JSON para almacenar los detalles del producto
        JsonObject jsonResponse = new JsonObject();

        // Aquí deberías realizar la lógica para buscar el producto en tu base de datos
        // Supongamos que tienes una función en tu servicio de producto para buscar un producto por ID
        Producto producto = productoService.buscarProducto(idProducto);

        if (producto != null) {
            // Si se encuentra el producto, agregar sus detalles al objeto JSON de respuesta
            jsonResponse.addProperty("idProducto", producto.getIdProducto());
            jsonResponse.addProperty("nombre", producto.getNombre());
            jsonResponse.addProperty("precio", producto.getPrecio());
            jsonResponse.addProperty("idCategoria", producto.getCategoria().getIdCategoria());
            jsonResponse.addProperty("idProveedor", producto.getProveedor().getIdProveedor());
            jsonResponse.addProperty("stockMinimo", producto.getStockMinimo());
            jsonResponse.addProperty("descripcion", producto.getDescripcion());
        }

        // Configurar la respuesta HTTP con el contenido JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Escribir el objeto JSON de respuesta en el cuerpo de la respuesta
        java.io.PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

}

package com.micompania.web.inventario.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.micompania.desktop.inventario.entidad.Cliente;
import com.micompania.desktop.inventario.entidad.DetalleIngreso;
import com.micompania.desktop.inventario.entidad.DetalleSalida;
import com.micompania.desktop.inventario.entidad.IngresoInventario;
import com.micompania.desktop.inventario.entidad.Inventario;
import com.micompania.desktop.inventario.entidad.Producto;
import com.micompania.desktop.inventario.entidad.Proveedor;
import com.micompania.desktop.inventario.entidad.SalidaInventario;
import com.micompania.desktop.inventario.negocio.DAO.DetalleIngresoDAO;
import com.micompania.desktop.inventario.negocio.DAO.IngresoInventarioDAO;
import com.micompania.desktop.inventario.negocio.DAO.InventarioDAO;
import com.micompania.desktop.inventario.negocio.DAO.ProductoDAO;
import com.micompania.desktop.inventario.negocio.DTO.InventarioDTO;
import com.micompania.desktop.inventario.negocio.DTO.MovimientoProductoDTO;
import com.micompania.desktop.inventario.negocio.Services.CategoriaService;
import com.micompania.desktop.inventario.negocio.Services.ClienteService;
import com.micompania.desktop.inventario.negocio.Services.DetalleIngresoService;
import com.micompania.desktop.inventario.negocio.Services.DetalleSalidaService;
import com.micompania.desktop.inventario.negocio.Services.IngresoInventarioService;
import com.micompania.desktop.inventario.negocio.Services.InventarioService;
import com.micompania.desktop.inventario.negocio.Services.ProductoService;
import com.micompania.desktop.inventario.negocio.Services.ProveedorService;
import com.micompania.desktop.inventario.negocio.Services.SalidaInventarioService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MovimientoServlet extends HttpServlet {

    private final ProveedorService proveedorService = new ProveedorService();
    private final CategoriaService categoriaService = new CategoriaService();
    private final ProductoService productoService = new ProductoService(new ProductoDAO(categoriaService, proveedorService));
    private final ClienteService clienteService = new ClienteService();
    private final IngresoInventarioService ingresoInventarioService = new IngresoInventarioService(new IngresoInventarioDAO());
    private final SalidaInventarioService salidaInventarioService = new SalidaInventarioService();
    private final DetalleIngresoService detalleIngresoService = new DetalleIngresoService(new DetalleIngresoDAO());
    private final DetalleSalidaService detalleSalidaService = new DetalleSalidaService();
    private final InventarioService inventarioService = new InventarioService(new InventarioDAO(productoService));

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");

        try {
            if (accion != null) {
                switch (accion) {

                    case "listar":
                        listarMovimiento(request, response);
                        break;

                    case "buscar":
                        buscarMovimiento(request, response);
                        break;

                    case "listarProductos":
                        listarProductos(request, response);
                        break;

                    case "listarProveedores":
                        listarProveedores(request, response);
                        break;

                    case "listarClientes":
                        listarClientes(request, response);
                        break;

                    case "guardarIngresoInventario":
                        guardarIngresoInventario(request, response);
                        break;

                    case "guardarDetalleIngreso":
                        guardarDetalleIngreso(request, response);
                        break;

                    case "guardarSalidaInventario":
                        guardarSalidaInventario(request, response);
                        break;

                    case "guardarDetalleSalida":
                        guardarDetalleSalida(request, response);
                        break;

                    case "listaInventario":
                        listaInventario(request, response);
                        break;

                    default:
                        response.sendRedirect("vista/movimientos/movimientoproducto.jsp");
                }

            } else {
                response.sendRedirect("vista/movimientos/movimientoproducto.jsp");
            }
        } catch (IOException | ServletException e) {
            response.sendRedirect("vista/movimientos/movimientoproducto.jsp");
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

    private void listarMovimiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<MovimientoProductoDTO> productosgenerales = productoService.obtenerMovimientosGenerales();
        request.setAttribute("productosgenerales", productosgenerales);
        request.getRequestDispatcher("vista/movimientos/movimientoproducto.jsp").forward(request, response);
    }

    private void buscarMovimiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<MovimientoProductoDTO> resultado;

        String nombreProducto = request.getParameter("nombreProducto");
        String tipoMovimiento = request.getParameter("tipoMovimiento");

        if (tipoMovimiento.equals("Ingreso")) {
            resultado = this.productoService.obtenerMovimientosPorProductoNombreTipoMovimiento(nombreProducto, "Ingreso");
        } else if (tipoMovimiento.equals("Salida")) {
            resultado = this.productoService.obtenerMovimientosPorProductoNombreTipoMovimiento(nombreProducto, "Salida");
        } else {
            resultado = this.productoService.obtenerMovimientosPorProductoNombre(nombreProducto);
        }

        Gson gson = new Gson();
        String jsonListaMovimientos = gson.toJson(resultado);

        // Imprimir el JSON para debuggear
        System.out.println("JSON de lista de movimientos:");
        System.out.println(jsonListaMovimientos);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonListaMovimientos);
        out.flush();
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Producto> resultado = this.productoService.obtenerTodosLosProductos();

        Gson gson = new Gson();
        String jsonProductos = gson.toJson(resultado);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonProductos);
        out.flush();
    }

    private void listarProveedores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Proveedor> resultado = this.proveedorService.obtenerTodosLosProveedores();

        Gson gson = new Gson();
        String jsonProveedores = gson.toJson(resultado);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonProveedores);
        out.flush();
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Cliente> resultado = this.clienteService.obtenerTodosLosClientes();

        Gson gson = new Gson();
        String jsonCliente = gson.toJson(resultado);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonCliente);
        out.flush();
    }

    private void guardarIngresoInventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json;
        while ((json = br.readLine()) != null) {
            sb.append(json);
        }
        Gson gson = new Gson();
        IngresoInventario ingresoInventario = gson.fromJson(sb.toString(), IngresoInventario.class);

        IngresoInventario ingresoinventariorespuesta = ingresoInventarioService.realizarIngresoInventario(ingresoInventario);

        Gson gsonrespuesta = new Gson();
        String jsonIngresoId = gson.toJson(ingresoinventariorespuesta.getIdIngreso());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonIngresoId);
        out.flush();
    }

    private void guardarDetalleIngreso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json;
        while ((json = br.readLine()) != null) {
            sb.append(json);
        }

        JsonArray detalleIngresoArray = JsonParser.parseString(sb.toString()).getAsJsonArray();

        List<DetalleIngreso> detalleIngresoList = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement element : detalleIngresoArray) {
            DetalleIngreso detalleIngreso = gson.fromJson(element, DetalleIngreso.class);
            detalleIngresoList.add(detalleIngreso);
        }
        ;

        boolean insercionExitosa = detalleIngresoService.insertarDetallesIngreso(detalleIngresoList);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"mensaje\": \"Detalle  agregado exitosamente\"}");

    }

    private void listaInventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Inventario> inventarios = this.inventarioService.obtenerTodosLosInventarios();

        Gson gson = new Gson();
        String jsonInventario = gson.toJson(inventarios);

        System.out.println("Json: " + jsonInventario);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonInventario);
        out.flush();
    }

    private void guardarSalidaInventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json;
        while ((json = br.readLine()) != null) {
            sb.append(json);
        }
        Gson gson = new Gson();
        SalidaInventario salidaInventario = gson.fromJson(sb.toString(), SalidaInventario.class);

        SalidaInventario salidainventariorespuesta = salidaInventarioService.realizarSalidaInventario(salidaInventario);

        Gson gsonrespuesta = new Gson();
        String jsonSalidaId = gson.toJson(salidainventariorespuesta.getIdSalida());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonSalidaId);
        out.flush();
    }

    private void guardarDetalleSalida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json;
        while ((json = br.readLine()) != null) {
            sb.append(json);
        }

        JsonArray detalleSalidaArray = JsonParser.parseString(sb.toString()).getAsJsonArray();

        List<DetalleSalida> detalleSalidaList = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement element : detalleSalidaArray) {
            DetalleSalida detalleSalida = gson.fromJson(element, DetalleSalida.class);
            detalleSalidaList.add(detalleSalida);
        }
        ;

        detalleSalidaService.insertarDetallesSalida(detalleSalidaList);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"mensaje\": \"Detalle  agregado exitosamente\"}");
    }

}

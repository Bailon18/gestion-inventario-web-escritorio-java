

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- Obtiene la URL actual --%>
<%
    String urlActual = request.getRequestURI();
%>

<div id="layoutSidenav_nav">
    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
            <div class="nav">
                <div class="sb-sidenav-menu-heading">MENU</div>

                <a class="nav-link" href="UsuarioServlet?accion=inicio">
                    <div class="sb-nav-link-icon"><i class="fas fa-home" style="color: white"></i></div>
                    Inicio
                </a>

                <a class="nav-link <%= urlActual.endsWith("productos.jsp") ? "active active-link" : "" %>" href="ProductoServlet?accion=listar">
                    <div class="sb-nav-link-icon"><i class="fas fa-box-open" style="color: white"></i></div>
                    Productos
                </a>
                <a class="nav-link <%= urlActual.endsWith("movimientoproducto.jsp") ? "active active-link" : "" %>" href="MovimientoServlet?accion=listar">
                    <div class="sb-nav-link-icon"><i class="fas fa-exchange-alt" style="color: white"></i></div>
                    Movimientos
                </a>
                <a class="nav-link <%= urlActual.endsWith("inventarios.jsp") ? "active active-link" : "" %>" href="InventarioServlet?accion=listar">
                    <div class="sb-nav-link-icon"><i class="fas fa-warehouse" style="color: white"></i></div>
                    Inventario
                </a>
                <a class="nav-link <%= urlActual.endsWith("usuarios.jsp") ? "active active-link" : "" %>" href="UsuarioServlet?accion=listar">
                    <div class="sb-nav-link-icon"><i class="fas fa-user" style="color: white"></i></div>
                    Usuarios
                </a>
            </div>
        </div>
        <div class="sb-sidenav-footer">
            <div class="small">Login:</div>
            <span>${usuario.nombre}</span>
        </div>
    </nav>
</div>

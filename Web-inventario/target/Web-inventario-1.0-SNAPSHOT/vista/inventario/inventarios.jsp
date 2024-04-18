
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (session.getAttribute("usuario") != null) {
%>

<!DOCTYPE html>
<html lang="en">


    <%@include file="../vistareutilizables/head.jsp" %>
    
    <style>
        .active-link {
          background-color: #2b4bb3;
          color: #fff;
        }
        .active-link i {
          color: #fff; 
        }
    </style>

    <body class="sb-nav-fixed">
        <!<!-- HEADER -->

        <%@include file="../vistareutilizables/navbar.jsp" %>

        <div id="layoutSidenav">
            <!-- MENU -->

            <%@include file="../vistareutilizables/menu.jsp" %>


            <!-- CONTENIDO -->
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <br>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                INVENTARIO
                            </div>
                            <div class="card-body">

                                <div class="mb-3 text-end">

                                </div>

                                <div class="table-responsive">
                                    <table id="tablaInventarios" class="table table-striped table-hover responsive"  style="width:100%">
                                        <thead style="background-color: black">
                                            <tr>
                                                <th class="col-1 text-center" style="color: white">ID</th>
                                                <th class="col-3 text-center" style="color: white">PRODUCTO</th>
                                                <th class="col-1 text-center" style="color: white">STOCK MÍNIMO</th>
                                                <th class="col-1 text-center" style="color: white">SALIDA</th>
                                                <th class="col-1 text-center" style="color: white">INGRESO</th>
                                                <th class="col-1 text-center" style="color: white">STOCK ACTUAL</th>
                                                <th class="col-1 text-center" style="color: white">ESTADO</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="inventario" items="${inventarios}">
                                                <tr>
                                                    <td class="text-center">${inventario.getIdInventario()}</td>
                                                    <td class="text-center">${inventario.getProducto().getNombre()}</td>
                                                    <td class="text-center">${inventario.getProducto().getStockMinimo()}</td>
                                                    <td class="text-center">
                                                        <c:if test="${inventario.getSalida() > 0}">
                                                            <i class="fas fa-arrow-down" style="color: red;"></i> ${inventario.getSalida()}
                                                        </c:if>
                                                    </td>
                                                    <td class="text-center">
                                                        <c:if test="${inventario.getIngreso() > 0}">
                                                            <i class="fas fa-arrow-up" style="color: green;"></i> ${inventario.getIngreso()}
                                                        </c:if>
                                                    </td>
                                                    <td class="text-center">${inventario.getStockActual()}</td>
                                                    <td class="text-center" style="
                                                        <c:choose>
                                                            <c:when test="${inventario.getProducto().getEstado() == 'Sin Stock'}">color: #C29701;</c:when>
                                                            <c:when test="${inventario.getProducto().getEstado() == 'Disponible'}">color: darkgreen;</c:when>
                                                            <c:when test="${inventario.getProducto().getEstado() == 'Por agotarse'}">color: red;</c:when>
                                                            <c:otherwise>color: black;</c:otherwise>
                                                        </c:choose> font-weight: bold;">
                                                        ${inventario.getProducto().getEstado()}
                                                    </td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>

                                    </table>
                                </div>

                            </div>
                        </div>
                    </div>
                </main>

                <%@include file="../vistareutilizables/footer.jsp" %>
            </div>
        </div>


        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/plug-ins/1.11.5/i18n/Spanish.json"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.datatables.net/2.0.0/js/dataTables.js"></script>
        <script src="https://cdn.datatables.net/2.0.0/js/dataTables.bootstrap5.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"></script>
        <script src="resources/js/scripts.js"></script>
        <script src="resources/assets/demo/chart-area-demo.js"></script>
        <script src="resources/assets/demo/chart-bar-demo.js"></script>


        <script>
            $(document).ready(function () {
                $('#tablaInventarios').DataTable({
                    responsive: true,
                    language: {
                        "sProcessing": "Procesando...",
                        "sLengthMenu": "Mostrar _MENU_ registros",
                        "sZeroRecords": "No se encontraron resultados",
                        "sEmptyTable": "Ningún dato disponible en esta tabla",
                        "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                        "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                        "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                        "sInfoPostFix": "",
                        "sSearch": "Buscar:",
                        "sUrl": "",
                        "sInfoThousands": ",",
                        "sLoadingRecords": "Cargando...",
                        "oPaginate": {
                            "sFirst": "Primero",
                            "sLast": "Último",
                            "sNext": "Siguiente",
                            "sPrevious": "Anterior"
                        },
                        "oAria": {
                            "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                            "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                        },
                        "buttons": {
                            "copy": "Copiar",
                            "colvis": "Visibilidad"
                        }
                    },
                    lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Todos"]],
                    order: [[0, 'desc']],
                    initComplete: function () {
                        // Agregar clases de Bootstrap al campo de búsqueda y al selector de cantidad de registros
                        $('.dataTables_filter input').addClass('form-control');
                        $('.dataTables_length select').addClass('form-select');
                    }
                });
            });
        </script>

    </body>


</html>
<%
    } else {
        response.sendRedirect("login.jsp");
    }
%>
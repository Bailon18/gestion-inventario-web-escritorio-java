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
                                PRODUCTOS
                            </div>
                            <div class="card-body">

                                <div class="mb-3 text-end">
                                    <button class="btn btn-primary" onclick='abrirAgregarProductoModal(${0}, "Nuevo Producto", "Guardar")'>Nuevo Producto</button>
                                </div>

                                <%@include file="modalAgregarProducto.jsp" %>

                                <div class="table-responsive">
                                    <table id="tablaProductos" class="table table-striped table-hover responsive"  style="width:100%">
                                        <thead style="background-color: black">
                                            <tr>
                                                <th class="text-center" style="color: white">ID</th>
                                                <th class="text-center" style="color: white">NOMBRE</th>
                                                <th class="text-center" style="color: white">PRECIO</th>
                                                <th class="text-center" style="color: white">CATEGORIA</th>
                                                <th class="text-center" style="color: white">PROVEEDOR</th>
                                                <th class="text-center" style="color: white">SOTCK MINIMO</th>
                                                <th class="text-center" style="color: white">ACCIONES</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="producto" items="${productos}">
                                                <tr>
                                                    <td class="text-center">${producto.idProducto}</td>
                                                    <td class="text-center">${producto.nombre}</td>
                                                    <td class="text-center">${producto.precio}</td>
                                                    <td class="text-center">${producto.categoria.nombre}</td>
                                                    <td class="text-center">${producto.proveedor.nombre}</td>
                                                    <td class="text-center">${producto.stockMinimo}</td>
                                                    <td class="text-center">
                                                        <button type="button" class="btn btn-link" title="Editar" onclick='abrirAgregarProductoModal(${producto.idProducto}, "Editar Producto", "Actualizar")'>
                                                            <i class="fas fa-edit fa-lg text-success"></i>
                                                        </button>

                                                        <button type="button" class="btn btn-link" title="Eliminar" onclick='eliminarProducto(${producto.idProducto}, "${producto.nombre}")'>
                                                            <i class="fas fa-trash-alt fa-lg text-danger ml-2"></i>
                                                        </button>
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

        <script>
                                                        $(document).ready(function () {
                                                            $('#tablaProductos').DataTable({
                                                                responsive:true,
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
                                                                },
                                                                responsive: true
                                                            });
                                                        });
        </script>


        <script>
            function abrirAgregarProductoModal(idProducto, titulo_modal, titulo_boton) {
                // Verificar si se debe hacer una consulta AJAX para obtener los detalles del producto
                if (titulo_modal === "Editar Producto") {
                    // Realizar una solicitud AJAX para obtener los detalles del producto
                    $.ajax({
                        type: "GET",
                        url: "ProductoServlet?accion=buscar&id=" + idProducto,
                        dataType: "json",
                        success: function (response) {
                            // Llenar el formulario del modal con los detalles del producto obtenido
                            console.log("Producto editar: ", response);
                            console.log(response); // Verifica la estructura del objeto response en la consola


                            $("#idProducto").val(response.idProducto);
                            $("#nombreProducto").val(response.nombre);
                            $("#precioProducto").val(response.precio);
                            $("#categoriaProducto").val(response.idCategoria); // Ajusta aquí si la estructura es diferente
                            $("#proveedorProducto").val(response.idProveedor); // Ajusta aquí si la estructura es diferente
                            $("#stockMinimoProducto").val(response.stockMinimo);
                            $("#descripcionProducto").val(response.descripcion);

                        },
                        error: function (err) {
                            console.error(err);
                        }
                    });
                } else {
                    // Limpiar el formulario del modal si no se está editando un producto
                    $("#idProducto").val("");
                    $("#nombreProducto").val("");
                    $("#precioProducto").val("");
                    $("#categoriaProducto").val("1");
                    $("#proveedorProducto").val("1");
                    $("#stockMinimoProducto").val("5");
                    $("#descripcionProducto").val("");
                }

                // Cambiar el título del modal y el texto del botón según el modo (Agregar o Editar)
                $(".modal-title").text(titulo_modal);
                $("#btnEdiSave").text(titulo_boton);

                // Mostrar el modal
                $('#agregarProductoModal').modal('show');
            }

            function eliminarProducto(idProducto, nombreProducto) {
                // Mostrar un mensaje de confirmación usando SweetAlert
                Swal.fire({
                    title: '¿Estás seguro?',
                    text: '¿Deseas eliminar el producto ' + nombreProducto + '?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#3085d6',
                    confirmButtonText: 'Sí, eliminar',
                    cancelButtonText: 'Cancelar'
                }).then((result) => {
                    if (result.isConfirmed) {
                        // Si el usuario confirma, enviar la solicitud AJAX para eliminar el producto
                        $.ajax({
                            type: "GET", // Cambiamos el tipo de solicitud a GET
                            url: "ProductoServlet?accion=eliminar&id=" + idProducto, // Ajustamos la URL para pasar el ID como parámetro
                            dataType: "json",
                            success: function (response) {
                                console.log(response);
                                // Mostrar mensaje de éxito usando SweetAlert
                                Swal.fire({
                                    title: 'Producto eliminado',
                                    text: `El producto "${nombreProducto}" ha sido eliminado exitosamente.`,
                                    icon: 'success',
                                    confirmButtonColor: '#3085d6'
                                }).then((result) => {
                                    // Recargar la página después de cerrar el mensaje
                                    if (result.isConfirmed) {
                                        location.reload();
                                    }
                                });
                            },
                            error: function (err) {
                                console.error(err);
                                // Mostrar mensaje de error usando SweetAlert
                                Swal.fire({
                                    title: 'Error',
                                    text: 'Hubo un error al intentar eliminar el producto.',
                                    icon: 'error',
                                    confirmButtonColor: '#3085d6'
                                });
                            }
                        });
                    }
                });
            }
        </script>
        <script src="resources/js/productos.js"></script>
    </body>


</html>
<%
    } else {
        response.sendRedirect("login.jsp");
    }
%>
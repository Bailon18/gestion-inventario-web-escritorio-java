
<%@ page buffer="18192kb" %>
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
                                MOVIMIENTO POR PRODUCTO
                            </div>
                            <div class="card-body">

                                <div class="row mb-3 mt-3">
                                    <div class="col-md-9">
                                        <div class="bg-light p-3 rounded shadow-sm">
                                            <div class="row align-items-center">
                                                <div class="col-md-4">
                                                    <!-- Campo de búsqueda por nombre de producto -->
                                                    <div class="input-group">
                                                        <input type="text" id="buscarNombreProducto" class="form-control" placeholder="Buscar por nombre de producto">
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <!-- Checkbox para seleccionar todos los movimientos -->
                                                    <div class="form-check form-switch">
                                                        <input class="form-check-input" type="checkbox" id="checkTodos">
                                                        <label class="form-check-label" for="checkTodos">Todos</label>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <!-- Combobox para seleccionar el tipo de movimiento -->
                                                    <select class="form-select" id="tipoMovimiento">
                                                        <option value="Salida">Salida</option>
                                                        <option value="Ingreso" select>Ingreso</option>
                                                        <option value="Ambos" select>Ambos</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-2">
                                                    <!-- Botón para buscar -->
                                                    <button class="btn btn-primary w-100" onclick="buscarMovimientos()">
                                                        <i class="fas fa-filter"></i> Filtrar
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3 text-end d-flex justify-content-end align-items-center">
                                        <button class="btn btn-primary" onclick='abrirModalMovimiento()'>
                                            <i class="fas fa-plus"></i> Nuevo Movimiento
                                        </button>
                                    </div>
                                </div>
                                <%@include file="modalMovimiento.jsp" %>
                                <div class="table-responsive">
                                    <table id="tablaMovimientoProducto"  class="table table-striped table-hover responsive"  style="width:100%">
                                        <thead style="background-color: black">
                                            <tr>
                                                <th class="text-center" style="color: white">ID</th>
                                                <th class="text-center" style="color: white">FECHA</th>
                                                <th class="text-center" style="color: white">PRODUCTO</th>
                                                <th class="text-center" style="color: white">CANTIDAD</th>
                                                <th class="text-center" style="color: white">CLIENTE/PROVEEDOR</th>
                                                <th class="text-center" style="color: white">TIPO MOVIMIENTO</th>
                                                <th class="text-center" style="color: white">ACCIONES</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="producto" items="${productosgenerales}">
                                                <tr>
                                                    <td class="text-center">${producto.idProducto}</td>
                                                    <td class="text-center">${producto.fecha}</td>
                                                    <td class="text-center">${producto.nombreProducto}</td>
                                                    <td class="text-center">${producto.cantidad}</td>
                                                    <td class="text-center">${producto.clienteProveedor}</td>
                                                    <td class="text-center">
                                                        <c:choose>
                                                            <c:when test="${producto.tipoMovimiento eq 'Salida'}">
                                                                <i class="fas fa-arrow-alt-circle-up" style="color: red;"></i>
                                                                <span style="color: red;">Salida</span>
                                                            </c:when>
                                                            <c:when test="${producto.tipoMovimiento eq 'Ingreso'}">
                                                                <i class="fas fa-arrow-alt-circle-down" style="color: green;"></i>
                                                                <span style="color: green;">Ingreso</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${producto.tipoMovimiento}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>

                                                    <td class="text-center">
                                                        <button type="button" class="btn btn-link" title="Editar" onclick='abrirAgregarUsuarioModal(${usuario.idUsuario}, "Editar Usuario", "Actualizar")'>
                                                            <i class="fas fa-edit fa-lg text-success"></i>
                                                        </button>

                                                        <button type="button" class="btn btn-link" title="Eliminar" onclick='eliminarUsuario(${usuario.idUsuario}, "${usuario.nombre}")'>
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
        <script src="resources/assets/demo/chart-area-demo.js"></script>
        <script src="resources/assets/demo/chart-bar-demo.js"></script>




        <script>
                                                            $('#checkTodos').change(function () {
                                                                if ($(this).is(':checked')) {
                                                                    $('#buscarNombreProducto').prop('disabled', true);
                                                                    $('#tipoMovimiento').prop('disabled', true);
                                                                    $('#buscarNombreProducto').val('');
                                                                } else {
                                                                    $('#buscarNombreProducto').prop('disabled', false);
                                                                    $('#tipoMovimiento').prop('disabled', false);
                                                                }
                                                            });

                                                            $(document).ready(function () {
                                                                $('#tablaMovimientoProducto').DataTable({
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
                                                                    order: [[1, 'desc']],
                                                                    searching: false, // Deshabilitar la búsqueda
                                                                    lengthChange: false, // Deshabilitar el selector de cantidad de registros
                                                                    initComplete: function () {
                                                                        // Agregar clases de Bootstrap al campo de búsqueda y al selector de cantidad de registros
                                                                        $('.dataTables_filter input').addClass('form-control');
                                                                        $('.dataTables_length select').addClass('form-select');
                                                                    }
                                                                });



                                                            });


        </script>

        <script>

            function cambiarTipoMovimiento() {

                var tipoMovimiento = $('#tipoMovimiento2').val();

                if (tipoMovimiento === "Ingreso") {
                    $('#inputCliente').val('');
                    $('#titulo_cliente_proveedor').text('Proveedor');
                    $('#titulo_pagina').text('Nuevo Ingreso a Inventario');
                    localStorage.setItem('tipomovimiento', "Ingreso");
                } else {
                    $('#titulo_cliente_proveedor').text('Cliente');
                    $('#titulo_pagina').text('Nuevo Salida a Inventario');
                    localStorage.setItem('tipomovimiento', "Salida");
                }


                limpiarDataLocalStorage();
            }

            function abrirVentanaProveedorOCliente() {

                var tipoMovimiento = localStorage.getItem('tipomovimiento');

                if (tipoMovimiento === "Ingreso") {
                    mostrarVentana(3);
                } else if (tipoMovimiento === "Salida") {
                    mostrarVentana(2);
                } else {
                    mostrarVentana(3);
                }


            }

            function mostrarVentana(numVentana) {
                $('#modalmovimiento div[id^="ventana"]').not('#ventana' + numVentana).hide();
                $('#ventana' + numVentana).show();
            }

            function llenarTablaMovimientos(data) {
                // Limpiar el contenido actual de la tabla
                $('#tablaMovimientoProducto tbody').empty();
                // Iterar sobre los datos y construir las filas de la tabla
                data.forEach(function (movimiento) {
                    var fila = '<tr>' +
                            '<td class="text-center">' + movimiento.idProducto + '</td>' +
                            '<td class="text-center">' + movimiento.fecha + '</td>' +
                            '<td class="text-center">' + movimiento.nombreProducto + '</td>' +
                            '<td class="text-center">' + movimiento.cantidad + '</td>' +
                            '<td class="text-center">' + movimiento.clienteProveedor + '</td>' +
                            '<td class="text-center">';
                    // Agregar ícono y color según el tipo de movimiento
                    if (movimiento.tipoMovimiento === 'Salida') {
                        fila += '<i class="fas fa-arrow-alt-circle-up" style="color: red;"></i>' +
                                '<span style="color: red;"> Salida</span>';
                    } else if (movimiento.tipoMovimiento === 'Ingreso') {
                        fila += '<i class="fas fa-arrow-alt-circle-down" style="color: green;"></i>' +
                                '<span style="color: green;"> Ingreso</span>';
                    } else {
                        fila += movimiento.tipoMovimiento;
                    }
                    // Cerrar la celda de tipo de movimiento
                    fila += '</td>' +
                            '<td class="text-center">' +
                            // Aquí puedes agregar botones de acciones como editar o eliminar
                            '<button type="button" class="btn btn-link" title="Editar" onclick="abrirAgregarUsuarioModal(' + movimiento.idProducto + ',\'Editar Producto\', \'Actualizar\')">' +
                            '<i class="fas fa-edit fa-lg text-success"></i>' +
                            '</button>' +
                            '<button type="button" class="btn btn-link" title="Eliminar" onclick="eliminarUsuario(' + movimiento.idProducto + ', \'' + movimiento.nombreProducto + '\')">' +
                            '<i class="fas fa-trash-alt fa-lg text-danger ml-2"></i>' +
                            '</button>' +
                            '</td>' +
                            '</tr>';
                    $('#tablaMovimientoProducto tbody').append(fila);
                });
            }

            function buscarMovimientos() {
                var nombreProducto = $('#buscarNombreProducto').val();
                var tipoMovimiento = $('#tipoMovimiento').val();
                var checkTodos = $('#checkTodos').is(':checked');

                if (checkTodos) {
                    var tabla = $('#tablaMovimientoProducto').DataTable();
                    tabla.search('').columns().search('').draw();
                } else {
                    // Si el checkbox Todos está desmarcado, realizar la búsqueda
                    $.ajax({
                        type: "GET",
                        url: "MovimientoServlet?accion=buscar",
                        data: {
                            nombreProducto: nombreProducto,
                            tipoMovimiento: tipoMovimiento
                        },
                        dataType: "json",
                        success: function (response) {
                            console.log(response);
                            // Llamar a la función para llenar la tabla con los datos recibidos
                            llenarTablaMovimientos(response);
                        },
                        error: function (err) {
                            console.error(err);
                            alert('Ocurrió un error al buscar movimientos. Por favor, inténtalo de nuevo.');
                        }
                    });
                }
            }

            $(document).ready(function () {
                // Guardar el contenido original del modal
                var contenidoOriginal = $('#modalmovimiento .modal-body').html();

                $('#modalmovimiento').on('hidden.bs.modal', function () {
                    $('#modalmovimiento .modal-body').html(contenidoOriginal);
                    localStorage.removeItem('productoSeleccionado');
                    localStorage.removeItem('carritoIngresos');
                    localStorage.removeItem('tipomovimiento');
                    localStorage.removeItem('clienteSeleccionado');
                    localStorage.removeItem('proveedorSeleccionado');
                    localStorage.removeItem('salidaDetallesTemp');
                    localStorage.removeItem('listaInventario');
                    localStorage.removeItem('listaProductos');
                    localStorage.removeItem('stockActualMap');
                });
            });

            function limpiarDataLocalStorage() {
                localStorage.removeItem('productoSeleccionado');
                localStorage.removeItem('carritoIngresos');
                localStorage.removeItem('clienteSeleccionado');
                localStorage.removeItem('proveedorSeleccionado');
                localStorage.removeItem('salidaDetallesTemp');
                localStorage.removeItem('stockActualMap');
                $('#tablaIngresosBody').empty();
                $('#inputCliente').val('');
            }

            function abrirModalMovimiento() {


                $.ajax({
                    type: "GET",
                    url: "MovimientoServlet?accion=listarProductos",
                    dataType: "json",
                    success: function (response) {
                        // Limpiar el contenido anterior de la tabla
                        $('#tablaProductos tbody').empty();

                        // Iterar sobre la lista de productos y agregarlos a la tabla
                        response.forEach(function (producto) {
                            var fila = '<tr>' +
                                    '<td class="text-center">' + producto.idProducto + '</td>' +
                                    '<td class="text-center">' + producto.nombre + '</td>' +
                                    '<td class="text-center">' + producto.categoria.nombre + '</td>' +
                                    '<td class="text-center"><button class="btn btn-primary" onclick="seleccionarProducto(' + producto.idProducto + ', \'' + producto.nombre + '\')" title="Seleccionar Producto"><i class="fas fa-check"></i></button></td>' +
                                    '</tr>';
                            $('#tablaProductos tbody').append(fila);
                        });
                        // Destruir la instancia de DataTables si ya está inicializada
                        if ($.fn.DataTable.isDataTable('#tablaProductos')) {
                            $('#tablaProductos').DataTable().destroy();
                        }


                        $('#tablaProductos').DataTable({
                            language: {
                                "sProcessing": "Procesando...",
                                "sLengthMenu": "Mostrar _MENU_ registros",
                                "sZeroRecords": "No se encontraron resultados",
                                "sEmptyTable": "Ningún dato disponible en esta tabla",
                                "sInfo": "",
                                "sInfoEmpty": "",
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
                            order: [[0, 'desc']], // Aquí falta una coma
                            lengthChange: false,
                            initComplete: function () {
                                // Agregar clases de Bootstrap al campo de búsqueda y al selector de cantidad de registros
                                $('.dataTables_filter input').addClass('form-control');
                                $('.dataTables_length select').addClass('form-select');
                            }
                        });

                        // Mostrar el modal después de cargar los productos en la tabla
                        $('#modalmovimiento').modal('show');

                    },
                    error: function (err) {
                        console.error(err);
                        // Manejar el error si la consulta falla
                        alert('Ocurrió un error al obtener la lista de productos. Por favor, inténtalo de nuevo.');
                    }

                });

                $.ajax({
                    type: "GET",
                    url: "MovimientoServlet?accion=listarProveedores",
                    dataType: "json",
                    success: function (response) {
                        // Limpiar el contenido anterior de la tabla de proveedores
                        $('#tablaProveedorBody').empty();

                        // Iterar sobre la lista de proveedores y agregarlos a la tabla de proveedores
                        response.forEach(function (proveedor) {
                            var fila = '<tr>' +
                                    '<td class="text-center">' + proveedor.idProveedor + '</td>' +
                                    '<td class="text-center">' + proveedor.nombre + '</td>' +
                                    '<td class="text-center">' + proveedor.telefono + '</td>' +
                                    '<td class="text-center"><button class="btn btn-primary" onclick="seleccionarProveedor(' + proveedor.idProveedor + ', \'' + proveedor.nombre + '\')" title="Seleccionar Proveedor"><i class="fas fa-check"></i></button></td>' +
                                    '</tr>';
                            $('#tablaProveedorBody').append(fila);
                        });

                        // Inicializar o destruir y reinicializar DataTables para la tabla de proveedores
                        if ($.fn.DataTable.isDataTable('#tablaProveedor')) {
                            $('#tablaProveedor').DataTable().destroy();
                        }

                        $('#tablaProveedor').DataTable({
                            language: {
                                "sProcessing": "Procesando...",
                                "sLengthMenu": "Mostrar _MENU_ registros",
                                "sZeroRecords": "No se encontraron resultados",
                                "sEmptyTable": "Ningún dato disponible en esta tabla",
                                "sInfo": "",
                                "sInfoEmpty": "",
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
                            lengthChange: false,
                            initComplete: function () {
                                $('.dataTables_filter input').addClass('form-control');
                                $('.dataTables_length select').addClass('form-select');
                            }
                        });
                    },
                    error: function (err) {
                        console.error(err);
                        alert('Ocurrió un error al obtener la lista de proveedores. Por favor, inténtalo de nuevo.');
                    }
                });

                $.ajax({
                    type: "GET",
                    url: "MovimientoServlet?accion=listarClientes",
                    dataType: "json",
                    success: function (response) {
                        // Limpiar el contenido anterior de la tabla de proveedores
                        $('#tablaClienteBody').empty();

                        // Iterar sobre la lista de proveedores y agregarlos a la tabla de proveedores
                        response.forEach(function (cliente) {
                            var fila = '<tr>' +
                                    '<td class="text-center">' + cliente.idCliente + '</td>' +
                                    '<td class="text-center">' + cliente.nombre + '</td>' +
                                    '<td class="text-center">' + cliente.identificacion + '</td>' +
                                    '<td class="text-center"><button class="btn btn-primary" onclick="seleccionarCliente(' + cliente.idCliente + ', \'' + cliente.nombre + '\')" title="Seleccionar Cliente"><i class="fas fa-check"></i></button></td>' +
                                    '</tr>';
                            $('#tablaClienteBody').append(fila);
                        });

                        // Inicializar o destruir y reinicializar DataTables para la tabla de proveedores
                        if ($.fn.DataTable.isDataTable('#tablaCliente')) {
                            $('#tablaCliente').DataTable().destroy();
                        }

                        $('#tablaCliente').DataTable({
                            language: {
                                "sProcessing": "Procesando...",
                                "sLengthMenu": "Mostrar _MENU_ registros",
                                "sZeroRecords": "No se encontraron resultados",
                                "sEmptyTable": "Ningún dato disponible en esta tabla",
                                "sInfo": "",
                                "sInfoEmpty": "",
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
                            lengthChange: false,
                            initComplete: function () {
                                $('.dataTables_filter input').addClass('form-control');
                                $('.dataTables_length select').addClass('form-select');
                            }
                        });
                    },
                    error: function (err) {
                        console.error(err);
                        alert('Ocurrió un error al obtener la lista de proveedores. Por favor, inténtalo de nuevo.');
                    }
                });

                cargarListas();
            }

            function seleccionarProveedor(idProveedor, nombre) {

                var proveedorSeleccionado = {
                    idProveedor: idProveedor,
                    nombre: nombre
                };

                localStorage.setItem('proveedorSeleccionado', JSON.stringify(proveedorSeleccionado));

                mostrarVentana(1);

                $('#inputCliente').val(nombre);
            }

            function seleccionarCliente(idCliente, nombre) {

                var clienteSeleccionado = {
                    idCliente: idCliente,
                    nombre: nombre
                };

                localStorage.setItem('clienteSeleccionado', JSON.stringify(clienteSeleccionado));

                mostrarVentana(1);

                $('#inputCliente').val(nombre);
            }

            function seleccionarProducto(idProducto, nombre) {

                var productoSeleccionado = {
                    idProducto: idProducto,
                    nombre: nombre
                };

                localStorage.setItem('productoSeleccionado', JSON.stringify(productoSeleccionado));
                mostrarVentana(1);
                $('#inputProducto').val(nombre);
            }

            function agregarProductoACarrito() {

                var errores = "";
                var cantidad = parseInt($('#cantidad').val());
                var tipoMovimiento = $('#tipoMovimiento2').val();


                if (cantidad <= 0 || isNaN(cantidad)) {
                    errores += "La cantidad debe ser mayor a 0.<br>";
                }

                var producto = JSON.parse(localStorage.getItem('productoSeleccionado'));

                if (!producto) {
                    errores += "Por favor, selecciona un producto.<br>";
                }

                if (errores !== "") {
                    Swal.fire({
                        title: "Error",
                        html: errores,
                        icon: "error"
                    });
                    return;
                }

                if (tipoMovimiento === "Ingreso") {
                    // Obtener el carrito de ingresos del localStorage
                    var carritoIngresos = JSON.parse(localStorage.getItem('carritoIngresos')) || [];

                    // Buscar si el producto ya está en el carrito
                    var encontrado = false;
                    for (var i = 0; i < carritoIngresos.length; i++) {
                        if (carritoIngresos[i].nombre === producto.nombre) {
                            carritoIngresos[i].cantidad += cantidad;
                            encontrado = true;
                            break;
                        }
                    }

                    // Si el producto no está en el carrito, agregarlo
                    if (!encontrado) {
                        carritoIngresos.push({
                            id: producto.idProducto,
                            nombre: producto.nombre,
                            cantidad: cantidad
                        });
                    }

                    localStorage.setItem('carritoIngresos', JSON.stringify(carritoIngresos));

                    var carritoIngresos = JSON.parse(localStorage.getItem('carritoIngresos')) || [];

                    mostrarCarritoIngresos(carritoIngresos);

                } else {

                    // Obtener la lista de productos y la lista de inventario del localStorage
                    var listaProductos = JSON.parse(localStorage.getItem('listaProductos'));
                    var listaInventario = JSON.parse(localStorage.getItem('listaInventario'));

                    // Calcular el stock mínimo y el stock actual del producto seleccionado
                    var stockMinimo = obtenerStockMinimo(producto.idProducto, listaProductos);
                    var stockActual = obtenerStockActual(producto.idProducto, listaInventario);

                    // Verificar si el stock actual es 0
                    if (stockActual === 0) {
                        Swal.fire({
                            title: "Advertencia",
                            text: "No se puede realizar la salida. El stock actual es 0",
                            icon: "warning"
                        });
                        return;
                    }

                    // Verificar si la cantidad solicitada es mayor que el stock actual
                    if (cantidad > stockActual) {
                        Swal.fire({
                            title: "Advertencia",
                            text: "No se puede realizar la salida. La cantidad solicitada es mayor que el stock actual",
                            icon: "warning"
                        });
                        return;
                    }

                    var idProductoSeleccionado = producto.idProducto;

                    // Obtener el objeto de stock actual del localStorage
                    var stockActualMap = JSON.parse(localStorage.getItem('stockActualMap')) || {};

                    if (!stockActualMap.hasOwnProperty(idProductoSeleccionado)) {


                        var nuevoStock = stockActual - cantidad;

                        if (nuevoStock < stockMinimo) {
                            Swal.fire({
                                title: "Advertencia",
                                text: "No se puede realizar la salida. El stock mínimo para el producto es: " + stockMinimo,
                                icon: "warning"
                            });
                            return;
                        }

                        stockActualMap[idProductoSeleccionado] = nuevoStock - cantidad;
                        // Guardar el objeto de stock actual actualizado en el localStorage
                        localStorage.setItem('stockActualMap', JSON.stringify(stockActualMap));
                    } else {
                        var stockActualExistente = stockActualMap[idProductoSeleccionado];
                        var nuevoStock = stockActualExistente - cantidad;

                        console.log("Nuevo stock: " + nuevoStock);

                        if (nuevoStock < stockMinimo) {
                            Swal.fire({
                                title: "Advertencia",
                                text: "No se puede realizar la salida. El stock mínimo para el producto es: " + stockMinimo,
                                icon: "warning"
                            });
                            return;
                        }

                        stockActualMap[idProductoSeleccionado] = nuevoStock;
                        // Guardar el objeto de stock actual actualizado en el localStorage
                        localStorage.setItem('stockActualMap', JSON.stringify(stockActualMap));
                    }

                    // 1. Obtener la lista de detalles de salida del localStorage
                    var salidaDetallesTemp = JSON.parse(localStorage.getItem('salidaDetallesTemp')) || [];

                    // 2. Verificar si el producto ya existe en la lista temporal de detalles de salida
                    var salidaExistente = null;
                    for (var i = 0; i < salidaDetallesTemp.length; i++) {
                        if (salidaDetallesTemp[i].nombre === producto.nombre) {
                            salidaExistente = salidaDetallesTemp[i];
                            break;
                        }
                    }

                    // 3. Si el producto ya existe, actualizar la cantidad; de lo contrario, agregar un nuevo detalle de salida
                    if (salidaExistente !== null) {
                        salidaExistente.cantidad += cantidad;
                    } else {
                        var nuevoDetalleSalida = {
                            id: producto.idProducto,
                            nombre: producto.nombre,
                            cantidad: cantidad
                        };


                        var productoExistente = false;
                        for (var i = 0; i < salidaDetallesTemp.length; i++) {
                            if (salidaDetallesTemp[i].nombre === producto.nombre) {
                                salidaDetallesTemp[i].cantidad += cantidad;
                                productoExistente = true;
                                break;
                            }
                        }

                        if (!productoExistente) {
                            salidaDetallesTemp.push(nuevoDetalleSalida);
                        }


                    }

                    // 4. Guardar la lista actualizada de detalles de salida en el localStorage
                    localStorage.setItem('salidaDetallesTemp', JSON.stringify(salidaDetallesTemp));


                    var carritoSalida = JSON.parse(localStorage.getItem('salidaDetallesTemp')) || [];

                    mostrarCarritoIngresos(carritoSalida);

                }

                $('#cantidad').val('');
                $('#inputProducto').val('');
                localStorage.removeItem('productoSeleccionado');

                Swal.fire("Éxito", "El producto se ha agregado al carrito exitosamente", "success");
            }

            function eliminarProducto(nombreProducto) {

                var tipoMovimiento = $('#tipoMovimiento2').val();

                if (tipoMovimiento === "Ingreso") {

                    var carritoIngresos = JSON.parse(localStorage.getItem('carritoIngresos')) || [];
                    carritoIngresos = carritoIngresos.filter(function (producto) {
                        return producto.nombre !== nombreProducto;
                    });
                    localStorage.setItem('carritoIngresos', JSON.stringify(carritoIngresos));
                    mostrarCarritoIngresos(carritoIngresos);

                } else {

                    var salidaDetallesTemp = JSON.parse(localStorage.getItem('salidaDetallesTemp')) || [];
                    salidaDetallesTemp = salidaDetallesTemp.filter(function (detalle) {
                        return detalle.nombre !== nombreProducto;
                    });
                    localStorage.setItem('salidaDetallesTemp', JSON.stringify(salidaDetallesTemp));
                    mostrarCarritoIngresos(salidaDetallesTemp);
                }
            }

            function aumentarCantidad(nombreProducto) {
                var tipoMovimiento = $('#tipoMovimiento2').val();
                if (tipoMovimiento === "Ingreso") {
                    var carritoIngresos = JSON.parse(localStorage.getItem('carritoIngresos')) || [];
                    carritoIngresos.forEach(function (producto) {
                        if (producto.nombre === nombreProducto) {
                            producto.cantidad++;
                        }
                    });
                    localStorage.setItem('carritoIngresos', JSON.stringify(carritoIngresos));
                    // Actualizar la tabla
                    mostrarCarritoIngresos(carritoIngresos);
                } else {
                    var salidaDetallesTemp = JSON.parse(localStorage.getItem('salidaDetallesTemp')) || [];
                    var productoAumentar = null;
                    for (var i = 0; i < salidaDetallesTemp.length; i++) {
                        if (salidaDetallesTemp[i].nombre === nombreProducto) {
                            productoAumentar = salidaDetallesTemp[i];
                            break;
                        }
                    }
                    if (productoAumentar) {
                        // Obtener el stock actual y mínimo
                        var listaProductos = JSON.parse(localStorage.getItem('listaProductos'));
                        var listaInventario = JSON.parse(localStorage.getItem('listaInventario'));
                        var stockMinimo = obtenerStockMinimo(productoAumentar.id, listaProductos);
                        var stockActual = obtenerStockActual(productoAumentar.id, listaInventario);
                        // Validar si se puede aumentar la cantidad
                        if (stockActual === 0) {
                            Swal.fire({
                                title: "Advertencia",
                                text: "No se puede realizar la salida. El stock actual es 0",
                                icon: "warning"
                            });
                            return;
                        }
                        if (productoAumentar.cantidad >= stockActual) {
                            Swal.fire({
                                title: "Advertencia",
                                text: "No se puede aumentar la cantidad. La cantidad solicitada es mayor o igual que el stock actual",
                                icon: "warning"
                            });
                            return;
                        }
                        // Aumentar la cantidad
                        productoAumentar.cantidad++;
                        // Actualizar el stockActualMap
                        var stockActualMap = JSON.parse(localStorage.getItem('stockActualMap')) || {};
                        if (!stockActualMap.hasOwnProperty(productoAumentar.id)) {
                            var nuevoStock = stockActual - 1;
                            if (nuevoStock < stockMinimo) {
                                Swal.fire({
                                    title: "Advertencia",
                                    text: "No se puede realizar la salida. El stock mínimo para el producto es: " + stockMinimo,
                                    icon: "warning"
                                });
                                return;
                            }
                            stockActualMap[productoAumentar.id] = nuevoStock;
                        } else {
                            stockActualMap[productoAumentar.id]--;
                            if (stockActualMap[productoAumentar.id] < stockMinimo) {
                                Swal.fire({
                                    title: "Advertencia",
                                    text: "No se puede realizar la salida. El stock mínimo para el producto es: " + stockMinimo,
                                    icon: "warning"
                                });
                                return;
                            }
                        }
                        localStorage.setItem('stockActualMap', JSON.stringify(stockActualMap));
                        // Guardar la lista actualizada en el localStorage
                        localStorage.setItem('salidaDetallesTemp', JSON.stringify(salidaDetallesTemp));
                        // Actualizar la tabla
                        mostrarCarritoIngresos(salidaDetallesTemp);
                    }
                }
            }

            function disminuirCantidad(nombreProducto) {
                var tipoMovimiento = $('#tipoMovimiento2').val();

                if (tipoMovimiento === "Ingreso") {
                    var carritoIngresos = JSON.parse(localStorage.getItem('carritoIngresos')) || [];
                    carritoIngresos.forEach(function (producto) {
                        if (producto.nombre === nombreProducto) {
                            if (producto.cantidad > 1) {
                                producto.cantidad--;
                            }
                        }
                    });
                    localStorage.setItem('carritoIngresos', JSON.stringify(carritoIngresos));
                    mostrarCarritoIngresos(carritoIngresos);
                } else {
                    var salidaDetallesTemp = JSON.parse(localStorage.getItem('salidaDetallesTemp')) || [];
                    var productoDisminuir = null;
                    for (var i = 0; i < salidaDetallesTemp.length; i++) {
                        if (salidaDetallesTemp[i].nombre === nombreProducto) {
                            productoDisminuir = salidaDetallesTemp[i];
                            break;
                        }
                    }
                    if (productoDisminuir) {
                        // Obtener el stock mínimo y actual
                        var listaProductos = JSON.parse(localStorage.getItem('listaProductos'));
                        var listaInventario = JSON.parse(localStorage.getItem('listaInventario'));
                        var stockMinimo = obtenerStockMinimo(productoDisminuir.id, listaProductos);
                        var stockActual = obtenerStockActual(productoDisminuir.id, listaInventario);

                        // Validar si se puede disminuir la cantidad
                        if (productoDisminuir.cantidad === 1) {
                            Swal.fire({
                                title: "Advertencia",
                                text: "No se puede disminuir más la cantidad. La cantidad mínima es 1",
                                icon: "warning"
                            });
                            return;
                        }
                        // Disminuir la cantidad
                        productoDisminuir.cantidad--;

                        // Actualizar el stockActualMap
                        var stockActualMap = JSON.parse(localStorage.getItem('stockActualMap')) || {};
                        if (!stockActualMap.hasOwnProperty(productoDisminuir.id)) {
                            stockActualMap[productoDisminuir.id] = stockActual + 1; // Sumamos 1 al stock actual
                            if (stockActual + 1 < stockMinimo) {
                                Swal.fire({
                                    title: "Advertencia",
                                    text: "No se puede realizar la salida. El stock mínimo para el producto es: " + stockMinimo,
                                    icon: "warning"
                                });
                                return;
                            }
                        } else {
                            stockActualMap[productoDisminuir.id]++; // Sumamos 1 al stock actual
                            if (stockActualMap[productoDisminuir.id] < stockMinimo) {
                                Swal.fire({
                                    title: "Advertencia",
                                    text: "No se puede realizar la salida. El stock mínimo para el producto es: " + stockMinimo,
                                    icon: "warning"
                                });
                                return;
                            }
                        }

                        localStorage.setItem('stockActualMap', JSON.stringify(stockActualMap));
                        localStorage.setItem('salidaDetallesTemp', JSON.stringify(salidaDetallesTemp));
                        mostrarCarritoIngresos(salidaDetallesTemp);
                    }
                }
            }

            function mostrarCarritoIngresos(carritoIngresos) {
                var tablaIngresosBody = document.getElementById('tablaIngresosBody');
                tablaIngresosBody.innerHTML = '';

                carritoIngresos.forEach(function (producto) {
                    var fila = '<tr>' +
                            '<td class="text-center">' + producto.id + '</td>' +
                            '<td class="text-center">' + producto.nombre + '</td>' +
                            '<td class="text-center">' + producto.cantidad + '</td>' +
                            '<td class="text-center">' +
                            '<button class="btn" onclick="eliminarProducto(\'' + producto.nombre + '\')" style="background-color: transparent; border: none;" title="Eliminar producto"><i class="fas fa-trash-alt" style="color: red;"></i></button>' +
                            '<button class="btn" onclick="aumentarCantidad(\'' + producto.nombre + '\')" style="background-color: transparent; border: none;" title="Aumentar cantidad"><i class="fas fa-plus" style="color: green;"></i></button>' +
                            '<button class="btn" onclick="disminuirCantidad(\'' + producto.nombre + '\')" style="background-color: transparent; border: none;" title="Disminuir cantidad"><i class="fas fa-minus" style="color: orange;"></i></button>' +
                            '</td>' +
                            '</tr>';
                    tablaIngresosBody.innerHTML += fila;
                });
            }

            function obtenerUsuarioId(callback) {
                $.ajax({
                    type: "GET",
                    url: "UsuarioServlet?accion=obtenerUsuarioSession",
                    dataType: "json",
                    success: function (usuario) {
                        callback(usuario.idUsuario);
                    },
                    error: function (xhr, status, error) {
                        callback(null);
                    }
                });
            }

            function guardarMovimiento() {

                // Obtener los valores de los campos del formulario
                var fecha = document.getElementById('fechaInicio').value;
                var proveedorString = localStorage.getItem('proveedorSeleccionado');
                var clienteString = localStorage.getItem('clienteSeleccionado');
                var proveedorId = proveedorString ? JSON.parse(proveedorString).idProveedor : null;
                var clienteId = clienteString ? JSON.parse(clienteString).idCliente : null;

                // Lista para almacenar los mensajes de error
                var mensajesError = [];

                // Validar si la fecha está vacía
                if (!fecha) {
                    mensajesError.push("Por favor, seleccione una fecha.");
                }

                // Validar si se seleccionó un proveedor (para ingreso) o un cliente (para salida)
                var tipoMovimiento = $('#tipoMovimiento2').val();
                if (tipoMovimiento === "Ingreso") {
                    if (!proveedorId) {
                        mensajesError.push("Debe seleccionar un proveedor antes de guardar el movimiento.");
                    }
                } else {
                    if (!clienteId) {
                        mensajesError.push("Debe seleccionar un cliente antes de guardar el movimiento.");
                    }
                }

                // Verificar si el carrito de localStorage tiene datos
                var carrito = tipoMovimiento === "Ingreso" ? JSON.parse(localStorage.getItem('carritoIngresos')) || [] :
                        JSON.parse(localStorage.getItem('salidaDetallesTemp')) || [];
                if (carrito.length === 0) {
                    mensajesError.push("El carrito está vacío.");
                }

                // Verificar si hay mensajes de error
                if (mensajesError.length > 0) {
                    // Mostrar mensajes de error con SweetAlert
                    Swal.fire({
                        title: "Error",
                        text: mensajesError.join("\n"),
                        icon: "error",
                        button: "Aceptar"
                    });
                } else {

                    obtenerUsuarioId(function (usuarioId) {
                        if (usuarioId !== null) {
                            var tipoMovimiento = $('#tipoMovimiento2').val();
                            if (tipoMovimiento === "Ingreso") {
                                // Crear objeto de movimiento para ingreso
                                var movimientoIngreso = {
                                    fecha: fecha,
                                    proveedor: {
                                        idProveedor: proveedorId
                                    },
                                    usuario: {
                                        idUsuario: usuarioId
                                    },
                                    tipoMovimiento: "INGRESO"
                                };

                                // Enviar la solicitud de ingreso al servidor
                                $.ajax({
                                    type: "POST",
                                    url: "MovimientoServlet?accion=guardarIngresoInventario",
                                    data: JSON.stringify(movimientoIngreso),
                                    contentType: "application/json; charset=utf-8",
                                    dataType: "json",
                                    success: function (response) {
                                        // Construir el detalle de ingreso con el ID de ingreso retornado
                                        var detalleIngreso = [];
                                        carrito.forEach(function (item) {
                                            detalleIngreso.push({
                                                producto: {idProducto: item.id},
                                                cantidad: item.cantidad,
                                                ingreso: {idIngreso: response}
                                            });
                                        });

                                        // Enviar el detalle de ingreso al servidor
                                        $.ajax({
                                            type: "POST",
                                            url: "MovimientoServlet?accion=guardarDetalleIngreso",
                                            data: JSON.stringify(detalleIngreso),
                                            contentType: "application/json; charset=utf-8",
                                            dataType: "json",
                                            success: function () {
                                                // Mostrar mensaje de éxito usando SweetAlert
                                                Swal.fire({
                                                    icon: 'success',
                                                    html: 'Ingreso guardado correctamente.',
                                                    showConfirmButton: false,
                                                    timer: 1500
                                                });
                                                setTimeout(function () {
                                                    $('#modalmovimiento').modal('hide');
                                                    location.reload();
                                                }, 1500);

                                                // Limpiar el carrito y otros datos del localStorage
                                                localStorage.removeItem('productoSeleccionado');
                                                localStorage.removeItem('carritoIngresos');
                                                localStorage.removeItem('tipomovimiento');
                                                localStorage.removeItem('clienteSeleccionado');
                                                localStorage.removeItem('proveedorSeleccionado');
                                                localStorage.removeItem('salidaDetallesTemp');
                                                localStorage.removeItem('listaInventario');
                                                localStorage.removeItem('listaProductos');
                                                localStorage.removeItem('stockActualMap');
                                            },
                                            error: function (err) {
                                                console.error(err);
                                                // Mostrar mensaje de error usando SweetAlert
                                                Swal.fire({
                                                    icon: 'error',
                                                    title: 'Error al guardar el detalle de ingreso.',
                                                    text: 'Por favor, intenta nuevamente más tarde.',
                                                    confirmButtonColor: '#3085d6',
                                                    confirmButtonText: 'Aceptar'
                                                });
                                            }
                                        });
                                    },
                                    error: function (err) {
                                        console.error(err);
                                        // Mostrar mensaje de error usando SweetAlert
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Error al guardar el ingreso de inventario.',
                                            text: 'Por favor, intenta nuevamente más tarde.',
                                            confirmButtonColor: '#3085d6',
                                            confirmButtonText: 'Aceptar'
                                        });
                                    }
                                });
                            } else {
                                // Crear objeto de movimiento para salida
                                var movimientoSalida = {
                                    fecha: fecha,
                                    cliente: {
                                        idCliente: clienteId
                                    },
                                    usuario: {
                                        idUsuario: usuarioId
                                    },
                                    tipoMovimiento: "SALIDA"
                                };

                                // Enviar la solicitud de salida al servidor
                                $.ajax({
                                    type: "POST",
                                    url: "MovimientoServlet?accion=guardarSalidaInventario",
                                    data: JSON.stringify(movimientoSalida),
                                    contentType: "application/json; charset=utf-8",
                                    dataType: "json",
                                    success: function (response) {
                                        // Construir el detalle de salida con el ID de salida retornado
                                        var detalleSalida = [];
                                        carrito.forEach(function (item) {
                                            detalleSalida.push({
                                                producto: {idProducto: item.id},
                                                cantidad: item.cantidad,
                                                salida: {idSalida: response}
                                            });
                                        });

                                        // Enviar el detalle de salida al servidor
                                        $.ajax({
                                            type: "POST",
                                            url: "MovimientoServlet?accion=guardarDetalleSalida",
                                            data: JSON.stringify(detalleSalida),
                                            contentType: "application/json; charset=utf-8",
                                            dataType: "json",
                                            success: function () {
                                                // Mostrar mensaje de éxito usando SweetAlert
                                                Swal.fire({
                                                    icon: 'success',
                                                    html: 'Salida guardada correctamente.',
                                                    showConfirmButton: false,
                                                    timer: 1500
                                                });
                                                setTimeout(function () {
                                                    $('#modalmovimiento').modal('hide');
                                                    location.reload();
                                                }, 1500);

                                                // Limpiar el carrito y otros datos del localStorage
                                                localStorage.removeItem('productoSeleccionado');
                                                localStorage.removeItem('carritoIngresos');
                                                localStorage.removeItem('tipomovimiento');
                                                localStorage.removeItem('clienteSeleccionado');
                                                localStorage.removeItem('proveedorSeleccionado');
                                                localStorage.removeItem('salidaDetallesTemp');
                                                localStorage.removeItem('listaInventario');
                                                localStorage.removeItem('listaProductos');
                                                localStorage.removeItem('stockActualMap');
                                            },
                                            error: function (err) {
                                                console.error(err);
                                                // Mostrar mensaje de error usando SweetAlert
                                                Swal.fire({
                                                    icon: 'error',
                                                    title: 'Error al guardar el detalle de salida.',
                                                    text: 'Por favor, intenta nuevamente más tarde.',
                                                    confirmButtonColor: '#3085d6',
                                                    confirmButtonText: 'Aceptar'
                                                });
                                            }
                                        });
                                    },
                                    error: function (err) {
                                        console.error(err);
                                        // Mostrar mensaje de error usando SweetAlert
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Error al guardar la salida de inventario.',
                                            text: 'Por favor, intenta nuevamente más tarde.',
                                            confirmButtonColor: '#3085d6',
                                            confirmButtonText: 'Aceptar'
                                        });
                                    }
                                });
                            }
                        } else {
                            // Mostrar mensaje de error si no se pudo obtener el ID del usuario
                            Swal.fire({
                                title: "Error",
                                text: "No se pudo obtener usuario de la sesión.",
                                icon: "error",
                                button: "Aceptar"
                            });
                        }
                    });


                }
            }

            function cargarListas() {
                $.ajax({
                    type: "GET",
                    url: "MovimientoServlet?accion=listarProductos",
                    dataType: "json",
                    success: function (listaProductos) {
                        localStorage.setItem('listaProductos', JSON.stringify(listaProductos));
                    },
                    error: function (xhr, status, error) {
                        console.error("Error al obtener la lista de productos:", error);
                    }
                });

                $.ajax({
                    type: "GET",
                    url: "MovimientoServlet?accion=listaInventario",
                    dataType: "json",
                    success: function (listaInventario) {
                        localStorage.setItem('listaInventario', JSON.stringify(listaInventario));
                    },
                    error: function (xhr, status, error) {
                        console.error("Error al obtener la lista de productos:", error);
                    }
                });


            }

            function obtenerStockMinimo(idProducto, listaProductos) {
                for (var i = 0; i < listaProductos.length; i++) {
                    if (listaProductos[i].idProducto === idProducto) {
                        return listaProductos[i].stockMinimo;
                    }
                }
                return 0;
            }

            function obtenerStockActual(idProducto, listaInventario) {
                for (var i = 0; i < listaInventario.length; i++) {
                    if (listaInventario[i].producto.idProducto === idProducto) {
                        return listaInventario[i].stockActual;
                    }
                }
                return 0;
            }

        </script>

        <script src="resources/js/usuario.js"></script>

    </body>


</html>
<%    } else {
        response.sendRedirect("login.jsp");
    }
%>
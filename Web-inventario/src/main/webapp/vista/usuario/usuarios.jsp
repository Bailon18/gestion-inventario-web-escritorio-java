
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

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
                                USUARIOS
                            </div>
                            <div class="card-body">

                                <div class="mb-3 text-end">
                                    <button class="btn btn-primary" onclick='abrirAgregarUsuarioModal(${0}, "Nuevo Usuario", "Guardar")'>Nuevo Usuario</button>
                                </div>

                                <%@include file="modalAgregarUsuario.jsp" %>

                                <div class="table-responsive">
                                    <table id="tablaInventarios" class="table table-striped table-hover responsive"  style="width:100%">
                                        <thead style="background-color: black">
                                            <tr>
                                                <th class="text-center" style="color: white">ID</th>
                                                <th class="text-center" style="color: white">NOMBRE</th>
                                                <th class="text-center" style="color: white">APELLIDOS</th>
                                                <th class="text-center" style="color: white">CORREO</th>
                                                <th class="text-center" style="color: white">ROL</th>
                                                <th class="text-center" style="color: white">ESTADO</th>
                                                <th class="text-center" style="color: white">ACCIONES</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="usuario" items="${usuarios}">
                                                <tr>
                                                    <td class="text-center">${usuario.idUsuario}</td>
                                                    <td class="text-center">${usuario.nombre}</td>
                                                    <td class="text-center">${usuario.apellido}</td>
                                                    <td class="text-center">${usuario.correo}</td>
                                                    <td class="text-center">${usuario.tipo}</td>
                                                    <td class="text-center">${usuario.activo ? 'Activo' : 'Inactivo'}</td>
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

        <script>
            function abrirAgregarUsuarioModal(idUsuario, titulo_modal, titulo_boton) {

                if (titulo_modal === "Editar Usuario") {

                    $.ajax({
                        type: "GET",
                        url: "UsuarioServlet?accion=buscar&id=" + idUsuario,
                        dataType: "json",
                        success: function (response) {

                            console.log("USUARIO EDUITAR :" + response.activo)

                            $("#idUsuario").val(response.idUsuario);
                            $("#nombreUsuario").val(response.nombre);
                            $("#apellidoUsuario").val(response.apellido);
                            $("#correoUsuario").val(response.correo);
                            $("#contrasenaUsuario").val(response.contrasena);
                            $("#usernameUsuario").val(response.username);
                            $("#rolUsuario").val(response.rol);
                            $("#estadoUsuario").val(response.isActivo());
                        },
                        error: function (err) {
                            console.error(err);
                        }
                    });
                } else {

                    $("#idUsuario").val("");
                    $("#nombreUsuario").val("");
                    $("#apellidoUsuario").val("");
                    $("#correoUsuario").val("");
                    $("#contrasenaUsuario").val("");
                    $("#usernameUsuario").val("");
                    $("#rolUsuario").val("EMPLEADO");
                    $("#estadoUsuario").val("true");
                }

                $(".modal-title").text(titulo_modal);
                $("#btnEdiSave").text(titulo_boton);

                // Mostrar el modal
                $('#agregarUsuarioModal').modal('show');
            }

            function eliminarUsuario(idUsuario, usuarioNombre) {
                Swal.fire({
                    title: '¿Estás seguro?',
                    text: '¿Deseas eliminar el producto ' + usuarioNombre + '?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#3085d6',
                    confirmButtonText: 'Sí, eliminar',
                    cancelButtonText: 'Cancelar'
                }).then((result) => {
                    if (result.isConfirmed) {

                        $.ajax({
                            type: "GET",
                            url: "UsuarioServlet?accion=eliminar&id=" + idUsuario,
                            dataType: "json",
                            success: function (response) {
                                console.log(response);

                                Swal.fire({
                                    title: 'Usuario eliminado',
                                    text: `El usuario "${usuarioNombre}" ha sido eliminado exitosamente.`,
                                    icon: 'success',
                                    confirmButtonColor: '#3085d6'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        location.reload();
                                    }
                                });
                            },
                            error: function (err) {
                                console.error(err);
                                Swal.fire({
                                    title: 'Error',
                                    text: 'Hubo un error al intentar eliminar el usuario.',
                                    icon: 'error',
                                    confirmButtonColor: '#3085d6'
                                });
                            }
                        });
                    }
                });
            }
        </script>

        <script src="resources/js/usuario.js"></script>

    </body>


</html>
<%    } else {
        response.sendRedirect("login.jsp");
    }
%>
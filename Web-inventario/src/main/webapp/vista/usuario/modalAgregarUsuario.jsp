
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- modalAgregarProducto.jsp -->
<div class="modal fade" id="agregarUsuarioModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">${titulo_modal}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="formularioAgregarUsuario" method="post">
                    <div class="row">
                        <!-- Columna 1 -->
                        <div class="col-md-6">
                            <input type="hidden" class="form-control" id="idUsuario">

                            <!-- Nombre del producto -->
                            <div class="mb-3">
                                <label for="nombreUsuario" class="form-label">Nombre<span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="nombreUsuario" required>
                            </div>

                        </div>
                        
                        <!-- Columna 2 -->
                        <div class="col-md-6">
                            
                            <!-- Apellidos usuario -->
                            <div class="mb-3">
                                <label for="apellidoUsuario" class="form-label">Apellidos<span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="apellidoUsuario" required>
                            </div>
                            
                        </div>
                        
                    </div>
                    
                    <div class="row">
                        <!-- correo del usuario-->
                        <div class="mb-3">
                            <div class="mb-3">
                                <label for="correoUsuario" class="form-label">Correo<span class="text-danger">*</span></label>
                                <input type="email" class="form-control" id="correoUsuario" required>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <!-- Columna 1 -->
                        <div class="col-md-6">
               
                            <!-- contraseña del usuario -->
                            <div class="mb-3">
                                <label for="contrasenaUsuario" class="form-label">Contraseña<span class="text-danger">*</span></label>
                                <input type="password" class="form-control" id="contrasenaUsuario" required>
                            </div>

                             <!-- Username del usuario -->
                            <div class="mb-3">
                                <label for="usernameUsuario" class="form-label">Username<span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="usernameUsuario" required>
                            </div>
 

                        </div>
                        <!-- Columna 2 -->
                        <div class="col-md-6">

                            <!-- Rol del usuario -->
                            <div class="mb-3">
                                <label for="rolUsuario" class="form-label">Rol<span class="text-danger">*</span></label>
                                <select class="form-select" id="rolUsuario" required>
                                    <option value="ADMINISTRADOR" selected>Administrador</option>
                                    <option value="EMPLEADO">Empleado</option>
                                </select>
                            </div>

                            <!-- Estado del usuario -->
                            <div class="mb-3">
                                <label for="estadoUsuario" class="form-label">Estado Usuario<span class="text-danger">*</span></label>
                                <select class="form-select" id="estadoUsuario" required>
                                    <option value="true">Activo</option>
                                    <option value="false">Inactivo</option>
                                </select>
                            </div>


                            
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" id="btnEdiSave" onclick="guardarUsuario()">${titulo_boton}</button>
            </div>
        </div>
    </div>
</div>

            
            

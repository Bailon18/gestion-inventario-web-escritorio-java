
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="modal fade" id="modalmovimiento" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="exampleModalLabel">Movimiento</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Contenido de las ventanas -->
            <div class="modal-body">

                <!-- Nuevo Ingreso 1 -->
                <div id="ventana1">

                    <h5 id="titulo_pagina">Nuevo ingreso a inventario</h5>
                    <br>
                    <!-- Fila 1: Tipo de Movimiento -->
                    <div class="row mb-3">

                        <div class="col-sm">
                            <label for="tipoMovimiento" class="col col-form-label">Tipo de Movimiento</label>
                            <select class="form-select" id="tipoMovimiento2" onchange="cambiarTipoMovimiento()">
                                <option value="Ingreso">Ingreso</option>
                                <option value="Salida">Salida</option>
                            </select>
                        </div>

                    </div>

                    <!-- Fila 2: Fecha de Inicio y Cliente -->
                    <div class="row mb-3">

                        <div class="col-sm-6">
                            <label for="fechaInicio" class="col-sm-6 col-form-label">Fecha de Inicio</label>
                            <input type="date" class="form-control" id="fechaInicio">
                        </div>

                        <div class="col-sm-6">
                            <label for="inputCliente" id="titulo_cliente_proveedor" class="col-sm-6 col-form-label">Proveedor</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="inputCliente">
                                <button class="btn btn-primary" type="button" onclick="abrirVentanaProveedorOCliente()"><i class="fas fa-search"></i></button>
                            </div>
                        </div>
                    </div>

                    <!-- Fila 3: Producto y Cantidad -->
                    <div class="row mb-3">

                        <div class="col-sm-8">
                            <label for="inputProducto" class="col-sm-8 col-form-label">Producto</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="inputProducto">
                                <button class="btn btn-primary" type="button" onclick="mostrarVentana(4)"><i class="fas fa-search"></i></button>
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <label for="cantidad" class="col-sm-8 col-form-label">Cantidad</label>
                            <input type="number" class="form-control" id="cantidad">
                        </div>

                    </div>

                    <!-- Fila 4: Botón Agregar -->
                    <div class="row mb-4">
                        <div class="col-sm-12">
                            <button type="button" class="btn btn-primary" onclick="agregarProductoACarrito()"><i class="fas fa-plus"></i> Agregar</button>
                        </div>
                    </div>

                    <!-- Fila 5: Tabla -->
                    <div class="row">
                        <div class="col-sm-12">
                            <table id="tablaProductoscarrito" class="table table-striped table-hover w-100">
                                <thead style="background-color: black">
                                    <tr>
                                        <th class="text-center" style="color: white">ID</th>
                                        <th class="text-center" style="color: white">Producto</th>
                                        <th class="text-center" style="color: white">Cantidad</th>
                                        <th class="col-4 text-center" style="color: white">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody id="tablaIngresosBody">
                                    <!-- Aquí se mostrarán los datos de la tabla -->
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Ventana 2 -->
                <div id="ventana2" style="display: none;">
                    <h5>Clientes</h5>
                    
                    <table id="tablaCliente" class="table table-striped table-hover w-100">

                        <thead style="background-color: black">
                            <tr>
                                <th class="text-center" style="color: white">ID</th>
                                <th class="text-center" style="color: white">Cliente</th>
                                <th class="text-center" style="color: white">Identificacion</th>
                                <th class="text-center" style="color: white">Acción</th>
                            </tr>
                        </thead>
                        <tbody id="tablaClienteBody">

                        </tbody>
                    </table>
                </div>

                <!-- Ventana 3 -->
                <div id="ventana3" style="display: none;">

                    <h5>Proveedor</h5>

                    <table id="tablaProveedor" class="table table-striped table-hover w-100">

                        <thead style="background-color: black">
                            <tr>
                                <th class="text-center" style="color: white">ID</th>
                                <th class="text-center" style="color: white">Proveedor</th>
                                <th class="text-center" style="color: white">Telefono</th>
                                <th class="text-center" style="color: white">Acción</th>
                            </tr>
                        </thead>
                        <tbody id="tablaProveedorBody">

                        </tbody>
                    </table>
                </div>


                <!-- Ventana 4 -->
                <div id="ventana4" style="display: none;">

                    <h6>Producto</h6>

                    <table id="tablaProductos" class="table table-striped table-hover w-100">
                        <thead style="background-color: black">
                            <tr>
                                <th class="text-center" style="color: white">ID</th>
                                <th class="text-center" style="color: white">Producto</th>
                                <th class="text-center" style="color: white">Categoría</th>
                                <th class="text-center" style="color: white">Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="mostrarVentana(1)">Ir al inicio</button>
                <button type="button" class="btn btn-primary" onclick="guardarMovimiento()">Guardar Movimiento</button>
            </div>

        </div>
    </div>
</div>

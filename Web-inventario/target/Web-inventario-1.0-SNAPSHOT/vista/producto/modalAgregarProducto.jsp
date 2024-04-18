
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- modalAgregarProducto.jsp -->
<div class="modal fade" id="agregarProductoModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">${titulo_modal}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="formularioAgregarProducto" method="POST">
                    <div class="row">
                        <!-- Columna 1 -->
                        <div class="col-md-6">
                            <input type="hidden" class="form-control" id="idProducto">
                            <!-- Nombre del producto -->
                            <div class="mb-3">
                                <label for="nombreProducto" class="form-label">Nombre<span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="nombreProducto" required='true'>
                            </div>

                            <!-- Categoría del producto -->
                            <div class="mb-3">
                                <label for="categoriaProducto" class="form-label">Categoría<span class="text-danger">*</span></label>
                                <select class="form-select" id="categoriaProducto" required='true'>
                                    <c:forEach var="categoria" items="${categorias}">
                                        <option value="${categoria.idCategoria}">${categoria.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Stock Mínimo del producto -->
                            <div class="mb-3">
                                <label for="stockMinimoProducto" class="form-label">Stock Mínimo<span class="text-danger">*</span></label>
                                <input type="number" class="form-control" id="stockMinimoProducto" required='true'>
                            </div>

                        </div>
                        <!-- Columna 2 -->
                        <div class="col-md-6">

                            <!-- Precio del producto -->
                            <div class="mb-3">
                                <label for="precioProducto" class="form-label">Precio<span class="text-danger">*</span></label>
                                <input type="number" class="form-control" id="precioProducto" required='true'>
                            </div>

                            <!-- Proveedor del producto -->
                            <div class="mb-3">
                                <label for="proveedorProducto" class="form-label">Proveedor<span class="text-danger">*</span></label>
                                <select class="form-select" id="proveedorProducto" required>
                                    <c:forEach var="proveedor" items="${proveedores}" varStatus="loop">
                                        <option value="${proveedor.idProveedor}" <c:if test="${loop.index == 0}">selected</c:if>>${proveedor.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Estado del producto -->
                            <div class="mb-3">
                                <label for="estadoProducto" class="form-label">Estado Producto<span class="text-danger">*</span></label>
                                <select class="form-select" id="estadoProducto" required='true'>
                                    <option value="activo" selected>Activo</option>
                                    <option value="inactivo">Inactivo</option>
                                </select>
                            </div>

                        </div>
                    </div>
                    <div class="row">
                        <!-- Descripción del producto -->
                        <div class="mb-3">
                            <label for="descripcionProducto" class="form-label">Descripción<span class="text-danger">*</span></label>
                            <textarea class="form-control" id="descripcionProducto" rows="3" required></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="submit" class="btn btn-primary" id="btnEdiSave" onclick="guardarProducto(event)">${titulo_boton}</button>
            </div>
        </div>
    </div>
</div>

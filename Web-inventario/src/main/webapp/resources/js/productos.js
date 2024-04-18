function guardarProducto(event) {
    event.preventDefault(); // Prevenir el comportamiento predeterminado del botón de enviar

    // Obtener los datos del formulario
    var idProducto = $("#idProducto").val() ?? 0;
    var nombreProducto = $("#nombreProducto").val().trim();
    var stockMinimoProducto = $("#stockMinimoProducto").val().trim();
    var precioProducto = $("#precioProducto").val().trim();
    
    var descripcionProducto = $("#descripcionProducto").val().trim();
    
    
    var categoriaId = $("#categoriaProducto").length > 0 ? $("#categoriaProducto").val() : '';
    var proveedorId = $("#proveedorProducto").length > 0 ? $("#proveedorProducto").val() : '';
    var estadoProducto =  $("#estadoProducto").length > 0 ? $("#estadoProducto").val() : '';

    // Verificar que todos los campos obligatorios estén completos
    if (nombreProducto === "" || stockMinimoProducto === "" || precioProducto === "" || estadoProducto === "" || descripcionProducto === "" || categoriaId === "" || proveedorId === "") {
        // Mostrar un mensaje de advertencia al usuario
        Swal.fire({
            icon: 'warning',
            title: '¡Atención!',
            html: 'Por favor, completa todos los campos obligatorios.',
            showConfirmButton: false,
            timer: 1500
        });
        return; // Detener la ejecución de la función si faltan campos obligatorios
    }

    // Si todos los campos están completos, enviar los datos del formulario al servidor
    var producto = {
        idProducto: idProducto,
        nombre: nombreProducto,
        stockMinimo: stockMinimoProducto,
        precio: precioProducto,
        estado: estadoProducto,
        descripcion: descripcionProducto,
        categoriaId: categoriaId,
        proveedorId: proveedorId
    };

    console.log("Productos a guardar " + idProducto);

    $.ajax({
        type: "POST",
        url: "ProductoServlet?accion=agregar",
        data: JSON.stringify(producto),
        contentType: "application/json; charset=utf-8",
        dataType: "json",

        success: function(response) {
            Swal.fire({
                icon: 'success',
                html: 'Producto guardado correctamente.',
                showConfirmButton: false,
                timer: 1500
            });
            setTimeout(function() {
                $('#agregarProductoModal').modal('hide');
                location.reload();
            }, 1500);
        },
        error: function(err) {
            console.error(err);
            // Mostrar mensaje de error usando SweetAlert
            Swal.fire({
                icon: 'error',
                title: 'Error al guardar el Producto.',
                text: 'Por favor, intenta nuevamente más tarde.',
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'Aceptar'
            });
        }
    });
}

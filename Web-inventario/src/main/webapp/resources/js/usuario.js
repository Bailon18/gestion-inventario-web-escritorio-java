function guardarUsuario() {
    // Obtener los datos del formulario
    var idUsuario = $("#idUsuario").val() ?? 0;
    var nombreUsuario = $("#nombreUsuario").val().trim();
    var apellidoUsuario = $("#apellidoUsuario").val().trim();
    var correoUsuario = $("#correoUsuario").val().trim();
    var contrasenaUsuario = $("#contrasenaUsuario").val().trim();
    var usernameUsuario = $("#usernameUsuario").val().trim();
    
    var rolUsuario = $("#rolUsuario").length > 0 ? $("#rolUsuario").val() : '';
    var estadoUsuario =  $("#estadoUsuario").length > 0 ? $("#estadoUsuario").val() : '';

    // Verificar que todos los campos obligatorios estén completos
    if (!nombreUsuario || !apellidoUsuario || !correoUsuario || !contrasenaUsuario || !usernameUsuario || !rolUsuario || !estadoUsuario) {
        
        Swal.fire({
            icon: 'warning',
            title: '¡Atención!',
            text: 'Por favor, completa todos los campos obligatorios.',
            confirmButtonColor: '#3085d6',
            confirmButtonText: 'Aceptar'
        });
        return;
    }

    var usuario = {
        idUsuario: idUsuario,
        nombre: nombreUsuario,
        apellido: apellidoUsuario,
        correo: correoUsuario,
        contrasena: contrasenaUsuario,
        username: usernameUsuario,
        rol: rolUsuario,
        estado: estadoUsuario
    };

    // Enviar los datos del usuario al servlet mediante una solicitud AJAX
    $.ajax({
        type: "POST",
        url: "UsuarioServlet?accion=agregar",
        data: JSON.stringify(usuario),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(response) {
            Swal.fire({
                icon: 'success',
                html: 'Usuario guardado correctamente.',
                showConfirmButton: false,
                timer: 1500
            });
            setTimeout(function() {
                $('#agregarUsuarioModal').modal('hide');
                location.reload();
            }, 1500);
        },
        error: function(err) {
            console.error(err);
            // Mostrar mensaje de error usando SweetAlert
            Swal.fire({
                icon: 'error',
                title: 'Error al guardar el usuario.',
                text: 'Por favor, intenta nuevamente más tarde.',
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'Aceptar'
            });
        }
    });
}

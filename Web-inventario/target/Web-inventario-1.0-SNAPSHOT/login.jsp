<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
     <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,500,700&display=swap">
    <link href="resources/css/estilopersonalizado.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-4">
            <div class="card">
                <div class="text-center">
                    <h2 class="text-secondary mt-3">Inicio de Sesión</h2>
                    <img src="resources/assets/img/inventario3.png" style="height: 150px"  alt="Logo" class="img-fluid mb-3">
                </div>
                <div class="card-body">
                    <form action="UsuarioServlet?accion=validarLogeo" method="POST">
                        <div class="form-group">
                            <label for="email">Correo:</label>
                            <input type="email" id="correo" name="correo" class="form-control" placeholder="Correo" required="required">
                        </div>
                        <div class="form-group">
                            <label for="password">Contraseña:</label>
                            <div class="input-group">
                                <input type="password" id="contrasena" name="contrasena" class="form-control" placeholder="Contraseña" required="required">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block">Iniciar sesión</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
<script src="resources/js/login.js"></script>
</body>
</html>

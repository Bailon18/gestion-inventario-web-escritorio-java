document.getElementById('togglePassword').addEventListener('click', function() {
    var passwordInput = document.getElementById('contrasena');
    var passwordIcon = document.querySelector('#togglePassword i');

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        passwordIcon.classList.remove('fa-eye'); 
        passwordIcon.classList.add('fa-eye-slash');
    } else {
        passwordInput.type = "password";
        passwordIcon.classList.remove('fa-eye-slash');
        passwordIcon.classList.add('fa-eye');
    }
});

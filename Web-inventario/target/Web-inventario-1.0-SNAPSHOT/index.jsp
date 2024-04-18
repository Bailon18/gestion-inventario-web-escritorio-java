
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%
    if (session.getAttribute("usuario") != null) {
%>

<!DOCTYPE html>
<html lang="en">
    <%@include file="vista/vistareutilizables/head.jsp" %>
    <body class="sb-nav-fixed">

        <!<!-- HEADER -->
        <%@include file="vista/vistareutilizables/navbar.jsp" %>

        <!<!-- MENU -->
        <div id="layoutSidenav">

            <%@include file="vista/vistareutilizables/menu.jsp" %>


            <div id="layoutSidenav_content">
                <main>

                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Datos estadisticos</h1>
                        <ol class="breadcrumb mb-4">
                        </ol>
                        <div class="card mb-4">
                            <div class="card-body">
                                Datos estadisticos INVENTARIO
                            </div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-chart-area me-1"></i>
                                Estadísticas de ingreso y salida
                            </div>
                            <div id="container" width="100%" height="30"></div>

                        </div>
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-bar me-1"></i>
                                        Estadísticas de ingreso y salida por mes
                                    </div>
                                    <div id="container2" width="100%" height="50"></div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-pie me-1"></i>
                                        Total de Movimiento por Mes
                                    </div>
                                    <div id="container3" width="100%" height="50"></div>

                                </div>
                            </div>
                        </div>
                    </div>

                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Your Website 2023</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="resources/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="resources/assets/demo/chart-bar-demo.js"></script>
        <script src="resources/assets/demo/chart-pie-demo.js"></script>

        <script>

            $(document).ready(function () {
                $.ajax({
                    type: "GET",
                    url: "UsuarioServlet?accion=datoestadistico",
                    dataType: "json",
                    success: function (response) {
                        // Verificar si hay datos estadísticos disponibles
                        if (response != null && response.length > 0) {
                            // Crear arrays para almacenar los datos de ingreso y salida
                            var meses = [];
                            var totalIngreso = [];
                            var totalSalida = [];

                            // Iterar sobre los datos devueltos y agregarlos a los arrays correspondientes
                            $.each(response, function (index, estadistica) {
                                meses.push(estadistica.mes); // Usar "mes" en lugar de "mes_nombre"
                                totalIngreso.push(estadistica.totalIngreso);
                                totalSalida.push(estadistica.totalSalida);
                            });

                            // Crear el gráfico de columnas utilizando Highcharts
                            Highcharts.chart('container', {
                                chart: {
                                    type: 'column'
                                },
                                title: {
                                    text: ''
                                },
                                xAxis: {
                                    categories: meses,
                                    crosshair: true
                                },
                                yAxis: {
                                    min: 0,
                                    title: {
                                        text: 'Cantidad'
                                    }
                                },
                                tooltip: {
                                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                                            '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
                                    footerFormat: '</table>',
                                    shared: true,
                                    useHTML: true
                                },
                                plotOptions: {
                                    column: {
                                        pointPadding: 0.2,
                                        borderWidth: 0
                                    }
                                },
                                series: [{
                                        name: 'Total Ingreso',
                                        data: totalIngreso
                                    }, {
                                        name: 'Total Salida',
                                        data: totalSalida
                                    }]
                            });
                        }
                    },
                    error: function (err) {
                        console.error('Error al obtener datos estadísticos:', err);
                    }
                });
            });

            $(document).ready(function () {
                $.ajax({
                    type: "GET",
                    url: "UsuarioServlet?accion=datoestadistico",
                    dataType: "json",
                    success: function (response) {
                        // Verificar si hay datos estadísticos disponibles
                        if (response != null && response.length > 0) {
                            // Crear arrays para almacenar los datos de ingreso y salida
                            var meses = [];
                            var totalIngreso = [];
                            var totalSalida = [];

                            // Iterar sobre los datos devueltos y agregarlos a los arrays correspondientes
                            $.each(response, function (index, estadistica) {
                                meses.push(estadistica.mes); // Usar "mes" en lugar de "mes_nombre"
                                totalIngreso.push(estadistica.totalIngreso);
                                totalSalida.push(estadistica.totalSalida);
                            });

                            // Crear el gráfico de áreas spline utilizando Highcharts
                            Highcharts.chart('container2', {
                                chart: {
                                    type: 'areaspline'
                                },
                                title: {
                                    text: ''
                                },
                                xAxis: {
                                    categories: meses,
                                    title: {
                                        text: 'Mes'
                                    }
                                },
                                yAxis: {
                                    title: {
                                        text: 'Cantidad'
                                    }
                                },
                                tooltip: {
                                    shared: true,
                                    headerFormat: '<b>{point.key}</b><br>',
                                    pointFormat: '{series.name}: {point.y}',
                                    valueSuffix: ' unidades'
                                },
                                plotOptions: {
                                    areaspline: {
                                        fillOpacity: 0.5
                                    }
                                },
                                series: [{
                                        name: 'Total Ingreso',
                                        data: totalIngreso
                                    }, {
                                        name: 'Total Salida',
                                        data: totalSalida
                                    }]
                            });
                        }
                    },
                    error: function (err) {
                        console.error('Error al obtener datos estadísticos:', err);
                    }
                });
            });

            $(document).ready(function () {
                $.ajax({
                    type: "GET",
                    url: "UsuarioServlet?accion=datoestadistico",
                    dataType: "json",
                    success: function (response) {
                        // Verificar si hay datos estadísticos disponibles
                        if (response != null && response.length > 0) {
                            // Procesar los datos para el gráfico de pastel
                            const data = response.map(({ mes, totalIngreso, totalSalida }) => ({
                                    name: mes,
                                    y: totalIngreso + totalSalida
                                }));

                            Highcharts.chart('container3', {
                                chart: {
                                    type: 'pie'
                                },
                                title: {
                                    text: ''
                                },
                                series: [{
                                        name: 'Total Movimiento',
                                        colorByPoint: true,
                                        data: data
                                    }],
                                tooltip: {
                                    valueSuffix: '',
                                    pointFormat: '{point.name}: <b>{point.y}</b>'
                                },
                                subtitle: {
                                    text: 'Source:<a href="https://www.mdpi.com/2072-6643/11/3/684/htm" target="_default">MDPI</a>'
                                },
                                plotOptions: {
                                    series: {
                                        allowPointSelect: true,
                                        cursor: 'pointer',
                                        dataLabels: {
                                            enabled: true,
                                            distance: 20,
                                            format: '{point.percentage:.1f}%',
                                            style: {
                                                fontSize: '1.2em',
                                                textOutline: 'none',
                                                opacity: 0.7
                                            },
                                            filter: {
                                                operator: '>',
                                                property: 'percentage',
                                                value: 10
                                            }
                                        }
                                    }
                                }
                            });
                        } else {
                            console.log('No hay datos estadísticos disponibles.');
                        }
                    },
                    error: function (err) {
                        console.error('Error al obtener datos estadísticos:', err);
                    }
                });
            });


        </script>


    </body>
</html>
<%    } else {
        response.sendRedirect("login.jsp");
    }
%>
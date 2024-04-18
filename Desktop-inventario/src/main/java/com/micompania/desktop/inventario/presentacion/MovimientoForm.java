package com.micompania.desktop.inventario.presentacion;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.micompania.desktop.inventario.entidad.*;
import com.micompania.desktop.inventario.entidad.ENUM.TipoMovimiento;
import com.micompania.desktop.inventario.negocio.DAO.*;
import com.micompania.desktop.inventario.negocio.Services.*;
import java.awt.Component;
import java.awt.Window;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public final class MovimientoForm extends javax.swing.JDialog {

    private static final int MODO_INGRESO = 1;
    private static final int MODO_SALIDA = 2;

    private final ClienteService clienteService;
    private final ProductoService productoService;
    private final ProveedorService proveedorService;
    private final InventarioService inventarioService;
    private final IngresoInventarioService ingresoInventarioService;
    private final SalidaInventarioService salidaInventarioService;

    private List<DetalleSalida> salidaDetallesTemp = new ArrayList<>();
    // Declaración e inicialización del mapa
    Map<Integer, Integer> stockActualMap = new HashMap<>();

    DefaultTableModel model_proveedor;
    DefaultTableModel model_producto;
    DefaultTableModel model_cliente;
    DefaultTableModel model_carrito;
    DefaultTableModel model_inventario;
    DefaultTableModel model_movimiento;
    DefaultTableModel modelo_movimiento_producto;

    private Proveedor proveedorseleccionado;
    private Cliente clienteseleccionado;
    private Producto productoSeleccionado;
    private List<DetalleIngreso> ingresoDetalles = new ArrayList<>();
    private List<DetalleSalida> salidaDetalles = new ArrayList<>();

    private Usuario usuariologeado;

    PrincipalForm pantallaprincipal;

    public MovimientoForm(java.awt.Frame parent, boolean modal, int modo, Object ingresosalida,
            DefaultTableModel modelo_inventario, Usuario usuario, DefaultTableModel modelo_movimiento, DefaultTableModel modelo_movimiento_producto
    ) {

        super(parent, modal);
        initComponents();
        this.pantallaprincipal = new PrincipalForm();
        this.model_inventario = modelo_inventario;
        this.model_movimiento = modelo_movimiento;
        this.modelo_movimiento_producto = modelo_movimiento_producto;

        PANELMOVIMIENTO_PROVEEDOR.setName("PROVEEDOR_PANEL");
        PANELMOVIMIENTO_CLIENTE.setName("CLIENTE_PANEL");
        PANELMOVIMIENTO_INGRESO.setName("INGRESO_PANEL");
        PANELMOVIMIENTO_PRODUCTO.setName("PRODUCTO_PANEL");

        this.configurarTablaProducto();
        this.configurarTablaCliente();
        this.configurarTablaProveedor();
        this.configurarTablaCarrito();

        this.clienteService = new ClienteService();
        this.proveedorService = new ProveedorService();
        this.productoService = new ProductoService(new ProductoDAO(new CategoriaService(), new ProveedorService()));

        this.inventarioService = new InventarioService(new InventarioDAO(productoService));
        this.ingresoInventarioService = new IngresoInventarioService(new IngresoInventarioDAO());
        this.salidaInventarioService = new SalidaInventarioService();

        this.llenarTablaProveedor(this.proveedorService.obtenerTodosLosProveedores(), this.model_proveedor);
        this.llenarTablaProducto(this.productoService.obtenerTodosLosProductos(), this.model_producto);
        this.llenarTablaCliente(this.clienteService.obtenerTodosLosClientes(), this.model_cliente);

        this.cambiarVista(PANELMOVIMIENTO_INGRESO);
        this.setLocationRelativeTo(null);
        this.usuariologeado = usuario;

        this.cambiartipotitulo();

        tabla_producto_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int filaSeleccionada = tabla_producto_cliente.getSelectedRow();
                    if (filaSeleccionada != -1) {

                        int idCliente = (int) tabla_producto_cliente.getValueAt(filaSeleccionada, 0);
                        String nombreCliente = (String) tabla_producto_cliente.getValueAt(filaSeleccionada, 1);
                        clienteseleccionado = new Cliente();
                        clienteseleccionado.setIdCliente(idCliente);
                        txt_prove_cliente.setText(nombreCliente);
                        cambiarVista(PANELMOVIMIENTO_INGRESO);

                    }
                }
            }
        });

        tabla_proveedor_movimiento.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int filaSeleccionada = tabla_proveedor_movimiento.getSelectedRow();
                    if (filaSeleccionada != -1) {

                        int idProveedor = (int) tabla_proveedor_movimiento.getValueAt(filaSeleccionada, 0);
                        String nombreProveedor = (String) tabla_proveedor_movimiento.getValueAt(filaSeleccionada, 1);

                        txt_prove_cliente.setText(nombreProveedor);
                        proveedorseleccionado = new Proveedor();
                        proveedorseleccionado.setIdProveedor(idProveedor);
                        cambiarVista(PANELMOVIMIENTO_INGRESO);

                    }
                }
            }
        });

        tabla_producto_movimiento.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int filaSeleccionada = tabla_producto_movimiento.getSelectedRow();
                    if (filaSeleccionada != -1) {

                        int idProducto = (int) tabla_producto_movimiento.getValueAt(filaSeleccionada, 0);
                        String nombreProducto = (String) tabla_producto_movimiento.getValueAt(filaSeleccionada, 1);

                        productoSeleccionado = new Producto();
                        productoSeleccionado.setIdProducto(idProducto);
                        productoSeleccionado.setNombre(nombreProducto);

                        txt_producto.setText(nombreProducto);
                        cambiarVista(PANELMOVIMIENTO_INGRESO);

                    }
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PANELMOVIMIENTO = new javax.swing.JPanel();
        PANELMOVIMIENTO_INGRESO = new javax.swing.JPanel();
        titulo_ventana = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbo_tipo_movimiento = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jDateFecha = new com.toedter.calendar.JDateChooser();
        titulotipo = new javax.swing.JLabel();
        txt_prove_cliente = new javax.swing.JTextField();
        btn_busqueda_pro_cli = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txt_producto = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        btn_buscar_producto = new javax.swing.JButton();
        jSpcantidad = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabla_detalle__movimiento = new javax.swing.JTable();
        btn_agregar_lote = new javax.swing.JButton();
        btn_agregar_nuevo_movimiento = new javax.swing.JButton();
        PANELMOVIMIENTO_PRODUCTO = new javax.swing.JPanel();
        btn_retornar_productos = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabla_producto_movimiento = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        PANELMOVIMIENTO_PROVEEDOR = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        btn_retornar_proveedores = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabla_proveedor_movimiento = new javax.swing.JTable();
        PANELMOVIMIENTO_CLIENTE = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabla_producto_cliente = new javax.swing.JTable();
        btn_retornar_cliente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(470, 585));
        setPreferredSize(new java.awt.Dimension(470, 585));
        setResizable(false);
        setSize(new java.awt.Dimension(470, 585));
        getContentPane().setLayout(new java.awt.FlowLayout());

        PANELMOVIMIENTO_INGRESO.setMinimumSize(new java.awt.Dimension(460, 530));
        PANELMOVIMIENTO_INGRESO.setPreferredSize(new java.awt.Dimension(460, 540));
        PANELMOVIMIENTO_INGRESO.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titulo_ventana.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        titulo_ventana.setText("Nuevo Ingreso a Inventario");
        PANELMOVIMIENTO_INGRESO.add(titulo_ventana, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 239, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Tipo movimiento");
        PANELMOVIMIENTO_INGRESO.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 170, -1));

        cbo_tipo_movimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INGRESO", "SALIDA" }));
        cbo_tipo_movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_tipo_movimientoActionPerformed(evt);
            }
        });
        PANELMOVIMIENTO_INGRESO.add(cbo_tipo_movimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 160, 35));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Fecha Inicio");
        PANELMOVIMIENTO_INGRESO.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 170, -1));

        jDateFecha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PANELMOVIMIENTO_INGRESO.add(jDateFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 170, 35));

        titulotipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        titulotipo.setText("Proveedor / Cliente");
        PANELMOVIMIENTO_INGRESO.add(titulotipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 170, -1));

        txt_prove_cliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PANELMOVIMIENTO_INGRESO.add(txt_prove_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 160, 35));

        btn_busqueda_pro_cli.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_busqueda_pro_cli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar.png"))); // NOI18N
        btn_busqueda_pro_cli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busqueda_pro_cliActionPerformed(evt);
            }
        });
        PANELMOVIMIENTO_INGRESO.add(btn_busqueda_pro_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, 50, 35));
        PANELMOVIMIENTO_INGRESO.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 410, 10));

        txt_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PANELMOVIMIENTO_INGRESO.add(txt_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 230, 35));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Producto");
        PANELMOVIMIENTO_INGRESO.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 170, -1));

        btn_buscar_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_buscar_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar.png"))); // NOI18N
        btn_buscar_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_productoActionPerformed(evt);
            }
        });
        PANELMOVIMIENTO_INGRESO.add(btn_buscar_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(263, 250, 40, 35));
        PANELMOVIMIENTO_INGRESO.add(jSpcantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 120, 35));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Cantidad");
        PANELMOVIMIENTO_INGRESO.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, 90, -1));

        tabla_detalle__movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla_detalle__movimiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "PRODUCTO", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_detalle__movimiento.setShowGrid(true);
        jScrollPane5.setViewportView(tabla_detalle__movimiento);

        PANELMOVIMIENTO_INGRESO.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 410, 140));

        btn_agregar_lote.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_agregar_lote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nuevo.png"))); // NOI18N
        btn_agregar_lote.setText("Agregar");
        btn_agregar_lote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_loteActionPerformed(evt);
            }
        });
        PANELMOVIMIENTO_INGRESO.add(btn_agregar_lote, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 293, 130, 35));

        btn_agregar_nuevo_movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_agregar_nuevo_movimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/guardar.png"))); // NOI18N
        btn_agregar_nuevo_movimiento.setText("Guardar");
        btn_agregar_nuevo_movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_nuevo_movimientoActionPerformed(evt);
            }
        });
        PANELMOVIMIENTO_INGRESO.add(btn_agregar_nuevo_movimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 490, 130, 35));

        PANELMOVIMIENTO.add(PANELMOVIMIENTO_INGRESO);

        PANELMOVIMIENTO_PRODUCTO.setPreferredSize(new java.awt.Dimension(460, 540));
        PANELMOVIMIENTO_PRODUCTO.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_retornar_productos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_retornar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/return.png"))); // NOI18N
        btn_retornar_productos.setText("Retornar");
        btn_retornar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_retornar_productosActionPerformed(evt);
            }
        });
        PANELMOVIMIENTO_PRODUCTO.add(btn_retornar_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 450, 150, 35));

        tabla_producto_movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla_producto_movimiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "IDPRODUCTO", "PRODUCTO", "CATEGORIA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_producto_movimiento.setShowGrid(true);
        jScrollPane6.setViewportView(tabla_producto_movimiento);

        PANELMOVIMIENTO_PRODUCTO.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 410, 330));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Listado Productos");
        PANELMOVIMIENTO_PRODUCTO.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 239, -1));

        PANELMOVIMIENTO.add(PANELMOVIMIENTO_PRODUCTO);

        PANELMOVIMIENTO_PROVEEDOR.setPreferredSize(new java.awt.Dimension(460, 540));
        PANELMOVIMIENTO_PROVEEDOR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Listado Proveedores");
        PANELMOVIMIENTO_PROVEEDOR.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 239, -1));

        btn_retornar_proveedores.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_retornar_proveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/return.png"))); // NOI18N
        btn_retornar_proveedores.setText("Retornar");
        btn_retornar_proveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_retornar_proveedoresActionPerformed(evt);
            }
        });
        PANELMOVIMIENTO_PROVEEDOR.add(btn_retornar_proveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 460, 140, 35));

        tabla_proveedor_movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla_proveedor_movimiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "IDPROVEEDORES", "PROVEEDOR", "TELEFONO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_proveedor_movimiento.setShowGrid(true);
        jScrollPane7.setViewportView(tabla_proveedor_movimiento);

        PANELMOVIMIENTO_PROVEEDOR.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 410, 350));

        PANELMOVIMIENTO.add(PANELMOVIMIENTO_PROVEEDOR);

        PANELMOVIMIENTO_CLIENTE.setMinimumSize(new java.awt.Dimension(450, 530));
        PANELMOVIMIENTO_CLIENTE.setPreferredSize(new java.awt.Dimension(460, 540));
        PANELMOVIMIENTO_CLIENTE.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("Listado Cliente");
        PANELMOVIMIENTO_CLIENTE.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 239, -1));

        tabla_producto_cliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla_producto_cliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "IDCLIENTE", "CLIENTE", "IDENTIFICACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_producto_cliente.setShowGrid(true);
        jScrollPane8.setViewportView(tabla_producto_cliente);

        PANELMOVIMIENTO_CLIENTE.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 410, 350));

        btn_retornar_cliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_retornar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/return.png"))); // NOI18N
        btn_retornar_cliente.setText("Retornar");
        btn_retornar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_retornar_clienteActionPerformed(evt);
            }
        });
        PANELMOVIMIENTO_CLIENTE.add(btn_retornar_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 460, 140, 35));

        PANELMOVIMIENTO.add(PANELMOVIMIENTO_CLIENTE);

        getContentPane().add(PANELMOVIMIENTO);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void configurarTablaProducto() {
        this.model_producto = (DefaultTableModel) tabla_producto_movimiento.getModel();
        tabla_producto_movimiento.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla_producto_movimiento.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla_producto_movimiento.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla_producto_movimiento.getTableHeader().setResizingAllowed(false);
    }

    public void configurarTablaCliente() {
        this.model_cliente = (DefaultTableModel) tabla_producto_cliente.getModel();
        tabla_producto_cliente.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabla_producto_cliente.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla_producto_cliente.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla_producto_cliente.getTableHeader().setResizingAllowed(false);
    }

    public void configurarTablaProveedor() {
        this.model_proveedor = (DefaultTableModel) tabla_proveedor_movimiento.getModel();
        tabla_proveedor_movimiento.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla_proveedor_movimiento.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla_proveedor_movimiento.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla_proveedor_movimiento.getTableHeader().setResizingAllowed(false);
    }

    public void configurarTablaCarrito() {
        this.model_carrito = (DefaultTableModel) tabla_detalle__movimiento.getModel();
        tabla_detalle__movimiento.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla_detalle__movimiento.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla_detalle__movimiento.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla_detalle__movimiento.getTableHeader().setResizingAllowed(false);
    }

    public void llenarTablaProducto(List<Producto> productos, DefaultTableModel modelo) {
        modelo.setRowCount(0);

        for (Producto producto : productos) {

            Object[] rowData = {
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getCategoria().getNombre(),};
            modelo.addRow(rowData);
        }

        modelo.fireTableDataChanged();
    }

    // metodo generico
    public <T> void llenarTablaDetalle(List<T> detallesIngresoSalida, DefaultTableModel modelo) {
        modelo.setRowCount(0);

        for (T detalle : detallesIngresoSalida) {
            Object[] rowData;

            if (detalle instanceof DetalleIngreso) {
                // Tratar como DetalleIngreso
                DetalleIngreso detalleIngreso = (DetalleIngreso) detalle;
                rowData = new Object[]{
                    detalleIngreso.getProducto().getIdProducto(),
                    detalleIngreso.getProducto().getNombre(),
                    detalleIngreso.getCantidad()
                };
            } else if (detalle instanceof DetalleSalida) {
                // Tratar como DetalleSalida
                DetalleSalida detalleSalida = (DetalleSalida) detalle;
                rowData = new Object[]{
                    detalleSalida.getProducto().getIdProducto(),
                    detalleSalida.getProducto().getNombre(),
                    detalleSalida.getCantidad()
                };
            } else {
                rowData = new Object[]{};
            }

            modelo.addRow(rowData);
        }

        modelo.fireTableDataChanged();
    }

    public void llenarTablaCliente(List<Cliente> clientes, DefaultTableModel modelo) {
        modelo.setRowCount(0);

        for (Cliente cliente : clientes) {

            Object[] rowData = {
                cliente.getIdCliente(),
                cliente.getNombre(),
                cliente.getIdentificacion(),};
            modelo.addRow(rowData);
        }

        modelo.fireTableDataChanged();
    }

    public void llenarTablaProveedor(List<Proveedor> proveedores, DefaultTableModel modelo) {
        modelo.setRowCount(0);

        for (Proveedor proveedor : proveedores) {

            Object[] rowData = {
                proveedor.getIdProveedor(),
                proveedor.getNombre(),
                proveedor.getTelefono(),};
            modelo.addRow(rowData);
        }

        modelo.fireTableDataChanged();
    }

    public void abrirTipoVentana() {
        String modo = cbo_tipo_movimiento.getSelectedItem().toString();
        if (modo.equals("INGRESO")) {
            if (PANELMOVIMIENTO_PROVEEDOR != null) {
                cambiarVista(PANELMOVIMIENTO_PROVEEDOR);
            }
        } else {
            if (PANELMOVIMIENTO_CLIENTE != null) {
                cambiarVista(PANELMOVIMIENTO_CLIENTE);
            }
        }
    }


    private void btn_retornar_proveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_retornar_proveedoresActionPerformed
        cambiarVista(PANELMOVIMIENTO_INGRESO);
    }//GEN-LAST:event_btn_retornar_proveedoresActionPerformed

    private void btn_retornar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_retornar_productosActionPerformed
        cambiarVista(PANELMOVIMIENTO_INGRESO);
    }//GEN-LAST:event_btn_retornar_productosActionPerformed

    private void btn_busqueda_pro_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busqueda_pro_cliActionPerformed
        // TODO add your handling code here:
        this.abrirTipoVentana();
    }//GEN-LAST:event_btn_busqueda_pro_cliActionPerformed

    private void btn_retornar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_retornar_clienteActionPerformed
        cambiarVista(PANELMOVIMIENTO_INGRESO);
    }//GEN-LAST:event_btn_retornar_clienteActionPerformed

    private void btn_buscar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_productoActionPerformed
        cambiarVista(PANELMOVIMIENTO_PRODUCTO);
    }//GEN-LAST:event_btn_buscar_productoActionPerformed

    private void cbo_tipo_movimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_tipo_movimientoActionPerformed

        this.cambiartipotitulo();
    }//GEN-LAST:event_cbo_tipo_movimientoActionPerformed

    private void btn_agregar_loteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_loteActionPerformed

        int cantidad;

        try {
            cantidad = Integer.parseInt(jSpcantidad.getValue().toString());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String modo = cbo_tipo_movimiento.getSelectedItem().toString();

        if (productoSeleccionado != null) {

            if (modo.equals("INGRESO")) {

                DetalleIngreso nuevoDetalle = new DetalleIngreso();
                nuevoDetalle.setProducto(productoSeleccionado);
                nuevoDetalle.setCantidad(cantidad);

                boolean productoExistente = false;
                for (DetalleIngreso detalle : ingresoDetalles) {
                    if (detalle.getProducto().getNombre().equals(productoSeleccionado.getNombre())) {
                        detalle.setCantidad(detalle.getCantidad() + cantidad);
                        productoExistente = true;
                        break;
                    }
                }

                if (!productoExistente) {
                    ingresoDetalles.add(nuevoDetalle);
                }

                limpiarCampos();
                this.llenarTablaDetalle(ingresoDetalles, model_carrito);

            } 
            else {
                List<Producto> listaProductos = this.productoService.obtenerTodosLosProductos();
                List<Inventario> listaInventario = this.inventarioService.obtenerTodosLosInventarios();

                int stockMinimo = obtenerStockMinimo(productoSeleccionado.getIdProducto(), listaProductos);
                int stockActual = obtenerStockActual(productoSeleccionado.getIdProducto(), listaInventario);

                System.out.println("Stock minimo: " + stockMinimo);
                System.out.println("Stock actual: " + stockActual);

                if (stockActual == 0) {
                    JOptionPane.showMessageDialog(this, "No se puede realizar la salida. El stock atual es : " + stockActual, "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (cantidad > stockActual) {
                    JOptionPane.showMessageDialog(this, "No se puede realizar la salida. La cantidad solicitada es mayor que el stock actual.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idProductoSeleccionado = productoSeleccionado.getIdProducto();

                if (!stockActualMap.containsKey(idProductoSeleccionado)) {
                    System.out.println("No contiene el id dentro ");

                    int nuevostock = stockActual - cantidad; // 0

                    if (nuevostock < stockMinimo) {
                        JOptionPane.showMessageDialog(this, "No se puede realizar la salida. El stock mínimo para el producto es: " + stockMinimo, "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    stockActualMap.put(idProductoSeleccionado, stockActual - cantidad);

                } else {

                    int stockActualExistente = stockActualMap.get(idProductoSeleccionado); // busca el producto dentro de la lista temporal
                    int nuevoStock = stockActualExistente - cantidad; // del stock temporal del p - resta la cantidad 

                    System.out.println("Nuevo stock: " + nuevoStock);

                    if (nuevoStock < stockMinimo) {
                        JOptionPane.showMessageDialog(this, "No se puede realizar la salida. El stock mínimo para el producto es: " + stockMinimo, "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    stockActualMap.put(idProductoSeleccionado, nuevoStock);
                }

                // Verificar si ya existe el producto en la lista temporal
                DetalleSalida salidaExistente = null;
                for (DetalleSalida detalle : salidaDetallesTemp) {
                    if (detalle.getProducto().getNombre().equals(productoSeleccionado.getNombre())) {
                        salidaExistente = detalle;
                        break;
                    }
                }

                if (salidaExistente != null) {
                    salidaExistente.setCantidad(salidaExistente.getCantidad() + cantidad);
                } else {
                    DetalleSalida salidaDetalle = new DetalleSalida();
                    salidaDetalle.setProducto(productoSeleccionado);
                    salidaDetalle.setCantidad(cantidad);

                    boolean productoExistente = false;
                    for (DetalleSalida detalle : salidaDetallesTemp) {
                        if (detalle.getProducto().getNombre().equals(productoSeleccionado.getNombre())) {
                            detalle.setCantidad(detalle.getCantidad() + cantidad);
                            productoExistente = true;
                            break;
                        }
                    }

                    if (!productoExistente) {
                        salidaDetalles.add(salidaDetalle);
                        salidaDetallesTemp.add(salidaDetalle);
                    }
                }

                limpiarCampos();
                this.llenarTablaDetalle(salidaDetalles, model_carrito);

            }

        }
        else{
        
            JOptionPane.showMessageDialog(this, "Selecciona un producto ", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_btn_agregar_loteActionPerformed

    private int obtenerStockMinimo(int idProducto, List<Producto> listaProductos) {
        for (Producto producto : listaProductos) {
            if (producto.getIdProducto() == idProducto) {
                return producto.getStockMinimo();
            }
        }
        return 0;
    }

    private int obtenerStockActual(int idProducto, List<Inventario> listaInventario) {
        for (Inventario inventario : listaInventario) {
            if (inventario.getProducto().getIdProducto() == idProducto) {
                return inventario.getStockActual();
            }
        }
        return 0;
    }


    private void btn_agregar_nuevo_movimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_nuevo_movimientoActionPerformed

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaSeleccionada = jDateFecha.getDate();

        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fecha", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fechaFormateada = formatoFecha.format(fechaSeleccionada);

        String tipoMovimientoString = cbo_tipo_movimiento.getSelectedItem().toString();

        if (tipoMovimientoString.equals("INGRESO") && !"".equals(fechaFormateada)) {

            if (usuariologeado != null && proveedorseleccionado != null) {
                TipoMovimiento tipoMovimiento = TipoMovimiento.valueOf(tipoMovimientoString);

                IngresoInventario nuevoMovimiento = new IngresoInventario();
                nuevoMovimiento.setFecha(fechaFormateada);
                nuevoMovimiento.setUsuario(usuariologeado);
                nuevoMovimiento.setTipoMovimiento(tipoMovimiento);
                nuevoMovimiento.setProveedor(proveedorseleccionado);

                IngresoInventarioService ingresoService = new IngresoInventarioService(new IngresoInventarioDAO());
                nuevoMovimiento = ingresoService.realizarIngresoInventario(nuevoMovimiento);

                if (nuevoMovimiento.getIdIngreso() > 0) {

                    List<DetalleIngreso> detalles = ingresoDetalles;
                    DetalleIngresoService detalleService = new DetalleIngresoService(new DetalleIngresoDAO());

                    if (ingresoDetalles.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No se puede realziar un  ingreso , selecciona un producto", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    detalleService.insertarDetallesIngreso(detalles, nuevoMovimiento.getIdIngreso());

                    this.pantallaprincipal.llenarTablaInventario(this.inventarioService.obtenerTodosLosInventarios(), this.model_inventario);
                    this.pantallaprincipal.llenarTablaMovimientos(this.ingresoInventarioService.obtenerTodosLosIngresos(), this.model_movimiento);
                    this.pantallaprincipal.llenarTablaMovimientosProductos(this.productoService.obtenerMovimientosGenerales(), this.modelo_movimiento_producto);

                    JOptionPane.showMessageDialog(this, "Nuevo movimiento y detalles agregados exitosamente");

                    Window window = SwingUtilities.getWindowAncestor((Component) evt.getSource());
                    if (window instanceof JDialog) {
                        JDialog dialog = (JDialog) window;
                        dialog.dispose();
                    }

                    this.pantallaprincipal.verificarProductosPorAgotarse(this.inventarioService.obtenerTodosLosInventarios());

                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar el movimiento", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {

            if (usuariologeado != null && clienteseleccionado != null) {

                TipoMovimiento tipoMovimiento = TipoMovimiento.valueOf(tipoMovimientoString);

                SalidaInventario nuevoMovimiento = new SalidaInventario();
                nuevoMovimiento.setFecha(fechaFormateada);
                nuevoMovimiento.setUsuario(usuariologeado);
                nuevoMovimiento.setTipoMovimiento(tipoMovimiento);
                nuevoMovimiento.setCliente(clienteseleccionado);

                SalidaInventarioService salidaService = new SalidaInventarioService();
                nuevoMovimiento = salidaService.realizarSalidaInventario(nuevoMovimiento);

                if (nuevoMovimiento.getIdSalida() > 0) {

                    List<DetalleSalida> detalles = salidaDetalles;
                    DetalleSalidaService detalleService = new DetalleSalidaService();

                    if (salidaDetalles.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No se puede realizar una salida, selecciona un producto", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    detalleService.insertarDetallesSalida(detalles, nuevoMovimiento.getIdSalida());

                    this.pantallaprincipal.llenarTablaInventario(this.inventarioService.obtenerTodosLosInventarios(), this.model_inventario);
                    this.pantallaprincipal.llenarTablaMovimientos(this.salidaInventarioService.obtenerTodasLasSalidas(), this.model_movimiento);
                    this.pantallaprincipal.llenarTablaMovimientosProductos(this.productoService.obtenerMovimientosGenerales(), this.modelo_movimiento_producto);

                    JOptionPane.showMessageDialog(this, "Nuevo movimiento y detalles agregados exitosamente");

                    Window window = SwingUtilities.getWindowAncestor((Component) evt.getSource());
                    if (window instanceof JDialog) {
                        JDialog dialog = (JDialog) window;
                        dialog.dispose();
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar el movimiento", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

    }//GEN-LAST:event_btn_agregar_nuevo_movimientoActionPerformed

    private void limpiarCampos() {
        jSpcantidad.setValue(0);
        productoSeleccionado = null;
        txt_producto.setText("");
    }

    public void cambiarVista(JPanel panel) {
        //System.out.println("LLEGO "+ panel.getName()); // llega null !
        panel.setSize(PANELMOVIMIENTO.getWidth(), PANELMOVIMIENTO.getHeight());
        PANELMOVIMIENTO.removeAll();
        PANELMOVIMIENTO.add(panel); // aquii
        PANELMOVIMIENTO.repaint();
        activarVista(panel);
    }

    public void activarVista(JPanel panel) {

        PANELMOVIMIENTO_CLIENTE.setEnabled(false);
        PANELMOVIMIENTO_CLIENTE.setVisible(false);

        PANELMOVIMIENTO_INGRESO.setEnabled(false);
        PANELMOVIMIENTO_INGRESO.setVisible(false);

        PANELMOVIMIENTO_PROVEEDOR.setEnabled(false);
        PANELMOVIMIENTO_PROVEEDOR.setVisible(false);

        PANELMOVIMIENTO_PRODUCTO.setEnabled(false);
        PANELMOVIMIENTO_PRODUCTO.setVisible(false);

        panel.setEnabled(true);
        panel.setVisible(true);
    }

    public void cambiartipotitulo() {
        String modo = cbo_tipo_movimiento.getSelectedItem().toString();
        if (modo.equals("INGRESO")) {
            titulotipo.setText("Proveedor");
            titulo_ventana.setText("Nuevo Ingreso a Inventario");

        } else {
            titulotipo.setText("Cliente");
            titulo_ventana.setText("Nueva Salida del Inventario");
        }
        txt_prove_cliente.setText("");
    }

    public static void main(String args[]) {

        FlatIntelliJLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MovimientoForm dialog = new MovimientoForm(new javax.swing.JFrame(), true, 1, null, null, null, null, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PANELMOVIMIENTO;
    private javax.swing.JPanel PANELMOVIMIENTO_CLIENTE;
    private javax.swing.JPanel PANELMOVIMIENTO_INGRESO;
    private javax.swing.JPanel PANELMOVIMIENTO_PRODUCTO;
    private javax.swing.JPanel PANELMOVIMIENTO_PROVEEDOR;
    private javax.swing.JButton btn_agregar_lote;
    private javax.swing.JButton btn_agregar_nuevo_movimiento;
    private javax.swing.JButton btn_buscar_producto;
    private javax.swing.JButton btn_busqueda_pro_cli;
    private javax.swing.JButton btn_retornar_cliente;
    private javax.swing.JButton btn_retornar_productos;
    private javax.swing.JButton btn_retornar_proveedores;
    private javax.swing.JComboBox<String> cbo_tipo_movimiento;
    private com.toedter.calendar.JDateChooser jDateFecha;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner jSpcantidad;
    private javax.swing.JTable tabla_detalle__movimiento;
    private javax.swing.JTable tabla_producto_cliente;
    private javax.swing.JTable tabla_producto_movimiento;
    private javax.swing.JTable tabla_proveedor_movimiento;
    private javax.swing.JLabel titulo_ventana;
    private javax.swing.JLabel titulotipo;
    private javax.swing.JTextField txt_producto;
    private javax.swing.JTextField txt_prove_cliente;
    // End of variables declaration//GEN-END:variables
}

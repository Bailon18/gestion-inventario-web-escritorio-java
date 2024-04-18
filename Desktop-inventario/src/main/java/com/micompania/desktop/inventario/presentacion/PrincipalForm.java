package com.micompania.desktop.inventario.presentacion;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.micompania.desktop.inventario.conexion.Conexion;
import com.micompania.desktop.inventario.entidad.ENUM.TipoMovimiento;
import com.micompania.desktop.inventario.entidad.IngresoInventario;
import com.micompania.desktop.inventario.entidad.Inventario;
import com.micompania.desktop.inventario.entidad.Producto;
import com.micompania.desktop.inventario.entidad.SalidaInventario;
import com.micompania.desktop.inventario.entidad.Usuario;
import com.micompania.desktop.inventario.negocio.DAO.IngresoInventarioDAO;
import com.micompania.desktop.inventario.negocio.DAO.InventarioDAO;
import com.micompania.desktop.inventario.negocio.DAO.ProductoDAO;
import com.micompania.desktop.inventario.negocio.DAO.UsuarioDAO;
import com.micompania.desktop.inventario.negocio.DTO.MovimientoProductoDTO;
import com.micompania.desktop.inventario.negocio.Services.CategoriaService;
import com.micompania.desktop.inventario.negocio.Services.IngresoInventarioService;
import com.micompania.desktop.inventario.negocio.Services.InventarioService;
import com.micompania.desktop.inventario.negocio.Services.ProductoService;
import com.micompania.desktop.inventario.negocio.Services.ProveedorService;
import com.micompania.desktop.inventario.negocio.Services.SalidaInventarioService;
import com.micompania.desktop.inventario.negocio.Services.UsuarioService;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public final class PrincipalForm extends javax.swing.JFrame {

    private Usuario usuario;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final InventarioService inventarioService;
    private final IngresoInventarioService ingresoinventarioService;
    private final SalidaInventarioService salidainventarioService;
    DefaultTableModel model;
    DefaultTableModel model_producto;
    DefaultTableModel model_inventario;
    DefaultTableModel model_movimiento;
    DefaultTableModel model_movimiento_producto;

    public PrincipalForm() {

        this.usuarioService = new UsuarioService(new UsuarioDAO());
        this.productoService = new ProductoService(new ProductoDAO(new CategoriaService(), new ProveedorService()));
        this.inventarioService = new InventarioService(new InventarioDAO(productoService));
        this.ingresoinventarioService = new IngresoInventarioService(new IngresoInventarioDAO());
        this.salidainventarioService = new SalidaInventarioService();
        initComponents();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.configurarTablaUsuario();
        this.configurarTablaProducto();
        this.configurarTablaInventario();
        this.configurarTablaMovimiento();
        this.configurarTablaMovimientoProducto();
        this.setLocationRelativeTo(null);
        this.cambiarVista(panel_productos);
        this.llenarTablaUsuario(this.usuarioService.obtenerTodosLosUsuarios(), this.model);
        this.llenarTablaProductos(this.productoService.obtenerTodosLosProductos(), this.model_producto);
        this.llenarTablaInventario(this.inventarioService.obtenerTodosLosInventarios(), this.model_inventario);
        this.llenarTablaMovimientos(this.ingresoinventarioService.obtenerTodosLosIngresos(), this.model_movimiento);
        this.llenarTablaMovimientosProductos(this.productoService.obtenerMovimientosGenerales(), this.model_movimiento_producto);

        checkTodosMovimiento.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    txtproducto_busqueda.setText("");
                    txtproducto_busqueda.setEnabled(false);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    txtproducto_busqueda.setText("");
                    txtproducto_busqueda.setEnabled(true);
                }
            }
        });
    }

    public PrincipalForm(Usuario usuario) {
        this();
        setUsuario(usuario);

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            verificarProductosPorAgotarse(this.inventarioService.obtenerTodosLosInventarios());
        }
    }

    // ###################################### Movimiento por producto ######################################
    public void configurarTablaMovimientoProducto() {
        this.model_movimiento_producto = (DefaultTableModel) tabla_movimiento_producto.getModel();
        tabla_movimiento_producto.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla_movimiento_producto.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla_movimiento_producto.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla_movimiento_producto.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabla_movimiento_producto.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabla_movimiento_producto.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabla_movimiento_producto.getTableHeader().setResizingAllowed(false);
    }

    public void llenarTablaMovimientosProductos(List<MovimientoProductoDTO> movimientoproductos, DefaultTableModel modelo) {

        modelo.setRowCount(0);

        for (MovimientoProductoDTO movi_pro : movimientoproductos) {

            Object[] rowData = {
                movi_pro.getIdProducto(),
                movi_pro.getFecha(),
                movi_pro.getNombreProducto(),
                movi_pro.getCantidad(),
                movi_pro.getClienteProveedor(),
                movi_pro.getTipoMovimiento(),};
            modelo.addRow(rowData);
        }

        modelo.fireTableDataChanged();
    }

    // ######################################## Modulo Usuario ###############################################
    public void configurarTablaUsuario() {
        this.model = (DefaultTableModel) tabla_usuarios.getModel();
        tabla_usuarios.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla_usuarios.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla_usuarios.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla_usuarios.getColumnModel().getColumn(3).setPreferredWidth(200);
        tabla_usuarios.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabla_usuarios.getColumnModel().getColumn(5).setPreferredWidth(70);
        tabla_usuarios.getTableHeader().setResizingAllowed(false);
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        txt_usuario_logeado.setText(this.usuario.getNombre() + " " + this.usuario.getApellido());
        txt_rol.setText(this.usuario.getTipo().toString());

        if (!"ADMINISTRADOR".equals(this.usuario.getTipo().toString())) {
            btn_menu_usuario.hide();
        }

    }

    public void llenarTablaUsuario(List<Usuario> usuarios, DefaultTableModel modelo) {
        modelo.setRowCount(0);

        for (Usuario usuario : usuarios) {
            String estado = usuario.isActivo() ? "Activo" : "Inactivo";

            Object[] rowData = {
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getTipo(),
                estado
            };
            modelo.addRow(rowData);
        }

        modelo.fireTableDataChanged();
    }

    public void realizarBusqueda() {

        String filtro = txt_campo_filtro.getText().toLowerCase();

        List<Usuario> usuariosFiltrados;

        if (!filtro.isEmpty()) {
            usuariosFiltrados = this.usuarioService.buscarUsuariosPorFiltro(filtro);
        } else {
            usuariosFiltrados = this.usuarioService.obtenerTodosLosUsuarios();
        }
        llenarTablaUsuario(usuariosFiltrados, this.model);
    }

    // ###################################### Modulo Inventario ############################################
    public void configurarTablaInventario() {
        this.model_inventario = (DefaultTableModel) tabla_inventario.getModel();
        tabla_inventario.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla_inventario.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla_inventario.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla_inventario.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabla_inventario.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabla_inventario.getColumnModel().getColumn(5).setPreferredWidth(150);
        tabla_inventario.getColumnModel().getColumn(6).setPreferredWidth(150);
        tabla_inventario.getTableHeader().setResizingAllowed(false);
        EstadoCellRenderer estadoCellRenderer = new EstadoCellRenderer();
        tabla_inventario.getColumnModel().getColumn(6).setCellRenderer(estadoCellRenderer);
    }

    public void llenarTablaInventario(List<Inventario> inventarios, DefaultTableModel modelo) {

        modelo.setRowCount(0);

        for (Inventario inventario : inventarios) {
            Object[] rowData = {
                inventario.getIdInventario(),
                inventario.getProducto().getNombre(),
                inventario.getProducto().getStockMinimo(),
                inventario.getSalida(),
                inventario.getIngreso(),
                inventario.getStockActual(),
                inventario.getProducto().getEstado()
            };
            modelo.addRow(rowData);
        }

        modelo.fireTableDataChanged();
    }

    public void verificarProductosPorAgotarse(List<Inventario> inventarios) {

        List<Producto> productosPorAgotarse = new ArrayList<>();

        for (Inventario inventario : inventarios) {
            if (inventario.getProducto().getEstado().equals("Por agotarse")) {
                productosPorAgotarse.add(inventario.getProducto());
            }
        }

        StringBuilder mensaje = new StringBuilder("Productos por agotarse:\n");
        for (Producto producto : productosPorAgotarse) {
            mensaje.append(producto.getNombre()).append("\n");
        }
        JOptionPane.showMessageDialog(this, mensaje.toString(), "Alerta", JOptionPane.WARNING_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panel_contenido = new javax.swing.JPanel();
        panel_productos = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_campo_filtro_producto = new javax.swing.JTextField();
        btn_busqueda_producto = new javax.swing.JButton();
        btn_principal_nuevoproducto = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_producto = new javax.swing.JTable();
        btn_producto_editar = new javax.swing.JButton();
        btn_producto_eliminar = new javax.swing.JButton();
        btn_producto_exportar = new javax.swing.JButton();
        panel_usuarios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_usuarios = new javax.swing.JTable();
        txt_campo_filtro = new javax.swing.JTextField();
        btn_busqueda = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btn_principal_nuevosuario = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btn_usuario_inabilitar = new javax.swing.JButton();
        btn_usuario_editar = new javax.swing.JButton();
        panel_movimientos = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btn_busqueda_movimiento = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabla_movimiento = new javax.swing.JTable();
        btn_ver_detalle_movimiento = new javax.swing.JButton();
        jDateFechaFin = new com.toedter.calendar.JDateChooser();
        jDateFechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        cbo_tipo_movimiento = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        btn_nuevo_movimiento = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        btn_ver_movimiento_productog = new javax.swing.JButton();
        panel_inventario = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_campo_filtro_inventario = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabla_inventario = new javax.swing.JTable();
        btn_busqueda_inventario = new javax.swing.JButton();
        btn_reporte_inventario = new javax.swing.JButton();
        panel_movimiento_general = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cbo_tipo_movimiento_producto = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        btn_nuevo_movimiento_producto = new javax.swing.JButton();
        txtproducto_busqueda = new javax.swing.JTextField();
        Producto1 = new javax.swing.JLabel();
        checkTodosMovimiento = new javax.swing.JCheckBox();
        btn_busqueda_movimiento_producto = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabla_movimiento_producto = new javax.swing.JTable();
        btn_ver_movimiento_lote = new javax.swing.JButton();
        btn_reporte_movimiento_totales = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_usuario_logeado = new javax.swing.JLabel();
        txt_rol = new javax.swing.JLabel();
        panel_cabecera = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_menu_usuario = new javax.swing.JButton();
        btn_menu_movimiento = new javax.swing.JButton();
        btn_menu_inventario = new javax.swing.JButton();
        btn_menu_producto = new javax.swing.JButton();
        btn_cerrar_sesion = new javax.swing.JLabel();
        panel_menu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_productos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Listado Productos");
        panel_productos.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 239, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Busqueda general");
        panel_productos.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 239, -1));

        txt_campo_filtro_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panel_productos.add(txt_campo_filtro_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 373, 35));

        btn_busqueda_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_busqueda_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar.png"))); // NOI18N
        btn_busqueda_producto.setText("buscar");
        btn_busqueda_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busqueda_productoActionPerformed(evt);
            }
        });
        panel_productos.add(btn_busqueda_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 127, 35));

        btn_principal_nuevoproducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_principal_nuevoproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nuevo.png"))); // NOI18N
        btn_principal_nuevoproducto.setText("Nuevo Producto");
        btn_principal_nuevoproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_principal_nuevoproductoActionPerformed(evt);
            }
        });
        panel_productos.add(btn_principal_nuevoproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 120, 170, 35));

        tabla_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla_producto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "PRECIO", "CATEGORIA", "PROVEEDOR", "STOCK MINIMO", "ESTADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_producto.setShowGrid(true);
        jScrollPane3.setViewportView(tabla_producto);

        panel_productos.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 893, 326));

        btn_producto_editar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_producto_editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editar.png"))); // NOI18N
        btn_producto_editar.setText("Editar");
        btn_producto_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_producto_editarActionPerformed(evt);
            }
        });
        panel_productos.add(btn_producto_editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 520, 120, 35));

        btn_producto_eliminar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_producto_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bloquear.png"))); // NOI18N
        btn_producto_eliminar.setText("Inabilitar");
        btn_producto_eliminar.setToolTipText("Inabilitar usuario");
        btn_producto_eliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_producto_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_producto_eliminarActionPerformed(evt);
            }
        });
        panel_productos.add(btn_producto_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 520, 120, 35));

        btn_producto_exportar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_producto_exportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/export.png"))); // NOI18N
        btn_producto_exportar.setText("Exportar");
        btn_producto_exportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_producto_exportarActionPerformed(evt);
            }
        });
        panel_productos.add(btn_producto_exportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 520, 120, 35));

        panel_usuarios.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla_usuarios.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla_usuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "APELLIDO", "CORREO", "ROL", "ESTADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_usuarios.setShowGrid(true);
        jScrollPane1.setViewportView(tabla_usuarios);

        panel_usuarios.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 893, 326));

        txt_campo_filtro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panel_usuarios.add(txt_campo_filtro, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 373, 35));

        btn_busqueda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_busqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar.png"))); // NOI18N
        btn_busqueda.setText("buscar");
        btn_busqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busquedaActionPerformed(evt);
            }
        });
        panel_usuarios.add(btn_busqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 127, 35));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Listado Usuarios");
        panel_usuarios.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 239, -1));

        btn_principal_nuevosuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_principal_nuevosuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nuevo.png"))); // NOI18N
        btn_principal_nuevosuario.setText("Nuevo Usuario");
        btn_principal_nuevosuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_principal_nuevosuarioActionPerformed(evt);
            }
        });
        panel_usuarios.add(btn_principal_nuevosuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 120, 170, 35));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Busqueda general");
        panel_usuarios.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 239, -1));

        btn_usuario_inabilitar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_usuario_inabilitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bloquear.png"))); // NOI18N
        btn_usuario_inabilitar.setText("Inabilitar");
        btn_usuario_inabilitar.setToolTipText("Inabilitar usuario");
        btn_usuario_inabilitar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_usuario_inabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuario_inabilitarActionPerformed(evt);
            }
        });
        panel_usuarios.add(btn_usuario_inabilitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 520, 120, 35));

        btn_usuario_editar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_usuario_editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editar.png"))); // NOI18N
        btn_usuario_editar.setText("Editar");
        btn_usuario_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuario_editarActionPerformed(evt);
            }
        });
        panel_usuarios.add(btn_usuario_editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 520, 120, 35));

        panel_movimientos.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Listado Movimientos por Lote");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Tipo movimiento");

        btn_busqueda_movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_busqueda_movimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar.png"))); // NOI18N
        btn_busqueda_movimiento.setText("buscar");
        btn_busqueda_movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busqueda_movimientoActionPerformed(evt);
            }
        });

        tabla_movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla_movimiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "IDMOVIMIENTO", "FECHA", "PROVEEDOR / CLIENTE", "EMPLEADO", "TIPO MOVIMIENTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_movimiento.setShowGrid(true);
        jScrollPane5.setViewportView(tabla_movimiento);

        btn_ver_detalle_movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ver_detalle_movimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detalle.png"))); // NOI18N
        btn_ver_detalle_movimiento.setText("ver detalle");
        btn_ver_detalle_movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_detalle_movimientoActionPerformed(evt);
            }
        });

        jDateFechaFin.setDateFormatString("y MMM d");
        jDateFechaFin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jDateFechaInicio.setDateFormatString("y MMM d");
        jDateFechaInicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Busqueda por rango de fecha");

        cbo_tipo_movimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INGRESO", "SALIDA" }));
        cbo_tipo_movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_tipo_movimientoActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Fecha Fin");

        btn_nuevo_movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_nuevo_movimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nuevo.png"))); // NOI18N
        btn_nuevo_movimiento.setText("Nuevo Movimiento");
        btn_nuevo_movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo_movimientoActionPerformed(evt);
            }
        });

        btn_limpiar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_limpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/limpiar.png"))); // NOI18N
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Fecha Inicio");

        btn_ver_movimiento_productog.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ver_movimiento_productog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ver.png"))); // NOI18N
        btn_ver_movimiento_productog.setText("ver Movimiento por producto");
        btn_ver_movimiento_productog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_movimiento_productogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_movimientosLayout = new javax.swing.GroupLayout(panel_movimientos);
        panel_movimientos.setLayout(panel_movimientosLayout);
        panel_movimientosLayout.setHorizontalGroup(
            panel_movimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_movimientosLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panel_movimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_movimientosLayout.createSequentialGroup()
                        .addComponent(btn_ver_movimiento_productog, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_ver_detalle_movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_movimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel_movimientosLayout.createSequentialGroup()
                            .addGap(160, 160, 160)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_movimientosLayout.createSequentialGroup()
                            .addGroup(panel_movimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel_movimientosLayout.createSequentialGroup()
                                    .addComponent(cbo_tipo_movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(27, 27, 27)
                                    .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addComponent(btn_busqueda_movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_movimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panel_movimientosLayout.createSequentialGroup()
                                .addComponent(jDateFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jDateFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_nuevo_movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(59, 59, 59))
        );
        panel_movimientosLayout.setVerticalGroup(
            panel_movimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_movimientosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel15)
                .addGap(29, 29, 29)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_movimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addGap(2, 2, 2)
                .addGroup(panel_movimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_nuevo_movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_movimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbo_tipo_movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_busqueda_movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_movimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ver_detalle_movimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ver_movimiento_productog, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_inventario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Listado Inventarios");
        panel_inventario.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 239, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Busqueda general");
        panel_inventario.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 239, -1));

        txt_campo_filtro_inventario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panel_inventario.add(txt_campo_filtro_inventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 373, 35));

        tabla_inventario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla_inventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "PRODUCTO", "STOCK MINIMO", "SALIDA", "INGRESO", "STOCK ACTUAL", "ESTADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_inventario.setShowGrid(true);
        jScrollPane4.setViewportView(tabla_inventario);

        panel_inventario.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 893, 326));

        btn_busqueda_inventario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_busqueda_inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar.png"))); // NOI18N
        btn_busqueda_inventario.setText("buscar");
        btn_busqueda_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busqueda_inventarioActionPerformed(evt);
            }
        });
        panel_inventario.add(btn_busqueda_inventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 127, 35));

        btn_reporte_inventario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_reporte_inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/export.png"))); // NOI18N
        btn_reporte_inventario.setText("Exportar");
        btn_reporte_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporte_inventarioActionPerformed(evt);
            }
        });
        panel_inventario.add(btn_reporte_inventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 120, 120, 35));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Listado Movimientos por producto");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Filtro movimiento");

        cbo_tipo_movimiento_producto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INGRESO", "SALIDA", "AMBOS" }));
        cbo_tipo_movimiento_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_tipo_movimiento_productoActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Tipo movimiento");

        btn_nuevo_movimiento_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_nuevo_movimiento_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nuevo.png"))); // NOI18N
        btn_nuevo_movimiento_producto.setText("Nuevo Movimiento");
        btn_nuevo_movimiento_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo_movimiento_productoActionPerformed(evt);
            }
        });

        txtproducto_busqueda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        Producto1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Producto1.setText("Producto");

        checkTodosMovimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        checkTodosMovimiento.setText("Todos");

        btn_busqueda_movimiento_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_busqueda_movimiento_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar.png"))); // NOI18N
        btn_busqueda_movimiento_producto.setText("buscar");
        btn_busqueda_movimiento_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busqueda_movimiento_productoActionPerformed(evt);
            }
        });

        tabla_movimiento_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla_movimiento_producto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "IDPRODUCTO", "FECHA", "PRODUCTO", "CANTIDAD", "CLIENTE / PROVEEDOR", "TIPO MOVIMIENTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_movimiento_producto.setShowGrid(true);
        jScrollPane6.setViewportView(tabla_movimiento_producto);

        btn_ver_movimiento_lote.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ver_movimiento_lote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ver.png"))); // NOI18N
        btn_ver_movimiento_lote.setText("ver Movimiento por lote");
        btn_ver_movimiento_lote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_movimiento_loteActionPerformed(evt);
            }
        });

        btn_reporte_movimiento_totales.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_reporte_movimiento_totales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/export.png"))); // NOI18N
        btn_reporte_movimiento_totales.setText("Exportar");
        btn_reporte_movimiento_totales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporte_movimiento_totalesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_movimiento_generalLayout = new javax.swing.GroupLayout(panel_movimiento_general);
        panel_movimiento_general.setLayout(panel_movimiento_generalLayout);
        panel_movimiento_generalLayout.setHorizontalGroup(
            panel_movimiento_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_movimiento_generalLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panel_movimiento_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_movimiento_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel_movimiento_generalLayout.createSequentialGroup()
                            .addGroup(panel_movimiento_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel_movimiento_generalLayout.createSequentialGroup()
                                    .addGroup(panel_movimiento_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Producto1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panel_movimiento_generalLayout.createSequentialGroup()
                                            .addComponent(txtproducto_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(checkTodosMovimiento)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel_movimiento_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_movimiento_generalLayout.createSequentialGroup()
                                            .addComponent(cbo_tipo_movimiento_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btn_busqueda_movimiento_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_nuevo_movimiento_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_movimiento_generalLayout.createSequentialGroup()
                        .addComponent(btn_reporte_movimiento_totales)
                        .addGap(18, 18, 18)
                        .addComponent(btn_ver_movimiento_lote, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59))
        );
        panel_movimiento_generalLayout.setVerticalGroup(
            panel_movimiento_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_movimiento_generalLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel_movimiento_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_movimiento_generalLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel21)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_movimiento_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbo_tipo_movimiento_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_nuevo_movimiento_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_busqueda_movimiento_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_movimiento_generalLayout.createSequentialGroup()
                        .addComponent(Producto1)
                        .addGap(3, 3, 3)
                        .addComponent(txtproducto_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(checkTodosMovimiento))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_movimiento_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ver_movimiento_lote, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_reporte_movimiento_totales))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_contenidoLayout = new javax.swing.GroupLayout(panel_contenido);
        panel_contenido.setLayout(panel_contenidoLayout);
        panel_contenidoLayout.setHorizontalGroup(
            panel_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_inventario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_contenidoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 1008, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE)))
            .addGroup(panel_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_contenidoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_usuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(panel_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_movimientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_contenidoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_movimiento_general, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panel_contenidoLayout.setVerticalGroup(
            panel_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_contenidoLayout.createSequentialGroup()
                .addComponent(panel_inventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panel_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_contenidoLayout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(panel_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(23, Short.MAX_VALUE)))
            .addGroup(panel_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_contenidoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_usuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(panel_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_movimientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_contenidoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_movimiento_general, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel1.add(panel_contenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 1030, 600));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/user.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 10, 50, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logoo.png"))); // NOI18N
        jLabel1.setToolTipText("");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel1.setName(""); // NOI18N
        jLabel1.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 50, 210, -1));

        txt_usuario_logeado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_usuario_logeado.setForeground(new java.awt.Color(255, 255, 255));
        txt_usuario_logeado.setText("Nombre usuario");
        jPanel1.add(txt_usuario_logeado, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 10, 160, -1));

        txt_rol.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_rol.setForeground(new java.awt.Color(255, 255, 255));
        txt_rol.setText("rol");
        jPanel1.add(txt_rol, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 30, 160, -1));

        panel_cabecera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo.jpg"))); // NOI18N
        panel_cabecera.setText("jLabel1");
        jPanel1.add(panel_cabecera, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 1030, 60));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nombre usuario");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 10, 140, -1));

        btn_menu_usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_menu_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/user.png"))); // NOI18N
        btn_menu_usuario.setText("Usuarios");
        btn_menu_usuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_menu_usuario.setHideActionText(true);
        btn_menu_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_menu_usuarioActionPerformed(evt);
            }
        });
        jPanel1.add(btn_menu_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 170, 40));

        btn_menu_movimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_menu_movimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movimientos.png"))); // NOI18N
        btn_menu_movimiento.setText("Movimientos");
        btn_menu_movimiento.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_menu_movimiento.setHideActionText(true);
        btn_menu_movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_menu_movimientoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_menu_movimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 170, 40));

        btn_menu_inventario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_menu_inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proveedores.png"))); // NOI18N
        btn_menu_inventario.setText("Inventario");
        btn_menu_inventario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_menu_inventario.setHideActionText(true);
        btn_menu_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_menu_inventarioActionPerformed(evt);
            }
        });
        jPanel1.add(btn_menu_inventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 170, 40));

        btn_menu_producto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_menu_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/productos.png"))); // NOI18N
        btn_menu_producto.setText("Productos");
        btn_menu_producto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_menu_producto.setHideActionText(true);
        btn_menu_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_menu_productoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_menu_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 170, 40));

        btn_cerrar_sesion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_cerrar_sesion.setForeground(new java.awt.Color(255, 255, 255));
        btn_cerrar_sesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar-sesion.png"))); // NOI18N
        btn_cerrar_sesion.setText("Cerrar sesión");
        btn_cerrar_sesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cerrar_sesionMouseClicked(evt);
            }
        });
        jPanel1.add(btn_cerrar_sesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 616, 150, 30));

        panel_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo.jpg"))); // NOI18N
        panel_menu.setText("jLabel1");
        jPanel1.add(panel_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 195, 660));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_menu_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menu_usuarioActionPerformed
        cambiarVista(panel_usuarios);
    }//GEN-LAST:event_btn_menu_usuarioActionPerformed

    private void btn_menu_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menu_productoActionPerformed
        cambiarVista(panel_productos);
    }//GEN-LAST:event_btn_menu_productoActionPerformed

    private void btn_menu_movimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menu_movimientoActionPerformed

        cambiarVista(panel_movimiento_general);
    }//GEN-LAST:event_btn_menu_movimientoActionPerformed

    private void btn_menu_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menu_inventarioActionPerformed
        cambiarVista(panel_inventario);
    }//GEN-LAST:event_btn_menu_inventarioActionPerformed

    private void btn_cerrar_sesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cerrar_sesionMouseClicked

        this.dispose();
        LoginFormm login = new LoginFormm();
        login.setVisible(true);
    }//GEN-LAST:event_btn_cerrar_sesionMouseClicked

    private void btn_busquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busquedaActionPerformed
        this.realizarBusqueda();
    }//GEN-LAST:event_btn_busquedaActionPerformed

    private void btn_principal_nuevosuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_principal_nuevosuarioActionPerformed

        UsuarioNewEditForm usuarioform = new UsuarioNewEditForm(this, true, 1, null, this.model);
        usuarioform.setVisible(true);
    }//GEN-LAST:event_btn_principal_nuevosuarioActionPerformed

    private void btn_usuario_inabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuario_inabilitarActionPerformed


    }//GEN-LAST:event_btn_usuario_inabilitarActionPerformed

    private void btn_usuario_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuario_editarActionPerformed

        int filaSeleccionada = tabla_usuarios.getSelectedRow();

        if (filaSeleccionada != -1) {
            int idUsuario = (int) tabla_usuarios.getValueAt(filaSeleccionada, 0);

            Usuario usuario = usuarioService.buscarUsuario(idUsuario);

            if (usuario != null) {
                UsuarioNewEditForm editForm = new UsuarioNewEditForm(this, true, 2, usuario, this.model);
                editForm.setVisible(true);

                llenarTablaUsuario(usuarioService.obtenerTodosLosUsuarios(), this.model);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para editar usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_usuario_editarActionPerformed

    //##################################### Productos ###############################
    public void configurarTablaProducto() {
        this.model_producto = (DefaultTableModel) tabla_producto.getModel();
        tabla_producto.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla_producto.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla_producto.getColumnModel().getColumn(2).setPreferredWidth(50);
        tabla_producto.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabla_producto.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabla_producto.getColumnModel().getColumn(5).setPreferredWidth(80);
        tabla_producto.getColumnModel().getColumn(5).setPreferredWidth(70);
        tabla_producto.getTableHeader().setResizingAllowed(false);
    }

    public void realizarBusquedaProducto() {

        String filtro = txt_campo_filtro_producto.getText().toLowerCase();

        List<Producto> productoFiltrados = null;

        if (!filtro.isEmpty()) {
            productoFiltrados = this.productoService.buscarProductoPorCriterio(filtro);
        } else {
            productoFiltrados = this.productoService.obtenerTodosLosProductos();
        }
        llenarTablaProductos(productoFiltrados, this.model_producto);
    }

    public void llenarTablaProductos(List<Producto> productos, DefaultTableModel modelo) {

        modelo.setRowCount(0);

        for (Producto producto : productos) {

            String estado = producto.isActivo() == true ? "Activo" : "Inactivo";

            Object[] rowData = {
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getCategoria().getNombre(),
                producto.getProveedor().getNombre(),
                producto.getStockMinimo(),
                estado
            };
            modelo.addRow(rowData);
        }

        modelo.fireTableDataChanged();
        this.llenarTablaInventario(this.inventarioService.obtenerTodosLosInventarios(), this.model_inventario);
    }

    //##################################### Movimientos ###############################
    public void configurarTablaMovimiento() {

        this.model_movimiento = (DefaultTableModel) tabla_movimiento.getModel();
        tabla_movimiento.getColumnModel().getColumn(0).setPreferredWidth(80);
        tabla_movimiento.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla_movimiento.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabla_movimiento.getColumnModel().getColumn(3).setPreferredWidth(200);
        tabla_movimiento.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabla_movimiento.getTableHeader().setResizingAllowed(false);
    }

    // Metodo generico
    public <T> void llenarTablaMovimientos(List<T> movimientos, DefaultTableModel modelo) {

        modelo.setRowCount(0);

        for (T movimiento : movimientos) {

            if (movimiento instanceof IngresoInventario) {
                IngresoInventario ingreso = (IngresoInventario) movimiento;

                Object[] rowData1 = {
                    ingreso.getIdIngreso(),
                    ingreso.getFecha(),
                    ingreso.getProveedor().getNombre(),
                    ingreso.getUsuario().getNombre(),
                    ingreso.getTipoMovimiento()
                };

                modelo.addRow(rowData1);

            } else if (movimiento instanceof SalidaInventario) {
                SalidaInventario salida = (SalidaInventario) movimiento;

                Object[] rowData2 = {
                    salida.getIdSalida(),
                    salida.getFecha(),
                    salida.getCliente().getNombre(),
                    salida.getUsuario().getNombre(),
                    salida.getTipoMovimiento()
                };

                modelo.addRow(rowData2);

            }
        }

        modelo.fireTableDataChanged();
    }

    private void btn_busqueda_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busqueda_productoActionPerformed
        this.realizarBusquedaProducto();
    }//GEN-LAST:event_btn_busqueda_productoActionPerformed

    private void btn_principal_nuevoproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_principal_nuevoproductoActionPerformed
        ProductoNewEditForm productoform = new ProductoNewEditForm(this, true, 1, null, this.model_producto, this.model_inventario);
        productoform.setVisible(true);
    }//GEN-LAST:event_btn_principal_nuevoproductoActionPerformed

    private void btn_producto_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_producto_editarActionPerformed

        int filaSeleccionada = tabla_producto.getSelectedRow();

        if (filaSeleccionada != -1) {
            int idUsuario = (int) tabla_producto.getValueAt(filaSeleccionada, 0);

            Producto producto = productoService.buscarProducto(idUsuario);

            if (producto != null) {
                ProductoNewEditForm editForm = new ProductoNewEditForm(this, true, 2, producto, this.model_producto, this.model_inventario);
                editForm.setVisible(true);

                llenarTablaProductos(productoService.obtenerTodosLosProductos(), this.model_producto);
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para editar producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_producto_editarActionPerformed

    private void btn_producto_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_producto_eliminarActionPerformed

        int filaSeleccionada = tabla_producto.getSelectedRow();

        if (filaSeleccionada != -1) {

            int idProducto = (int) tabla_producto.getValueAt(filaSeleccionada, 0);

            if (idProducto > 0) {

                int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de inhabilitar al producto?", "Confirmar Inhabilitación", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {

                    productoService.cambiarEstadoProducto(idProducto, false);
                    llenarTablaProductos(productoService.obtenerTodosLosProductos(), this.model_producto);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para inhabilitar producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_producto_eliminarActionPerformed

    private void btn_busqueda_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busqueda_inventarioActionPerformed

        String filtro = txt_campo_filtro_inventario.getText().toLowerCase();

        List<Inventario> inventarioFiltrados = null;

        if (!filtro.isEmpty()) {
            inventarioFiltrados = this.inventarioService.buscarInventarioPorCriterio(filtro);
        } else {
            inventarioFiltrados = this.inventarioService.obtenerTodosLosInventarios();
        }
        llenarTablaInventario(inventarioFiltrados, this.model_inventario);
    }//GEN-LAST:event_btn_busqueda_inventarioActionPerformed

    private void btn_busqueda_movimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busqueda_movimientoActionPerformed

        Date fechaInicio = jDateFechaFin.getDate();
        Date fechaFin = jDateFechaFin.getDate();

        String tipo = cbo_tipo_movimiento.getSelectedItem().toString();

        if (fechaInicio == null || fechaFin == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione fechas de inicio y fin.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fechaInicio.before(fechaFin)) {
            JOptionPane.showMessageDialog(this, "La fecha de fin debe ser posterior a la fecha de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaIniciofinal = formatoFecha.format(jDateFechaInicio.getDate());
        String fechaFinfinal = formatoFecha.format(jDateFechaFin.getDate());

        if (tipo.equals("INGRESO")) {

            List<IngresoInventario> ingresos = this.ingresoinventarioService.buscarIngresosPorFecha(fechaIniciofinal, fechaFinfinal);

            if (ingresos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron ingresos en el rango de fechas seleccionado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            llenarTablaMovimientos(ingresos, this.model_movimiento);

        } else {

            List<SalidaInventario> salidas = this.salidainventarioService.buscarSalidasPorFecha(fechaIniciofinal, fechaFinfinal);

            if (salidas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron salidas en el rango de fechas seleccionado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            llenarTablaMovimientos(salidas, this.model_movimiento);
        }

    }//GEN-LAST:event_btn_busqueda_movimientoActionPerformed

    private void btn_ver_detalle_movimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_detalle_movimientoActionPerformed

        int filaSeleccionada = tabla_movimiento.getSelectedRow();

        if (filaSeleccionada != -1) {

            int idMovimiento = (int) tabla_movimiento.getValueAt(filaSeleccionada, 0);
            TipoMovimiento tipoMovimiento = (TipoMovimiento) tabla_movimiento.getValueAt(filaSeleccionada, 4);
            String tipo = tipoMovimiento.name();

            DetalleMovimiento detalle = new DetalleMovimiento(this, true, idMovimiento, tipo);
            detalle.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para ver detalle del movimiento", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_ver_detalle_movimientoActionPerformed

    private void btn_nuevo_movimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo_movimientoActionPerformed
        MovimientoForm movimientoform = new MovimientoForm(this, true, 1, null, this.model_inventario, this.usuario,
                this.model_movimiento, this.model_movimiento_producto);
        movimientoform.setVisible(true);

    }//GEN-LAST:event_btn_nuevo_movimientoActionPerformed

    private void cbo_tipo_movimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_tipo_movimientoActionPerformed

//        txtproducto_busqueda.setEnabled(false);
//        checkTodosMovimiento.setSelected(false);

    }//GEN-LAST:event_cbo_tipo_movimientoActionPerformed

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed

        String tipo = cbo_tipo_movimiento.getSelectedItem().toString();
        jDateFechaFin.setDate(null);
        jDateFechaInicio.setDate(null);

        if (tipo.equals("INGRESO")) {
            llenarTablaMovimientos(this.ingresoinventarioService.obtenerTodosLosIngresos(), this.model_movimiento);
        } else {
            llenarTablaMovimientos(this.salidainventarioService.obtenerTodasLasSalidas(), this.model_movimiento);
        }
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_producto_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_producto_exportarActionPerformed

        try {

            JasperDesign design = JRXmlLoader.load("src/main/resources/ReporteInventario.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, Conexion.getConeccion());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException ex) {
            Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_producto_exportarActionPerformed

    private void cbo_tipo_movimiento_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_tipo_movimiento_productoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_tipo_movimiento_productoActionPerformed

    private void btn_nuevo_movimiento_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo_movimiento_productoActionPerformed
        MovimientoForm movimientoform = new MovimientoForm(this, true, 1, null, this.model_inventario, this.usuario,
                this.model_movimiento, this.model_movimiento_producto);
        movimientoform.setVisible(true);
    }//GEN-LAST:event_btn_nuevo_movimiento_productoActionPerformed

    private void btn_busqueda_movimiento_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busqueda_movimiento_productoActionPerformed

        List<MovimientoProductoDTO> resultado;
        System.out.println("llego");

        if (checkTodosMovimiento.isSelected() == false) {

            System.out.println("llego1");

            String campo_busqueda = txtproducto_busqueda.getText().trim();

            if (campo_busqueda.equals("")) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre del producto a buscar...", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cbo_tipo_movimiento_producto.getSelectedItem().equals("INGRESO")) {
                resultado = this.productoService.obtenerMovimientosPorProductoNombreTipoMovimiento(campo_busqueda, "Ingreso");
            } else if (cbo_tipo_movimiento_producto.getSelectedItem().equals("SALIDA")) {
                resultado = this.productoService.obtenerMovimientosPorProductoNombreTipoMovimiento(campo_busqueda, "Salida");
            } else {
                resultado = this.productoService.obtenerMovimientosPorProductoNombre(campo_busqueda);
            }

        } else {
            resultado = this.productoService.obtenerMovimientosGenerales();
        }

        this.llenarTablaMovimientosProductos(resultado, model_movimiento_producto);

    }//GEN-LAST:event_btn_busqueda_movimiento_productoActionPerformed

    private void btn_ver_movimiento_loteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_movimiento_loteActionPerformed
        cambiarVista(panel_movimientos);
    }//GEN-LAST:event_btn_ver_movimiento_loteActionPerformed

    private void btn_ver_movimiento_productogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_movimiento_productogActionPerformed
        cambiarVista(panel_movimiento_general);
    }//GEN-LAST:event_btn_ver_movimiento_productogActionPerformed

    private void btn_reporte_movimiento_totalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporte_movimiento_totalesActionPerformed
        try {

            JasperDesign design = JRXmlLoader.load("src/main/resources/reportetotalportipo.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, Conexion.getConeccion());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException ex) {
            Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_reporte_movimiento_totalesActionPerformed

    private void btn_reporte_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporte_inventarioActionPerformed
        try {

            JasperDesign design = JRXmlLoader.load("src/main/resources/ReporteInventarioTotales.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, Conexion.getConeccion());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException ex) {
            Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_reporte_inventarioActionPerformed

    public void cambiarVista(JPanel panel) {
        panel.setSize(panel_contenido.getWidth(), panel_contenido.getHeight());
        panel_contenido.removeAll();
        panel_contenido.add(panel);
        panel_contenido.repaint();
        activarVista(panel);
    }

    public void activarVista(JPanel panel) {

        panel_movimientos.setEnabled(false);
        panel_movimientos.setVisible(false);

        panel_productos.setEnabled(false);
        panel_productos.setVisible(false);

        panel_usuarios.setEnabled(false);
        panel_usuarios.setVisible(false);

        panel_inventario.setEnabled(false);
        panel_inventario.setVisible(false);

        panel_movimiento_general.setEnabled(false);
        panel_movimiento_general.setVisible(false);

        panel.setEnabled(true);
        panel.setVisible(true);
    }

    public static void main(String args[]) {

        FlatIntelliJLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Producto1;
    private javax.swing.JButton btn_busqueda;
    private javax.swing.JButton btn_busqueda_inventario;
    private javax.swing.JButton btn_busqueda_movimiento;
    private javax.swing.JButton btn_busqueda_movimiento_producto;
    private javax.swing.JButton btn_busqueda_producto;
    private javax.swing.JLabel btn_cerrar_sesion;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_menu_inventario;
    private javax.swing.JButton btn_menu_movimiento;
    private javax.swing.JButton btn_menu_producto;
    private javax.swing.JButton btn_menu_usuario;
    private javax.swing.JButton btn_nuevo_movimiento;
    private javax.swing.JButton btn_nuevo_movimiento_producto;
    private javax.swing.JButton btn_principal_nuevoproducto;
    private javax.swing.JButton btn_principal_nuevosuario;
    private javax.swing.JButton btn_producto_editar;
    private javax.swing.JButton btn_producto_eliminar;
    private javax.swing.JButton btn_producto_exportar;
    private javax.swing.JButton btn_reporte_inventario;
    private javax.swing.JButton btn_reporte_movimiento_totales;
    private javax.swing.JButton btn_usuario_editar;
    private javax.swing.JButton btn_usuario_inabilitar;
    private javax.swing.JButton btn_ver_detalle_movimiento;
    private javax.swing.JButton btn_ver_movimiento_lote;
    private javax.swing.JButton btn_ver_movimiento_productog;
    private javax.swing.JComboBox<String> cbo_tipo_movimiento;
    private javax.swing.JComboBox<String> cbo_tipo_movimiento_producto;
    private javax.swing.JCheckBox checkTodosMovimiento;
    private com.toedter.calendar.JDateChooser jDateFechaFin;
    private com.toedter.calendar.JDateChooser jDateFechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel panel_cabecera;
    private javax.swing.JPanel panel_contenido;
    private javax.swing.JPanel panel_inventario;
    private javax.swing.JLabel panel_menu;
    private javax.swing.JPanel panel_movimiento_general;
    private javax.swing.JPanel panel_movimientos;
    private javax.swing.JPanel panel_productos;
    private javax.swing.JPanel panel_usuarios;
    private javax.swing.JTable tabla_inventario;
    private javax.swing.JTable tabla_movimiento;
    private javax.swing.JTable tabla_movimiento_producto;
    private javax.swing.JTable tabla_producto;
    private javax.swing.JTable tabla_usuarios;
    private javax.swing.JTextField txt_campo_filtro;
    private javax.swing.JTextField txt_campo_filtro_inventario;
    private javax.swing.JTextField txt_campo_filtro_producto;
    private javax.swing.JLabel txt_rol;
    private javax.swing.JLabel txt_usuario_logeado;
    private javax.swing.JTextField txtproducto_busqueda;
    // End of variables declaration//GEN-END:variables

}

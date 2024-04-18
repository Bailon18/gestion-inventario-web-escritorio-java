
package com.micompania.desktop.inventario.presentacion;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.micompania.desktop.inventario.entidad.Categoria;
import com.micompania.desktop.inventario.entidad.Producto;
import com.micompania.desktop.inventario.entidad.Proveedor;
import com.micompania.desktop.inventario.negocio.DAO.InventarioDAO;
import com.micompania.desktop.inventario.negocio.DAO.ProductoDAO;
import com.micompania.desktop.inventario.negocio.Services.CategoriaService;
import com.micompania.desktop.inventario.negocio.Services.InventarioService;
import com.micompania.desktop.inventario.negocio.Services.ProductoService;
import com.micompania.desktop.inventario.negocio.Services.ProveedorService;
import java.awt.Component;
import java.awt.Window;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public final class ProductoNewEditForm extends javax.swing.JDialog {

    private final CategoriaService categoriaService;
    private final ProveedorService proveedorService;
    
    
    private static final int MODO_CREAR = 1;
    private static final int MODO_EDITAR = 2;
    private int modo;
    private Producto productoEditando; 
    ProductoService productoServicio;
    InventarioService inventarioService;
    PrincipalForm dm ;
    DefaultTableModel model;
    DefaultTableModel model_inventario;
    
    public ProductoNewEditForm(java.awt.Frame parent, boolean modal, int modo, Producto producto, DefaultTableModel modelo, DefaultTableModel modelo_inventario) {
        super(parent, modal);
        this.dm = new PrincipalForm();
        initComponents();
        this.setLocationRelativeTo(null);
        this.categoriaService =  new CategoriaService();
        this.proveedorService = new ProveedorService();
        this.productoServicio  = new ProductoService(new ProductoDAO(categoriaService, proveedorService));
        this.inventarioService = new InventarioService(new InventarioDAO(this.productoServicio));
        this.modo = modo;
       
        this.model = modelo;
        this.model_inventario = modelo_inventario;
        this.productoEditando = producto;
        if (modo == MODO_EDITAR && producto != null) {
            llenarCamposProductos();
            txt_usuario_titulo.setText("Editar Producto");
            btn_producto_guardar.setText("Actualizar");
        }

        this.llenarComboboxCategoria();
        this.llenarComboboxProveedor();
    }


    private void llenarCamposProductos() {

        txt_nombre.setText(productoEditando.getNombre());
        txt_precio.setText(""+productoEditando.getPrecio());
        txt_descripcion.setText(productoEditando.getDescripcion());
        
        cbo_producto_categoria.setSelectedItem(productoEditando.getCategoria().getNombre());
        cbo_producto_proveedor.setSelectedItem(productoEditando.getProveedor().getNombre());
        
        spinner_stockMinimo.setValue(productoEditando.getStockMinimo());

        if (productoEditando.isActivo()) {
            cbo_estado.setSelectedItem("Activo"); 
        } else {
            cbo_estado.setSelectedItem("Inactivo");
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_usuario_titulo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_precio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_descripcion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbo_producto_categoria = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbo_producto_proveedor = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        spinner_stockMinimo = new javax.swing.JSpinner();
        Estado = new javax.swing.JLabel();
        cbo_estado = new javax.swing.JComboBox<>();
        btn_producto_guardar = new javax.swing.JButton();
        btn_producto_cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(490, 514));
        setPreferredSize(new java.awt.Dimension(440, 514));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_usuario_titulo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_usuario_titulo.setText("Nuevo Producto");
        getContentPane().add(txt_usuario_titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 140, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        txt_nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 194, 32));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Precio");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 50, -1));

        txt_precio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txt_precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 194, 32));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Descripción");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 90, -1));

        txt_descripcion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txt_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 406, 32));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Categoria");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 140, -1));

        cbo_producto_categoria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(cbo_producto_categoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 410, 32));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Proveedor");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        cbo_producto_proveedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(cbo_producto_proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 410, 32));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Stock minimo");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 110, -1));

        spinner_stockMinimo.setPreferredSize(new java.awt.Dimension(64, 30));
        getContentPane().add(spinner_stockMinimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 198, 32));

        Estado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Estado.setText("Estado producto");
        getContentPane().add(Estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 110, -1));

        cbo_estado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbo_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        getContentPane().add(cbo_estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 370, 190, 32));

        btn_producto_guardar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_producto_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/guardar.png"))); // NOI18N
        btn_producto_guardar.setText("Guardar");
        btn_producto_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_producto_guardarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_producto_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 430, 150, -1));

        btn_producto_cancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_producto_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancelar.png"))); // NOI18N
        btn_producto_cancelar.setText("Cancelar");
        btn_producto_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_producto_cancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_producto_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 159, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_producto_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_producto_guardarActionPerformed

        Producto producto = obtenerDatosFormulario();
        if(producto != null){
            if (this.modo == MODO_CREAR) {
                this.productoServicio.agregarProducto(producto);
                this.dm.llenarTablaProductos(this.productoServicio.obtenerTodosLosProductos(), this.model);
                this.dm.llenarTablaInventario(this.inventarioService.obtenerTodosLosInventarios(), this.model_inventario);
            } else if (modo == MODO_EDITAR && productoEditando != null) {
                producto.setIdProducto(productoEditando.getIdProducto());
                this.productoServicio.actualizarProducto(producto);
            }

            Window window = SwingUtilities.getWindowAncestor((Component) evt.getSource());
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                dialog.dispose();
            }
        }
        


    }//GEN-LAST:event_btn_producto_guardarActionPerformed

    private String validarCampos() {
        
        String mensajeError = "";

        String nombre = txt_nombre.getText();
        String descripcion = txt_descripcion.getText();
        int stockminimo = (int) spinner_stockMinimo.getValue();

        if (nombre.trim().isEmpty()) {
            mensajeError += "Nombre del producto es un campo obligatorio.\n";
        }

        try {
            double precio = Double.parseDouble(txt_precio.getText());
            if (precio <= 0) {
                mensajeError += "El precio debe ser un número válido y mayor que cero.\n";
            }
        } catch (NumberFormatException e) {
            mensajeError += "El precio debe ser un número válido.\n";
        }

        if (descripcion.trim().isEmpty()) {
            mensajeError += "Descripcion del producto es un campo obligatorio.\n";
        }
        if (stockminimo <= 0) {
            mensajeError += "El stock mínimo debe ser un número válido y mayor que cero.\n";
        }


        return mensajeError;
    }

    
    private Producto obtenerDatosFormulario() {
        
        String mensajeError = validarCampos();

        if (!mensajeError.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error al guardar:\n" + mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }else{
            String nombre = txt_nombre.getText();
            String descripcion = txt_descripcion.getText();
            int categoria_id = cbo_producto_categoria.getSelectedIndex()+1;
            int proveedor_id = cbo_producto_proveedor.getSelectedIndex()+1;
            boolean activo = cbo_estado.getSelectedItem().toString().equals("Activo");
            int stockminimo = (int) spinner_stockMinimo.getValue();
            double precio = Double.parseDouble(txt_precio.getText());
            
            return new Producto(nombre,descripcion, precio , new Categoria(categoria_id), new Proveedor(proveedor_id) , stockminimo, activo);
        }


        
    }
    
    
    public void llenarComboboxCategoria(){
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        for (Categoria categoria : categorias) {
            cbo_producto_categoria.addItem(categoria.getNombre());
        }
    }
    
    public void llenarComboboxProveedor(){
        List<Proveedor> proveedores = proveedorService.obtenerTodosLosProveedores();
        for (Proveedor proveedor : proveedores) {
            cbo_producto_proveedor.addItem(proveedor.getNombre());
        }
    }
    
    
    private void btn_producto_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_producto_cancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_producto_cancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        
        FlatIntelliJLaf.setup();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProductoNewEditForm dialog = new ProductoNewEditForm(new javax.swing.JFrame(), true, 1 , null, null, null);
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
    private javax.swing.JLabel Estado;
    private javax.swing.JButton btn_producto_cancelar;
    private javax.swing.JButton btn_producto_guardar;
    private javax.swing.JComboBox<String> cbo_estado;
    private javax.swing.JComboBox<String> cbo_producto_categoria;
    private javax.swing.JComboBox<String> cbo_producto_proveedor;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSpinner spinner_stockMinimo;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_precio;
    private javax.swing.JLabel txt_usuario_titulo;
    // End of variables declaration//GEN-END:variables
}


package com.micompania.desktop.inventario.presentacion;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.micompania.desktop.inventario.entidad.DetalleIngreso;
import com.micompania.desktop.inventario.entidad.DetalleSalida;
import com.micompania.desktop.inventario.entidad.IngresoInventario;
import com.micompania.desktop.inventario.entidad.SalidaInventario;
import com.micompania.desktop.inventario.negocio.DAO.DetalleIngresoDAO;
import com.micompania.desktop.inventario.negocio.DAO.IngresoInventarioDAO;
import com.micompania.desktop.inventario.negocio.Services.DetalleIngresoService;
import com.micompania.desktop.inventario.negocio.Services.DetalleSalidaService;
import com.micompania.desktop.inventario.negocio.Services.IngresoInventarioService;
import com.micompania.desktop.inventario.negocio.Services.SalidaInventarioService;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public final class DetalleMovimiento extends javax.swing.JDialog {

    DefaultTableModel model_detalle;
    private final IngresoInventarioService ingresoInventarioService;
    private final SalidaInventarioService salidaInventarioService;
    private final DetalleIngresoService detalleIngresoService;
    private final DetalleSalidaService detalleSalidaService;
    
   
    public DetalleMovimiento (java.awt.Frame parent, boolean modal,int idMovimiento, String tipo) {
        
        initComponents();
        this.setLocationRelativeTo(null);
        this.configurarTablaDetalle();
        this.ingresoInventarioService = new IngresoInventarioService(new IngresoInventarioDAO());
        this.salidaInventarioService = new SalidaInventarioService();
        this.detalleIngresoService = new DetalleIngresoService(new DetalleIngresoDAO());
        this.detalleSalidaService = new DetalleSalidaService();
        
        if(tipo.equals("INGRESO")){
            IngresoInventario ingreso = ingresoInventarioService.buscarIngresoInventario(idMovimiento);
            txt_cliente_proveedor.setText(""+ingreso.getProveedor().getNombre());
            tituloProveedorCliente.setText("Proveedor");
            txt_fecha.setText(""+ingreso.getFecha());
            llenarTablaDetalle(detalleIngresoService.obtenerDetallesIngreso(idMovimiento), this.model_detalle);
        }else{
            tituloProveedorCliente.setText("Cliente");
            SalidaInventario salida = salidaInventarioService.buscarSalidaInventario(idMovimiento);
            txt_cliente_proveedor.setText(""+salida.getCliente().getNombre());
            txt_fecha.setText(""+salida.getFecha());
            llenarTablaDetalle(detalleSalidaService.obtenerDetallesSalida(idMovimiento), this.model_detalle);
        }
       
    }

    
    public void configurarTablaDetalle(){
        
        this.model_detalle = (DefaultTableModel) tabla_detalle.getModel();
        tabla_detalle.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla_detalle.getColumnModel().getColumn(1).setPreferredWidth(200); 
        tabla_detalle.getColumnModel().getColumn(2).setPreferredWidth(70); 
        tabla_detalle.getTableHeader().setResizingAllowed(false);
    }
    
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


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo_tipo_ventana = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_fecha = new javax.swing.JTextField();
        tituloProveedorCliente = new javax.swing.JLabel();
        txt_cliente_proveedor = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabla_detalle = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        titulo_tipo_ventana.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        titulo_tipo_ventana.setText("Detalle Movimiento");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Fecha Inicio");

        txt_fecha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tituloProveedorCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tituloProveedorCliente.setText("Proveedor / Cliente");

        txt_cliente_proveedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tabla_detalle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabla_detalle.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_detalle.setShowGrid(true);
        jScrollPane5.setViewportView(tabla_detalle);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(tituloProveedorCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(titulo_tipo_ventana, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_cliente_proveedor)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(titulo_tipo_ventana)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(tituloProveedorCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cliente_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[]) {

        
        FlatIntelliJLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DetalleMovimiento dialog = new DetalleMovimiento(new javax.swing.JFrame(), true, 0, null);
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tabla_detalle;
    private javax.swing.JLabel tituloProveedorCliente;
    private javax.swing.JLabel titulo_tipo_ventana;
    private javax.swing.JTextField txt_cliente_proveedor;
    private javax.swing.JTextField txt_fecha;
    // End of variables declaration//GEN-END:variables
}


package com.micompania.desktop.inventario.presentacion;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.micompania.desktop.inventario.entidad.ENUM.Rol;
import com.micompania.desktop.inventario.entidad.Usuario;
import com.micompania.desktop.inventario.negocio.DAO.UsuarioDAO;
import com.micompania.desktop.inventario.negocio.Services.UsuarioService;
import java.awt.Component;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class UsuarioNewEditForm extends javax.swing.JDialog {

    
    private static final int MODO_CREAR = 1;
    private static final int MODO_EDITAR = 2;
    private int modo;
    private Usuario usuarioEditando; 
    UsuarioService usuarioServicio;
    PrincipalForm dm ;
    DefaultTableModel model;

    
    public UsuarioNewEditForm(java.awt.Frame parent, boolean modal, int modo, Usuario usuario, DefaultTableModel modelo) {
        super(parent, modal);
        this.dm = new PrincipalForm();
        initComponents();
        this.setLocationRelativeTo(null);
        UsuarioDAO dao = new UsuarioDAO();
        this.usuarioServicio = new UsuarioService(dao);
        this.modo = modo;
        this.usuarioEditando = usuario;
        this.model = modelo; 
        if (modo == MODO_EDITAR && usuario != null) {
            llenarCamposUsuario();
            txt_usuario_titulo.setText("Editar Usuario");
            btn_usuario_guardar.setText("Actualizar");
        }
    }


    private void llenarCamposUsuario() {

        txt_usuario_nombre.setText(usuarioEditando.getNombre());
        txt_usuario_apellido.setText(usuarioEditando.getApellido());
        txt_usuario_correo.setText(usuarioEditando.getCorreo());
        txt_usuario_contrasena.setText(usuarioEditando.getPassword());
        txt_usuario_username.setText(usuarioEditando.getUsername());

        cbo_usuario_rol.setSelectedItem(usuarioEditando.getTipo().toString());

        if (usuarioEditando.isActivo()) {
            cbo_usuario_estado.setSelectedItem("Activo"); 
        } else {
            cbo_usuario_estado.setSelectedItem("Inactivo");
        }

    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_usuario_titulo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_usuario_nombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_usuario_apellido = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_usuario_correo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_usuario_contrasena = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        cbo_usuario_rol = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txt_usuario_username = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbo_usuario_estado = new javax.swing.JComboBox<>();
        btn_usuario_cancelar = new javax.swing.JButton();
        btn_usuario_guardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(490, 500));
        setResizable(false);
        setSize(new java.awt.Dimension(490, 500));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_usuario_titulo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_usuario_titulo.setText("Nuevo Usuario");
        getContentPane().add(txt_usuario_titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 26, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 91, -1, -1));

        txt_usuario_nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txt_usuario_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 114, 194, 32));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Apellidos");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 91, -1, -1));

        txt_usuario_apellido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txt_usuario_apellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 114, 194, 32));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Correo");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 164, -1, -1));

        txt_usuario_correo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txt_usuario_correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 187, 406, 32));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Contraseña");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 237, -1, -1));

        txt_usuario_contrasena.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txt_usuario_contrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 260, 194, 32));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Rol");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 237, -1, -1));

        cbo_usuario_rol.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbo_usuario_rol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Empleado" }));
        getContentPane().add(cbo_usuario_rol, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 260, 194, 32));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Username");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 304, -1, -1));

        txt_usuario_username.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(txt_usuario_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 327, 194, 32));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Estado");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 303, -1, -1));

        cbo_usuario_estado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbo_usuario_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        getContentPane().add(cbo_usuario_estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 326, 194, 32));

        btn_usuario_cancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_usuario_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancelar.png"))); // NOI18N
        btn_usuario_cancelar.setText("Cancelar");
        btn_usuario_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuario_cancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_usuario_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 406, 159, -1));

        btn_usuario_guardar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_usuario_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/guardar.png"))); // NOI18N
        btn_usuario_guardar.setText("Guardar");
        btn_usuario_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuario_guardarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_usuario_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 406, 181, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_usuario_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuario_guardarActionPerformed
        
        Usuario usuario = obtenerDatosFormulario();
        if (usuario != null) {
            if (modo == MODO_CREAR) {
                this.usuarioServicio.agregarUsuario(usuario);
                this.dm.llenarTablaUsuario(this.usuarioServicio.obtenerTodosLosUsuarios(), this.model);
            } else if (modo == MODO_EDITAR && usuarioEditando != null) {

                usuario.setIdUsuario(usuarioEditando.getIdUsuario()); 
                this.usuarioServicio.actualizarUsuario(usuario);
            }
            
            Window window = SwingUtilities.getWindowAncestor((Component) evt.getSource());
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                dialog.dispose();
            }
        }
        
    }//GEN-LAST:event_btn_usuario_guardarActionPerformed

    private void btn_usuario_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuario_cancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_usuario_cancelarActionPerformed

    private String validarCampos() {
        
        String mensajeError = "";

        String nombre = txt_usuario_nombre.getText();
        String apellido = txt_usuario_apellido.getText();
        String correo = txt_usuario_correo.getText();
        String username = txt_usuario_username.getText();
        String password = new String(txt_usuario_contrasena.getPassword());
        String rol = cbo_usuario_rol.getSelectedItem().toString();

   
        if (nombre.trim().isEmpty()) {
            mensajeError += "Nombre es un campo obligatorio.\n";
        }

        if (apellido.trim().isEmpty()) {
            mensajeError += "Apellido es un campo obligatorio.\n";
        }

        if (correo.trim().isEmpty()) {
            mensajeError += "Correo es un campo obligatorio.\n";
        }
        
        if (password.trim().isEmpty()) {
            mensajeError += "Contraseña es un campo obligatorio.\n";
        }

        if (username.trim().isEmpty()) {
            mensajeError += "Username es un campo obligatorio.\n";
        }
        
        return mensajeError;
    }

    
    private Usuario obtenerDatosFormulario() {
        String mensajeError = validarCampos();

        if (!mensajeError.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error al guardar:\n" + mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String nombre = txt_usuario_nombre.getText();
        String apellido = txt_usuario_apellido.getText();
        String correo = txt_usuario_correo.getText();
        String username = txt_usuario_username.getText();
        String password = new String(txt_usuario_contrasena.getPassword());
        String rolStr = cbo_usuario_rol.getSelectedItem().toString().toUpperCase();

        Rol rol = Rol.valueOf(rolStr);
        boolean activo = cbo_usuario_estado.getSelectedItem().toString().equals("Activo");

        return new Usuario(nombre, apellido, correo, username, password, rol, activo);
    }

    
    public static void main(String args[]) {

        FlatIntelliJLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UsuarioNewEditForm dialog = new UsuarioNewEditForm(new javax.swing.JFrame(), true, 1 , null, null);
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
    private javax.swing.JButton btn_usuario_cancelar;
    private javax.swing.JButton btn_usuario_guardar;
    private javax.swing.JComboBox<String> cbo_usuario_estado;
    private javax.swing.JComboBox<String> cbo_usuario_rol;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField txt_usuario_apellido;
    private javax.swing.JPasswordField txt_usuario_contrasena;
    private javax.swing.JTextField txt_usuario_correo;
    private javax.swing.JTextField txt_usuario_nombre;
    private javax.swing.JLabel txt_usuario_titulo;
    private javax.swing.JTextField txt_usuario_username;
    // End of variables declaration//GEN-END:variables
}

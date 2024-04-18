package com.micompania.desktop.inventario.presentacion;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.micompania.desktop.inventario.entidad.Usuario;
import com.micompania.desktop.inventario.negocio.DAO.UsuarioDAO;
import com.micompania.desktop.inventario.negocio.Services.UsuarioService;
import javax.swing.JOptionPane;


public class LoginFormm extends javax.swing.JFrame {

    private final UsuarioDAO usuariodao;
    private final UsuarioService usuarioService;
    
    public LoginFormm() {
        this.usuariodao = new UsuarioDAO();
        this.usuarioService = new UsuarioService(usuariodao);
        initComponents();
        this.setLocationRelativeTo(null);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_correo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btn_ingresar = new javax.swing.JButton();
        txt_contrasena = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Bienvenido");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Correo");

        txt_correo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_correo.setText("admin@gmail.com");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Contraseña");

        btn_ingresar.setBackground(new java.awt.Color(0, 51, 153));
        btn_ingresar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_ingresar.setForeground(new java.awt.Color(255, 255, 255));
        btn_ingresar.setText("Ingresar");
        btn_ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ingresarActionPerformed(evt);
            }
        });

        txt_contrasena.setText("admin123");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_contrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(90, 90, 90)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jLabel2))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(btn_ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jLabel1))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel3)
                .addGap(61, 61, 61)
                .addComponent(jLabel2)
                .addGap(3, 3, 3)
                .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_contrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btn_ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ingresarActionPerformed
        
        String correo = txt_correo.getText();
        char[] passwordChars = txt_contrasena.getPassword();
        String contrasena = new String(passwordChars);

        Usuario usuario = usuarioService.validarInicioSesion(correo, contrasena);

        if (usuario != null) {
            if (usuario.isActivo()) {
                String mensajeBienvenida = String.format("¡Bienvenido %s! Has iniciado sesión como %s.",
                        usuario.getNombre(), usuario.getTipo().toString().toUpperCase());
                JOptionPane.showMessageDialog(this, mensajeBienvenida, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                
                PrincipalForm principal = new PrincipalForm();
                principal.setUsuario(usuario);
                principal.setVisible(true);
                this.dispose();
                
            } else {
                JOptionPane.showMessageDialog(this, "Usuario desactivado. Comuníquese con el administrador", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_ingresarActionPerformed


    public static void main(String args[]) {

        FlatIntelliJLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFormm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ingresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField txt_contrasena;
    private javax.swing.JTextField txt_correo;
    // End of variables declaration//GEN-END:variables
}

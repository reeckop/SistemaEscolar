package GUI;

import persistencia.PersistenciaInscripciones;
import javax.swing.JOptionPane;

/*
*
* @author Ricardo & valeria
*/
public class PanelRotarRol extends javax.swing.JPanel {

    private PersistenciaInscripciones pInscripciones;

    public PanelRotarRol(PersistenciaInscripciones pIns) {
        this.pInscripciones = pIns;
        initComponents();
    }

    private void btnRotarActionPerformed(java.awt.event.ActionEvent evt) {
        String clave = txtClave.getText().trim();
        if (clave.isEmpty())
            return;

        String resultado = pInscripciones.rotarRol(clave);
        lblResultado.setText("<html><center>" + resultado + "</center></html>");
        JOptionPane.showMessageDialog(this, resultado);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        txtClave = new javax.swing.JTextField();
        btnRotar = new javax.swing.JButton();
        lblResultado = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel1.setText("Rotación de Roles (Lista Circular)");

        jLabel2.setText("Clave del Curso:");
        btnRotar.setText("Rotar Rol (Tutor / Líder)");
        btnRotar.addActionListener(evt -> btnRotarActionPerformed(evt));

        lblResultado.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblResultado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultado.setText("---");
        lblResultado.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btnRotar, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(lblResultado, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(100, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(30, 30, 30)
                        .addComponent(jLabel1).addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2).addComponent(txtClave))
                        .addGap(20, 20, 20)
                        .addComponent(btnRotar, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(lblResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(80, Short.MAX_VALUE)));
    }

    private javax.swing.JButton btnRotar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JTextField txtClave;
}
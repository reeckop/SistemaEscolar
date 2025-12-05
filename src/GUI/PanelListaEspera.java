package GUI;

import persistencia.PersistenciaInscripciones;
import javax.swing.JOptionPane;

/**
 *
 * @author Ricardo
 */
public class PanelListaEspera extends javax.swing.JPanel {

    private PersistenciaInscripciones pInscripciones;

    public PanelListaEspera(PersistenciaInscripciones pIns) {
        this.pInscripciones = pIns;
        initComponents();
    }

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {
        String clave = txtClave.getText().trim();
        try {
            int n = Integer.parseInt(txtCantidad.getText().trim());

            pInscripciones.imprimirListaEspera(clave, n);
            JOptionPane.showMessageDialog(this,"La lista se ha generado en la Consola.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad valida:");
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        txtClave = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnVer = new javax.swing.JButton();
        lblNota = new javax.swing.JLabel();

        jLabel1.setText("Clave Curso:");
        jLabel2.setText("Cantidad a mostrar:");
        btnVer.setText("Ver Lista de Espera");
        btnVer.addActionListener(evt -> btnVerActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNota)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2).addComponent(jLabel1))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtClave, javax.swing.GroupLayout.DEFAULT_SIZE, 100,
                                                        Short.MAX_VALUE)
                                                .addComponent(txtCantidad)))
                                .addComponent(btnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 240,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(100, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1).addComponent(txtClave))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2).addComponent(txtCantidad))
                        .addGap(30, 30, 30)
                        .addComponent(btnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNota)
                        .addContainerGap(80, Short.MAX_VALUE)));
    }

    private javax.swing.JButton btnVer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblNota;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtClave;
}
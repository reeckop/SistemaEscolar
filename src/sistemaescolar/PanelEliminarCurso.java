package sistemaescolar;

import entidades.Curso;
import persistencia.PersistenciaCursos;
import javax.swing.JOptionPane;

public class PanelEliminarCurso extends javax.swing.JPanel {

    private PersistenciaCursos pCursos;

    public PanelEliminarCurso(PersistenciaCursos pCursos) {
        this.pCursos = pCursos;
        initComponents();
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        String clave = txtClave.getText().trim();
        if (clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la clave del curso.");
            return;
        }
        
        Curso eliminado = pCursos.eliminarCurso(clave);
        if (eliminado != null) {
            JOptionPane.showMessageDialog(this, "Curso eliminado: " + eliminado.getNombre());
            txtClave.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un curso con esa clave.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtClave = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); 
        jLabel1.setText("Eliminar Curso");

        jLabel2.setText("Clave del Curso:");
        btnEliminar.setText("Eliminar Definitivamente");
        btnEliminar.addActionListener(evt -> btnEliminarActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(40,40,40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18,18,18)
                        .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(30,30,30)
                .addComponent(jLabel1).addGap(20,20,20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2).addComponent(txtClave))
                .addGap(20,20,20)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
    }
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtClave;
}
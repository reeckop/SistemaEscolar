package GUI;

import entidades.Accion;
import entidades.Estudiante;
import persistencia.PersistenciaCalificaciones;
import persistencia.PersistenciaEstudiantes;
import javax.swing.JOptionPane;

public class PanelEnviarCalificacion extends javax.swing.JPanel {

    private final PersistenciaCalificaciones pCalificaciones;
    private final PersistenciaEstudiantes pEstudiantes;

    public PanelEnviarCalificacion(PersistenciaCalificaciones pCal, PersistenciaEstudiantes pEst) {
        this.pCalificaciones = pCal;
        this.pEstudiantes = pEst;
        initComponents();
    }

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {
        String matricula = txtMatricula.getText().trim();
        String califStr = txtCalificacion.getText().trim();

        if (matricula.isEmpty() || califStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Estudiante e = pEstudiantes.buscarEstudiante(matricula);
        if (e == null) {
            JOptionPane.showMessageDialog(this, "Estudiante no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double calif = Double.parseDouble(califStr);
            if (calif < 0 || calif > 100) {
                JOptionPane.showMessageDialog(this, "La calificación debe estar entre 0 y 100.", "Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            pCalificaciones.enviarSolicitudCalificacion(new Accion(Accion.Tipo.CALIFICACION, e, calif));
            JOptionPane.showMessageDialog(this, "Solicitud enviada a la COLA de procesamiento.");
            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Calificación inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtMatricula.setText("");
        txtCalificacion.setText("");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCalificacion = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Enviar Solicitud de Calificación");

        jLabel1.setText("Matrícula Estudiante:");
        jLabel2.setText("Calificación (0-100):");

        btnEnviar.setText("Enviar Solicitud");
        btnEnviar.addActionListener(evt -> btnEnviarActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTitulo)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                150, Short.MAX_VALUE)
                                                        .addComponent(txtMatricula)
                                                        .addComponent(txtCalificacion))))
                                .addContainerGap(100, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtCalificacion, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(100, Short.MAX_VALUE)));
    }

    private javax.swing.JButton btnEnviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtCalificacion;
    private javax.swing.JTextField txtMatricula;
}

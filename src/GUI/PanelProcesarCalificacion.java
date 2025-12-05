package GUI;

import entidades.Accion;
import entidades.Estudiante;
import persistencia.PersistenciaAcciones;
import persistencia.PersistenciaCalificaciones;
import javax.swing.JOptionPane;

/*
*
* @author Ricardo & valeria
*/
public class PanelProcesarCalificacion extends javax.swing.JPanel {

    private final PersistenciaCalificaciones pCalificaciones;
    private final PersistenciaAcciones pAcciones;

    public PanelProcesarCalificacion(PersistenciaCalificaciones pCal, PersistenciaAcciones pAcc) {
        this.pCalificaciones = pCal;
        this.pAcciones = pAcc;
        initComponents();
    }

    private void btnProcesarActionPerformed(java.awt.event.ActionEvent evt) {
        if (!pCalificaciones.haySolicitudes()) {
            lblEstado.setText("Estado: La cola esta vacia.");
            JOptionPane.showMessageDialog(this, "No hay solicitudes pendientes.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Accion procesada = pCalificaciones.procesarSiguienteSolicitud();
        if (procesada != null) {
            // Registrar en historial para deshacer
            pAcciones.registrarAccion(procesada);

            Estudiante e = (Estudiante) procesada.getObjeto();
            Double calif = (Double) procesada.getInfoAdicional();

            String msg = String.format("Procesado: %s - Calificación: %.2f", e.getNombreCompleto(), calif);
            lblEstado.setText("Estado: " + msg);
            JOptionPane.showMessageDialog(this, "Solicitud procesada exitosamente.\n" + msg);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        btnProcesar = new javax.swing.JButton();
        lblEstado = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Procesar Calificaciones (Cola)");

        btnProcesar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnProcesar.setText("Procesar Siguiente");
        btnProcesar.addActionListener(evt -> btnProcesarActionPerformed(evt));

        lblEstado.setFont(new java.awt.Font("Segoe UI", 2, 14));
        lblEstado.setForeground(new java.awt.Color(0, 102, 204));
        lblEstado.setText("Estado: Esperando...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTitulo)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 400,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblInfo)
                                        .addComponent(btnProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 450,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(50, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblInfo)
                                .addGap(30, 30, 30)
                                .addComponent(btnProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(lblEstado)
                                .addContainerGap(80, Short.MAX_VALUE)));
    }

    private javax.swing.JButton btnProcesar;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblTitulo;
}

package GUI;

import entidades.Curso;
import persistencia.PersistenciaCursos;
import javax.swing.JOptionPane;


/**
 *
 * @author Ricardo
 */
public class PanelAgregarCurso extends javax.swing.JPanel {

    private final PersistenciaCursos pCursos;

    public PanelAgregarCurso(PersistenciaCursos pCursos) {
        this.pCursos = pCursos;
        initComponents();
    }

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        String clave = txtClave.getText().trim();
        String nombre = txtNombre.getText().trim();
        String capacidadStr = txtCapacidad.getText().trim();

        if (clave.isEmpty() || nombre.isEmpty() || capacidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int capacidad = Integer.parseInt(capacidadStr);
            if (pCursos.buscarCurso(clave) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un curso con esa clave.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Curso nuevo = new Curso(clave, nombre, capacidad);
            pCursos.agregarCurso(nuevo);
            JOptionPane.showMessageDialog(this, "Curso agregado exitosamente.");
            limpiarCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser un número entero.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtClave.setText("");
        txtNombre.setText("");
        txtCapacidad.setText("");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtClave = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCapacidad = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Agregar Nuevo Curso");

        jLabel1.setText("Clave:");
        jLabel2.setText("Nombre:");
        jLabel3.setText("Capacidad:");

        btnAgregar.setText("Agregar Curso");
        btnAgregar.addActionListener(evt -> btnAgregarActionPerformed(evt));

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
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3))
                                                .addGap(30, 30, 30)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                150, Short.MAX_VALUE)
                                                        .addComponent(txtClave)
                                                        .addComponent(txtNombre)
                                                        .addComponent(txtCapacidad))))
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
                                        .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(80, Short.MAX_VALUE)));
    }

    private javax.swing.JButton btnAgregar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtCapacidad;
    private javax.swing.JTextField txtClave;
    private javax.swing.JTextField txtNombre;
}

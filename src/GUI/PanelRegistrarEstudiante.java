package GUI;

import entidades.Accion;
import entidades.Estudiante;
import persistencia.PersistenciaAcciones;
import persistencia.PersistenciaEstudiantes;
import javax.swing.JOptionPane;

/**
 * Panel para el registro de nuevos estudiantes.
 * Se integra en el MenuPrincipal sin abrir ventanas nuevas.
 */
public class PanelRegistrarEstudiante extends javax.swing.JPanel {

    // Referencias a los controladores (Recibidos desde el Menú)
    private final PersistenciaEstudiantes pEstudiantes;
    private final PersistenciaAcciones pAcciones;

    /**
     * Constructor que recibe las instancias de persistencia.
     *
     */
    public PanelRegistrarEstudiante(PersistenciaEstudiantes pEst, PersistenciaAcciones pAcc) {
        this.pEstudiantes = pEst;
        this.pAcciones = pAcc;
        initComponents();
    }

    /**
     * Logica principal del boton Registrar.
     * Valida datos, verifica duplicados, guarda en BST y registra la accion.
     */
    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {
        // Recolectaa el texto
        String matricula = txtMatricula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String direccion = txtDireccion.getText().trim();

        // Validaciones basicas (Campos vacíos)
        if (matricula.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La matrícula y el nombre son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Verifica si existe en el BST
            if (pEstudiantes.buscarEstudiante(matricula) != null) {
                JOptionPane.showMessageDialog(this, "Error: Ya existe un estudiante con la matrícula " + matricula, "Matrícula Duplicada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crea el Estudiante
            Estudiante nuevoEstudiante = new Estudiante(matricula, nombre, telefono, correo, direccion);

            // Guardaren el BST (Persistencia)
            pEstudiantes.agregarEstudiante(nuevoEstudiante);

            // Registra la accion en la Pila
            Accion accionRegistro = new Accion(Accion.Tipo.REGISTRO_ESTUDIANTE, nuevoEstudiante, null);
            pAcciones.registrarAccion(accionRegistro);

            // Avisa al usuario
            JOptionPane.showMessageDialog(this, "Estudiante registrado exitosamente.");
            limpiarCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + e.getMessage());
        }
    }

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarCampos();
    }

    // Metodo para limpiar el texto de los campos
    private void limpiarCampos() {
        txtMatricula.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Registro de Nuevo Estudiante");

        jLabel1.setText("Matrícula:");

        jLabel2.setText("Nombre Completo:");

        jLabel3.setText("Teléfono:");

        jLabel4.setText("Correo Electrónico:");

        jLabel5.setText("Dirección:");

        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar Campos");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        // --- Diseño del Layout (GroupLayout) ---
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
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel5))
                                                .addGap(28, 28, 28)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnRegistrar,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnLimpiar,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE, 130,
                                                                        Short.MAX_VALUE))
                                                        .addComponent(txtMatricula)
                                                        .addComponent(txtNombre)
                                                        .addComponent(txtTelefono)
                                                        .addComponent(txtCorreo)
                                                        .addComponent(txtDireccion))))
                                .addContainerGap(120, Short.MAX_VALUE)));
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
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(80, Short.MAX_VALUE)));
    }// </editor-fold>

    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
}
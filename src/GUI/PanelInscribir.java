package GUI;

import entidades.Accion;
import entidades.Curso;
import entidades.Estudiante;
import persistencia.PersistenciaAcciones;
import persistencia.PersistenciaCursos;
import persistencia.PersistenciaEstudiantes;
import persistencia.PersistenciaInscripciones;
import javax.swing.JOptionPane;

/*
*
* @author Ricardo
*/
public class PanelInscribir extends javax.swing.JPanel {
    private final PersistenciaInscripciones pInscripciones;
    private final PersistenciaAcciones pAcciones;
    private final PersistenciaEstudiantes pEstudiantes;
    private final PersistenciaCursos pCursos;

    public PanelInscribir(PersistenciaInscripciones pInsc, PersistenciaAcciones pAcc, PersistenciaEstudiantes pEst,
            PersistenciaCursos pCur) {
        this.pInscripciones = pInsc;
        this.pAcciones = pAcc;
        this.pEstudiantes = pEst;
        this.pCursos = pCur;
        initComponents();
    }

    // Obtiene la matricula y clave, si ambas estan vacias da error, si no las inscribe y luego registra la accion
    private void btnInscribirActionPerformed(java.awt.event.ActionEvent evt) {
        String matricula = txtMatricula.getText().trim();
        String clave = txtClave.getText().trim();

        if (matricula.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar matrícula y clave del curso.", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String resultado = pInscripciones.inscribir(matricula, clave);
        JOptionPane.showMessageDialog(this, resultado);

        // Si fue exitoso o lista de espera, registrar acción para deshacer
        if (resultado.contains("exitosa") || resultado.contains("Espera")) {
            Estudiante e = pEstudiantes.buscarEstudiante(matricula);
            Curso c = pCursos.buscarCurso(clave);
            if (e != null && c != null) {
                pAcciones.registrarAccion(new Accion(Accion.Tipo.INSCRIPCION_CURSO, e, c));
            }
        }

        limpiarCampos();
    }
    
    // MEtodo par alimpiar 
    private void limpiarCampos() {
        txtMatricula.setText("");
        txtClave.setText("");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtClave = new javax.swing.JTextField();
        btnInscribir = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Inscribir Estudiante a Curso");

        jLabel1.setText("Matricula Estudiante:");
        jLabel2.setText("Clave del Curso:");

        btnInscribir.setText("Inscribir");
        btnInscribir.addActionListener(evt -> btnInscribirActionPerformed(evt));

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
                                                        .addComponent(btnInscribir,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 150,
                                                                Short.MAX_VALUE)
                                                        .addComponent(txtMatricula)
                                                        .addComponent(txtClave))))
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
                                        .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(btnInscribir, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(100, Short.MAX_VALUE)));
    }

    private javax.swing.JButton btnInscribir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtClave;
    private javax.swing.JTextField txtMatricula;
}

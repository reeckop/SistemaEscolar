package sistemaescolar;

import entidades.Estudiante;
import persistencia.PersistenciaEstudiantes;
import javax.swing.JOptionPane;

/**
 *
 * @author Ricardo
 */
public class PanelBuscarEstudiante extends javax.swing.JPanel {

    private PersistenciaEstudiantes pEstudiantes;

    public PanelBuscarEstudiante(PersistenciaEstudiantes pEst) {
        this.pEstudiantes = pEst;
        initComponents();
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        String matricula = txtMatricula.getText().trim();
        
        if (matricula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una matrícula.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Estudiante e = pEstudiantes.buscarEstudiante(matricula);
        
        if (e != null) {
            txtResultado.setText("");
            txtResultado.append("--- ESTUDIANTE ENCONTRADO ---\n");
            txtResultado.append("Matrícula: " + e.getMatricula() + "\n");
            txtResultado.append("Nombre: " + e.getNombreCompleto() + "\n");
            txtResultado.append("Teléfono: " + e.getTelefono() + "\n"); // Asumiendo que existe el getter
            txtResultado.append("Correo: " + e.getCorreo() + "\n");     // Asumiendo que existe el getter
            txtResultado.append("Promedio: " + String.format("%.2f", e.promedio()) + "\n");
        } else {
            txtResultado.setText("");
            JOptionPane.showMessageDialog(this, "Estudiante no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResultado = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setText("Matrícula:");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtResultado.setColumns(20);
        txtResultado.setRows(5);
        jScrollPane1.setViewportView(txtResultado);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Búsqueda de Estudiante");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
    }

    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextArea txtResultado;
}
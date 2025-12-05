package sistemaescolar;

import persistencia.PersistenciaInscripciones;
import java.util.List;
import javax.swing.JOptionPane;

public class PanelVerInscritos extends javax.swing.JPanel {

    private PersistenciaInscripciones pInscripciones;

    public PanelVerInscritos(PersistenciaInscripciones pIns) {
        this.pInscripciones = pIns;
        initComponents();
    }

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {
        String clave = txtClave.getText().trim();
        if (clave.isEmpty()) return;

        List<String> lista = pInscripciones.obtenerListaInscritos(clave);
        txtArea.setText("");
        if (lista.isEmpty()) {
            txtArea.setText("No hay inscritos o el curso no existe.");
        } else {
            txtArea.append("--- Estudiantes Inscritos en " + clave + " ---\n\n");
            for (String s : lista) {
                txtArea.append(s + "\n");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        txtClave = new javax.swing.JTextField();
        btnVer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();

        jLabel1.setText("Clave del Curso:");
        btnVer.setText("Ver Inscritos");
        btnVer.addActionListener(evt -> btnVerActionPerformed(evt));
        txtArea.setColumns(20); txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(30,30,30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18,18,18)
                        .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18,18,18)
                        .addComponent(btnVer)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(30,30,30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1).addComponent(txtClave).addComponent(btnVer))
                .addGap(18,18,18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }
    private javax.swing.JButton btnVer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtClave;
}
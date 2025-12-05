package GUI;

import sistemaescolar.SistemaConsola;

import entidades.Estudiante;
import estructuras.AVL;
import persistencia.PersistenciaEstudiantes;
import java.util.List;

public class PanelReportePromedios extends javax.swing.JPanel {

        private PersistenciaEstudiantes pEstudiantes;

        public PanelReportePromedios(PersistenciaEstudiantes pEst) {
                this.pEstudiantes = pEst;
                initComponents();
                generarReporte();
        }

        private void generarReporte() {
                List<Estudiante> lista = pEstudiantes.listarEstudiantes();
                if (lista.isEmpty()) {
                        txtArea.setText("No hay estudiantes registrados.");
                        return;
                }

                // Construcción del AVL dinámico
                AVL<SistemaConsola.ParPromedio> avl = new AVL<>();
                for (Estudiante e : lista) {
                        avl.insert(new SistemaConsola.ParPromedio(e));
                }

                // Obtener lista ordenada del AVL
                List<SistemaConsola.ParPromedio> ordenados = avl.obtenerListaInOrder();

                txtArea.setText("--- Estudiantes ordenados por Promedio (AVL) ---\n\n");
                for (SistemaConsola.ParPromedio par : ordenados) {
                        txtArea.append(par.toString() + "\n");
                }
        }

        @SuppressWarnings("unchecked")
        private void initComponents() {
                jLabel1 = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                txtArea = new javax.swing.JTextArea();
                btnActualizar = new javax.swing.JButton();

                jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14));
                jLabel1.setText("Reporte de Promedios (Estructura AVL)");

                txtArea.setColumns(20);
                txtArea.setRows(5);
                jScrollPane1.setViewportView(txtArea);

                btnActualizar.setText("Refrescar Reporte");
                btnActualizar.addActionListener(evt -> generarReporte());

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup().addGap(30, 30, 30)
                                                .addGroup(layout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel1)
                                                                                .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(btnActualizar))
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                500,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(50, Short.MAX_VALUE)));
                layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup().addGap(30, 30, 30)
                                                .addGroup(layout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(jLabel1).addComponent(btnActualizar))
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(30, Short.MAX_VALUE)));
        }

        private javax.swing.JButton btnActualizar;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTextArea txtArea;
}
package GUI;

import entidades.Curso;
import persistencia.PersistenciaCursos;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/*
*
* @author Ricardo & valeria
*/
public class PanelListarCursos extends javax.swing.JPanel {

    private final PersistenciaCursos pCursos;
    private DefaultTableModel modeloTabla;

    public PanelListarCursos(PersistenciaCursos pCursos) {
        this.pCursos = pCursos;
        initComponents();
        cargarDatos();
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<Curso> lista = pCursos.listarCursos();
        for (Curso c : lista) {
            Object[] fila = {
                    c.getClave(),
                    c.getNombre(),
                    c.getCapacidadMax(),
                    c.getInscritos().size() // Asumiendo que podemos ver cuántos hay
            };
            modeloTabla.addRow(fila);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCursos = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Listado de Cursos");

        modeloTabla = new DefaultTableModel(
                new Object[][] {},
                new String[] { "Clave", "Nombre", "Capacidad", "Inscritos" }) {
            boolean[] canEdit = new boolean[] { false, false, false, false };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tablaCursos.setModel(modeloTabla);
        jScrollPane1.setViewportView(tablaCursos);

        btnActualizar.setText("Actualizar Lista");
        btnActualizar.addActionListener(evt -> cargarDatos());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTitulo)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnActualizar))
                                .addContainerGap(30, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblTitulo)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizar)
                                .addContainerGap(40, Short.MAX_VALUE)));
    }

    private javax.swing.JButton btnActualizar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tablaCursos;
}

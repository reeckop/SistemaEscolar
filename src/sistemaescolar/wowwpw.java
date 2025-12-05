package sistemaescolar;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import persistencia.*;

/**
 *
 * @author Ricardo
 */
public class wowwpw extends javax.swing.JFrame {
    private PersistenciaEstudiantes pEstudiantes = new PersistenciaEstudiantes();
    private PersistenciaCursos pCursos = new PersistenciaCursos();
    private PersistenciaInscripciones pInscripciones = new PersistenciaInscripciones(pCursos, pEstudiantes);
    private PersistenciaCalificaciones pCalificaciones = new PersistenciaCalificaciones();
    private PersistenciaAcciones pAcciones = new PersistenciaAcciones();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(wowwpw.class.getName());

    public wowwpw() {
        initComponents();
        setTitle("Sistema Escolar");
        setSize(800, 600);
        this.setLayout(new BorderLayout());
    }
    
    private void mostrarPanel(JPanel p) {
        p.setSize(this.getWidth(), this.getHeight());
        p.setLocation(0, 0);
        
        this.getContentPane().removeAll(); // Quita lo que haya
        this.getContentPane().add(p, BorderLayout.CENTER); // Pone el nuevo
        this.revalidate(); // Recalcula el diseño
        this.repaint();    // Pinta de nuevo
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        menuEstudiantes = new javax.swing.JMenu();
        itemRegistrarEstudiantes = new javax.swing.JMenuItem();
        itemBuscarMatriculaEstudiante = new javax.swing.JMenuItem();
        menuCursos = new javax.swing.JMenu();
        itemAgregarCurso = new javax.swing.JMenuItem();
        itemEliminarCurso = new javax.swing.JMenuItem();
        itemListarCursos = new javax.swing.JMenuItem();
        menuInscripciones = new javax.swing.JMenu();
        itemInscribirEstudianteCurso = new javax.swing.JMenuItem();
        itemListaInscritosCurso = new javax.swing.JMenuItem();
        itemListaEsperaCurso = new javax.swing.JMenuItem();
        menuCalificaciones = new javax.swing.JMenu();
        itemEnviarCalificacion = new javax.swing.JMenuItem();
        itemProcesarSiguiente = new javax.swing.JMenuItem();
        menuAcciones = new javax.swing.JMenu();
        itemDesahacer = new javax.swing.JMenuItem();
        menuReportes = new javax.swing.JMenu();
        itemListasrEstudiantesPromedio = new javax.swing.JMenuItem();
        itemRotar = new javax.swing.JMenuItem();
        menuSalir = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuEstudiantes.setText("Estudiantes");

        itemRegistrarEstudiantes.setText("Registrar Estudiante");
        itemRegistrarEstudiantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRegistrarEstudiantesActionPerformed(evt);
            }
        });
        menuEstudiantes.add(itemRegistrarEstudiantes);

        itemBuscarMatriculaEstudiante.setText("Buscar Matricula");
        itemBuscarMatriculaEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBuscarMatriculaEstudianteActionPerformed(evt);
            }
        });
        menuEstudiantes.add(itemBuscarMatriculaEstudiante);

        jMenuBar1.add(menuEstudiantes);

        menuCursos.setText("Cursos");

        itemAgregarCurso.setText("Agragar");
        itemAgregarCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAgregarCursoActionPerformed(evt);
            }
        });
        menuCursos.add(itemAgregarCurso);

        itemEliminarCurso.setText("Eliminar");
        menuCursos.add(itemEliminarCurso);

        itemListarCursos.setText("Listar");
        menuCursos.add(itemListarCursos);

        jMenuBar1.add(menuCursos);

        menuInscripciones.setText("Inscripciones");

        itemInscribirEstudianteCurso.setText("Inscribir estudiante en Curso");
        menuInscripciones.add(itemInscribirEstudianteCurso);

        itemListaInscritosCurso.setText("Mostrar lista de inscritos de un curso");
        itemListaInscritosCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemListaInscritosCursoActionPerformed(evt);
            }
        });
        menuInscripciones.add(itemListaInscritosCurso);

        itemListaEsperaCurso.setText("Mostrar lista de espera de un curso ");
        menuInscripciones.add(itemListaEsperaCurso);

        jMenuBar1.add(menuInscripciones);

        menuCalificaciones.setText("Calificaciones");

        itemEnviarCalificacion.setText("Enviar solicitud de calificación");
        menuCalificaciones.add(itemEnviarCalificacion);

        itemProcesarSiguiente.setText("Procesar siguiente solicitud ");
        menuCalificaciones.add(itemProcesarSiguiente);

        jMenuBar1.add(menuCalificaciones);

        menuAcciones.setText("Acciones");

        itemDesahacer.setText("Desahacer ultima accion");
        menuAcciones.add(itemDesahacer);

        jMenuBar1.add(menuAcciones);

        menuReportes.setText("Reportes");

        itemListasrEstudiantesPromedio.setText("Listar estudiantes ordenados por promedio");
        menuReportes.add(itemListasrEstudiantesPromedio);

        itemRotar.setText("Rotar rol de tutor/líder de proyecto  ");
        itemRotar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRotarActionPerformed(evt);
            }
        });
        menuReportes.add(itemRotar);

        jMenuBar1.add(menuReportes);

        menuSalir.setText("Salir");
        jMenuBar1.add(menuSalir);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 554, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemRegistrarEstudiantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRegistrarEstudiantesActionPerformed
        PanelRegistrarEstudiante panel = new PanelRegistrarEstudiante(pEstudiantes, pAcciones);
        mostrarPanel(panel);
    }//GEN-LAST:event_itemRegistrarEstudiantesActionPerformed

    private void itemAgregarCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAgregarCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemAgregarCursoActionPerformed

    private void itemListaInscritosCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemListaInscritosCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemListaInscritosCursoActionPerformed

    private void itemRotarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRotarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemRotarActionPerformed

    private void itemBuscarMatriculaEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBuscarMatriculaEstudianteActionPerformed
        PanelBuscarEstudiante panel = new PanelBuscarEstudiante(pEstudiantes);
        mostrarPanel(panel);
    }//GEN-LAST:event_itemBuscarMatriculaEstudianteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new wowwpw().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemAgregarCurso;
    private javax.swing.JMenuItem itemBuscarMatriculaEstudiante;
    private javax.swing.JMenuItem itemDesahacer;
    private javax.swing.JMenuItem itemEliminarCurso;
    private javax.swing.JMenuItem itemEnviarCalificacion;
    private javax.swing.JMenuItem itemInscribirEstudianteCurso;
    private javax.swing.JMenuItem itemListaEsperaCurso;
    private javax.swing.JMenuItem itemListaInscritosCurso;
    private javax.swing.JMenuItem itemListarCursos;
    private javax.swing.JMenuItem itemListasrEstudiantesPromedio;
    private javax.swing.JMenuItem itemProcesarSiguiente;
    private javax.swing.JMenuItem itemRegistrarEstudiantes;
    private javax.swing.JMenuItem itemRotar;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuAcciones;
    private javax.swing.JMenu menuCalificaciones;
    private javax.swing.JMenu menuCursos;
    private javax.swing.JMenu menuEstudiantes;
    private javax.swing.JMenu menuInscripciones;
    private javax.swing.JMenu menuReportes;
    private javax.swing.JMenu menuSalir;
    // End of variables declaration//GEN-END:variables

}

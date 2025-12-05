package GUI;

import entidades.Accion;
import entidades.Curso;
import entidades.Estudiante;
import persistencia.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class MenuPrincipal extends javax.swing.JFrame {

    // --- CONTROLADORES E INSTANCIAS ---
    private PersistenciaEstudiantes pEstudiantes = new PersistenciaEstudiantes();
    private PersistenciaCursos pCursos = new PersistenciaCursos();
    private PersistenciaInscripciones pInscripciones = new PersistenciaInscripciones(pCursos, pEstudiantes);
    private PersistenciaCalificaciones pCalificaciones = new PersistenciaCalificaciones();
    private PersistenciaAcciones pAcciones = new PersistenciaAcciones();

    public MenuPrincipal() {
        initComponents();
        setTitle("Sistema Escolar - Proyecto Final");
        setSize(850, 600);
        this.setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Centrar ventana
    }

    private void mostrarPanel(JPanel p) {
        p.setSize(this.getWidth(), this.getHeight());
        p.setLocation(0, 0);
        this.getContentPane().removeAll();
        this.getContentPane().add(p, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    // --- ACCIONES DE MENÚ ---

    // 1. ESTUDIANTES
    private void itemRegistrarEstudiantesActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelRegistrarEstudiante(pEstudiantes, pAcciones));
    }

    private void itemBuscarMatriculaEstudianteActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelBuscarEstudiante(pEstudiantes));
    }

    // 2. CURSOS
    private void itemAgregarCursoActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelAgregarCurso(pCursos));
    }

    private void itemEliminarCursoActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelEliminarCurso(pCursos));
    }

    private void itemListarCursosActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelListarCursos(pCursos));
    }

    // 3. INSCRIPCIONES
    private void itemInscribirEstudianteCursoActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelInscribir(pInscripciones, pAcciones, pEstudiantes, pCursos));
    }

    private void itemListaInscritosCursoActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelVerInscritos(pInscripciones));
    }

    private void itemListaEsperaCursoActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelListaEspera(pInscripciones));
    }

    // 4. CALIFICACIONES
    private void itemEnviarCalificacionActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelEnviarCalificacion(pCalificaciones, pEstudiantes));
    }

    private void itemProcesarSiguienteActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelProcesarCalificacion(pCalificaciones, pAcciones));
    }

    // 5. ACCIONES (DESHACER) - Lógica directa, no requiere panel
    private void itemDesahacerActionPerformed(java.awt.event.ActionEvent evt) {
        Accion ultima = pAcciones.deshacerUltimaAccion();
        if (ultima == null) {
            JOptionPane.showMessageDialog(this, "La pila de acciones está vacía.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String mensaje = "Deshaciendo acción: " + ultima.getTipo();
        switch (ultima.getTipo()) {
            case REGISTRO_ESTUDIANTE -> {
                Estudiante e = (Estudiante) ultima.getObjeto();
                pEstudiantes.eliminarEstudiante(e.getMatricula());
                mensaje += "\nEstudiante " + e.getMatricula() + " eliminado del BST.";
            }
            case CALIFICACION -> {
                Estudiante e = (Estudiante) ultima.getObjeto();
                e.eliminarUltimaCalificacion();
                mensaje += "\nÚltima calificación eliminada.";
            }
            case INSCRIPCION_CURSO -> {
                Estudiante e = (Estudiante) ultima.getObjeto();
                Curso c = (Curso) ultima.getInfoAdicional();
                String res = pInscripciones.cancelarInscripcion(e.getMatricula(), c.getClave());
                mensaje += "\n" + res;
            }
            default -> mensaje += "\nEsta acción no es reversible automáticamente.";
        }
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // 6. REPORTES
    private void itemListasrEstudiantesPromedioActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelReportePromedios(pEstudiantes));
    }

    private void itemRotarActionPerformed(java.awt.event.ActionEvent evt) {
        mostrarPanel(new PanelRotarRol(pInscripciones));
    }

    // --- CÓDIGO GENERADO (Layout Básico) ---
    @SuppressWarnings("unchecked")
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
        itemRegistrarEstudiantes.addActionListener(evt -> itemRegistrarEstudiantesActionPerformed(evt));
        menuEstudiantes.add(itemRegistrarEstudiantes);

        itemBuscarMatriculaEstudiante.setText("Buscar Matrícula");
        itemBuscarMatriculaEstudiante.addActionListener(evt -> itemBuscarMatriculaEstudianteActionPerformed(evt));
        menuEstudiantes.add(itemBuscarMatriculaEstudiante);
        jMenuBar1.add(menuEstudiantes);

        menuCursos.setText("Cursos");
        itemAgregarCurso.setText("Agregar Curso");
        itemAgregarCurso.addActionListener(evt -> itemAgregarCursoActionPerformed(evt));
        menuCursos.add(itemAgregarCurso);

        itemEliminarCurso.setText("Eliminar Curso");
        itemEliminarCurso.addActionListener(evt -> itemEliminarCursoActionPerformed(evt));
        menuCursos.add(itemEliminarCurso);

        itemListarCursos.setText("Listar Cursos");
        itemListarCursos.addActionListener(evt -> itemListarCursosActionPerformed(evt));
        menuCursos.add(itemListarCursos);
        jMenuBar1.add(menuCursos);

        menuInscripciones.setText("Inscripciones");
        itemInscribirEstudianteCurso.setText("Inscribir Estudiante");
        itemInscribirEstudianteCurso.addActionListener(evt -> itemInscribirEstudianteCursoActionPerformed(evt));
        menuInscripciones.add(itemInscribirEstudianteCurso);

        itemListaInscritosCurso.setText("Ver Inscritos");
        itemListaInscritosCurso.addActionListener(evt -> itemListaInscritosCursoActionPerformed(evt));
        menuInscripciones.add(itemListaInscritosCurso);

        itemListaEsperaCurso.setText("Ver Lista de Espera");
        itemListaEsperaCurso.addActionListener(evt -> itemListaEsperaCursoActionPerformed(evt));
        menuInscripciones.add(itemListaEsperaCurso);
        jMenuBar1.add(menuInscripciones);

        menuCalificaciones.setText("Calificaciones");
        itemEnviarCalificacion.setText("Enviar Solicitud");
        itemEnviarCalificacion.addActionListener(evt -> itemEnviarCalificacionActionPerformed(evt));
        menuCalificaciones.add(itemEnviarCalificacion);

        itemProcesarSiguiente.setText("Procesar Siguiente");
        itemProcesarSiguiente.addActionListener(evt -> itemProcesarSiguienteActionPerformed(evt));
        menuCalificaciones.add(itemProcesarSiguiente);
        jMenuBar1.add(menuCalificaciones);

        menuAcciones.setText("Acciones");
        itemDesahacer.setText("Deshacer última acción");
        itemDesahacer.addActionListener(evt -> itemDesahacerActionPerformed(evt));
        menuAcciones.add(itemDesahacer);
        jMenuBar1.add(menuAcciones);

        menuReportes.setText("Reportes");
        itemListasrEstudiantesPromedio.setText("Estudiantes por Promedio (AVL)");
        itemListasrEstudiantesPromedio.addActionListener(evt -> itemListasrEstudiantesPromedioActionPerformed(evt));
        menuReportes.add(itemListasrEstudiantesPromedio);

        itemRotar.setText("Rotar Rol Tutor/Líder");
        itemRotar.addActionListener(evt -> itemRotarActionPerformed(evt));
        menuReportes.add(itemRotar);
        jMenuBar1.add(menuReportes);

        menuSalir.setText("Salir");
        menuSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.exit(0);
            }
        });
        jMenuBar1.add(menuSalir);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 600, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));

        pack();
    }

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        java.awt.EventQueue.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }

    // Variables
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
}
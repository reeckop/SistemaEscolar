package sistemaescolar;

import entidades.*;
import persistencia.*;
import estructuras.ListaDobleCircular;
import estructuras.AVL;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author valeria & Ricardo
 */
public class Sistema {
    private PersistenciaEstudiantes pEstudiantes;
    private PersistenciaCursos pCursos;
    private PersistenciaAcciones pAcciones;
    private PersistenciaCalificaciones pCalificaciones;
    private ControlInscripciones cInscripciones;

    public Sistema() {
        pEstudiantes = new PersistenciaEstudiantes();
        pCursos = new PersistenciaCursos();
        pAcciones = new PersistenciaAcciones();
        pCalificaciones = new PersistenciaCalificaciones();
        cInscripciones = new ControlInscripciones(pCursos, pEstudiantes);
    }


    public String registrarEstudiante(String mat, String nom, String tel, String email, 
                                      String calle, String num, String col, String cd) {
        if (pEstudiantes.buscarEstudiante(mat) != null) {
            return "Error: Ya existe un estudiante con la matrícula " + mat;
        }
        String direccionCompleta = calle + " " + num + ", " + col + ", " + cd;
        Estudiante e = new Estudiante(mat, nom, tel, email, direccionCompleta);
        pEstudiantes.agregarEstudiante(e);
        
        // Registrar acción para Deshacer
        pAcciones.registrarAccion(new Accion(Accion.Tipo.REGISTRO_ESTUDIANTE, e, null));
        return "Estudiante registrado exitosamente.";
    }
    
    public Estudiante buscarEstudiante(String matricula) {
        return pEstudiantes.buscarEstudiante(matricula);
    }

    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return pEstudiantes.listarEstudiantes();
    }
    
    public List<String> reportePorPromedio() {
        List<Estudiante> lista = pEstudiantes.listarEstudiantes();
        AVL<ParPromedio> arbolAVL = new AVL<>();
        
        for (Estudiante e : lista) {
            arbolAVL.insert(new ParPromedio(e));
        }
        
        List<ParPromedio> ordenados = arbolAVL.obtenerListaInOrder(); // Requiere cambio en AVL
        List<String> resultado = new ArrayList<>();
        for (ParPromedio p : ordenados) {
            resultado.add(p.toString());
        }
        return resultado;
    }

    // Clase auxiliar privada para el AVL
    private class ParPromedio implements Comparable<ParPromedio> {
        Estudiante e; 
        double prom;
        public ParPromedio(Estudiante e) { this.e = e; this.prom = e.promedio(); }
        @Override public int compareTo(ParPromedio o) { return Double.compare(this.prom, o.prom); }
        @Override public String toString() { return e.toString(); }
    }

    public String agregarCurso(String clave, String nombre, int capacidad) {
        if (pCursos.buscarCurso(clave) != null) return "Error: Clave duplicada.";
        Curso c = new Curso(clave, nombre, capacidad);
        pCursos.agregarCurso(c);
        pAcciones.registrarAccion(new Accion(Accion.Tipo.INSCRIPCION_CURSO, c, "CREACION_CURSO"));
        return "Curso creado correctamente.";
    }

    public List<Curso> obtenerCursos() {
        return pCursos.listarCursos();
    }

    public String inscribirEstudiante(String matricula, String claveCurso) {
        try {
            String resultado = cInscripciones.inscribir(matricula, claveCurso);
            // Si fue exitoso, guardamos acción
            if (resultado.contains("éxito") || resultado.contains("espera")) {
                Accion a = new Accion(Accion.Tipo.INSCRIPCION_CURSO, matricula, claveCurso);
                pAcciones.registrarAccion(a);
            }
            return resultado;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public List<String> obtenerInscritos(String claveCurso) {
        return cInscripciones.obtenerListaInscritos(claveCurso);
    }
    
    public List<String> obtenerListaEspera(String claveCurso, int n) {
        return cInscripciones.obtenerListaEspera(claveCurso, n);
    }

    public String encolarCalificacion(String matricula, double calificacion) {
        Estudiante e = pEstudiantes.buscarEstudiante(matricula);
        if (e == null) return "Error: Estudiante no encontrado.";
        
        Accion solicitud = new Accion(Accion.Tipo.CALIFICACION, e, calificacion);
        pCalificaciones.enviarSolicitud(solicitud);
        return "Solicitud enviada a la cola.";
    }

    public String procesarSiguienteCalificacion() {
        Accion a = pCalificaciones.procesarSiguiente();
        if (a == null) return "No hay solicitudes pendientes.";
        
        // Al procesar, guardamos en el historial para deshacer
        pAcciones.registrarAccion(a); 
        Estudiante e = (Estudiante) a.getObjeto();
        return "Calificación procesada para: " + e.getNombreCompleto();
    }

    public String deshacerUltimaAccion() {
        Accion a = pAcciones.popAccion();
        if (a == null) return "No hay acciones para deshacer.";

        switch (a.getTipo()) {
            case REGISTRO_ESTUDIANTE:
                Estudiante e = (Estudiante) a.getObjeto();
                pEstudiantes.eliminarEstudiante(e.getMatricula());
                return "Deshecho: Registro de " + e.getMatricula();

            case CALIFICACION:
                Estudiante est = (Estudiante) a.getObjeto();
                est.eliminarUltimaCalificacion();
                return "Deshecho: Calificación de " + est.getMatricula();

            case INSCRIPCION_CURSO:
                // Lógica especial: verificar si era creación de curso o inscripción
                if (a.getInfoAdicional() != null && a.getInfoAdicional().equals("CREACION_CURSO")) {
                    Curso c = (Curso) a.getObjeto();
                    pCursos.eliminarCurso(c.getClave());
                    return "Deshecho: Creación de curso " + c.getClave();
                } else {
                    // Era inscripción de alumno
                    String mat = (String) a.getObjeto();
                    String clave = (String) a.getInfoAdicional();
                    cInscripciones.cancelarInscripcion(mat, clave);
                    return "Deshecho: Inscripción de " + mat;
                }
            default:
                return "Acción no soportada para deshacer.";
        }
    }
    
    // Getters de persistencia por si la GUI necesita algo muy específico
    public PersistenciaCursos getPCursos() { return pCursos; }
}
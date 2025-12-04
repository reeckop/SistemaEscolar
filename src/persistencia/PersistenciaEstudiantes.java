package persistencia;

import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Ricardo
 */
public class PersistenciaEstudiantes {
    private BST<Estudiante> bstEstudiantes;

    public PersistenciaEstudiantes() {
        this.bstEstudiantes = new BST<>();
    }

    public void agregarEstudiante(Estudiante e) {
        bstEstudiantes.insertar(e);
    }

    public Estudiante buscarEstudiante(String matricula) {
        Estudiante dummy = new Estudiante(matricula, "", "", "", "", "", "", "");
        return bstEstudiantes.buscar(dummy);

    public void eliminarEstudiante(String matricula) {
        Estudiante dummy = new Estudiante(matricula, "", "", "", "", "", "", "");
        bstEstudiantes.eliminar(dummy);
    }

    public List<Estudiante> listarEstudiantes() {
        return bstEstudiantes.inOrden();
    }
    
    public List<Estudiante> listarEstudiantesPorPromedio() {
        List<Estudiante> lista = bstEstudiantes.inOrden();
        return lista; 
    }
}

public class PersistenciaCursos {
    private Diccionario<String, Curso> diccionarioCursos;

    public PersistenciaCursos() {
        // Asumiendo un tamaño inicial para la tabla hash del diccionario
        this.diccionarioCursos = new Diccionario<>(100); 
    }

    public void agregarCurso(Curso c) {
        diccionarioCursos.agregar(c.getClave(), c);
    }

    public Curso eliminarCurso(String clave) {
        Curso c = diccionarioCursos.buscar(clave);
        diccionarioCursos.eliminar(clave);
        return c;
    }
    
    public Curso buscarCurso(String clave) {
         return diccionarioCursos.buscar(clave);
    }

    public List<Curso> listarCursos() {
        return diccionarioCursos.listar();
    }
}

public class PersistenciaAcciones {
    private Pila<Accion> pilaAcciones;

    public PersistenciaAcciones() {
        this.pilaAcciones = new Pila<>();
    }

    public void registrarAccion(Accion a) {
        pilaAcciones.push(a);
    }

    public void deshacerUltimaAccion() {
        if (!pilaAcciones.isEmpty()) {
            Accion ultima = pilaAcciones.pop();
            System.out.println("Deshaciendo acción: " + ultima.getTipo());
    }
}

public class PersistenciaCalificaciones {
    private Cola<Accion> colaSolicitudes;

    public PersistenciaCalificaciones() {
        this.colaSolicitudes = new Cola<>();
    }

    public void enviarSolicitudCalificacion(Accion a) {
        colaSolicitudes.enqueue(a);
    }

    public void procesarSiguienteSolicitud() {
        if (!colaSolicitudes.isEmpty()) {
            Accion a = colaSolicitudes.dequeue();
            System.out.println("Procesando solicitud para: " + a.getEstudianteAntes().getMatricula());
        }
    }
}

package persistencia;

import entidades.*;
import estructuras.ListaEnlazadaSimple;
import estructuras.ListaDobleCircular;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author valeria & Ricardo
 */
public class ControlInscripciones {
    private PersistenciaCursos pCursos;
    private PersistenciaEstudiantes pEstudiantes;

    public ControlInscripciones(PersistenciaCursos pc, PersistenciaEstudiantes pe) {
        this.pCursos = pc;
        this.pEstudiantes = pe;
    }

    public String inscribir(String matricula, String claveCurso) {
        Estudiante e = pEstudiantes.buscarEstudiante(matricula);
        Curso c = pCursos.buscarCurso(claveCurso);

        if (e == null) return "Estudiante no encontrado.";
        if (c == null) return "Curso no encontrado.";

        // Verificar si ya está inscrito
        if (c.getInscritos().contains(e)) return "El estudiante ya está inscrito.";

        if (c.getInscritos().size() < c.getCapacidadMax()) {
            c.agregarInscrito(e);
            return "Inscripción exitosa.";
        } else  {
            c.getListaEspera().add(e);
            return "Curso lleno. Estudiante agregado a Lista de Espera.";
        }
    }
    
    public void cancelarInscripcion(String matricula, String claveCurso) {
        Curso c = pCursos.buscarCurso(claveCurso);
        Estudiante e = pEstudiantes.buscarEstudiante(matricula);
        if (c != null && e != null) {
            // Intentar remover de inscritos
            boolean borrado = c.getInscritos().remove(e);
            if (!borrado) {
                // Si no estaba en inscritos, intentar remover de espera
                c.getListaEspera().remove(e);
            }
        }
    }

    public List<String> obtenerListaInscritos(String claveCurso) {
        List<String> lista = new ArrayList<>();
        Curso c = pCursos.buscarCurso(claveCurso);
        if (c != null) {
            ListaEnlazadaSimple<Estudiante> inscritos = c.getInscritos();
            for (int i = 0; i < inscritos.size(); i++) {
                lista.add(inscritos.get(i).toString());
            }
        }
        return lista;
    }
    
    public List<String> obtenerListaEspera(String claveCurso, int n) {
        List<String> lista = new ArrayList<>();
        Curso c = pCursos.buscarCurso(claveCurso);
        if (c != null) {
            ListaDobleCircular<Estudiante> espera = c.getListaEspera();
            // Necesitamos un método en ListaDobleCircular para obtener elementos sin imprimir
            // Asumiendo que agregas un método 'get(i)' o iteras manualmente
            // Aquí usamos una simulación, asumiendo que modificas ListaDobleCircular para devolver lista
            ListaDobleCircular<Estudiante> temp = espera.recorrerN(n); 
            // Tu clase actual imprime en recorrerN, cámbiala para retornar lista.
        }
        return lista;
    }
}
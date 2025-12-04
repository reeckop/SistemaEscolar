package persistencia;

import entidades.Curso;
import estructuras.Diccionario;
import java.util.List;

/**
 *
 * @author valeria & Ricardo
 */
public class PersistenciaCursos {
    private Diccionario<String, Curso> diccionarioCursos;

    public PersistenciaCursos() {
        this.diccionarioCursos = new Diccionario<>(20);
    }

    public void agregarCurso(Curso c) {
        diccionarioCursos.put(c.getClave(), c);
    }

    public Curso eliminarCurso(String clave) {
        Curso c = diccionarioCursos.get(clave);
        if (c != null) {
            diccionarioCursos.remove(clave);
        }
        return c;
    }
    
    public Curso buscarCurso(String clave) {
        return diccionarioCursos.get(clave);
    }

    public List<Curso> listarCursos() {
        return diccionarioCursos.values();
    }
}
package persistencia;

import estructuras.Diccionario;
import entidades.Curso;
import java.util.List;

/**
 *
 * @author valeria & Ricardo
 */
public class PersistenciaCursos {
    
    private Diccionario<String, Curso> diccionarioCursos;

    public PersistenciaCursos() {
        this.diccionarioCursos = new Diccionario<>(100); 
    }

    public void agregarCurso(Curso c) {
        diccionarioCursos.put(c.getClave(), c); 
    }

    public Curso buscarCurso(String clave) {
        return diccionarioCursos.get(clave);
    }

    public void eliminarCurso(String clave) {
        diccionarioCursos.remove(clave);
    }

    public List<Curso> listarCursos() {
        return diccionarioCursos.values();
    }
}
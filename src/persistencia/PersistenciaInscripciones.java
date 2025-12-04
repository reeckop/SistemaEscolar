package persistencia;

import entidades.Curso;
import entidades.Estudiante;
import estructuras.ListaDobleCircular;
import estructuras.ListaEnlazadaSimple;

/**
 *
 * @author valeria & Ricardo
 */
public class PersistenciaInscripciones {

    public void inscribirEstudiante(Estudiante e, Curso c) {
        c.agregarInscrito(e);
    }

    public ListaEnlazadaSimple<Estudiante> mostrarInscritos(Curso c) {
        return c.getInscritos();
    }

    public ListaDobleCircular<Estudiante> mostrarListaEspera(Curso c, int n) {
        return c.recorrerEsperaN(n);
    }

    public Estudiante rotarRol(Curso c) {
        // Si no hay roles asignados, intenta llenarlos con los inscritos
        if (c.getRoles().isEmpty() && !c.getInscritos().isEmpty()) {
            for(int i=0; i < c.getInscritos().size(); i++) {
                c.getRoles().addLast(c.getInscritos().get(i));
            }
        }
        return c.siguienteRol();
    }
}
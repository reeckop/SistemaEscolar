package persistencia;
import estructuras.Cola;
import entidades.Accion;

/**
 *
 * @author valeria & Ricardo
 */
public class PersistenciaCalificaciones {
    private Cola<Accion> cola;

    public PersistenciaCalificaciones() {
        this.cola = new Cola<>();
    }

    public void enviarSolicitud(Accion a) {
        cola.enqueue(a);
    }

    public Accion procesarSiguiente() {
        if (cola.isEmpty()) return null;
        Accion a = cola.dequeue();
        
        entidades.Estudiante e = (entidades.Estudiante) a.getObjeto();
        double cal = (double) a.getInfoAdicional();
        e.agregarCalificacion(cal);
        
        return a;
    }
}
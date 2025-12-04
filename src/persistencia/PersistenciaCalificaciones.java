package persistencia;

import entidades.Accion;
import entidades.Estudiante;
import estructuras.Cola;

/**
 *
 * @author valeria & Ricardo
 */
public class PersistenciaCalificaciones {
    private Cola<Accion> colaSolicitudes;

    public PersistenciaCalificaciones() {
        this.colaSolicitudes = new Cola<>();
    }

    public void enviarSolicitudCalificacion(Accion a) {
        colaSolicitudes.enqueue(a);
    }

    // Regresa la accionn procesada para que el sistema la registre en el historial
    public Accion procesarSiguienteSolicitud() {
        if (!colaSolicitudes.isEmpty()) {
            Accion solicitud = colaSolicitudes.dequeue();
            // Aplica la calificación
            Estudiante est = (Estudiante) solicitud.getObjeto();
            Double calif = (Double) solicitud.getInfoAdicional();
            est.agregarCalificacion(calif);
            return solicitud;
        }
        return null;
    }
    
    public boolean haySolicitudes() {
        return !colaSolicitudes.isEmpty();
    }
}
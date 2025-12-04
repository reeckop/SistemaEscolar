package persistencia;
import estructuras.Pila;
import entidades.Accion;

/**
 *
 * @author valeria & Ricardo
 */
public class PersistenciaAcciones {
    private Pila<Accion> pila;

    public PersistenciaAcciones() {
        this.pila = new Pila<>();
    }

    public void registrarAccion(Accion a) {
        pila.push(a);
    }

    public Accion popAccion() {
        if (pila.isEmpty()) return null;
        return pila.pop();
    }
}
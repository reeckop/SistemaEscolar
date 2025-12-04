package persistencia;

import entidades.Accion;
import estructuras.Pila;

/**
 *
 * @author valeria & Ricardo
 */
public class PersistenciaAcciones {
    private Pila<Accion> pilaAcciones;

    public PersistenciaAcciones() {
        this.pilaAcciones = new Pila<>();
    }

    public void registrarAccion(Accion a) {
        pilaAcciones.push(a);
    }

    public Accion deshacerUltimaAccion() {
        if (!pilaAcciones.isEmpty()) {
            return pilaAcciones.pop();
        }
        return null;
    }
    
    public boolean isEmpty() {
        return pilaAcciones.isEmpty();
    }
}
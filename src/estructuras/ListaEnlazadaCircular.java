package estructuras;

/**
 *
 * @author valeria
 */
public class ListaEnlazadaCircular<T> {

    private Nodo<T> cabeza;
    private Nodo<T> actual;
    private int tamaño;

    public ListaEnlazadaCircular() {
        cabeza = null;
        actual = null;
        tamaño = 0;
    }

    public boolean isEmpty() {
        return cabeza == null;
    }

    public int size() {
        return tamaño;
    }

    // Agregar al final
    public void addLast(T dato) {
        Nodo<T> nodo = new Nodo<>(dato);
        if (isEmpty()) {
            cabeza = nodo;
            nodo.setSiguiente(cabeza);
        } else {
            Nodo<T> temp = cabeza;
            while (temp.getSiguiente() != cabeza) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(nodo);
            nodo.setSiguiente(cabeza);
        }
        tamaño++;
    }

    // Obtener siguiente elemento (rotación)
    public T siguiente() {
        if (isEmpty()) return null;
        if (actual == null) actual = cabeza;
        else actual = actual.getSiguiente();
        return actual.getDato();
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> temp = cabeza;
        do {
            sb.append(temp.getDato()).append(", ");
            temp = temp.getSiguiente();
        } while (temp != cabeza);
        sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }
}
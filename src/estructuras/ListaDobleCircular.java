package estructuras;

/**
 *
 * @author valeria & Ricardo
 */
public class ListaDobleCircular<T> {

    private Nodo<T> cabeza;
    private int tam;

    public ListaDobleCircular() {
        cabeza = null;
        tam = 0;
    }

    // Agregar al final
    public void add(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
            cabeza.setSiguiente(cabeza);
            cabeza.setAnterior(cabeza);
        } else {
            Nodo<T> ultimo = cabeza.getAnterior();
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
            nuevo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevo);
        }

        tam++;
    }

    // Eliminar un nodo específico
    public boolean remove(T dato) {
        if (cabeza == null)
            return false;

        Nodo<T> actual = cabeza;
        boolean encontrado = false;

        for (int i = 0; i < tam; i++) {
            if (actual.getDato().equals(dato)) {
                encontrado = true;
                break;
            }
            actual = actual.getSiguiente();
        }

        if (!encontrado)
            return false;

        if (tam == 1) {
            cabeza = null;
        } else {
            Nodo<T> prev = actual.getAnterior();
            Nodo<T> next = actual.getSiguiente();
            prev.setSiguiente(next);
            next.setAnterior(prev);
            if (actual == cabeza) {
                cabeza = next;
            }
        }

        tam--;
        return true;
    }

    // Recorrer hacia adelante N elementos desde la cabeza
    public void recorrerAdelante(int n) {
        if (cabeza == null)
            return;

        Nodo<T> temp = cabeza;
        for (int i = 0; i < n && i < tam; i++) {
            System.out.println(temp.getDato());
            temp = temp.getSiguiente();
        }
    }

    // Recorrer hacia atrás N elementos desde la cabeza
    public void recorrerAtras(int n) {
        if (cabeza == null)
            return;

        Nodo<T> temp = cabeza.getAnterior(); // último nodo
        for (int i = 0; i < n && i < tam; i++) {
            System.out.println(temp.getDato());
            temp = temp.getAnterior();
        }
    }

    public ListaDobleCircular<T> recorrerN(int n) {
        ListaDobleCircular<T> sublista = new ListaDobleCircular<>();
        Nodo<T> temp = cabeza;
        int contador = 0;

        if (temp == null)
            return sublista;

        do {
            sublista.add(temp.getDato());
            temp = temp.getSiguiente();
            contador++;
        } while (temp != cabeza && contador < n);

        return sublista;
    }

    public boolean isEmpty() {
        return tam == 0;
    }

    public int size() {
        return tam;
    }

    public java.util.List<T> toList() {
        java.util.List<T> lista = new java.util.ArrayList<>();
        if (cabeza == null)
            return lista;

        Nodo<T> temp = cabeza;
        do {
            lista.add(temp.getDato());
            temp = temp.getSiguiente();
        } while (temp != cabeza);
        return lista;
    }
}

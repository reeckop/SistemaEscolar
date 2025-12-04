package estructuras;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author valeria & Ricardo
 */
public class BST<T extends Comparable<T>> {

    private class Nodo<T> {
        T dato;
        Nodo<T> izquierda, derecha;

        public Nodo(T dato) {
            this.dato = dato;
            this.izquierda = null;
            this.derecha = null;
        }
    }

    private Nodo<T> raiz;

    public BST() {
        this.raiz = null;
    }

    public void insert(T dato) {
        raiz = insertRec(raiz, dato);
    }

    private Nodo<T> insertRec(Nodo<T> nodo, T dato) {
        if (nodo == null) return new Nodo<>(dato);
        if (dato.compareTo(nodo.dato) < 0) nodo.izquierda = insertRec(nodo.izquierda, dato);
        else if (dato.compareTo(nodo.dato) > 0) nodo.derecha = insertRec(nodo.derecha, dato);
        return nodo;
    }

    public T search(T dato) {
        Nodo<T> nodo = searchRec(raiz, dato);
        return nodo == null ? null : nodo.dato;
    }

    private Nodo<T> searchRec(Nodo<T> nodo, T dato) {
        if (nodo == null || nodo.dato.equals(dato)) return nodo;
        if (dato.compareTo(nodo.dato) < 0) return searchRec(nodo.izquierda, dato);
        else return searchRec(nodo.derecha, dato);
    }

    public void delete(T dato) {
        raiz = deleteRec(raiz, dato);
    }

    private Nodo<T> deleteRec(Nodo<T> root, T dato) {
        if (root == null) return root;

        if (dato.compareTo(root.dato) < 0)
            root.izquierda = deleteRec(root.izquierda, dato);
        else if (dato.compareTo(root.dato) > 0)
            root.derecha = deleteRec(root.derecha, dato);
        else {
            // Nodo con un solo hijo o sin hijos
            if (root.izquierda == null) return root.derecha;
            else if (root.derecha == null) return root.izquierda;

            // Nodo con dos hijos: obtener sucesor in-order
            root.dato = minValue(root.derecha);
            root.derecha = deleteRec(root.derecha, root.dato);
        }
        return root;
    }
    
    public void inOrder() {
        inOrderRec(raiz);
    }

    private void inOrderRec(Nodo<T> nodo) {
        if (nodo != null) {
            inOrderRec(nodo.izquierda);
            System.out.println(nodo.dato); // Imprime el dato
            inOrderRec(nodo.derecha);
        }
    }

    private T minValue(Nodo<T> root) {
        T minv = root.dato;
        while (root.izquierda != null) {
            minv = root.izquierda.dato;
            root = root.izquierda;
        }
        return minv;
    }

    public List<T> getInOrderList() {
        List<T> lista = new ArrayList<>();
        inOrderListRec(raiz, lista);
        return lista;
    }

    private void inOrderListRec(Nodo<T> nodo, List<T> lista) {
        if (nodo != null) {
            inOrderListRec(nodo.izquierda, lista);
            lista.add(nodo.dato);
            inOrderListRec(nodo.derecha, lista);
        }
    }
    
    public boolean isEmpty() { return raiz == null; }
}
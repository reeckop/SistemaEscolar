package estructuras;

import java.util.ArrayList;
import java.util.List;

/**
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
        // Si es menor va a la izq, si es mayor a la der
        int cmp = dato.compareTo(nodo.dato);
        if (cmp < 0) nodo.izquierda = insertRec(nodo.izquierda, dato);
        else if (cmp > 0) nodo.derecha = insertRec(nodo.derecha, dato);
        // Si cmp == 0, es duplicado, no hacemos nada (o actualizamos si quisieras)
        return nodo;
    }

    public T search(T dato) {
        Nodo<T> nodo = searchRec(raiz, dato);
        return nodo == null ? null : nodo.dato;
    }

    private Nodo<T> searchRec(Nodo<T> nodo, T dato) {
        if (nodo == null) return null;
        
        int cmp = dato.compareTo(nodo.dato);
        
        if (cmp == 0) return nodo; // ¡Encontrado!
        if (cmp < 0) return searchRec(nodo.izquierda, dato);
        else return searchRec(nodo.derecha, dato);
    }

    public void delete(T dato) {
        raiz = deleteRec(raiz, dato);
    }

    private Nodo<T> deleteRec(Nodo<T> root, T dato) {
        if (root == null) return root;

        int cmp = dato.compareTo(root.dato);

        if (cmp < 0) root.izquierda = deleteRec(root.izquierda, dato);
        else if (cmp > 0) root.derecha = deleteRec(root.derecha, dato);
        else {
            // Nodo encontrado
            if (root.izquierda == null) return root.derecha;
            else if (root.derecha == null) return root.izquierda;

            root.dato = minValue(root.derecha);
            root.derecha = deleteRec(root.derecha, root.dato);
        }
        return root;
    }
    
    // Metodo auxiliar para obtener mínimo
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
    
    public void inOrder() {
        if (raiz == null) System.out.println(" (Árbol vacío) ");
        else inOrderRec(raiz);
    }

    private void inOrderRec(Nodo<T> nodo) {
        if (nodo != null) {
            inOrderRec(nodo.izquierda);
            System.out.println(nodo.dato);
            inOrderRec(nodo.derecha);
        }
    }

    public boolean isEmpty() { return raiz == null; }
}
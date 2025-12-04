/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author valeria
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

    // Insertar un nuevo dato
    public void insert(T dato) {
        raiz = insertRec(raiz, dato);
    }

    private Nodo<T> insertRec(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return new Nodo<>(dato);
        }

        if (dato.compareTo(nodo.dato) < 0) {
            nodo.izquierda = insertRec(nodo.izquierda, dato);
        } else if (dato.compareTo(nodo.dato) > 0) {
            nodo.derecha = insertRec(nodo.derecha, dato);
        }
        // Si es igual, no insertamos (o puedes manejar duplicados)
        return nodo;
    }

    // Buscar un dato
    public T search(T dato) {
        Nodo<T> nodo = searchRec(raiz, dato);
        return nodo == null ? null : nodo.dato;
    }

    private Nodo<T> searchRec(Nodo<T> nodo, T dato) {
        if (nodo == null || nodo.dato.equals(dato)) return nodo;

        if (dato.compareTo(nodo.dato) < 0)
            return searchRec(nodo.izquierda, dato);
        else
            return searchRec(nodo.derecha, dato);
    }

    // Recorrido in-order
    public void inOrder() {
        inOrderRec(raiz);
    }

    private void inOrderRec(Nodo<T> nodo) {
        if (nodo != null) {
            inOrderRec(nodo.izquierda);
            System.out.println(nodo.dato);
            inOrderRec(nodo.derecha);
        }
    }

    // Saber si está vacío
    public boolean isEmpty() {
        return raiz == null;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author valeria & Ricardo
 */
public class AVL<T extends Comparable<T>> {

    private class Nodo<T> {
        T dato;
        Nodo<T> izquierda, derecha;
        int altura;

        public Nodo(T dato) {
            this.dato = dato;
            this.izquierda = null;
            this.derecha = null;
            this.altura = 1;
        }
    }

    private Nodo<T> raiz;

    public AVL() {
        raiz = null;
    }

    // Altura del nodo
    private int altura(Nodo<T> nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    // Factor de balance
    private int getBalance(Nodo<T> nodo) {
        return nodo == null ? 0 : altura(nodo.izquierda) - altura(nodo.derecha);
    }

    // Rotación derecha
    private Nodo<T> rotarDerecha(Nodo<T> y) {
        Nodo<T> x = y.izquierda;
        Nodo<T> T2 = x.derecha;

        x.derecha = y;
        y.izquierda = T2;

        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;

        return x;
    }

    // Rotación izquierda
    private Nodo<T> rotarIzquierda(Nodo<T> x) {
        Nodo<T> y = x.derecha;
        Nodo<T> T2 = y.izquierda;

        y.izquierda = x;
        x.derecha = T2;

        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;

        return y;
    }

    // Insertar dato
    public void insert(T dato) {
        raiz = insertRec(raiz, dato);
    }

    private Nodo<T> insertRec(Nodo<T> nodo, T dato) {
        if (nodo == null) return new Nodo<>(dato);

        if (dato.compareTo(nodo.dato) < 0)
            nodo.izquierda = insertRec(nodo.izquierda, dato);
        else if (dato.compareTo(nodo.dato) > 0)
            nodo.derecha = insertRec(nodo.derecha, dato);
        else
            return nodo; // duplicados no permitidos

        // Actualizar altura
        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));

        int balance = getBalance(nodo);

        // Caso izquierda-izquierda
        if (balance > 1 && dato.compareTo(nodo.izquierda.dato) < 0)
            return rotarDerecha(nodo);

        // Caso derecha-derecha
        if (balance < -1 && dato.compareTo(nodo.derecha.dato) > 0)
            return rotarIzquierda(nodo);

        // Caso izquierda-derecha
        if (balance > 1 && dato.compareTo(nodo.izquierda.dato) > 0) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }

        // Caso derecha-izquierda
        if (balance < -1 && dato.compareTo(nodo.derecha.dato) < 0) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        return nodo;
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

    public boolean isEmpty() {
        return raiz == null;
    }
    
    public List<T> obtenerListaInOrder() {
    List<T> lista = new ArrayList<>();
    recolectarInOrder(raiz, lista);
    return lista;
}

    // Método recursivo privado
    private void recolectarInOrder(Nodo<T> nodo, List<T> lista) {
        if (nodo != null) {
            recolectarInOrder(nodo.izquierda, lista);
            lista.add(nodo.dato);
            recolectarInOrder(nodo.derecha, lista);
        }
    }
}
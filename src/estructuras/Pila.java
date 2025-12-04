/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author valeria
 */
public class Pila<T> {

    private Nodo<T> top;

    public Pila() {
        top = null;
    }

    // Push
    public void push(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        nuevo.setSiguiente(top);
        top = nuevo;
    }

    // Pop
    public T pop() {
        if (isEmpty()) return null;

        T dato = top.getDato();
        top = top.getSiguiente();
        return dato;
    }

    // Peek
    public T peek() {
        if (isEmpty()) return null;
        return top.getDato();
    }

    public boolean isEmpty() {
        return top == null;
    }
}

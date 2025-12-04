/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author valeria
 */
public class Cola<T> {


    private Nodo<T> principio;  // Primer elemento de la cola
    private Nodo<T> ultimo;     // Último elemento de la cola
    private int tam;

    public Cola() {
        this.principio = null;
        this.ultimo = null;
        this.tam = 0;
    }

    // Insertar al final de la cola
    public void enqueue(T data) {
        Nodo<T> nuevo = new Nodo<>(data);

        if (isEmpty()) {
            principio = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }

        tam++;
    }

    // Sacar el elemento al frente
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T dato = principio.getDato();
        // usar el getter para avanzar al siguiente nodo
        principio = principio.getSiguiente();

        // Si la cola quedó vacía
        if (principio == null) {
            ultimo = null;
        }

        tam--;
        return dato;
    }

    // Ver el primer elemento sin sacarlo
    public T peek() {
        return isEmpty() ? null : principio.getDato();
    }

    public boolean isEmpty() {
        return principio == null;
    }

    public int size() {
        return tam;
    }

    @Override
    public String toString() {
        String s = "[";
        Nodo<T> temp = principio;

        while (temp != null) {
            s += temp.getDato();
            if (temp.getSiguiente() != null) s += ", ";
            temp = temp.getSiguiente();
        }

        s += "]";
        return s;
    }
}


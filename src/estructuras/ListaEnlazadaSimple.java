/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author valeria
 */
public class ListaEnlazadaSimple<T> {

    private Nodo<T> cabeza;  // Primero
    private int tam;

    public ListaEnlazadaSimple() {
        this.cabeza = null;
        this.tam = 0;
    }

    
    
    // Agregar al inicio
    public void addFirst(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
        tam++;
    }

    // Agregar al final
    public void addLast(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (isEmpty()) {
            cabeza = nuevo;
        } else {
            Nodo<T> temp = cabeza;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(nuevo);
        }
        tam++;
    }
    
    public boolean remove(T dato) {
        if (cabeza == null) return false;

        if (cabeza.getDato().equals(dato)) {
            removeFirst();
            return true;
        }

        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tam--;
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false; // no se encontró
    }
    
    // Eliminar el primero
    public T removeFirst() {
        if (isEmpty()) return null;

        T dato = cabeza.getDato();
        cabeza = cabeza.getSiguiente();
        tam--;
        return dato;
    }

    // Buscar elemento (true si existe)
    public boolean contains(T dato) {
        Nodo<T> temp = cabeza;
        while (temp != null) {
            if (temp.getDato().equals(dato)) {
                return true;
            }
            temp = temp.getSiguiente();
        }
        return false;
    }

    // Saber si está vacía
    public boolean isEmpty() {
        return cabeza == null;
    }

    // Tamaño
    public int size() {
        return tam;
    }

    // Obtener elemento por índice
    public T get(int index) {
        if (index < 0 || index >= tam) return null;

        Nodo<T> temp = cabeza;
        for (int i = 0; i < index; i++) {
            temp = temp.getSiguiente();
        }
        return temp.getDato();
    }

    // ToString para ver la lista
    @Override
    public String toString() {
        String s = "[";
        Nodo<T> temp = cabeza;

        while (temp != null) {
            s += temp.getDato();
            if (temp.getSiguiente() != null) s += ", ";
            temp = temp.getSiguiente();
        }

        s += "]";
        return s;
    }
}


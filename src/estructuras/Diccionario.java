/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author valeria
 */

public class Diccionario<K, V> {

    private static class Entrada<K, V> {
        K clave;
        V valor;

        public Entrada(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    private List<ListaEnlazadaSimple<Entrada<K,V>>> baldes; // Tabla de baldes
    private int capacidad; // Tamaño de la tabla
    
    public Diccionario(int capacidad) {
        this.capacidad = capacidad;
        baldes = new ArrayList<>(capacidad);
        for (int i = 0; i < capacidad; i++) {
            baldes.add(new ListaEnlazadaSimple<Entrada<K, V>>());
        }
    }

    // Función hash simple
    private int hash(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }

    // Agregar o actualizar
    public void put(K clave, V valor) {
        int index = hash(clave);
        ListaEnlazadaSimple<Entrada<K, V>> lista = baldes.get(index);

        // Buscar si ya existe la clave
        boolean encontrado = false;
        for (int i = 0; i < lista.size(); i++) {
            Entrada<K, V> e = lista.get(i);
            if (e.clave.equals(clave)) {
                e.valor = valor; // actualizar
                encontrado = true;
                break;
            }
        }

        // Si no existe, agregar al final
        if (!encontrado) {
            lista.addLast(new Entrada<>(clave, valor));
        }
    }

    // Obtener valor por clave
    public V get(K clave) {
        int index = hash(clave);
        ListaEnlazadaSimple<Entrada<K, V>> lista = baldes.get(index);

        for (int i = 0; i < lista.size(); i++) {
            Entrada<K, V> e = lista.get(i);
            if (e.clave.equals(clave)) {
                return e.valor;
            }
        }

        return null; // no encontrado
    }

    // Eliminar entrada por clave
    public boolean remove(K clave) {
        int index = hash(clave);
        ListaEnlazadaSimple<Entrada<K, V>> lista = baldes.get(index);

        for (int i = 0; i < lista.size(); i++) {
            Entrada<K, V> e = lista.get(i);
            if (e.clave.equals(clave)) {
                lista.remove(e); // elimina el nodo en la posición i
                return true;
            }
        }

        return false; // no se encontró la clave
    }

    // Listar todos los valores
    public List<V> values() {
        List<V> result = new ArrayList<>();
        for (ListaEnlazadaSimple<Entrada<K, V>> lista : baldes) {
            for (int i = 0; i < lista.size(); i++) {
                Entrada<K, V> e = lista.get(i);
                result.add(e.valor);
            }
        }
        return result;
    }

    // Saber si está vacío
    public boolean isEmpty() {
        for (ListaEnlazadaSimple<Entrada<K, V>> lista : baldes) {
            if (lista.size() > 0) return false;
        }
        return true;
    }
}


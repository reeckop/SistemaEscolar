package entidades;

import estructuras.ListaDobleCircular;
import estructuras.ListaEnlazadaCircular;
import estructuras.ListaEnlazadaSimple;

/**
 *
 * @author valeria & Ricardo
 */
public class Curso {

    private String clave;
    private String nombre;
    private int capacidadMax;
    
    // Lista de estudiantes inscritos (simple)
    private ListaEnlazadaSimple<Estudiante> inscritos;
    
    // Lista de espera (doble circular)
    private ListaDobleCircular<Estudiante> listaEspera;
    
    // Lista para roles de tutor/líder (circular simple)
    private ListaEnlazadaCircular<Estudiante> roles;

    // Constructor
    public Curso(String clave, String nombre, int capacidadMax) {
        this.clave = clave;
        this.nombre = nombre;
        this.capacidadMax = capacidadMax;
        this.inscritos = new ListaEnlazadaSimple<>();
        this.listaEspera = new ListaDobleCircular<>();
        this.roles = new ListaEnlazadaCircular<>();
    }

    // Getters
    public String getClave() {
        return clave;
    }

    public String getNombre() {
        return nombre;
    }

    public ListaEnlazadaSimple<Estudiante> getInscritos() {
        return inscritos;
    }

    public ListaDobleCircular<Estudiante> getListaEspera() {
        return listaEspera;
    }

    public ListaEnlazadaCircular<Estudiante> getRoles() {
        return roles;
    }
    
    public int getCapacidadMax() {
        return capacidadMax;
    }

    // Métodos para inscripciones
    public void agregarInscrito(Estudiante e) {
        if (inscritos.size() < capacidadMax) {
            inscritos.addLast(e);
        } else {
            listaEspera.add(e);
        }
    }

    // Rotar rol de tutor/líder
    public Estudiante siguienteRol() {
        if (roles.isEmpty()) return null;
        return roles.siguiente(); // asume que ListaEnlazadaCircular implementa siguiente()
    }

    // Mostrar primeros N de la lista de espera
    public ListaDobleCircular<Estudiante> recorrerEsperaN(int n) {
        return listaEspera.recorrerN(n); // asume recorrerN(n) devuelve sublista
    }

    @Override
    public String toString() {
        return clave + " - " + nombre + " (Inscritos: " + inscritos.size() + ")";
    }
}

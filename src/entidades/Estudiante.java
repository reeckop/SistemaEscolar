package entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author valeria & Ricardo
 */
public class Estudiante implements Comparable<Estudiante> {

    private String matricula;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private String direccion; 
    private List<Double> calificaciones;

    public Estudiante(String matricula, String nombreCompleto, String telefono, String correo, String direccion) {
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.calificaciones = new ArrayList<>();
    }

    public String getMatricula() { return matricula; }
    public String getNombreCompleto() { return nombreCompleto; }
    public List<Double> getCalificaciones() { return calificaciones; }

    public void agregarCalificacion(double calificacion) {
        calificaciones.add(calificacion);
    }
    
    public void eliminarUltimaCalificacion() {
        if (!calificaciones.isEmpty()) {
            calificaciones.remove(calificaciones.size() - 1);
        }
    }

    public double promedio() {
        if (calificaciones.isEmpty()) return 0.0;
        return promedioRecursivo(calificaciones, 0) / calificaciones.size();
    }

    private double promedioRecursivo(List<Double> lista, int index) {
        if (index == lista.size()) return 0;
        return lista.get(index) + promedioRecursivo(lista, index + 1);
    }

    @Override
    public String toString() {
        return "Matricula: " + matricula + " | Nombre: " + nombreCompleto + 
               " | Prom: " + String.format("%.2f", promedio()) + 
               " | Califs: " + calificaciones;
    }

    @Override
    public int compareTo(Estudiante o) {
        return this.matricula.compareTo(o.matricula);
    }
}
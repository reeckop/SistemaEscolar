package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
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

    // Getters
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
        double suma = 0;
        for(Double c : calificaciones) suma += c;
        return suma / calificaciones.size();
    }

    @Override
    public String toString() {
        return String.format("Matricula: %s | Nombre: %s | Prom: %.2f", matricula, nombreCompleto, promedio());
    }

    @Override
    public int compareTo(Estudiante o) {
        // Esto ordena y busca por matrícula alfabéticamente
        return this.matricula.compareToIgnoreCase(o.matricula);
    }

    // --- AGREGADO: Esencial para que las busquedas funcionen ---
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Estudiante other = (Estudiante) obj;
        return Objects.equals(matricula, other.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }
}
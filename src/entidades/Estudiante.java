/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author valeria
 */

public class Estudiante {

    private String matricula;
    private String nombreCompleto;
    
    // Datos de contacto
    private String telefono;
    private String correo;
    private String direccion; // Puedes unificar calle, número, colonia, ciudad
    
    // Lista dinámica de calificaciones
    private List<Double> calificaciones;

    // Constructor
    public Estudiante(String matricula, String nombreCompleto, String telefono, String correo, String direccion) {
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.calificaciones = new ArrayList<>();
    }

    // Getters y setters
    public String getMatricula() {
        return matricula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Double> getCalificaciones() {
        return calificaciones;
    }

    // Agregar calificación
    public void agregarCalificacion(double calificacion) {
        calificaciones.add(calificacion);
    }

    // Calcular promedio recursivamente
    public double promedio() {
        return promedioRecursivo(calificaciones, 0);
    }

    private double promedioRecursivo(List<Double> lista, int index) {
        if (lista.isEmpty()) return 0;
        if (index == lista.size()) return 0;
        return (lista.get(index) + promedioRecursivo(lista, index + 1));
    }

    public double promedioFinal() {
        if (calificaciones.isEmpty()) return 0;
        return promedio() / calificaciones.size();
    }

    @Override
    public String toString() {
        return matricula + " - " + nombreCompleto + " | Promedio: " + promedioFinal();
    }
}


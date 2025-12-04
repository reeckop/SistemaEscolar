/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author valeria
 */
public class Accion {

    public enum Tipo {
        REGISTRO_ESTUDIANTE,
        ELIMINACION_ESTUDIANTE,
        INSCRIPCION_CURSO,
        CALIFICACION
    }

    private Tipo tipo;
    private Object objeto; // puede ser Estudiante, Curso, o cualquier dato necesario
    private Object infoAdicional; // opcional, por ejemplo calificación o curso

    // Constructor
    public Accion(Tipo tipo, Object objeto, Object infoAdicional) {
        this.tipo = tipo;
        this.objeto = objeto;
        this.infoAdicional = infoAdicional;
    }

    // Getters
    public Tipo getTipo() {
        return tipo;
    }

    public Object getObjeto() {
        return objeto;
    }

    public Object getInfoAdicional() {
        return infoAdicional;
    }

    // Setters
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    public void setInfoAdicional(Object infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    @Override
    public String toString() {
        return "Accion{" +
                "tipo=" + tipo +
                ", objeto=" + objeto +
                ", infoAdicional=" + infoAdicional +
                '}';
    }
}

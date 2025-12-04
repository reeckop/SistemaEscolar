package entidades;

/**
 *
 * @author valeria & Ricardo
 */
public class Accion {

    public enum Tipo {
        REGISTRO_ESTUDIANTE,
        ELIMINACION_ESTUDIANTE,
        INSCRIPCION_CURSO,
        CALIFICACION
    }

    private Tipo tipo;
    private Object objeto;
    private Object infoAdicional;

    public Accion(Tipo tipo, Object objeto, Object infoAdicional) {
        this.tipo = tipo;
        this.objeto = objeto;
        this.infoAdicional = infoAdicional;
    }

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

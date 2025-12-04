package persistencia;

import entidades.Estudiante;
import estructuras.BST;
import java.util.List;

/**
 *
 * @author valeria & Ricardo
 */
public class PersistenciaEstudiantes {
    
    private BST<Estudiante> bstEstudiantes;

    public PersistenciaEstudiantes() {
        this.bstEstudiantes = new BST<>();
    }

    // Agregar estudiante al BST
    public void agregarEstudiante(Estudiante e) {
        bstEstudiantes.insert(e);
    }

    // Buscar estudiante por matrícula
    public Estudiante buscarEstudiante(String matricula) {
        Estudiante dummy = new Estudiante(matricula, "", "", "", "");
        return bstEstudiantes.search(dummy);
    }

    // Eliminar estudiante por matrícula
    public void eliminarEstudiante(String matricula) {
        Estudiante dummy = new Estudiante(matricula, "", "", "", "");
        bstEstudiantes.delete(dummy);
    }

    // Obtener lista de todos los estudiantes
    public List<Estudiante> listarEstudiantes() {
        return bstEstudiantes.getInOrderList(); 
    }
    
    // Método para imprimir en consola
    public void imprimirListado() {
        bstEstudiantes.inOrder();
    }
    
    public boolean isEmpty() {
        return bstEstudiantes.isEmpty();
    }
}
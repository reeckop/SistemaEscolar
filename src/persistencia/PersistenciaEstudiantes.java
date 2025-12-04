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
        bstEstudiantes.insert(e); // Método corregido de 'insertar' a 'insert'
    }

    // Buscar estudiante por matrícula
    public Estudiante buscarEstudiante(String matricula) {
        // Corrección: Constructor con 5 argumentos (matrícula y cadenas vacías)
        Estudiante dummy = new Estudiante(matricula, "", "", "", "");
        return bstEstudiantes.search(dummy); // Método corregido de 'buscar' a 'search'
    }

    // Eliminar estudiante por matrícula
    public void eliminarEstudiante(String matricula) {
        Estudiante dummy = new Estudiante(matricula, "", "", "", "");
        bstEstudiantes.delete(dummy); // Requiere que BST tenga el método 'delete' implementado
    }

    // Obtener lista de todos los estudiantes (para reportes)
    public List<Estudiante> listarEstudiantes() {
        // Requiere que BST tenga el método 'getInOrderList' que devuelve List<T>
        return bstEstudiantes.getInOrderList(); 
    }
    
    // Método opcional si solo quieres imprimir en consola directamente
    public void imprimirListado() {
        bstEstudiantes.inOrder();
    }
    
    // Verificar si está vacío
    public boolean isEmpty() {
        return bstEstudiantes.isEmpty();
    }
}
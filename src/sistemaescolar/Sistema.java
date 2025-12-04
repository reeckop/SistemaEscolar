package sistemaescolar;

import entidades.Accion;
import entidades.Curso;
import entidades.Estudiante;
import estructuras.AVL;
import persistencia.*;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author valeria & Ricardo
 */
public class Sistema {
    private static PersistenciaEstudiantes pEstudiantes = new PersistenciaEstudiantes();
    private static PersistenciaCursos pCursos = new PersistenciaCursos();
    private static PersistenciaCalificaciones pCalificaciones = new PersistenciaCalificaciones();
    private static PersistenciaAcciones pAcciones = new PersistenciaAcciones();
    private static PersistenciaInscripciones pInscripciones = new PersistenciaInscripciones();
    
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            mostrarMenu();
            try {
                String entrada = scanner.nextLine(); // Leemos como String para evitar errores de buffer
                opcion = Integer.parseInt(entrada);
                ejecutarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Debes ingresar un número válido.");
            } catch (Exception e) {
                System.out.println(">> Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        } while (opcion != 7);
    }

    private static void mostrarMenu() {
        System.out.println("========================================");
        System.out.println("        SISTEMA ESCOLAR - MENU          ");
        System.out.println("========================================");
        System.out.println("1. Estudiantes (Registrar / Buscar)");
        System.out.println("2. Cursos (Agregar / Eliminar / Listar)");
        System.out.println("3. Inscripciones (Inscribir / Ver)");
        System.out.println("4. Calificaciones (Solicitar / Procesar)");
        System.out.println("5. Acciones (Deshacer última operación)");
        System.out.println("6. Reportes (Promedios / Roles)");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1: menuEstudiantes(); break;
            case 2: menuCursos(); break;
            case 3: menuInscripciones(); break;
            case 4: menuCalificaciones(); break;
            case 5: deshacerUltimaAccion(); break;
            case 6: menuReportes(); break;
            case 7: System.out.println("Cerrando el sistema..."); break;
            default: System.out.println(">> Opción no válida.");
        }
    }

    private static void menuEstudiantes() {
        System.out.println("\n--- GESTIÓN DE ESTUDIANTES ---");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Buscar estudiante");
        System.out.print("Opción: ");
        String op = scanner.nextLine();

        if (op.equals("1")) {
            System.out.print("Matrícula: "); String mat = scanner.nextLine();
            
            // Verificamos si ya existe antes de pedir el resto de datos
            if (pEstudiantes.buscarEstudiante(mat) != null) {
                System.out.println(">> Error: Ya existe un estudiante con esa matrícula.");
                return;
            }

            System.out.print("Nombre Completo: "); String nom = scanner.nextLine();
            System.out.print("Teléfono: "); String tel = scanner.nextLine();
            System.out.print("Correo: "); String mail = scanner.nextLine();
            System.out.print("Dirección completa: "); String dir = scanner.nextLine();

            Estudiante nuevo = new Estudiante(mat, nom, tel, mail, dir);
            pEstudiantes.agregarEstudiante(nuevo);
            
            pAcciones.registrarAccion(new Accion(Accion.Tipo.REGISTRO_ESTUDIANTE, nuevo, null));
            System.out.println(">> Estudiante registrado exitosamente.");

        } else if (op.equals("2")) {
            System.out.print("Ingrese matrícula a buscar: ");
            String mat = scanner.nextLine();
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e != null) {
                System.out.println(">> Estudiante encontrado:\n" + e);
            } else {
                System.out.println(">> Estudiante no encontrado.");
            }
        }
    }

    private static void menuCursos() {
        System.out.println("\n--- GESTIÓN DE CURSOS ---");
        System.out.println("1. Agregar curso");
        System.out.println("2. Eliminar curso");
        System.out.println("3. Listar cursos");
        System.out.print("Opción: ");
        String op = scanner.nextLine();

        if (op.equals("1")) {
            System.out.print("Clave: "); String clave = scanner.nextLine();
            System.out.print("Nombre: "); String nom = scanner.nextLine();
            System.out.print("Capacidad: "); int cap = Integer.parseInt(scanner.nextLine());

            Curso nuevo = new Curso(clave, nom, cap);
            pCursos.agregarCurso(nuevo);
            System.out.println(">> Curso agregado.");

        } else if (op.equals("2")) {
            System.out.print("Clave del curso a eliminar: "); String clave = scanner.nextLine();
            Curso eliminado = pCursos.eliminarCurso(clave);
            if (eliminado != null) {
                System.out.println(">> Curso '" + eliminado.getNombre() + "' eliminado.");
            } else {
                System.out.println(">> Curso no encontrado.");
            }

        } else if (op.equals("3")) {
            List<Curso> lista = pCursos.listarCursos();
            if (lista.isEmpty()) System.out.println(">> No hay cursos registrados.");
            for (Curso c : lista) System.out.println(c);
        }
    }

    private static void menuInscripciones() {
        System.out.println("\n--- INSCRIPCIONES ---");
        System.out.println("1. Inscribir estudiante");
        System.out.println("2. Ver inscritos de un curso");
        System.out.println("3. Ver lista de espera");
        System.out.print("Opción: ");
        String op = scanner.nextLine();

        if (op.equals("1")) {
            System.out.print("Matrícula Estudiante: "); String mat = scanner.nextLine();
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e == null) { System.out.println(">> Estudiante no existe."); return; }

            System.out.print("Clave Curso: "); String clave = scanner.nextLine();
            Curso c = pCursos.buscarCurso(clave);
            if (c == null) { System.out.println(">> Curso no existe."); return; }

            pInscripciones.inscribirEstudiante(e, c);
            pAcciones.registrarAccion(new Accion(Accion.Tipo.INSCRIPCION_CURSO, e, c));
            System.out.println(">> Proceso realizado (Verificar si quedó inscrito o en espera).");

        } else if (op.equals("2")) {
            System.out.print("Clave Curso: "); String clave = scanner.nextLine();
            Curso c = pCursos.buscarCurso(clave);
            if (c != null) System.out.println("Inscritos: " + pInscripciones.mostrarInscritos(c));
            else System.out.println(">> Curso no encontrado.");

        } else if (op.equals("3")) {
            System.out.print("Clave Curso: "); String clave = scanner.nextLine();
            Curso c = pCursos.buscarCurso(clave);
            if (c != null) {
                System.out.print("¿Cuántos mostrar?: ");
                int n = Integer.parseInt(scanner.nextLine());
                // Asume que el método devuelve una lista o imprime
                System.out.println("Primeros " + n + " en espera:");
                pInscripciones.mostrarListaEspera(c, n); 
            }
        }
    }

    private static void menuCalificaciones() {
        System.out.println("\n--- CALIFICACIONES ---");
        System.out.println("1. Solicitar calificación (Encolar)");
        System.out.println("2. Procesar siguiente solicitud");
        System.out.print("Opción: ");
        String op = scanner.nextLine();

        if (op.equals("1")) {
            System.out.print("Matrícula Estudiante: "); String mat = scanner.nextLine();
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e != null) {
                System.out.print("Calificación: ");
                double calif = Double.parseDouble(scanner.nextLine());
                pCalificaciones.enviarSolicitudCalificacion(new Accion(Accion.Tipo.CALIFICACION, e, calif));
                System.out.println(">> Solicitud enviada a la cola.");
            } else {
                System.out.println(">> Estudiante no encontrado.");
            }

        } else if (op.equals("2")) {
            Accion procesada = pCalificaciones.procesarSiguienteSolicitud();
            if (procesada != null) {
                pAcciones.registrarAccion(procesada); // Guardar para deshacer
                Estudiante e = (Estudiante) procesada.getObjeto();
                System.out.println(">> Calificación aplicada a: " + e.getNombreCompleto());
            } else {
                System.out.println(">> No hay solicitudes pendientes.");
            }
        }
    }

    private static void deshacerUltimaAccion() {
        Accion ultima = pAcciones.deshacerUltimaAccion();
        if (ultima == null) {
            System.out.println(">> No hay acciones para deshacer.");
            return;
        }

        System.out.println(">> Deshaciendo acción: " + ultima.getTipo());
        switch (ultima.getTipo()) {
            case REGISTRO_ESTUDIANTE:
                Estudiante e = (Estudiante) ultima.getObjeto();
                pEstudiantes.eliminarEstudiante(e.getMatricula());
                System.out.println(">> Registro eliminado.");
                break;

            case CALIFICACION:
                Estudiante estC = (Estudiante) ultima.getObjeto();
                estC.eliminarUltimaCalificacion();
                System.out.println(">> Calificación eliminada.");
                break;

            case INSCRIPCION_CURSO:
                Estudiante estI = (Estudiante) ultima.getObjeto();
                Curso cursoI = (Curso) ultima.getInfoAdicional();
                // Intenta quitar de inscritos, si falla, intenta quitar de espera
                if (cursoI.getInscritos().remove(estI)) {
                     System.out.println(">> Inscripción eliminada.");
                } else if (cursoI.getListaEspera().remove(estI)) {
                     System.out.println(">> Eliminado de lista de espera.");
                }
                break;
                
            default:
                System.out.println(">> Tipo de acción no soportado para deshacer.");
        }
    }

    private static void menuReportes() {
        System.out.println("\n--- REPORTES ---");
        System.out.println("1. Listar estudiantes por promedio (AVL)");
        System.out.println("2. Rotar rol de tutor/líder");
        System.out.print("Opción: ");
        String op = scanner.nextLine();

        if (op.equals("1")) {
            List<Estudiante> lista = pEstudiantes.listarEstudiantes();
            if (lista.isEmpty()) {
                System.out.println(">> No hay estudiantes registrados.");
                return;
            }
            
            AVL<ParPromedio> avl = new AVL<>();
            for (Estudiante e : lista) {
                avl.insert(new ParPromedio(e));
            }
            System.out.println("--- Estudiantes ordenados por Promedio ---");
            avl.inOrder(); // Imprime en orden ascendente

        } else if (op.equals("2")) {
            System.out.print("Clave Curso: "); String clave = scanner.nextLine();
            Curso c = pCursos.buscarCurso(clave);
            if (c != null) {
                Estudiante lider = pInscripciones.rotarRol(c);
                if (lider != null) System.out.println(">> Nuevo Líder/Tutor: " + lider.getNombreCompleto());
                else System.out.println(">> No hay inscritos para asignar rol.");
            } else {
                System.out.println(">> Curso no encontrado.");
            }
        }
    }

    // Clase auxiliar interna para manejar el ordenamiento en el AVL
    static class ParPromedio implements Comparable<ParPromedio> {
        Estudiante estudiante;
        double promedio;

        public ParPromedio(Estudiante e) {
            this.estudiante = e;
            this.promedio = e.promedio();
        }

        @Override
        public int compareTo(ParPromedio o) {
            if (this.promedio < o.promedio) return -1;
            if (this.promedio > o.promedio) return 1;
            // Desempate por matrícula para que el AVL acepte promedios iguales
            return this.estudiante.compareTo(o.estudiante);
        }

        @Override
        public String toString() {
            return "Prom: " + String.format("%.2f", promedio) + " | " + estudiante.getNombreCompleto();
        }
    }
}
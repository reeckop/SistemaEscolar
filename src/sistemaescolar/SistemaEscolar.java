package sistemaescolar;

import entidades.Accion;
import entidades.Curso;
import entidades.Estudiante;
import estructuras.*;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author valeria & Ricardo
 */
public class SistemaEscolar {
    private static BST<Estudiante> bstEstudiantes = new BST<>();
    private static Diccionario<String, Curso> diccionarioCursos = new Diccionario<>(20);
    private static Cola<Accion> colaSolicitudes = new Cola<>();
    private static Pila<Accion> pilaAcciones = new Pila<>(); // [Requerimiento PDF 9] Pila Deshacer
    
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                ejecutarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        } while (opcion != 7);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- SISTEMA ESCOLAR ITSON ---");
        System.out.println("1. Estudiantes (Registrar / Buscar)");
        System.out.println("2. Cursos (Agregar / Eliminar / Listar)");
        System.out.println("3. Inscripciones (Inscribir / Listar Inscritos / Lista Espera)");
        System.out.println("4. Calificaciones (Solicitar / Procesar)");
        System.out.println("5. Acciones (Deshacer última acción)");
        System.out.println("6. Reportes (Por Promedio / Rotar Roles)");
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
            case 7: System.out.println("Saliendo del sistema..."); break;
            default: System.out.println("Opción no válida.");
        }
    }

    private static void menuEstudiantes() {
        System.out.println("1.1. Registrar estudiante");
        System.out.println("1.2. Buscar estudiante por matrícula");
        System.out.print("Opción: ");
        String op = scanner.nextLine();

        if (op.equals("1.1")) {
            System.out.print("Matrícula: "); String mat = scanner.nextLine();
            System.out.print("Nombre: "); String nom = scanner.nextLine();
            System.out.print("Teléfono: "); String tel = scanner.nextLine();
            System.out.print("Correo: "); String mail = scanner.nextLine();
            System.out.print("Dirección: "); String dir = scanner.nextLine();

            Estudiante nuevo = new Estudiante(mat, nom, tel, mail, dir);
            bstEstudiantes.insert(nuevo);
            
            // Registrar en pila de deshacer
            pilaAcciones.push(new Accion(Accion.Tipo.REGISTRO_ESTUDIANTE, nuevo, null));
            System.out.println("Estudiante registrado exitosamente.");

        } else if (op.equals("1.2")) {
            System.out.print("Ingrese matrícula a buscar: ");
            String mat = scanner.nextLine();
            Estudiante dummy = new Estudiante(mat, "", "", "", "");
            Estudiante encontrado = bstEstudiantes.search(dummy);

            if (encontrado != null) {
                System.out.println("Estudiante encontrado: " + encontrado);
            } else {
                System.out.println("Estudiante no encontrado.");
            }
        }
    }

    private static void menuCursos() {
        System.out.println("2.1. Agregar curso");
        System.out.println("2.2. Eliminar curso");
        System.out.println("2.3. Listar cursos");
        System.out.print("Opción: ");
        String op = scanner.nextLine();

        if (op.equals("2.1")) {
            System.out.print("Clave del curso: "); String clave = scanner.nextLine();
            System.out.print("Nombre del curso: "); String nombre = scanner.nextLine();
            System.out.print("Capacidad máxima: "); int cap = Integer.parseInt(scanner.nextLine());
            
            Curso nuevo = new Curso(clave, nombre, cap);
            diccionarioCursos.put(clave, nuevo);
            System.out.println("Curso agregado.");

        } else if (op.equals("2.2")) {
            System.out.print("Clave del curso a eliminar: "); String clave = scanner.nextLine();
            if (diccionarioCursos.remove(clave)) {
                System.out.println("Curso eliminado.");
            } else {
                System.out.println("Curso no encontrado.");
            }

        } else if (op.equals("2.3")) {
            List<Curso> lista = diccionarioCursos.values();
            if (lista.isEmpty()) System.out.println("No hay cursos registrados.");
            for (Curso c : lista) {
                System.out.println(c);
            }
        }
    }

    private static void menuInscripciones() {
        System.out.println("3.1. Inscribir estudiante");
        System.out.println("3.2. Listar inscritos de un curso");
        System.out.println("3.3. Ver lista de espera");
        System.out.print("Opción: ");
        String op = scanner.nextLine();

        if (op.equals("3.1")) {
            System.out.print("Matrícula del estudiante: ");
            String mat = scanner.nextLine();
            Estudiante est = bstEstudiantes.search(new Estudiante(mat, "", "", "", ""));
            
            if (est == null) {
                System.out.println("Estudiante no existe.");
                return;
            }

            System.out.print("Clave del curso: ");
            String clave = scanner.nextLine();
            Curso curso = diccionarioCursos.get(clave);

            if (curso == null) {
                System.out.println("Curso no existe.");
                return;
            }

            curso.agregarInscrito(est);
            System.out.println("Proceso de inscripción realizado.");
            
            // Guardar acción para deshacer
            pilaAcciones.push(new Accion(Accion.Tipo.INSCRIPCION_CURSO, est, curso));

        } else if (op.equals("3.2")) {
            System.out.print("Clave del curso: ");
            String clave = scanner.nextLine();
            Curso curso = diccionarioCursos.get(clave);
            if (curso != null) {
                System.out.println("Inscritos: " + curso.getInscritos());
            }

        } else if (op.equals("3.3")) {
            System.out.print("Clave del curso: ");
            String clave = scanner.nextLine();
            Curso curso = diccionarioCursos.get(clave);
            if (curso != null) {
                System.out.print("Cuantos alumnos de la espera ver?: ");
                int n = Integer.parseInt(scanner.nextLine());
                curso.recorrerEsperaN(n); // Asume que imprime internamente o devuelve lista
            }
        }
    }

    private static void menuCalificaciones() {
        System.out.println("4.1. Enviar solicitud de calificación");
        System.out.println("4.2. Procesar siguiente solicitud");
        System.out.print("Opción: ");
        String op = scanner.nextLine();

        if (op.equals("4.1")) {
            System.out.print("Matrícula estudiante: ");
            String mat = scanner.nextLine();
            Estudiante est = bstEstudiantes.search(new Estudiante(mat, "", "", "", ""));
            
            if (est != null) {
                System.out.print("Calificación a agregar: ");
                double calif = Double.parseDouble(scanner.nextLine());
                
                // Creamos una acción pero NO la ejecutamos aun, va a la cola
                Accion solicitud = new Accion(Accion.Tipo.CALIFICACION, est, calif);
                colaSolicitudes.enqueue(solicitud);
                System.out.println("Solicitud encolada.");
            } else {
                System.out.println("Estudiante no encontrado.");
            }

        } else if (op.equals("4.2")) {
            if (colaSolicitudes.isEmpty()) {
                System.out.println("No hay solicitudes pendientes.");
                return;
            }

            Accion solicitud = colaSolicitudes.dequeue();
            Estudiante est = (Estudiante) solicitud.getObjeto();
            Double calif = (Double) solicitud.getInfoAdicional();
            
            est.agregarCalificacion(calif);
            System.out.println("Calificación " + calif + " agregada a " + est.getNombreCompleto());

            pilaAcciones.push(solicitud);
        }
    }

    private static void deshacerUltimaAccion() {
        if (pilaAcciones.isEmpty()) {
            System.out.println("No hay acciones para deshacer.");
            return;
        }

        Accion ultima = pilaAcciones.pop();
        System.out.println("Deshaciendo: " + ultima.getTipo());

        switch (ultima.getTipo()) {
            case REGISTRO_ESTUDIANTE:
                Estudiante e = (Estudiante) ultima.getObjeto();
                bstEstudiantes.delete(e);
                System.out.println("Estudiante " + e.getMatricula() + " eliminado.");
                break;
                
            case INSCRIPCION_CURSO:
                Estudiante est = (Estudiante) ultima.getObjeto();
                Curso cur = (Curso) ultima.getInfoAdicional();

                if(cur.getInscritos().remove(est)) {
                    System.out.println("Inscripción eliminada de " + cur.getNombre());
                } else {
                    cur.getListaEspera().remove(est);
                    System.out.println("Removido de lista de espera.");
                }
                break;

            case CALIFICACION:
                Estudiante estCal = (Estudiante) ultima.getObjeto();
                estCal.eliminarUltimaCalificacion(); // Necesita método en Estudiante
                System.out.println("Última calificación eliminada.");
                break;
                
            default:
                System.out.println("Tipo de acción no soportado para deshacer.");
        }
    }

    private static void menuReportes() {
        System.out.println("6.1. Listar estudiantes por promedio (AVL)");
        System.out.println("6.2. Rotar rol de tutor/líder");
        System.out.print("Opción: ");
        String op = scanner.nextLine();

        if (op.equals("6.1")) {
            AVL<ParPromedio> avlReporte = new AVL<>();
            List<Estudiante> todos = bstEstudiantes.getInOrderList();
            
            for (Estudiante e : todos) {
                avlReporte.insert(new ParPromedio(e));
            }
            
            System.out.println("\n--- Reporte por Promedio Ascendente ---");
            avlReporte.inOrder(); // Imprime in-order del AVL

        } else if (op.equals("6.2")) {
            System.out.print("Clave del curso: ");
            String clave = scanner.nextLine();
            Curso curso = diccionarioCursos.get(clave);
            
            if (curso != null) {
                if (curso.getRoles().isEmpty() && !curso.getInscritos().isEmpty()) {
                    for(int i=0; i<curso.getInscritos().size(); i++) {
                        curso.getRoles().addLast(curso.getInscritos().get(i));
                    }
                }
                
                Estudiante lider = curso.siguienteRol();
                if (lider != null) {
                    System.out.println("Nuevo Líder/Tutor asignado: " + lider.getNombreCompleto());
                } else {
                    System.out.println("No hay estudiantes para asignar roles.");
                }
            } else {
                System.out.println("Curso no encontrado.");
            }
        }
    }

    // Clase auxiliar para manejar el ordenamiento por promedio en el AVL
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
            // Si promedios son iguales, desempatar por matrícula para no perder datos en el AVL
            return this.estudiante.compareTo(o.estudiante);
        }

        @Override
        public String toString() {
            return "Promedio: " + String.format("%.2f", promedio) + " | " + estudiante.getNombreCompleto();
        }
    }
}
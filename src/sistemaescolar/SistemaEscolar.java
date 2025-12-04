package sistemaescolar;

import entidades.Accion;
import entidades.Curso;
import entidades.Estudiante;
import java.util.List;
import java.util.Scanner;
import persistencia.*;
import estructuras.AVL;

public class SistemaEscolar {

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
                opcion = Integer.parseInt(scanner.nextLine());
                ejecutarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (opcion != 7);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- SISTEMA ESCOLAR ITSON ---");
        System.out.println("1. Estudiantes");
        System.out.println("2. Cursos");
        System.out.println("3. Inscripciones");
        System.out.println("4. Calificaciones");
        System.out.println("5. Acciones (Deshacer)");
        System.out.println("6. Reportes");
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
            case 7: System.out.println("Saliendo..."); break;
            default: System.out.println("Opción inválida.");
        }
    }

    private static void menuEstudiantes() {
        System.out.println("1.1. Registrar / 1.2. Buscar");
        String op = scanner.nextLine();
        
        if (op.equals("1.1")) {
            System.out.print("Matrícula: "); String mat = scanner.nextLine();
            System.out.print("Nombre: "); String nom = scanner.nextLine();
            System.out.print("Teléfono: "); String tel = scanner.nextLine();
            System.out.print("Correo: "); String mail = scanner.nextLine();
            System.out.print("Dirección: "); String dir = scanner.nextLine();
            
            Estudiante nuevo = new Estudiante(mat, nom, tel, mail, dir);
            
            pEstudiantes.agregarEstudiante(nuevo);
            pAcciones.registrarAccion(new Accion(Accion.Tipo.REGISTRO_ESTUDIANTE, nuevo, null));
            System.out.println("Registrado.");
            
        } else if (op.equals("1.2")) {
            System.out.print("Matrícula: "); String mat = scanner.nextLine();
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            System.out.println(e != null ? e : "No encontrado.");
        }
    }

    private static void menuCursos() {
        System.out.println("2.1. Agregar / 2.2. Eliminar / 2.3. Listar");
        String op = scanner.nextLine();
        
        if (op.equals("2.1")) {
            System.out.print("Clave: "); String clave = scanner.nextLine();
            System.out.print("Nombre: "); String nom = scanner.nextLine();
            System.out.print("Capacidad: "); int cap = Integer.parseInt(scanner.nextLine());
            
            pCursos.agregarCurso(new Curso(clave, nom, cap));
            System.out.println("Curso agregado.");
            
        } else if (op.equals("2.2")) {
            System.out.print("Clave: "); String clave = scanner.nextLine();
            pCursos.eliminarCurso(clave);
            System.out.println("Curso eliminado (si existía).");
            
        } else if (op.equals("2.3")) {
            List<Curso> lista = pCursos.listarCursos();
            for (Curso c : lista) System.out.println(c);
        }
    }

    private static void menuInscripciones() {
        System.out.println("3.1. Inscribir / 3.2. Ver Inscritos / 3.3. Ver Espera");
        String op = scanner.nextLine();
        
        if (op.equals("3.1")) {
            System.out.print("Matrícula: "); String mat = scanner.nextLine();
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            System.out.print("Clave Curso: "); String clave = scanner.nextLine();
            Curso c = pCursos.buscarCurso(clave);
            
            if (e != null && c != null) {
                pInscripciones.inscribirEstudiante(e, c);
                pAcciones.registrarAccion(new Accion(Accion.Tipo.INSCRIPCION_CURSO, e, c));
                System.out.println("Procesado.");
            }
        } 
    }

    private static void menuCalificaciones() {
        System.out.println("4.1. Solicitar / 4.2. Procesar");
        String op = scanner.nextLine();
        
        if (op.equals("4.1")) {
            System.out.print("Matrícula: "); String mat = scanner.nextLine();
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e != null) {
                System.out.print("Calif: "); double cal = Double.parseDouble(scanner.nextLine());
                pCalificaciones.enviarSolicitudCalificacion(new Accion(Accion.Tipo.CALIFICACION, e, cal));
                System.out.println("Encolado.");
            }
        } else if (op.equals("4.2")) {
            Accion procesada = pCalificaciones.procesarSiguienteSolicitud();
            if (procesada != null) {
                pAcciones.registrarAccion(procesada); // Registrar para deshacer
                System.out.println("Solicitud procesada.");
            } else {
                System.out.println("No hay solicitudes.");
            }
        }
    }

    private static void deshacerUltimaAccion() {
        Accion a = pAcciones.deshacerUltimaAccion();
        if (a == null) {
            System.out.println("Nada que deshacer.");
            return;
        }
        System.out.println("Deshaciendo: " + a.getTipo());
        
        switch(a.getTipo()) {
            case REGISTRO_ESTUDIANTE:
                Estudiante e = (Estudiante) a.getObjeto();
                pEstudiantes.eliminarEstudiante(e.getMatricula());
                break;
            case CALIFICACION:
                Estudiante ec = (Estudiante) a.getObjeto();
                ec.eliminarUltimaCalificacion();
                break;
            case INSCRIPCION_CURSO:
                break;
        }
    }
    
    private static void menuReportes() {
        System.out.println("6.1. AVL Promedio / 6.2. Rotar Rol");
        String op = scanner.nextLine();
        if(op.equals("6.1")) {
            List<Estudiante> lista = pEstudiantes.listarEstudiantes();
            AVL<ParPromedio> avl = new AVL<>();
            for(Estudiante e : lista) avl.insert(new ParPromedio(e));
            avl.inOrder();
        } else if (op.equals("6.2")) {
            System.out.print("Clave Curso: "); String clave = scanner.nextLine();
            Curso c = pCursos.buscarCurso(clave);
            if(c != null) {
                Estudiante l = pInscripciones.rotarRol(c);
                System.out.println("Nuevo rol: " + (l!=null ? l.getNombreCompleto() : "Nadie"));
            }
        }
    }
    
    static class ParPromedio implements Comparable<ParPromedio> {
        Estudiante estudiante;
        double promedio;
        public ParPromedio(Estudiante e) { this.estudiante = e; this.promedio = e.promedio(); }
        @Override
        public int compareTo(ParPromedio o) {
            if (this.promedio < o.promedio) return -1;
            if (this.promedio > o.promedio) return 1;
            return this.estudiante.compareTo(o.estudiante);
        }
        @Override public String toString() { return "Promedio: " + String.format("%.2f", promedio) + " | " + estudiante.getNombreCompleto(); }
    }
}
package sistemaescolar;

import entidades.Accion;
import entidades.Curso;
import entidades.Estudiante;
import estructuras.AVL;
import persistencia.*;
import java.util.List;
import java.util.Scanner;

/**
 * @author valeria & Ricardo
 */
public class Sistema {
    // Persistencia estática
    private static final PersistenciaEstudiantes pEstudiantes = new PersistenciaEstudiantes();
    private static final PersistenciaCursos pCursos = new PersistenciaCursos();
    private static final PersistenciaCalificaciones pCalificaciones = new PersistenciaCalificaciones();
    private static final PersistenciaAcciones pAcciones = new PersistenciaAcciones();
    private static final PersistenciaInscripciones pInscripciones = new PersistenciaInscripciones();
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Ingrese la opción: ");
            ejecutarOpcion(opcion);
            if(opcion != 7) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        } while (opcion != 7);
    }

    // Método auxiliar para leer enteros sin errores de buffer
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(">> Error: Debe ingresar un número válido.");
            return -1;
        }
    }
    
    // Método auxiliar para leer texto
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n========================================");
        System.out.println("        SISTEMA ESCOLAR - MENU          ");
        System.out.println("========================================");
        System.out.println("1. Estudiantes (Registrar / Buscar)");
        System.out.println("2. Cursos (Agregar / Eliminar / Listar)");
        System.out.println("3. Inscripciones (Inscribir / Ver)");
        System.out.println("4. Calificaciones (Solicitar / Procesar)");
        System.out.println("5. Acciones (Deshacer)");
        System.out.println("6. Reportes (Promedios / Roles)");
        System.out.println("7. Salir");
    }

    private static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> menuEstudiantes();
            case 2 -> menuCursos();
            case 3 -> menuInscripciones();
            case 4 -> menuCalificaciones();
            case 5 -> deshacerUltimaAccion();
            case 6 -> menuReportes();
            case 7 -> System.out.println(">> Saliendo del sistema...");
            default -> System.out.println(">> Opción no válida.");
        }
    }

    // --- MÓDULO ESTUDIANTES ---
    private static void menuEstudiantes() {
        System.out.println("\n--- GESTIÓN ESTUDIANTES ---");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Buscar estudiante");
        int op = leerEntero("Opción: ");

        if (op == 1) {
            String mat = leerTexto("Matrícula: ");
            
            if (pEstudiantes.buscarEstudiante(mat) != null) {
                System.out.println(">> Error: Ya existe un estudiante con esta matrícula.");
                return;
            }

            String nom = leerTexto("Nombre Completo: ");
            String tel = leerTexto("Teléfono: ");
            String mail = leerTexto("Correo: ");
            String dir = leerTexto("Dirección: ");

            Estudiante nuevo = new Estudiante(mat, nom, tel, mail, dir);
            pEstudiantes.agregarEstudiante(nuevo);
            
            pAcciones.registrarAccion(new Accion(Accion.Tipo.REGISTRO_ESTUDIANTE, nuevo, null));
            System.out.println(">> Estudiante registrado exitosamente.");

        } else if (op == 2) {
            String mat = leerTexto("Ingrese matrícula a buscar: ");
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e != null) {
                System.out.println(">> Estudiante encontrado: " + e);
            } else {
                System.out.println(">> Estudiante no encontrado.");
            }
        }
    }

    // --- MÓDULO CURSOS ---
    private static void menuCursos() {
        System.out.println("\n--- GESTIÓN DE CURSOS ---");
        System.out.println("1. Agregar curso");
        System.out.println("2. Eliminar curso");
        System.out.println("3. Listar cursos");
        int op = leerEntero("Opción: ");

        if (op == 1) {
            String clave = leerTexto("Clave: ");
            String nom = leerTexto("Nombre: ");
            int cap = leerEntero("Capacidad: ");

            Curso nuevo = new Curso(clave, nom, cap);
            pCursos.agregarCurso(nuevo);
            System.out.println(">> Curso agregado.");

        } else if (op == 2) {
            String clave = leerTexto("Clave del curso a eliminar: ");
            Curso eliminado = pCursos.eliminarCurso(clave);
            if (eliminado != null) {
                System.out.println(">> Curso '" + eliminado.getNombre() + "' eliminado.");
            } else {
                System.out.println(">> Curso no encontrado.");
            }

        } else if (op == 3) {
            List<Curso> lista = pCursos.listarCursos();
            if (lista.isEmpty()) System.out.println(">> No hay cursos registrados.");
            else lista.forEach(System.out::println);
        }
    }

    // --- MÓDULO INSCRIPCIONES ---
    private static void menuInscripciones() {
        System.out.println("\n--- INSCRIPCIONES ---");
        System.out.println("1. Inscribir estudiante");
        System.out.println("2. Ver inscritos de un curso");
        System.out.println("3. Ver lista de espera");
        int op = leerEntero("Opción: ");

        if (op == 1) {
            String mat = leerTexto("Matrícula Estudiante: ");
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e == null) { System.out.println(">> Error: Estudiante no existe."); return; }

            String clave = leerTexto("Clave Curso: ");
            Curso c = pCursos.buscarCurso(clave);
            if (c == null) { System.out.println(">> Error: Curso no existe."); return; }

            String resultado = pInscripciones.inscribir(mat, clave); // Usar lógica del control
            System.out.println(">> " + resultado);

            // Solo registramos la acción si fue exitosa o lista de espera
            if(resultado.contains("exitosa") || resultado.contains("Espera")) {
                 pAcciones.registrarAccion(new Accion(Accion.Tipo.INSCRIPCION_CURSO, e, c));
            }

        } else if (op == 2) {
            String clave = leerTexto("Clave Curso: ");
            List<String> inscritos = pInscripciones.obtenerListaInscritos(clave);
            if(inscritos.isEmpty()) System.out.println(">> No hay inscritos o curso no existe.");
            else inscritos.forEach(System.out::println);

        } else if (op == 3) {
            // Implementar visualización de lista de espera si se requiere
             System.out.println(">> Funcionalidad en construcción (depende de ListaDobleCircular).");
        }
    }

    // --- MÓDULO CALIFICACIONES ---
    private static void menuCalificaciones() {
        System.out.println("\n--- CALIFICACIONES ---");
        System.out.println("1. Solicitar calificación (Encolar)");
        System.out.println("2. Procesar siguiente solicitud");
        int op = leerEntero("Opción: ");

        if (op == 1) {
            String mat = leerTexto("Matrícula Estudiante: ");
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e != null) {
                System.out.print("Calificación: ");
                try {
                     double calif = Double.parseDouble(scanner.nextLine());
                     pCalificaciones.enviarSolicitudCalificacion(new Accion(Accion.Tipo.CALIFICACION, e, calif));
                     System.out.println(">> Solicitud enviada a la cola.");
                } catch(Exception ex) { System.out.println(">> Error al leer calificación."); }
            } else {
                System.out.println(">> Estudiante no encontrado.");
            }

        } else if (op == 2) {
            Accion procesada = pCalificaciones.procesarSiguienteSolicitud();
            if (procesada != null) {
                pAcciones.registrarAccion(procesada); 
                Estudiante e = (Estudiante) procesada.getObjeto();
                System.out.println(">> Calificación aplicada a: " + e.getNombreCompleto());
            } else {
                System.out.println(">> No hay solicitudes pendientes.");
            }
        }
    }

    // --- MÓDULO DESHACER ---
    private static void deshacerUltimaAccion() {
        Accion ultima = pAcciones.deshacerUltimaAccion();
        if (ultima == null) {
            System.out.println(">> No hay acciones para deshacer.");
            return;
        }

        System.out.println(">> Deshaciendo acción: " + ultima.getTipo());
        switch (ultima.getTipo()) {
            case REGISTRO_ESTUDIANTE -> {
                Estudiante e = (Estudiante) ultima.getObjeto();
                pEstudiantes.eliminarEstudiante(e.getMatricula());
                System.out.println(">> Registro eliminado.");
            }
            case CALIFICACION -> {
                Estudiante estC = (Estudiante) ultima.getObjeto();
                estC.eliminarUltimaCalificacion();
                System.out.println(">> Calificación eliminada.");
            }
            case INSCRIPCION_CURSO -> {
                Estudiante estI = (Estudiante) ultima.getObjeto();
                Curso cursoI = (Curso) ultima.getInfoAdicional();
                pInscripciones.cancelarInscripcion(estI.getMatricula(), cursoI.getClave());
                System.out.println(">> Inscripción cancelada.");
            }
            default -> System.out.println(">> Acción no soportada para deshacer.");
        }
    }

    // --- MÓDULO REPORTES ---
    private static void menuReportes() {
        System.out.println("\n--- REPORTES ---");
        System.out.println("1. Listar estudiantes por promedio (AVL)");
        int op = leerEntero("Opción: ");

        if (op == 1) {
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
            avl.inOrder(); 
        }
    }

    // Clase auxiliar para AVL
    static class ParPromedio implements Comparable<ParPromedio> {
        Estudiante estudiante;
        double promedio;

        public ParPromedio(Estudiante e) {
            this.estudiante = e;
            this.promedio = e.promedio();
        }

        @Override
        public int compareTo(ParPromedio o) {
            // Ordenar por promedio
            int cmp = Double.compare(this.promedio, o.promedio);
            if (cmp != 0) return cmp;
            // Desempate por matrícula
            return this.estudiante.getMatricula().compareTo(o.estudiante.getMatricula());
        }

        @Override
        public String toString() {
            return String.format("Prom: %.2f | %s", promedio, estudiante.getNombreCompleto());
        }
    }
}
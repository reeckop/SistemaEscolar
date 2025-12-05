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
 * @author Ricardo
 */
public class SistemaConsola {
    
    // Instancias de persistencia (Controladores)
    private static final PersistenciaEstudiantes pEstudiantes = new PersistenciaEstudiantes();
    private static final PersistenciaCursos pCursos = new PersistenciaCursos();
    private static final PersistenciaCalificaciones pCalificaciones = new PersistenciaCalificaciones();
    private static final PersistenciaAcciones pAcciones = new PersistenciaAcciones();
    
    // Inyectamos las dependencias necesarias en Inscripciones
    private static final PersistenciaInscripciones pInscripciones = new PersistenciaInscripciones(pCursos, pEstudiantes);
    
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

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            String input = scanner.nextLine().trim();
            if(input.isEmpty()) return -1;
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(">> Error: Debe ingresar un número válido.");
            return -1;
        }
    }
    
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("----------------------------------------");
        System.out.println("              SISTEMA ESCOLAR");
        System.out.println("----------------------------------------");
        System.out.println("1. Estudiantes (Registrar / Buscar)");
        System.out.println("2. Cursos (Agregar / Eliminar / Listar)");
        System.out.println("3. Inscripciones (Inscribir / Ver / Espera)");
        System.out.println("4. Calificaciones (Cola de solicitudes)");
        System.out.println("5. Acciones (Deshacer - Pila)");
        System.out.println("6. Reportes (Promedios AVL / Roles)");
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

    // --- 1. ESTUDIANTES (BST) ---
    private static void menuEstudiantes() {
        System.out.println("\n--- GESTIÓN ESTUDIANTES ---");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Buscar estudiante por matrícula");
        int op = leerEntero("Opción: ");

        if (op == 1) {
            String mat = leerTexto("Matrícula: ");
            if (pEstudiantes.buscarEstudiante(mat) != null) {
                System.out.println(">> Error: Ya existe esa matrícula.");
                return;
            }
            String nom = leerTexto("Nombre Completo: ");
            String tel = leerTexto("Teléfono: ");
            String mail = leerTexto("Correo: ");
            String dir = leerTexto("Dirección: ");

            Estudiante nuevo = new Estudiante(mat, nom, tel, mail, dir);
            pEstudiantes.agregarEstudiante(nuevo);
            
            // Registrar en Pila de Acciones
            pAcciones.registrarAccion(new Accion(Accion.Tipo.REGISTRO_ESTUDIANTE, nuevo, null));
            System.out.println(">> Estudiante registrado en BST.");

        } else if (op == 2) {
            String mat = leerTexto("Ingrese matrícula: ");
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e != null) {
                System.out.println(">> Encontrado: " + e);
            } else {
                System.out.println(">> Estudiante no encontrado.");
            }
        }
    }

    // --- 2. CURSOS (Diccionario) ---
    private static void menuCursos() {
        System.out.println("\n--- GESTIÓN DE CURSOS ---");
        System.out.println("1. Agregar curso");
        System.out.println("2. Eliminar curso");
        System.out.println("3. Listar cursos");
        int op = leerEntero("Opción: ");

        if (op == 1) {
            String clave = leerTexto("Clave: ");
            if(pCursos.buscarCurso(clave) != null){
                System.out.println(">> Error: Clave ya existe.");
                return;
            }
            String nom = leerTexto("Nombre: ");
            int cap = leerEntero("Capacidad: ");

            Curso nuevo = new Curso(clave, nom, cap);
            pCursos.agregarCurso(nuevo);
            System.out.println(">> Curso agregado al Diccionario.");

        } else if (op == 2) {
            String clave = leerTexto("Clave a eliminar: ");
            Curso eliminado = pCursos.eliminarCurso(clave);
            if (eliminado != null) System.out.println(">> Curso eliminado.");
            else System.out.println(">> Curso no encontrado.");

        } else if (op == 3) {
            List<Curso> lista = pCursos.listarCursos();
            if (lista.isEmpty()) System.out.println(">> No hay cursos.");
            else lista.forEach(System.out::println);
        }
    }

    // --- 3. INSCRIPCIONES (Listas Enlazadas / Dobles) ---
    private static void menuInscripciones() {
        System.out.println("\n--- INSCRIPCIONES ---");
        System.out.println("1. Inscribir estudiante");
        System.out.println("2. Ver inscritos de un curso");
        System.out.println("3. Ver lista de espera (Primeros N)");
        int op = leerEntero("Opción: ");

        if (op == 1) {
            String mat = leerTexto("Matrícula Estudiante: ");
            String clave = leerTexto("Clave Curso: ");

            // Delegamos toda la validación a PersistenciaInscripciones
            String resultado = pInscripciones.inscribir(mat, clave); 
            System.out.println(">> " + resultado);

            // Si fue exitoso o espera, registramos acción para deshacer
            if(resultado.contains("exitosa") || resultado.contains("Espera")) {
                 Estudiante e = pEstudiantes.buscarEstudiante(mat);
                 Curso c = pCursos.buscarCurso(clave);
                 if(e != null && c != null) {
                    pAcciones.registrarAccion(new Accion(Accion.Tipo.INSCRIPCION_CURSO, e, c));
                 }
            }

        } else if (op == 2) {
            String clave = leerTexto("Clave Curso: ");
            List<String> inscritos = pInscripciones.obtenerListaInscritos(clave);
            if(inscritos.isEmpty()) System.out.println(">> Sin inscritos o curso no existe.");
            else inscritos.forEach(System.out::println);

        } else if (op == 3) {
            String clave = leerTexto("Clave Curso: ");
            int n = leerEntero("¿Cuántos mostrar?: ");
            pInscripciones.imprimirListaEspera(clave, n);
        }
    }

    // --- 4. CALIFICACIONES (Cola) ---
    private static void menuCalificaciones() {
        System.out.println("\n--- CALIFICACIONES ---");
        System.out.println("1. Enviar solicitud (Encolar)");
        System.out.println("2. Procesar siguiente (Desencolar)");
        int op = leerEntero("Opción: ");

        if (op == 1) {
            String mat = leerTexto("Matrícula: ");
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e == null) { System.out.println(">> Estudiante no encontrado."); return; }
            
            double calif = -1;
            try {
                 System.out.print("Calificación: ");
                 calif = Double.parseDouble(scanner.nextLine());
            } catch(Exception ex) { 
                System.out.println(">> Dato inválido."); return;
            }

            pCalificaciones.enviarSolicitudCalificacion(new Accion(Accion.Tipo.CALIFICACION, e, calif));
            System.out.println(">> Solicitud agregada a la COLA.");

        } else if (op == 2) {
            if(!pCalificaciones.haySolicitudes()) {
                System.out.println(">> La cola está vacía.");
                return;
            }
            Accion procesada = pCalificaciones.procesarSiguienteSolicitud();
            // Registramos la acción procesada en la pila para poder deshacerla luego
            pAcciones.registrarAccion(procesada); 
            Estudiante e = (Estudiante) procesada.getObjeto();
            System.out.println(">> Calificación procesada y asignada a: " + e.getNombreCompleto());
        }
    }

    // --- 5. DESHACER (Pila) ---
    private static void deshacerUltimaAccion() {
        Accion ultima = pAcciones.deshacerUltimaAccion();
        if (ultima == null) {
            System.out.println(">> La PILA de acciones está vacía.");
            return;
        }

        System.out.println(">> Deshaciendo: " + ultima.getTipo());
        switch (ultima.getTipo()) {
            case REGISTRO_ESTUDIANTE -> {
                Estudiante e = (Estudiante) ultima.getObjeto();
                pEstudiantes.eliminarEstudiante(e.getMatricula());
                System.out.println(">> Estudiante eliminado del BST.");
            }
            case CALIFICACION -> {
                Estudiante e = (Estudiante) ultima.getObjeto();
                e.eliminarUltimaCalificacion();
                System.out.println(">> Última calificación eliminada del vector.");
            }
            case INSCRIPCION_CURSO -> {
                Estudiante e = (Estudiante) ultima.getObjeto();
                Curso c = (Curso) ultima.getInfoAdicional();
                String res = pInscripciones.cancelarInscripcion(e.getMatricula(), c.getClave());
                System.out.println(">> " + res);
            }
            default -> System.out.println(">> Acción no reversible.");
        }
    }

    // --- 6. REPORTES (AVL y Roles) ---
    private static void menuReportes() {
        System.out.println("\n--- REPORTES ---");
        System.out.println("1. Listar estudiantes por Promedio (AVL)");
        System.out.println("2. Rotar Rol (Tutor/Líder)");
        int op = leerEntero("Opción: ");

        if (op == 1) {
            List<Estudiante> lista = pEstudiantes.listarEstudiantes(); // Obtiene in-order del BST
            if (lista.isEmpty()) {
                System.out.println(">> No hay estudiantes.");
                return;
            }
            
            // Construcción dinámica del AVL para el reporte
            AVL<ParPromedio> avl = new AVL<>();
            for (Estudiante e : lista) {
                avl.insert(new ParPromedio(e));
            }
            System.out.println("--- Ordenado por Promedio (AVL In-Order) ---");
            avl.inOrder(); // Imprime en consola

        } else if (op == 2) {
            String clave = leerTexto("Clave del curso: ");
            System.out.println(">> " + pInscripciones.rotarRol(clave));
        }
    }

    // Clase auxiliar wrapper para ordenar en el AVL
    static class ParPromedio implements Comparable<ParPromedio> {
        Estudiante estudiante;
        double promedio;

        public ParPromedio(Estudiante e) {
            this.estudiante = e;
            this.promedio = e.promedio();
        }

        @Override
        public int compareTo(ParPromedio o) {
            int cmp = Double.compare(this.promedio, o.promedio);
            // Si son iguales, desempatamos por matrícula para que el AVL acepte ambos
            if (cmp == 0) return this.estudiante.getMatricula().compareTo(o.estudiante.getMatricula());
            return cmp;
        }

        @Override
        public String toString() {
            return String.format("Prom: %5.2f | %s", promedio, estudiante.getNombreCompleto());
        }
    }
}
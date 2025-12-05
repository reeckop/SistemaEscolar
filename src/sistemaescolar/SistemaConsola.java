package sistemaescolar;

import GUI.MenuPrincipal;
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

    // Dependencias necesarias en Inscripciones
    private static final PersistenciaInscripciones pInscripciones = new PersistenciaInscripciones(pCursos, pEstudiantes);

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new MenuPrincipal().setVisible(true));

        mostrarMenuPrincipal();
        System.out.print("Ingrese la opcion: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        while (opcion != 7) {
            ejecutarOpcion(opcion);

            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();

            mostrarMenuPrincipal();
            System.out.print("Ingrese la opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("--------------------------");
        System.out.println("     SISTEMA ESCOLAR");
        System.out.println("--------------------------");
        System.out.println("1. Estudiantes");
        System.out.println("2. Cursos");
        System.out.println("3. Inscripciones");
        System.out.println("4. Calificaciones");
        System.out.println("5. Acciones");
        System.out.println("6. Reportes");
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
            case 7 -> System.out.println("Saliendo...");
            default -> System.out.println("Opcion invalida!");
        }
    }

    // 1. ESTUDIANTES (BST) ---
    private static void menuEstudiantes() {
        System.out.println("\n    ESTUDIANTES");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Buscar estudiante por matricula");
        System.out.print("Opcion: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            String mat = leerTexto("Matricula: ");
            if (pEstudiantes.buscarEstudiante(mat) != null) {
                System.out.println("ERROR: Ya existe esa matricula.");
                return;
            }
            String nom = leerTexto("Nombre Completo: ");
            String tel = leerTexto("Telefono: ");
            String mail = leerTexto("Correo: ");
            String dir = leerTexto("Direccion: ");

            Estudiante nuevo = new Estudiante(mat, nom, tel, mail, dir);
            pEstudiantes.agregarEstudiante(nuevo);

            // Registrar en Pila de Acciones
            pAcciones.registrarAccion(new Accion(Accion.Tipo.REGISTRO_ESTUDIANTE, nuevo, null));
            System.out.println("Estudiante registrado en BST!!!.");

        } else if (op == 2) {
            String mat = leerTexto("Ingrese matricula: ");
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e != null) {
                System.out.println("Encontrado!!!: " + e);
            } else {
                System.out.println(">> Estudiante no encontrado.");
            }
        }
    }

    private static void menuCursos() {
        System.out.println("\n    CURSOS");
        System.out.println("1. Agregar curso");
        System.out.println("2. Eliminar curso");
        System.out.println("3. Listar cursos");
        System.out.print("Opcion: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            String clave = leerTexto("Clave: ");
            if (pCursos.buscarCurso(clave) != null) {
                System.out.println(">> Error: Clave ya existe.");
                return;
            }
            String nom = leerTexto("Nombre: ");
            System.out.print("Capacidad: ");
            int cap = scanner.nextInt();
            scanner.nextLine();

            Curso nuevo = new Curso(clave, nom, cap);
            pCursos.agregarCurso(nuevo);
            System.out.println(">> Curso agregado al Diccionario.");

        } else if (op == 2) {
            String clave = leerTexto("Clave a eliminar: ");
            Curso eliminado = pCursos.eliminarCurso(clave);
            if (eliminado != null)
                System.out.println(">> Curso eliminado.");
            else
                System.out.println(">> Curso no encontrado.");

        } else if (op == 3) {
            List<Curso> lista = pCursos.listarCursos();
            if (lista.isEmpty())
                System.out.println(">> No hay cursos.");
            else
                lista.forEach(System.out::println);
        }
    }

    // --- 3. INSCRIPCIONES (Listas Enlazadas / Dobles) ---
    private static void menuInscripciones() {
        System.out.println("\n      INSCRIPCIONES");
        System.out.println("1. Inscribir estudiante");
        System.out.println("2. Ver inscritos de un curso");
        System.out.println("3. Ver lista de espera (Primeros N)");
        System.out.print("Opcion: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            String mat = leerTexto("Matricula Estudiante: ");
            String clave = leerTexto("Clave Curso: ");

            // Validacion a PersistenciaInscripciones
            String resultado = pInscripciones.inscribir(mat, clave);
            System.out.println("Resultado: " + resultado);

            // Si fue exitoso o espera, se registra accion para deshacer
            if (resultado.contains("exitosa") || resultado.contains("Espera")) {
                Estudiante e = pEstudiantes.buscarEstudiante(mat);
                Curso c = pCursos.buscarCurso(clave);
                if (e != null && c != null) {
                    pAcciones.registrarAccion(new Accion(Accion.Tipo.INSCRIPCION_CURSO, e, c));
                }
            }

        } else if (op == 2) {
            String clave = leerTexto("Clave Curso: ");
            List<String> inscritos = pInscripciones.obtenerListaInscritos(clave);
            if (inscritos.isEmpty())
                System.out.println(">> Sin inscritos o curso no existe.");
            else
                inscritos.forEach(System.out::println);

        } else if (op == 3) {
            String clave = leerTexto("Clave Curso: ");
            System.out.print("Cuantos mostrar?: ");
            int n = scanner.nextInt();
            scanner.nextLine();
            pInscripciones.imprimirListaEspera(clave, n);
        }
    }

    // CALIFICACIONES (Cola)
    private static void menuCalificaciones() {
        System.out.println("\nCALIFICACIONES");
        System.out.println("1. Enviar solicitud");
        System.out.println("2. Procesar siguiente");
        System.out.print("Opcion: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            String mat = leerTexto("Matricula: ");
            Estudiante e = pEstudiantes.buscarEstudiante(mat);
            if (e == null) {
                System.out.println(">> Estudiante no encontrado.");
                return;
            }

            double calif = -1;
            try {
                System.out.print("Calificacion: ");
                calif = Double.parseDouble(scanner.nextLine());
            } catch (Exception ex) {
                System.out.println(">> Dato invalido.");
                return;
            }

            pCalificaciones.enviarSolicitudCalificacion(new Accion(Accion.Tipo.CALIFICACION, e, calif));
            System.out.println(">> Solicitud agregada a la COLA.");

        } else if (op == 2) {
            if (!pCalificaciones.haySolicitudes()) {
                System.out.println(">> La cola esta vacia.");
                return;
            }
            Accion procesada = pCalificaciones.procesarSiguienteSolicitud();
            // Registramos la accion procesada en la pila para poder deshacerla luego
            pAcciones.registrarAccion(procesada);
            Estudiante e = (Estudiante) procesada.getObjeto();
            System.out.println(">> Calificacion procesada y asignada a: " + e.getNombreCompleto());
        }
    }

    // DESHACER (Pila)
    private static void deshacerUltimaAccion() {
        Accion ultima = pAcciones.deshacerUltimaAccion();
        if (ultima == null) {
            System.out.println(">> La PILA de acciones esta vacia.");
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
                System.out.println(">> Ultima calificacion eliminada del vector.");
            }
            case INSCRIPCION_CURSO -> {
                Estudiante e = (Estudiante) ultima.getObjeto();
                Curso c = (Curso) ultima.getInfoAdicional();
                String res = pInscripciones.cancelarInscripcion(e.getMatricula(), c.getClave());
                System.out.println(">> " + res);
            }
            default -> System.out.println(">> Accion no reversible.");
        }
    }

    // REPORTES (AVL y Roles)
    private static void menuReportes() {
        System.out.println("\n      REPORTES");
        System.out.println("1. Listar estudiantes por Promedio");
        System.out.println("2. Rotar Rol");
        System.out.print("Opcion: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            List<Estudiante> lista = pEstudiantes.listarEstudiantes(); // Obtiene in-order del BST
            if (lista.isEmpty()) {
                System.out.println(">> No hay estudiantes.");
                return;
            }

            // Construccion dinamica del AVL para el reporte
            AVL<ParPromedio> avl = new AVL<>();
            for (Estudiante e : lista) {
                avl.insert(new ParPromedio(e));
            }
            System.out.println("--- Ordenado por Promedio (AVL In-Order) ---");
            avl.inOrder();

        } else if (op == 2) {
            String clave = leerTexto("Clave del curso: ");
            System.out.println(">> " + pInscripciones.rotarRol(clave));
        }
    }

    public static class ParPromedio implements Comparable<ParPromedio> {
        Estudiante estudiante;
        double promedio;

        public ParPromedio(Estudiante e) {
            this.estudiante = e;
            this.promedio = e.promedio();
        }

        @Override
        public int compareTo(ParPromedio o) {
            int cmp = Double.compare(this.promedio, o.promedio);
            // Si son iguales, desempatamos por matricula para que el AVL acepte ambos
            if (cmp == 0)
                return this.estudiante.getMatricula().compareTo(o.estudiante.getMatricula());
            return cmp;
        }

        @Override
        public String toString() {
            return String.format("Prom: %5.2f | %s", promedio, estudiante.getNombreCompleto());
        }
    }
}
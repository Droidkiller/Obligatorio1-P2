/*AUTORES:
 * Francisco Bonanni
 * Aitana Álvarez
 */
package p2obligatorio1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Sistema {
    private ArrayList<Jugador> jugadores;
    
    public Sistema() {
        this.jugadores = new ArrayList<>();
    }
    
    public void comenzar() {
        boolean enEjecucion = true;
        Scanner in = new Scanner(System.in);
        
        while (enEjecucion) {
            mostrarMenu();
            System.out.println("Ingrese su opción: ");
            
            try {
                int opcion = in.nextInt();
            
                switch (opcion) {
                    case 1:
                        registrarJugador();
                        break;
                    case 2:
                        iniciarNuevaPartida();
                        break;
                    case 3:
                        continuarPartida();
                        break;
                    case 4:
                        mostrarRanking();
                        break;
                    case 5:
                        enEjecucion = false;
                        System.out.println("Programa terminado. ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese un número del 1 al 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número del 1 al 5.\n");
                in.nextLine();
            }
            
        }
    }
    
    private void mostrarMenu() {
        System.out.println("\n==============================================");
        System.out.println("            Juego Medio Tateti - Menú           ");
        System.out.println("    AUTORES: Francisco Bonanni y Aitana Álvarez   ");
        System.out.println("==============================================");
        System.out.println("1 - Registrar un jugador");
        System.out.println("2 - Comienzo de partida común");
        System.out.println("3 - Continuación de partida");
        System.out.println("4 - Mostrar ranking e invictos");
        System.out.println("5 - Terminar el programa");
        System.out.println("==============================================");
    }

    // Opción 1
    private void registrarJugador() {
        System.out.println("\n--- REGISTRO DE JUGADOR ---");
        String nombre;
        int edad = 0;
        boolean esUnico;
        boolean edadValida;
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Ingrese nombre: ");
            nombre = in.nextLine().trim();
            esUnico = true;
            
            for (int i=0;i<jugadores.size() && esUnico;i++) {
                if (jugadores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                    System.out.println("Ese nombre ya existe. Por favor, ingrese uno diferente.");
                    esUnico = false;
                }
            }
        } while (!esUnico);
        
        do {
            System.out.print("Ingrese edad: ");
            edadValida = true;
            try {
                edad = in.nextInt();
                if (edad <= 0) {
                    System.out.println("La edad debe ser un número positivo.");
                    edadValida = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número entero para la edad.");
                edadValida = false;
                in.nextLine(); // clear invalid input
            }
        } while (!edadValida);
        in.nextLine();
        
        Jugador nuevoJugador = new Jugador(nombre, edad);
        jugadores.add(nuevoJugador);
        System.out.println("Jugador " + nombre + " registrado exitosamente.");
    }
    
    // Opción 2
    private void iniciarNuevaPartida() {
        if (jugadores.size() < 2) {
            System.out.println("Se necesitan al menos dos jugadores para iniciar una partida.");
            return;
        }

        System.out.println("\n--- INICIO DE PARTIDA ---");
        
        ArrayList<Jugador> listaJugadores = new ArrayList<>(jugadores);
        Collections.sort(listaJugadores, new CriterioAlfabetico());

        
        Jugador[] pareja = elegirJugadores(listaJugadores);
        Jugador jugadorBlanco = pareja[0];
        Jugador jugadorNegro = pareja[1];

        System.out.println("Partida iniciada:");
        System.out.println("Blanco: " + jugadorBlanco.getNombre() + " | Negro: " + jugadorNegro.getNombre());

        Partida partida = new Partida(jugadorBlanco, jugadorNegro);
        partida.iniciar();  
        
        Jugador ganador = partida.getGanador(); 
        boolean empate = partida.hayEmpate();    

        if (empate) {
            System.out.println("La partida terminó en empate.");
            jugadorBlanco.actualizarEstadisticas(false);
            jugadorNegro.actualizarEstadisticas(false);
        } else if (ganador != null) {
            System.out.println("¡El ganador es " + ganador.getNombre() + "!");
            ganador.actualizarEstadisticas(true);
            Jugador perdedor = (ganador == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
            perdedor.actualizarEstadisticas(false);
        }
    }
    
    private Jugador[] elegirJugadores(ArrayList<Jugador> listaJugadores) {
        Scanner in = new Scanner(System.in);
        Jugador jugadorBlanco = null;
        Jugador jugadorNegro = null;

        System.out.println("Lista de Jugadores:");
        for (int i = 0; i < listaJugadores.size(); i++) {
            System.out.println((i + 1) + ". " + listaJugadores.get(i).getNombre());
        }

        while (jugadorBlanco == null) {
            System.out.print("Seleccione el número para JUGADOR BLANCO (o): ");
            try {
                int index = in.nextInt();
                if (index >= 1 && index <= listaJugadores.size()) {
                    jugadorBlanco = listaJugadores.get(index - 1);
                } else {
                    System.out.println("Número no válido. Ingrese un número de la lista.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número válido.");
                in.nextLine(); // limpiar buffer
            }
        }

        while (jugadorNegro == null) {
            System.out.print("Seleccione el número para JUGADOR NEGRO (●): ");
            try {
                int index = in.nextInt();
                if (index >= 1 && index <= listaJugadores.size()) {
                    Jugador elegido = listaJugadores.get(index - 1);
                    if (elegido != jugadorBlanco) {
                        jugadorNegro = elegido;
                    } else {
                        System.out.println("Debe ser un jugador diferente al Blanco.");
                    }
                } else {
                    System.out.println("Número no válido. Ingrese un número de la lista.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número válido.");
                in.nextLine();
            }
        }

        return new Jugador[]{jugadorBlanco, jugadorNegro};
    }
    
    // Opción 3
    private void continuarPartida() {
        if (jugadores.size() < 2) {
            System.out.println("Se necesitan al menos dos jugadores para iniciar una partida.");
            return;
        }

        System.out.println("\n--- CONTINUACIÓN DE PARTIDA ---");

        ArrayList<Jugador> listaJugadores = new ArrayList<>(jugadores);
        Collections.sort(listaJugadores, new CriterioAlfabetico());

        Jugador[] pareja = elegirJugadores(listaJugadores);
        Jugador jugadorBlanco = pareja[0];
        Jugador jugadorNegro = pareja[1];

        Partida partida = new Partida(jugadorBlanco, jugadorNegro);

        Scanner in = new Scanner(System.in);
        System.out.println("Ingrese la secuencia de movimientos (ej: A1C B3C C2D):");
        String linea = in.nextLine().trim().toUpperCase();
        String[] jugadas = linea.split(" ");

        // Aplicar secuencia
        for (String jugada : jugadas) {
            partida.ejecutarMovimiento(jugada); 
            partida.getTablero().dibujarTablero(true);
            partida.cambiarTurno();
        }

        partida.iniciar(); 

        Jugador ganador = partida.getGanador();
        boolean empate = partida.hayEmpate();

        if (empate) {
            System.out.println("La partida terminó en empate.");
            jugadorBlanco.actualizarEstadisticas(false);
            jugadorNegro.actualizarEstadisticas(false);
        } else if (ganador != null) {
            System.out.println("¡El ganador es " + ganador.getNombre() + "!");
            ganador.actualizarEstadisticas(true);
            Jugador perdedor = (ganador == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
            perdedor.actualizarEstadisticas(false);
        }
    }
    
    // Opción 4
    private void mostrarRanking() {
        System.out.println("\n--- RANKING POR PARTIDAS GANADAS ---");
        
        // Ordenar por partidas ganadas (descendente)
        ArrayList<Jugador> listaJugadores = new ArrayList<>(jugadores);
        Collections.sort(listaJugadores, new CriterioPartidasGanadas());

        for (int i = 0; i < listaJugadores.size(); i++) {
            Jugador p = listaJugadores.get(i);
            System.out.println((i + 1) + ". " + p);
        }

        System.out.println("\n--- JUGADORES INVICTOS (Alfabético) ---");
        ArrayList<Jugador> invictos = new ArrayList<>();
        for (Jugador p : jugadores) {
            if (p.esInvicto()) {
                invictos.add(p);
            }
        }
        
        // Ordenar invictos alfabéticamente
        Collections.sort(invictos, Comparator.comparing(Jugador::getNombre, String.CASE_INSENSITIVE_ORDER));
        
        if (invictos.isEmpty()) {
            System.out.println("No hay jugadores invictos aún.");
        } else {
            for (Jugador j : invictos) {
                System.out.println("- " + j.getNombre());
            }
        }
    }
    
    // Ordenar alfabéticamente
    private class CriterioAlfabetico implements Comparator<Jugador> {
        @Override
        public int compare(Jugador j1, Jugador j2) {
            return j1.getNombre().compareToIgnoreCase(j2.getNombre());
        }
    }

    // Ordenar por partidas ganadas
    private class CriterioPartidasGanadas implements Comparator<Jugador> {
        @Override
        public int compare(Jugador j1, Jugador j2) {
            int diff = j2.getPartidasGanadas() - j1.getPartidasGanadas(); // descendiente
            if (diff == 0) {
                return j1.getNombre().compareToIgnoreCase(j2.getNombre()); // en caso de mismas partidas ganadas
            }
            return diff;
        }
    }
}

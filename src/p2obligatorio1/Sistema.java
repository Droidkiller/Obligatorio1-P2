/*AUTORES:
 * Francisco Bonanni
 * Aitana Álvarez
 */
package p2obligatorio1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

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
            int opcion = in.nextInt();
            
            switch (opcion) {
                case 1:
                    registrarJugador();
                    break;
                case 2:
                    iniciarNuevaPartida();
                    break;
                case 3:
                    // Continuar partida - Lógica pendiente
                    System.out.println("Opción pendiente: Continuar partida.");
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
        }
    }
    
    private void mostrarMenu() {
        System.out.println("\n==============================================");
        System.out.println("            Juego Medio Tateti - Menú           ");
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
        boolean esUnico;
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
        System.out.println("Ingrese edad: ");
        int edad = in.nextInt();
        
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
        
        ArrayList<Jugador> sortedPlayers = new ArrayList<>(jugadores);
        Collections.sort(sortedPlayers, Comparator.comparing(Jugador::getNombre, String.CASE_INSENSITIVE_ORDER));

        
        System.out.println("Lista de Jugadores:");
        for (int i = 0; i < sortedPlayers.size(); i++) {
            System.out.println((i + 1) + ". " + sortedPlayers.get(i).getNombre());
        }

        Jugador playerWhite = seleccionarJugador("JUGADOR BLANCO (o)", sortedPlayers);
        Jugador playerBlack = seleccionarJugador("JUGADOR NEGRO (●)", sortedPlayers);
        
        while (playerWhite == playerBlack) {
            System.out.println("¡ERROR! El jugador Blanco y el Negro deben ser diferentes.");
            playerBlack = seleccionarJugador("JUGADOR NEGRO (●)", sortedPlayers);
        }

        System.out.println("Partida iniciada:");
        System.out.println("Blanco: " + playerWhite.getNombre() + " | Negro: " + playerBlack.getNombre());

        // LÓGICA DEL JUEGO PENDIENTE: Crear Board y comenzar turnos aquí.
        // Game newGame = new Game(playerWhite, playerBlack);
        // newGame.run();
    }
    
    // Helper opción 2
    private Jugador seleccionarJugador(String rol, ArrayList<Jugador> jugadores) {
        int index = -1;
        Scanner in = new Scanner(System.in);
        while (index < 1 || index > jugadores.size()) {
            System.out.println("Seleccione el número para " + rol + ": ");
            index = in.nextInt();
            if (index < 1 || index > jugadores.size()) {
                System.out.println("Número no válido. Ingrese un número de la lista.");
            }
        }
        return jugadores.get(index - 1);
    }
    
    // Opción 4
    private void mostrarRanking() {
        System.out.println("\n--- RANKING POR PARTIDAS GANADAS ---");
        
        // Ordenar por partidas ganadas (descendente)
        ArrayList<Jugador> jugadoresClasificados = new ArrayList<>(jugadores);
        Collections.sort(jugadoresClasificados, Comparator.comparing(Jugador::getPartidasGanadas).reversed());

        for (int i = 0; i < jugadoresClasificados.size(); i++) {
            Jugador p = jugadoresClasificados.get(i);
            System.out.printf("%d. %s - Ganadas: %d / Jugadas: %d\n", 
                (i + 1), p.getNombre(), p.getPartidasGanadas(), p.getPartidasJugadas());
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
            System.out.println("No hay jugadores invictos aún (deben haber jugado y ganado todo).");
        } else {
            for (Jugador j : invictos) {
                System.out.println("- " + j.getNombre());
            }
        }
    }
}

/*AUTORES:
 * Francisco Bonanni
 * Aitana Álvarez
 */

package p2obligatorio1;

import java.util.Scanner;

public class Partida {
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private Tablero tablero;
    private boolean juegoTerminado;
    private boolean mostrarHeaders;

    public Partida(Jugador j1, Jugador j2) {
        this.jugador1 = j1;
        this.jugador2 = j2;
        this.tablero = new Tablero();

        jugador1.setColor('B');
        jugador2.setColor('N');
        jugadorActual = jugador1;
        juegoTerminado = false;
        mostrarHeaders = true;
    }

    public void iniciar() {
        Scanner in = new Scanner(System.in);
        while (!juegoTerminado) {
            tablero.dibujarTablero(mostrarHeaders);
            System.out.println("\nTurno de " + jugadorActual.getNombre() + " (" + jugadorActual.getColor() + ")");

            boolean jugadaValida = false;
            while (!jugadaValida && !juegoTerminado) {
                System.out.print("Ingrese su jugada: ");
                String jugada = in.nextLine().trim().toUpperCase();
                jugadaValida = procesarJugada(jugada, in);
            }
        }
        in.close();
    }

    private boolean procesarJugada(String jugada, Scanner in) {
        boolean jugadaValida = false;

        switch (jugada) {
            case "X":
                System.out.println(jugadorActual.getNombre() + " se rinde. Pierde la partida.");
                juegoTerminado = true;
                jugadaValida = true;
                break;

            case "T":
                System.out.print("¿Confirmar empate? (S/N): ");
                String confirm = in.nextLine().trim().toUpperCase();
                if (confirm.equals("S")) {
                    System.out.println("La partida termina en empate.");
                    juegoTerminado = true;
                }
                jugadaValida = true;
                break;

            case "B":
                mostrarHeaders = true;
                jugadaValida = true;
                break;

            case "N":
                mostrarHeaders = false;
                jugadaValida = true;
                break;

            case "H":
                // lógica de ayuda pendiente
                jugadaValida = true;
                break;

            default:
                if (jugada.length() == 3) {
                    char filaChar = jugada.charAt(0);
                    char colChar = jugada.charAt(1);
                    char accion = jugada.charAt(2);

                    int fila = filaChar - 'A';
                    int col = Character.getNumericValue(colChar) - 1;

                    if (esJugadaFormatoValido(jugada) && esJugadaEstadoValido(fila, col, accion)) {
                        if (accion == 'C' || accion == 'D') {
                            tablero.colocarPieza(fila, col, new Pieza(accion, jugadorActual.getColor()));
                        } else if (accion == 'I') {
                            tablero.invertirPieza(fila, col);
                        }
                        jugadaValida = true;
                    } else {
                        System.out.println("Movimiento inválido.");
                    }
                } else {
                    System.out.println("Formato de jugada inválido.");
                }
        }

        if (jugadaValida && !juegoTerminado) {
            cambiarTurno();
        }

        return jugadaValida;
    }

    private void cambiarTurno() {
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
    }

    private boolean esJugadaFormatoValido(String jugada) {
        boolean valido = false;
        if (jugada.length() == 3) {
            char fila = Character.toUpperCase(jugada.charAt(0));
            char col = jugada.charAt(1);
            char accion = Character.toUpperCase(jugada.charAt(2));

            boolean filaValida = fila >= 'A' && fila <= 'C';
            boolean colValida = col >= '1' && col <= '6';
            boolean accionValida = accion == 'C' || accion == 'D' || accion == 'I';

            valido = filaValida && colValida && accionValida;
        }
        return valido;
    }

    private boolean esJugadaEstadoValido(int fila, int col, char accion) {
        boolean valido = false;
        Pieza piezaExistente = tablero.getTablero()[fila][col];

        if (accion == 'C' || accion == 'D') {
            valido = (piezaExistente == null);
        } else if (accion == 'I') {
            valido = (piezaExistente != null && piezaExistente.getColor() == jugadorActual.getColor());
        }

        return valido;
    }
}

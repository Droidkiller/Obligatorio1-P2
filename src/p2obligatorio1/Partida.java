/*AUTORES:
 * Francisco Bonanni
 * Aitana Álvarez
 */

package p2obligatorio1;

import java.util.ArrayList;
import java.util.Scanner;

public class Partida {
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private Tablero tablero;
    private boolean juegoTerminado;
    private boolean mostrarHeaders;
    private Jugador ganador;
    private boolean huboEmpate;

    public Partida(Jugador j1, Jugador j2) {
        this.jugador1 = j1;
        this.jugador2 = j2;
        this.tablero = new Tablero();

        jugador1.setColor('B');
        jugador2.setColor('N');
        
        jugadorActual = jugador1;
        juegoTerminado = false;
        mostrarHeaders = true;
        huboEmpate = false;
    }
    
    public Jugador getGanador() {return ganador;}
    public Tablero getTablero() {return tablero;}

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
    }

    private boolean procesarJugada(String jugada, Scanner in) {
        boolean jugadaValida = false;

        switch (jugada) {
            case "X":
                System.out.println(jugadorActual.getNombre() + " se rinde. Pierde la partida.");
                juegoTerminado = true;
                jugadaValida = true;
                ganador = jugadorActual == jugador1 ? jugador2 : jugador1;
                break;

            case "T":
                System.out.print("¿Confirmar empate? (S/N): ");
                String confirm = in.nextLine().trim().toUpperCase();
                if (confirm.equals("S")) {
                    System.out.println("La partida termina en empate.");
                    juegoTerminado = true;
                    huboEmpate=true;
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
               jugadaValida = ejecutarMovimiento(jugada);
        }

        if (jugadaValida && !juegoTerminado) {
            if (verificarGanador()) {
                System.out.println("¡Hay ganador! " + ganador.getNombre());
            } else {
                cambiarTurno();
            }
        }

        return jugadaValida;
    }

    private void cambiarTurno() {
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
    }
    
    public boolean ejecutarMovimiento(String jugada) {
        boolean esValido = false;
        if (esJugadaFormatoValido(jugada)) {
            char filaChar = jugada.charAt(0);
            char colChar = jugada.charAt(1);
            char accion = jugada.charAt(2);

            int fila = filaChar - 'A';
            int col = Character.getNumericValue(colChar) - 1;

            if (esJugadaEstadoValido(fila, col, accion)) {
                if (accion == 'C' || accion == 'D') {
                    tablero.colocarPieza(fila, col, new Pieza(accion, jugadorActual.getColor()));
                } else if (accion == 'I') {
                    tablero.invertirPieza(fila, col);
                }
                esValido = true;
            } else {
                System.out.println("Movimiento inválido.");
            }
        } else {
            System.out.println("Formato de jugada inválido.");
        }
        return esValido;
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
    
    private boolean verificarGanador() {
        Pieza[][] t = tablero.getTablero();
        char[][] letras = new char[3][5]; // cada posición corresponde a la letra entera (col,col+1)
        ArrayList<int[]> coordsGanadoras = new ArrayList<>();
        boolean hayGanador = false;

        // Construir la matriz de letras según las piezas
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 5; col++) {
                Pieza p1 = t[fila][col];
                Pieza p2 = t[fila][col + 1];

                if (p1 != null && p2 != null) {
                    if (p1.getForma() == 'C' && p2.getForma() == 'D') {
                        letras[fila][col] = 'O';
                    } else if (p1.getForma() == 'D' && p2.getForma() == 'C') {
                        letras[fila][col] = 'X';
                    } else {
                        letras[fila][col] = ' ';
                    }
                } else {
                    letras[fila][col] = ' ';
                }
            }
        }

        // Horizontal
        for (int fila = 0; fila < 3 && !hayGanador; fila++) {
            for (int col = 0; col <= 2 && !hayGanador; col++) {
                char l = letras[fila][col];
                if (l != ' ' && l == letras[fila][col + 1] && l == letras[fila][col + 2]) {
                    hayGanador = true;
                    for (int c = col; c <= col + 2; c++) {
                        coordsGanadoras.add(new int[]{fila, c});
                        coordsGanadoras.add(new int[]{fila, c + 1}); // segunda mitad de la letra
                    }
                    ganador = (l == 'O') ? jugador1 : jugador2;
                }
            }
        }

        // Vertical
        for (int col = 0; col < 5 && !hayGanador; col++) {
            char l = letras[0][col];
            if (l != ' ' && l == letras[1][col] && l == letras[2][col]) {
                hayGanador = true;
                for (int fila = 0; fila < 3; fila++) {
                    coordsGanadoras.add(new int[]{fila, col});
                    coordsGanadoras.add(new int[]{fila, col + 1});
                }
                ganador = (l == 'O') ? jugador1 : jugador2;
            }
        }

        // Diagonal '\' (arriba-izq → abajo-der)
        for (int startCol = 0; startCol <= 2 && !hayGanador; startCol++) {
            char l = letras[0][startCol];
            if (l != ' ' && l == letras[1][startCol + 1] && l == letras[2][startCol + 2]) {
                hayGanador = true;
                for (int i = 0; i < 3; i++) {
                    coordsGanadoras.add(new int[]{i, startCol + i});
                    coordsGanadoras.add(new int[]{i, startCol + i + 1});
                }
                ganador = (l == 'O') ? jugador1 : jugador2;
            }
        }

        // Diagonal '/' (abajo-izq → arriba-der)
        for (int startCol = 0; startCol <= 2 && !hayGanador; startCol++) {
            char l = letras[2][startCol];
            if (l != ' ' && l == letras[1][startCol + 1] && l == letras[0][startCol + 2]) {
                hayGanador = true;
                for (int i = 0; i < 3; i++) {
                    coordsGanadoras.add(new int[]{2 - i, startCol + i});
                    coordsGanadoras.add(new int[]{2 - i, startCol + i + 1});
                }
                ganador = (l == 'O') ? jugador1 : jugador2;
            }
        }

        if (hayGanador) {
            tablero.resaltarAlineacionGanadora(coordsGanadoras.toArray(new int[0][]), ganador.getColor() == 'B' ? 'O' : 'X');
            juegoTerminado = true;
            tablero.dibujarTablero(mostrarHeaders);
        }

        return hayGanador;
    }

    
    public boolean hayEmpate() {
        boolean resultado = false;
        
        if (juegoTerminado && huboEmpate) {
            resultado = true;
        }

        return resultado;
    }
}

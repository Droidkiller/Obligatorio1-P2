/*AUTORES:
 * Francisco Bonanni
 * Aitana Álvarez
 */

package p2obligatorio1;

import java.util.Scanner;

public class Partida {
    private final Jugador jugador1;
    private final Jugador jugador2;
    private Jugador jugadorActual;
    private Jugador ganador;
    private final Tablero tablero;
    private boolean juegoTerminado;
    private boolean mostrarHeaders;    
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
            
            System.out.println("\nTurno de " + jugadorActual.getNombre() + " (" + jugadorActual.getColor() + ")");

            boolean jugadaValida = false;
            while (!jugadaValida && !juegoTerminado) {
                System.out.print("Ingrese su jugada: ");
                String jugada = in.nextLine().trim().toUpperCase();
                jugadaValida = procesarJugada(jugada, in);
            }
            tablero.dibujarTablero(mostrarHeaders);
        }
    }

    private boolean procesarJugada(String jugada, Scanner in) {
        boolean jugadaValida = false;
        boolean cambiaTurno = true;

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
                cambiaTurno = false;
                break;

            case "N":
                mostrarHeaders = false;
                jugadaValida = true;
                cambiaTurno = false;
                break;

            case "H":
                mostrarAyuda();
                jugadaValida = true;
                cambiaTurno = false;
                break;

            default:
               jugadaValida = ejecutarMovimiento(jugada);
        }
        
        if (jugadaValida && !juegoTerminado) {
            char letraGanadora = verificarGanador(tablero.getTablero(), true);
            if (letraGanadora == ' ' && cambiaTurno)
                cambiarTurno();
        }

        return jugadaValida;
    }

    public void cambiarTurno() {
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

    private char verificarGanador(Pieza[][] t, boolean actualizarEstado) {
        char[][] letras = new char[3][5]; 
        char letraGanadora = ' ';
        int[][] coordsGanadoras = null; 

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
        for (int fila = 0; fila < 3 && letraGanadora == ' '; fila++) {
            for (int col = 0; col <= 2 && letraGanadora == ' '; col++) {
                char l = letras[fila][col];
                if (l != ' ' && l == letras[fila][col + 1] && l == letras[fila][col + 2]) {
                    letraGanadora = l;
                    coordsGanadoras = new int[][]{{fila, col}, {fila, col+1}, {fila, col+2}};
                }
            }
        }

        // Vertical
        for (int col = 0; col < 5 && letraGanadora == ' '; col++) {
            char l = letras[0][col];
            if (l != ' ' && l == letras[1][col] && l == letras[2][col]) {
                letraGanadora = l;
                coordsGanadoras = new int[][]{{0, col}, {1, col}, {2, col}};
            }
        }

        // Diagonal '\'
        for (int startCol = 0; startCol <= 2 && letraGanadora == ' '; startCol++) {
            char l = letras[0][startCol];
            if (l != ' ' && l == letras[1][startCol + 1] && l == letras[2][startCol + 2]) {
                letraGanadora = l;
                coordsGanadoras = new int[][]{{0, startCol}, {1, startCol+1}, {2, startCol+2}};
            }
        }

        // Diagonal '/'
        for (int startCol = 0; startCol <= 2 && letraGanadora == ' '; startCol++) {
            char l = letras[2][startCol];
            if (l != ' ' && l == letras[1][startCol + 1] && l == letras[0][startCol + 2]) {
                letraGanadora = l;
                coordsGanadoras = new int[][]{{2, startCol}, {1, startCol+1}, {0, startCol+2}};
            }
        }

        // At the end, if we found a winner and need to update state, highlight
        if (actualizarEstado && letraGanadora != ' ') {
            ganador = letraGanadora == 'O' ? jugador1 : jugador2;
            juegoTerminado = true;
            tablero.resaltarAlineacionGanadora(coordsGanadoras, letraGanadora);            
        }

        return letraGanadora;
    }

    private void mostrarAyuda() {
        Pieza[][] original = tablero.getTablero();
        boolean hayJugadaGanadora = false;
        char letraObjetivo = (jugadorActual.getColor() == 'B') ? 'O' : 'X';

        for (int fila = 0; fila < 3 && !hayJugadaGanadora; fila++) {
            for (int col = 0; col < 6 && !hayJugadaGanadora; col++) {
                char[] acciones = {'C', 'D', 'I'};
                for (int i = 0; i < acciones.length && !hayJugadaGanadora; i++) {
                    char accion = acciones[i];
                    boolean jugadaValida;

                    if (accion == 'C' || accion == 'D') {
                        jugadaValida = (original[fila][col] == null);
                    } else { // 'I'
                        jugadaValida = (original[fila][col] != null && original[fila][col].getColor() == jugadorActual.getColor());
                    }

                    if (jugadaValida) {
                        // copiar tablero
                        Pieza[][] tmpTablero = new Pieza[3][6];
                        for (int f = 0; f < 3; f++) {
                            for (int c = 0; c < 6; c++) {
                                tmpTablero[f][c] = (original[f][c] == null) ? null
                                        : new Pieza(original[f][c].getForma(), original[f][c].getColor());
                            }
                        }

                        // aplicar jugada simulada
                        if (accion == 'C' || accion == 'D') {
                            tmpTablero[fila][col] = new Pieza(accion, jugadorActual.getColor());
                        } else { // 'I'
                            tmpTablero[fila][col].invertir();
                        }

                        // verificar si sería un ganador solo para la letra del jugador actual
                        char letraGanadora = verificarGanador(tmpTablero, false);
                        if (letraGanadora == letraObjetivo) {
                            System.out.println("(!) Jugada ganadora: " + (char)('A'+fila) + (col+1) + accion);
                            hayJugadaGanadora = true; // solo mostrar una
                        }
                    }
                }
            }
        }

        if (!hayJugadaGanadora) {
            System.out.println("No hay jugada ganadora disponible.");
        }
    }
    
    public boolean hayEmpate() {
        boolean resultado = false;
        
        if (juegoTerminado && huboEmpate) {
            resultado = true;
        }

        return resultado;
    }
}

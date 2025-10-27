/*AUTORES:
 * Francisco Bonanni
 * Aitana Álvarez
 */

package p2obligatorio1;

public class Tablero {
    private final Pieza[][] tablero;
    private final int filas = 3;
    private final int columnas = 6;
    private final int anchoCelda = 2;
    
    public Tablero() {
        tablero = new Pieza[filas][columnas];
    }
    
    public Pieza[][] getTablero() {
        return tablero;
    }
    
    public void colocarPieza(int fila, int col, Pieza pieza) {
        tablero[fila][col] = pieza;
    }

    public void invertirPieza(int fila, int col) {
        tablero[fila][col].invertir();        
    }
    
    public void dibujarTablero(boolean mostrarTitulos) {
        if (mostrarTitulos) {
            // mostrar numeros de columnas
            System.out.print("  ");
            for (int c = 0; c < columnas; c++) {
                System.out.print(" " + (c + 1) + " ");
            }
            System.out.println();
        }

        for (int fila = 0; fila < filas; fila++) {
            // borde superior
            System.out.print(mostrarTitulos ? "  " : "");
            for (int col = 0; col < columnas; col++) {
                System.out.print("+");
                for (int i = 0; i < anchoCelda; i++) System.out.print("-");
            }
            System.out.println("+");

            // filas
            for (int subFila = 0; subFila < 3; subFila++) {
                if (mostrarTitulos && subFila == 1) System.out.print((char)('A' + fila) + " ");
                else if (mostrarTitulos) System.out.print("  ");
                else System.out.print("");

                for (int col = 0; col < columnas; col++) {
                    System.out.print("|");
                    if (tablero[fila][col] != null) {
                        String celda = tablero[fila][col].getDibujo()[subFila];
                        System.out.print(celda + (celda.length() < anchoCelda ? " " : ""));
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println("|");
            }
        }

        // borde inferior
        System.out.print(mostrarTitulos ? "  " : "");
        for (int col = 0; col < columnas; col++) {
            System.out.print("+");
            for (int i = 0; i < anchoCelda; i++) System.out.print("-");
        }
        System.out.println("+");
    }
    
    public void resaltarAlineacionGanadora(int[][] coords, char letra) {
        for (int[] celda : coords) {
            int fila = celda[0];
            int col = celda[1];

            for (int c = col; c <= col + 1; c++) {
                if (tablero[fila][c] != null) {
                    String[] nuevoDibujo = new String[3];
                    for (int i = 0; i < 3; i++) {
                        nuevoDibujo[i] = "" + letra + letra;
                    }
                    tablero[fila][c].setDibujo(nuevoDibujo);
                }
            }
        }
    }
}

/*AUTORES:
 * Francisco Bonanni
 * Aitana Álvarez
 */

package p2obligatorio1;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;

public class P2Obligatorio1 {

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: UTF-8 encoding not supported.");
        }
        
        Pieza[][] tablero = new Pieza[3][6];
        int filas = tablero.length;
        int columnas = tablero[0].length;
        int anchoCelda = 2;

        // Column headers (numbers) - properly centered
    System.out.print("  "); // offset for row letters
for (int c = 0; c < columnas; c++) {
    String num = Integer.toString(c + 1);
    if (num.length() == 1) {
        System.out.print(" " + num); // left pad
    } 
    System.out.print(" "); // right pad to make total 2-char wide
}
System.out.println();

    for (int fila = 0; fila < filas; fila++) {
        // Top border of each row
        System.out.print("  "); // offset for row letters
        for (int col = 0; col < columnas; col++) {
            System.out.print("+");
            for (int i = 0; i < anchoCelda; i++) System.out.print("-");
        }
        System.out.println("+");

        // Each piece has 3 rows
        for (int subFila = 0; subFila < 3; subFila++) {
            // Row letters
            if (subFila == 1) {
                System.out.print((char) ('A' + fila) + " "); // center letter vertically
            } else {
                System.out.print("  "); // blank space for other subrows
            }

            for (int col = 0; col < columnas; col++) {
                System.out.print("|");
                if (tablero[fila][col] != null) {
                    String celda = tablero[fila][col].getDibujo()[subFila];
                    // pad right if shorter than cell width
                    System.out.print(celda + (celda.length() < anchoCelda ? " " : ""));
                } else {
                    System.out.print("  "); // empty cell
                }
            }
            System.out.println("|");
        }
    }

    // Bottom border of last row
    System.out.print("  "); // offset for row letters
    for (int col = 0; col < columnas; col++) {
        System.out.print("+");
        for (int i = 0; i < anchoCelda; i++) System.out.print("-");
    }
    System.out.println("+");
    }
}

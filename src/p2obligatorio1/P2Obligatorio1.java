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
        
        Sistema s = new Sistema();
        s.comenzar();
    }
}

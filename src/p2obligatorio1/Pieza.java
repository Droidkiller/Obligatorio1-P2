/*AUTORES:
 * Francisco Bonanni
 * Aitana Álvarez
 */
package p2obligatorio1;

public class Pieza {
    private char color; // 'B' = blanco, 'N' = negro
    private char forma; // 'C' o 'D'
    private String[] dibujo; // representación visual (3 filas)
    
    public Pieza(char forma, char color) {       
        setForma(forma);
        setColor(color);
        setDibujo();    
    }
    
    public char getForma() { return forma; }
    public char getColor() { return color; }
    public String[] getDibujo() { return dibujo; }

   
    private void setForma(char forma) { 
        this.forma = Character.toUpperCase(forma); 
    }
    private void setColor(char color) { 
        this.color = Character.toUpperCase(color); 
    }
    private void setDibujo() {
        String simbolo = (getColor() == 'B') ? "○" : "●";
        dibujo = new String[3];

        if (getForma() == 'C') {
            dibujo[0] = " " + simbolo;
            dibujo[1] = simbolo + " ";
            dibujo[2] = " " + simbolo;
        } else { // 'D'
            dibujo[0] = simbolo + " ";
            dibujo[1] = " " + simbolo;
            dibujo[2] = simbolo + " ";
        }
    }
    
    public void setDibujo(String[] dibujo) {
        this.dibujo = dibujo;
    }
    
    public void invertir() {
        setForma((forma == 'C') ? 'D' : 'C');
        setDibujo();
    }
    
    public void dibujarPieza() {
        for (String fila : dibujo) {
            System.out.println(fila);
        }
    }
}

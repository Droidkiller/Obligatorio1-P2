/*AUTORES:
 * Francisco Bonanni
 * Aitana Álvarez
 */
package p2obligatorio1;

public class Jugador {
    private String nombre;
    private int edad;
    private int partidasGanadas;
    private int partidasJugadas;
    private char color;
    private boolean esInvicto; 

    public Jugador(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.partidasGanadas = 0;
        this.partidasJugadas = 0;
        this.esInvicto = true;
        this.color=' ';
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }
   
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas() {
        this.partidasGanadas++;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas() {
        this.partidasJugadas++;
    }

    public boolean esInvicto() {
        return esInvicto;
    }

    public void setEsInvicto() {
        // Regla para ver si está invicto o no
        this.esInvicto = partidasJugadas == 0 || partidasJugadas == partidasGanadas;
    }
    
    public void setColor(char color) {
        this.color = Character.toUpperCase(color);
    }
    
    public char getColor() {
        return color;    
    }   
    
    public void actualizarEstadisticas(boolean gano) {
        this.setPartidasJugadas();
        if (gano) {
            this.setPartidasGanadas();
        }
        this.setEsInvicto();
    }

    @Override
    public String toString() {
        return nombre + " - Edad: " + edad + 
               " | Ganadas: " + partidasGanadas + 
               " | Jugadas: " + partidasJugadas;
    }
}

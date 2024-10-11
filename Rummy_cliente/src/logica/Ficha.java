
package logica;

import java.awt.Color;

public class Ficha {
    
    private String numero;
    private int rgb;

    public Ficha(String numero, int rgb) {
        this.numero = numero;
        this.rgb = rgb;
    }   
    
    public String getNumero() {
        return numero;
    }

    public int getColor() {
        return rgb;
    }      

    @Override
    public String toString() {
        return "Ficha{" + "numero=" + numero + ", rgb=" + rgb + '}';
    }  
}

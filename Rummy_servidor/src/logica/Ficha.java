package logica;

public class Ficha {
    public int numero; 
    private int color;
    
    public Ficha(int n, int RGB) {
        numero = n;        
        this.color = RGB;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

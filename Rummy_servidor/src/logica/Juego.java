package logica;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Juego {
    List<Ficha> bolsadefichas;
    private final List<Jugador> jugadores;  
    private final Ronda rondaActual;
    private boolean juegoTerminado;
    private Tablero tablero;
    public List<String> nombresJugadores;
    
    public Juego(List<Jugador> jugadores) {
        bolsadefichas = Crearfichas();
        this.jugadores = jugadores;   
        rondaActual = new Ronda(this);
        juegoTerminado = false;
        tablero = new Tablero(this);
        iniciarJuego();        
    }    
    
        //Método que crea la lista de fichas
    public ArrayList<Ficha> Crearfichas (){
        Color colores[] = {Color.BLUE, Color.RED, Color.ORANGE, Color.BLACK};

        ArrayList<Ficha> listaFichas = new ArrayList<>();
        
            for (int i=0; i< colores.length; i++ ){
                
                for (int j=1; j<=13; j++ ){

                   Ficha b = new Ficha(j, colores[i].getRGB()); 
                   Ficha d = new Ficha(j, colores[i].getRGB()); 
                   listaFichas.add(b);
                   listaFichas.add(d);                   
                }
            }
        
        return listaFichas;
    }
    
   
    
    private void iniciarJuego() {
        repartirFichas();
        Collections.shuffle(jugadores);        
    }
    
    private void repartirFichas(){
        Collections.shuffle(bolsadefichas);
        
        jugadores.forEach(jugador -> {
            List<Ficha> fichasJugador = new ArrayList<>();
            
            for(int i = 0; i < 14 ; i++)
                fichasJugador.add(bolsadefichas.remove(0));
            
            jugador.setMisfichas(fichasJugador);
        });
    }    
    
    public List<Ficha> getBolsadefichas() {
        return bolsadefichas;
    }

    public void setBolsadefichas(List<Ficha> Bolsadefichas) {
        this.bolsadefichas = Bolsadefichas;
    }       

    public Tablero getTablero() {
        return tablero;
    }   

    public List<Jugador> getJugadores() {
        return jugadores;
    }  
    
    public void cambiarTurno(){
        rondaActual.cambiarTurno();
    }
    
    public int getTurnoActual(){
        return rondaActual.getTurnoActual();
    }

    public Ronda getRondaActual() {
        return rondaActual;
    } 
    
    /**
     * Comprueba a que jugador le toca jugar
     */
    public void comprobarTurnoActual(){
        
    }
}

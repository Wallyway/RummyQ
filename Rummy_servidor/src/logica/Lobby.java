package logica;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lobby {
    public static final int MINJUGADORES = 2;
    private List<Jugador> jugadoresSala;

    public Lobby() {
        jugadoresSala = new ArrayList<>();
    }   
    
    public synchronized void agregarJugador(Jugador nuevoJugador){
        jugadoresSala.add(nuevoJugador);
        
        if(jugadoresSala.size() == MINJUGADORES) {
            Juego juego = new Juego(jugadoresSala);
            jugadoresSala.forEach(j ->  j.setJuego(juego));            
            //FIXME limpiar la sala
            //jugadoresSala = Collections.emptyList();            
        }
    } 
    
    public void removerJugador(Socket socketCiiente) {
        //jugadoresSala.removeIf(jugador -> jugador.getSocketCliente().equals(socketCiiente));
    }
}

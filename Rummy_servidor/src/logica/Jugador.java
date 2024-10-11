package logica;

import java.net.InetAddress;
import java.util.List;
public class Jugador {
    
    private Juego juego;    
    private List<Ficha> fichas;
    private final String nombre;
    private InetAddress ip;    
    
    public Jugador(String nombre, InetAddress ip ) {        
        this.nombre = nombre;
        this.ip = ip;        
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    public void setMisfichas(List<Ficha> Misfichas) {
        this.fichas = Misfichas;
    }

    public String getNombre() {
        return nombre;
    }

    public InetAddress getIp() {
        return ip;
    }  

    public Juego getJuego() {
        return juego;
    }  

    public void setJuego(Juego juego) {
        this.juego = juego;
    }   
    
    public Ficha robarFicha (){
        
        List<Ficha> robar = juego.getBolsadefichas();
        Ficha fichaRob =robar.remove(0);
        fichas.add(fichaRob);
        System.out.println(robar.size());
        return fichaRob;
    }    
}

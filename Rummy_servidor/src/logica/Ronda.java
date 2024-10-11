package logica;

public class Ronda {
    private int rondaActual;
    private int turnoActual;
    private int turnoAnterior;
    private Juego juego;
    
    public Ronda(Juego juego) {
        this.juego = juego;
        rondaActual = 0;
    }
    
    private void cambiarRonda(){
        rondaActual++;
    }
    
    public void cambiarTurno(){
        turnoActual++;
        
        if(turnoActual > juego.getJugadores().size()){
            cambiarRonda();
            turnoActual = 0;
        }        
    }

    public int getRondaActual() {
        return rondaActual;
    }

    public int getTurnoActual() {
        return turnoActual;
    }    
    
    public boolean esNuevoTurno(){
        return turnoActual != turnoAnterior;
    }
}

package logica;

import java.util.List;

public class DatosJuego {
    private List<Ficha> fichas;
    private List<String> nombresJugadores;
    private static DatosJuego datos;
    private Ficha[][] tablero; 
    private int miTurno;
    private int turnoActual;
    
    private DatosJuego(List<Ficha> fichas, List<String> nombresJugadores, int miTurno){
        this.fichas = fichas;
        this.nombresJugadores = nombresJugadores;        
        this.miTurno = miTurno;
        turnoActual = 0;
        tablero = new Ficha[8][20];
    }
    
    public static DatosJuego obtenerDatosJuegos(){        
        return datos;
    }

    public static void crearDatosJuego(List<Ficha> fichas, List<String> nombresJugadores, int miTurno) {
        datos = new DatosJuego(fichas, nombresJugadores, miTurno);
    }   
    
    public void agregarFicha(Ficha ficha){
        fichas.add(ficha);
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    public List<String> getNombresJugadores() {
        return nombresJugadores;
    }  

    public Ficha[][] getTablero() {
        return tablero;
    }

    public void setTablero(Ficha[][] tablero) {
        this.tablero = tablero;
    }   
    
    public void cambiarTurnoActual(int turnoActual){
        this.turnoActual = turnoActual;
    }

    public int getMiTurno() {
        return miTurno;
    }

    public int getTurnoActual() {
        return turnoActual;
    }
    
    
}

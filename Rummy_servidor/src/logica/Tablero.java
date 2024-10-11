package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.util.Pair;

public class Tablero {
    
    public Juego juego;
    
    Ficha [][] tablero;   
    
    List<Grupo> grupos;    

    public Tablero(Juego juego) {
        this.juego = juego;
        tablero = new Ficha[8][20];       
        inicializarTablero();
    }  
        
    public boolean verificarGruposModificados(List<Grupo> gruposModificados){
        Verificación v = new Verificación();        
       
        for(Grupo g: gruposModificados){            
            if(!v.Verificado(g.getFichas()))                
                return false;            
        }        
        
        return true;
    }      

    public void agregarFichas(List<Grupo> gruposModificados, List<List<Pair<Integer,Integer>>> posicionesFichas) {        
        int j = 0;
        for(Grupo g: gruposModificados){
            for(int i = 0; i < g.getFichas().size(); i++){                             
                System.out.println(posicionesFichas.get(j).get(i).getValue() + "," + posicionesFichas.get(j).get(i).getKey());   
                tablero[posicionesFichas.get(j).get(i).getKey()][posicionesFichas.get(j).get(i).getValue()] = g.getFichas().get(i);                
            }  
            j++;
        }
        
        juego.cambiarTurno();
    }    
    
    public List<List<Ficha>> obtenerFichasTablero(){
        List<List<Ficha>> fichasTablero = new ArrayList<>();
        
        for(int i = 0; i < tablero.length ; i++){
            List<Ficha> fichasFila = new ArrayList<>();
            for(int j = 0; j < tablero[0].length; j++){               
                fichasFila.add(tablero[i][j]);
            }
            fichasTablero.add(fichasFila);
        }
        
        return fichasTablero;
    }
    
    public void inicializarTablero(){
        for(int i = 0; i < tablero.length ; i++){
            for(int j = 0; j < tablero[0].length; j++){               
                tablero[i][j]  = new Ficha(0, 0);
            }
        }
    }
}

package logica;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Clase encargada de convertir la respuesta del servidor de formato json a objetos
 * @author ASUS
 */
public class MapperCliente {
    public static List<String> obtenerNombresJugadores(JSONObject json) {
        List<String> jugadores = new ArrayList<>(); 
        
        JSONArray jugadoresJson = json.getJSONArray("jugadores");
        for(int i = 0; i < jugadoresJson.length(); i++) {
            String jugador = jugadoresJson.getString(i);
            jugadores.add(jugador);
        }
        
        return jugadores;
    }
    
    public static List<Ficha> obtenerFichas(JSONObject json){
        List<Ficha> fichasJugador = new ArrayList<>();
        
        JSONArray fichasJson = json.getJSONArray("fichas");
        for(int i = 0; i < fichasJson.length(); i++){
            String numero = String.valueOf(fichasJson.getJSONObject(i).getInt("numero"));
            int rgb = fichasJson.getJSONObject(i).getInt("color");
            fichasJugador.add(new Ficha(numero, rgb));
        }
        
        return fichasJugador;
    }
    
    public static Ficha convertirFicha (JSONObject json){
        String num= String.valueOf(json.getJSONObject("fichaRobada").getInt("numero"));
        int rgb= json.getJSONObject("fichaRobada").getInt("color");
        
        return new Ficha (num, rgb);
    }
            
    public static Ficha[][] obtenerDatosTablero(JSONObject json) {
        Ficha[][] tablero = new Ficha[8][20];
        JSONArray datosTablero = json.getJSONArray("tablero");
        
        for(int i = 0; i < datosTablero.length(); i++){
            JSONArray filaFichas = datosTablero.getJSONArray(i);
            for(int j = 0; j < filaFichas.length(); j++) {
                JSONObject datosFicha = filaFichas.getJSONObject(j);
                
                int numero = datosFicha.getInt("numero");
                int color = datosFicha.getInt("color");
                
                tablero[i][j] = new Ficha(String.valueOf(numero), color);                
            }
        }        
        
        return tablero;
    }
    
    public static int obtenerTurno(JSONObject json){
        return json.getInt("turno");
    }    
}

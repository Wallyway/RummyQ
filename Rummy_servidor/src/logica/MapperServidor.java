package logica;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

public class MapperServidor {

    public static List<List<Pair<Integer, Integer>>> obtenerPosicionesFichas(JSONObject datosJson) {
        List<List<Pair<Integer, Integer>>> gruposF = new ArrayList<>();
        List<Pair<Integer, Integer>> posicionesFichas = new ArrayList<>();
        
        JSONArray posicionesFichasJson = datosJson.getJSONArray("posicionesFichas");
        for (int i = 0; i < posicionesFichasJson.length(); i++) {
            JSONArray posicionesGrupos = posicionesFichasJson.getJSONArray(i);
            
            for(int j = 0; j < posicionesGrupos.length(); j++){
                
                int y = posicionesGrupos.getJSONObject(j).getInt("value");
                int x = posicionesGrupos.getJSONObject(j).getInt("key");                                
                posicionesFichas.add(new Pair<>(x, y));
                
            }
            Comparator<Pair<Integer,Integer>> comparator = Comparator.comparing(par -> par.getKey());
            comparator = comparator.thenComparing(Comparator.comparing(par -> par.getValue()));
            Collections.sort(posicionesFichas, comparator);
            
            gruposF.add(posicionesFichas);
            posicionesFichas = new ArrayList<>();
        }        
        
        return gruposF;
    }

    public static List<Grupo> convertirAGrupos(JSONObject datosJson) {
        List<Grupo> gruposModificados = new ArrayList<>();
        List<Ficha> fichasGrupo = new ArrayList<>();       
        
        JSONArray fichas = datosJson.getJSONArray("fichas");
        for (int i = 0; i < fichas.length(); i++) {
            JSONArray fichasGr = fichas.getJSONArray(i);
            for(int j = 0; j < fichasGr.length(); j++){
                fichasGrupo.add(
                        new Ficha(
                                Integer.parseInt(fichasGr.getJSONObject(j).getString("numero")),
                                fichasGr.getJSONObject(j).getInt("color"))
                );                             
            }
            Collections.sort(fichasGrupo, Comparator.comparing(x -> x.getNumero()));
            gruposModificados.add(new Grupo(fichasGrupo));
            fichasGrupo = new ArrayList<>();            
        }       
        
        return gruposModificados;
    }
}

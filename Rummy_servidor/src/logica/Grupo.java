package logica;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
     
    List<Ficha> fichas;

    public Grupo(List<Ficha> Fichas) {
        fichas = Fichas;
    }

    public List<List<Ficha>> creacionGrupos ( ArrayList<Ficha> grupito ){
        List<List<Ficha>> grupo = new ArrayList<List<Ficha>>(); 
        grupo.add(grupito); 
        return grupo;
    }
    
    public List<Ficha> getFichas() {
        return fichas;
    }

    public void setFichas(ArrayList<Ficha> fichas) {
        this.fichas = fichas;
    }
}

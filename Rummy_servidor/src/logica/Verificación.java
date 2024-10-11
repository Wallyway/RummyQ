package logica;

import java.util.List;
import java.util.stream.Collectors;

public class Verificación {

    private Grupo grupo;

    public boolean Verificado(List<Ficha> fichas) {
        
        if (fichas.size() < 3) {
            return false;
        }     

       int coloresDistintos = cantidadColoresDistintos(fichas);                
       
       return validarTrio(coloresDistintos, fichas) || validarEscalera(coloresDistintos, fichas);
    }

    private int cantidadColoresDistintos(List<Ficha> grupitos) {
        return grupitos.stream()
                .collect(Collectors.groupingBy(Ficha::getColor))
                .size();
    }

    public boolean validarEscalera(int coloresDistintos, List<Ficha> fichas) {
        if (coloresDistintos != 1) 
            return false;        

        for (int i = 0; i < fichas.size() - 1; i++) {
            if (fichas.get(i).getNumero() != (fichas.get(i + 1).getNumero() - 1)) {
                return false;
            }
        }
        
        return true;
    }

    public boolean validarTrio(int coloresDistintos, List<Ficha> fichas) {
        if(coloresDistintos != fichas.size())
            return false;
        
         for (int i = 0; i < fichas.size() - 1; i++) {
            if (fichas.get(i).getNumero() != fichas.get(i + 1).getNumero()) {
                return false;
            }
        }
         
        return true;
    }
}

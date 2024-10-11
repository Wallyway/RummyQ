
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import logica.Ficha;
import logica.Grupo;
import logica.Verificación;
import presentacion.Modelo;


public class Launcher {

    private Modelo miApp;

    public Launcher() throws IOException {
        miApp = new Modelo();
        miApp.iniciar();
    }
    
    
    public static void main(String[] args) throws IOException {
        new Launcher();
    }
    
}

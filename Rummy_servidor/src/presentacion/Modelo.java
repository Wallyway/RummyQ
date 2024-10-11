package presentacion;


import java.io.IOException;
import logica.Servidor;

public class Modelo {

    private Servidor appServidor;
    
    public Modelo() {
    }

    public Servidor getAppServidor() {
        if(appServidor == null){
            appServidor = new Servidor();
        }
        return appServidor;
    }
    
    
    public void iniciar() throws IOException{
        getAppServidor().activar(true);
        getAppServidor().escucharClientes();      
    }
}

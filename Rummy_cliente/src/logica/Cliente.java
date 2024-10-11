package logica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.util.Pair;
import org.json.JSONObject;

public class Cliente {

    private Socket servidor;
    private DataInputStream datosEntrada;
    private DataOutputStream datosSalida;

    public void conectar(String nombreUsuario) throws IOException, InterruptedException {

        String ruta = "localhost"; // "127.0.0.1"
        int puerto = 5001;

        servidor = new Socket(ruta, puerto);

        datosEntrada = new DataInputStream(servidor.getInputStream());
        datosSalida = new DataOutputStream(servidor.getOutputStream());

        datosSalida.writeUTF(nombreUsuario);        
    }
    
    public boolean verificarInicioJuego() {
        JSONObject request = new JSONObject();
        request.put("accion", "juegoIniciado");
        JSONObject respuesta = enviarRequest(request);        
        
        if(respuesta.getInt("estado") != 1){
            return false;
        }
        
        DatosJuego.crearDatosJuego(MapperCliente.obtenerFichas(respuesta), 
                MapperCliente.obtenerNombresJugadores(respuesta),
                MapperCliente.obtenerTurno(respuesta));
        
        return true;          
    }
    
    private JSONObject enviarRequest(JSONObject datosRequest){
        try {            
            datosSalida.writeUTF(datosRequest.toString());
            String res = datosEntrada.readUTF();
            JSONObject respuesta = new JSONObject(res);
            return respuesta;
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        
        return null;
    } 
    
    public boolean enviarJugada(Map<Integer, Map<Ficha, Pair<Integer, Integer>>> grupos){        
        
        JSONObject peticion = new JSONObject();
        peticion.put("accion", "validarJugada");               
        peticion.put("fichas", grupos.values()
                .stream()                
                .map(Map::keySet)
                .collect(Collectors.toList()));
        peticion.put("posicionesFichas", grupos.values()
                .stream()
                .map(Map::values)
                .collect(Collectors.toList()));
                  
        System.out.println(peticion.toString());
        JSONObject respuesta = enviarRequest(peticion);
        System.out.println(respuesta.toString());
        
        DatosJuego.obtenerDatosJuegos().setTablero(MapperCliente.obtenerDatosTablero(respuesta));
        
        if(respuesta.getBoolean("valido"))
            return true;              
        
        return false;
    }
    
    
    public Ficha robarFicha(){
        JSONObject peticion = new JSONObject();
        peticion.put("accion", "robarFicha");
        JSONObject respuesta = enviarRequest(peticion);
        Ficha fichita = MapperCliente.convertirFicha(respuesta);
        DatosJuego.obtenerDatosJuegos().cambiarTurnoActual(MapperCliente.obtenerTurno(respuesta));        
        return fichita;
    }
    
    public boolean revisarCambioTurno() {
        
        JSONObject peticion = new JSONObject();
        peticion.put("accion" , "revisarFinTurno");
        
        JSONObject respuesta = enviarRequest(peticion);
        System.out.println(respuesta);
        if(respuesta.getBoolean("cambioTurno")){
            
            DatosJuego.obtenerDatosJuegos().setTablero(MapperCliente.obtenerDatosTablero(respuesta));
            DatosJuego.obtenerDatosJuegos().cambiarTurnoActual(MapperCliente.obtenerTurno(respuesta));
            return true;
        }               
        
        return false;
    }
}

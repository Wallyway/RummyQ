package logica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class Servidor {

    private ServerSocket servidor;
    private Socket cliente;
    private final int puerto;

    private boolean activado;
    private final Lobby salaPredeterminada;

    public Servidor() {
        activado = false;
        puerto = 5001;
        salaPredeterminada = new Lobby();
    }

    public void activar(boolean b) {
        activado = b;
    }

    public void escucharClientes() throws IOException {

        servidor = new ServerSocket(puerto);
        System.out.println("Esperando conexiones...");

        while (activado) {
            cliente = servidor.accept();            
            autenticar(cliente);
        }
    }

    private void autenticar(Socket socketCliente) {
        try {            
            DataInputStream reader = new DataInputStream(socketCliente.getInputStream());            
            String userName = reader.readUTF();
            
            Jugador jugador = new Jugador(userName, socketCliente.getInetAddress());
            salaPredeterminada.agregarJugador(jugador);            

            Thread playerThread = new Thread(() -> playerThread(cliente, jugador));
            playerThread.start();            

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void playerThread(Socket cliente, Jugador jugador) {
        try {
            DataInputStream reader = new DataInputStream(cliente.getInputStream());            
            DataOutputStream writer = new DataOutputStream(cliente.getOutputStream());
            
            JSONObject request;
            while (true) {

                String mensaje = reader.readUTF();
                request = new JSONObject(mensaje);
                JSONObject respuesta;
                switch (request.getString("accion")) {
                    case "juegoIniciado":
                        respuesta = new JSONObject();
                        if(jugador.getJuego() == null){
                            respuesta.put("estado", 0);
                            try{
                                writer.writeUTF(respuesta.toString());                                
                            }
                            catch(IOException e){
                                e.printStackTrace();
                            }
                        }                        
                        else {
                            //Si inició el juego devuleve el listado de jugadores y las fichas de este jugador                                
                            respuesta.put("jugadores", 
                                    jugador.getJuego().getJugadores()
                                            .stream()
                                            .map(Jugador::getNombre)
                                            .collect(Collectors.toList())                                            
                                );                                                        
                            respuesta.put("fichas", jugador.getFichas());
                            respuesta.put("estado", 1);   
                            respuesta.put("turno", jugador.getJuego().getJugadores().indexOf(jugador));
                            System.out.println(respuesta.toString());
                            writer.writeUTF(respuesta.toString());
                        }                                                
                        break;                    
                        
                    case "validarJugada":
                        respuesta = new JSONObject();
                        List<Grupo> gruposModificados = MapperServidor.convertirAGrupos(request);  
                        
                        if (jugador.getJuego().getTablero().verificarGruposModificados(gruposModificados) 
                                && jugador.getJuego().getJugadores().get(jugador.getJuego().getTurnoActual()) == jugador) {
                            jugador.getJuego().getTablero().agregarFichas(gruposModificados, MapperServidor.obtenerPosicionesFichas(request));                            
                            respuesta.put("valido", true);                            
                        }else
                            respuesta.put("valido", false);
                        
                        respuesta.put("tablero", jugador
                                .getJuego()
                                .getTablero()
                                .obtenerFichasTablero());
                        
                        System.out.println(respuesta.toString());
                        
                        writer.writeUTF(respuesta.toString());                        
                        break;
                    case "robarFicha":
                        Ficha fichaRobada= jugador.robarFicha();
                        respuesta = new JSONObject();
                        respuesta.put("fichaRobada", new JSONObject (fichaRobada));
                        jugador.getJuego().cambiarTurno();
                        respuesta.put("turno", jugador.getJuego().getTurnoActual());
                        
                        writer.writeUTF(respuesta.toString());
                        break;
                    case "revisarFinTurno" :
                        respuesta = new JSONObject();
                        respuesta.put("cambioTurno", jugador.getJuego().getRondaActual().esNuevoTurno());
                        respuesta.put("turno", jugador.getJuego().getTurnoActual());
                        respuesta.put("tablero", jugador
                                .getJuego()
                                .getTablero()
                                .obtenerFichasTablero());
                        
                        System.out.println(respuesta.toString());                        
                        writer.writeUTF(respuesta.toString());
                        break;
                    case "finalizarTurno" :
                        respuesta = new JSONObject();
                        jugador.getJuego().cambiarTurno();
                        respuesta.put("estado", true);
                        break;                      
                }                        
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            cerrarConexion(cliente);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }        
    }

    private void cerrarConexion(Socket socketCliente) {
        try {
            socketCliente.close();
        } catch (IOException ex) {
                ex.printStackTrace();
        }
    }
}

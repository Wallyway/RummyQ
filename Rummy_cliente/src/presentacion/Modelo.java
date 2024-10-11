package presentacion;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import logica.Cliente;
import logica.DatosJuego;
import logica.Ficha;

public class Modelo {

    private Cliente appCliente;
    private Juego juego;
    private static FichaPanel btnSeleccionado;
    private boolean esperandoPartida;
    private DatosJuego datosJuego;
    private Map<FichaPanel, Pair<Integer, Integer>> fichasModificadas;

    public Modelo() {
        esperandoPartida = true;
    }

    public Cliente getAppCliente() {
        if (appCliente == null) {
            appCliente = new Cliente();
        }
        return appCliente;
    }

    public void iniciar() {
        //Abrir el menu y no el tablero
        getTablero().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getTablero().setVisible(true);
    }

    //---------------------------------------------------    
    public void tomarFicha() {
        Ficha fichaRob = appCliente.robarFicha();
        crearFicha(fichaRob.getNumero(), fichaRob.getColor());
        verificarTurno();
        System.out.println("t act" + datosJuego.getTurnoActual());
    }

    //---------------------------------------------------
    public Juego getTablero() {
        if (juego == null) {
            juego = new Juego(this);
        }
        return juego;
    }

    public void seleccionarFicha(FichaPanel btn) {
        cambiarFicha(btn);
    }

    public void copiarInfoBoton(FichaPanel btnDestino) {

        if (!btnDestino.getText().equals("")) {

            cambiarFicha(btnDestino);
        } else if (btnSeleccionado != null) {

            btnDestino.setText(btnSeleccionado.getText());
            btnDestino.setForeground(btnSeleccionado.getForeground());
            btnDestino.setFont(new java.awt.Font("Tahoma", 0, 30));

            int x = 0, y = 0;
            for (int i = 0; i < juego.getPnlTablero().getComponentCount(); i++) {
                if (juego.getPnlTablero().getComponent(i) == btnDestino) {
                    x = i / 20;
                    y = i % 20;
                }
            }
            fichasModificadas.put(btnSeleccionado, new Pair<>(x, y));

            if (!juego.getPnlTablero().isAncestorOf(btnSeleccionado)) {
                btnSeleccionado.setVisible(false);
            } else {
                btnSeleccionado.setText("");
            }

            cambiarFicha(null);
        }

    }

    public void cambiarVista(String nombreVista) {
        CardLayout layout = (CardLayout) juego.getContentPane().getLayout();
        layout.show(juego.getContentPane(), nombreVista);
    }

    public void iniciarJuego() {
        try {
            String nombreUsuario = juego.getTxtUsuario().getText();
            getAppCliente().conectar(nombreUsuario);
            cambiarVista("vistaEspera");
            Thread esperaThread = new Thread(() -> verificarInicioPartida());
            esperaThread.start();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void verificarInicioPartida() {
        while (esperandoPartida) {
            if (appCliente.verificarInicioJuego()) {
                datosJuego = DatosJuego.obtenerDatosJuegos();
                esperandoPartida = false;
                fichasModificadas = new HashMap<>();
                cargarDatosEnVista();
                cambiarVista("vistaJuego");
                verificarTurno();
            }
        }
    }

    private void cargarDatosEnVista() {
        datosJuego.getFichas().forEach(ficha -> crearFicha(ficha.getNumero(), ficha.getColor()));
    }

    private void crearFicha(String numero, int rgb) {
        JPanel pnlFichas = getTablero().getPnlFichas();
        FichaPanel nuevaFicha = new FichaPanel(numero, new Color(rgb));
        nuevaFicha.addActionListener(juego.getControl());
        pnlFichas.add(nuevaFicha);
        pnlFichas.revalidate();
        pnlFichas.repaint();
    }

    void verificarValidezJugada() {
        if (!appCliente.enviarJugada(formarGrupos())) {
            JOptionPane.showMessageDialog(null, "Jugada incorrecta");
        }
        fichasModificadas.forEach((ficha, pos) -> ficha.setVisible(true));
        redibujarTablero();
        fichasModificadas.clear();
    }

    private void redibujarTablero() {
        Ficha[][] tablero = datosJuego.getTablero();
        
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                FichaPanel fichapnl = (FichaPanel) juego.getPnlTablero().getComponent(i * 20 + j);
                fichapnl.setText("");        

                if (Integer.parseInt(tablero[i][j].getNumero()) != 0) {

                    fichapnl.setText(String.valueOf(tablero[i][j].getNumero()));
                    fichapnl.setForeground(new Color(tablero[i][j].getColor()));
                }
            }
        }
    }

    private void cambiarFicha(FichaPanel ficha) {
        if (btnSeleccionado != null) {
            btnSeleccionado.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        if (ficha != null) {
            ficha.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        btnSeleccionado = ficha;
    }

    private Map<Integer, Map<Ficha, Pair<Integer, Integer>>> formarGrupos() {        
        Map<Integer, Map<Ficha, Pair<Integer, Integer>>> grupos = new HashMap<>();
        Map<Ficha, Pair<Integer, Integer>> fichasGrupo = new HashMap<>();

        try {

            for (int i = 1; i <= juego.getPnlTablero().getComponentCount(); i++) {

                FichaPanel ficha = ((FichaPanel) juego.getPnlTablero().getComponent(i - 1));

                if (ficha.getText().equals("")) {
                    if (fichasGrupo.size() > 0) {
                        grupos.put(grupos.size() + 1, fichasGrupo);
                        fichasGrupo = new HashMap<>();
                    }
                } else {
                    fichasGrupo.put(new Ficha(ficha.getText(), ficha.getForeground().getRGB()), new Pair<>((i - 1) / 20, (i - 1) % 20));

                    if (i % 20 == 0) {
                        grupos.put(grupos.size() + 1, fichasGrupo);
                        fichasGrupo = new HashMap<>();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return grupos;
    }

    private void verificarTurno() {
        if (datosJuego.getMiTurno() != datosJuego.getTurnoActual()) {
            habilitarComponentes(false);
            Thread turnoThread = new Thread(() -> comparadorTurno());
            turnoThread.start();
        }
        else {
            habilitarComponentes(true);
        }
    }

    private void habilitarComponentes(boolean habilitar) {
        for (Component c : juego.getPnlTablero().getComponents()) {
            c.setEnabled(habilitar);
        }

        for (Component c : juego.getPnlFichas().getComponents()) {
            c.setEnabled(habilitar);
        }

        for (Component c : juego.getPnlAcciones().getComponents()) {
            c.setEnabled(habilitar);
        }
    }

    private void comparadorTurno() {
        while (datosJuego.getMiTurno() != datosJuego.getTurnoActual()) {
            if (appCliente.revisarCambioTurno()) {
                redibujarTablero();
                verificarTurno();
            }
            
            //Para que no sature el server
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

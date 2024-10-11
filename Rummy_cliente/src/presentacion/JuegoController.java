package presentacion;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author ASUS
 */
public class JuegoController implements ActionListener{
    
    private Modelo modelo;    

    public JuegoController(Modelo modelo) {
        this.modelo = modelo;        
    }   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == modelo.getTablero().getBtnTomarFicha()){
            modelo.tomarFicha();
        }
        else if(modelo.getTablero().getPnlFichas().isAncestorOf((Component) e.getSource())){
            modelo.seleccionarFicha((FichaPanel) e.getSource());
        }
        else if(modelo.getTablero().getPnlTablero().isAncestorOf((Component) e.getSource())){
            modelo.copiarInfoBoton((FichaPanel) e.getSource());
        }
        else if(modelo.getTablero().getBtnIniciarJuego() == e.getSource()) {
            modelo.iniciarJuego();
        }
        else if(e.getSource() == modelo.getTablero().getBtnVerificar()){
            modelo.verificarValidezJugada();
        }
        else if(e.getSource() == modelo.getTablero().getBtnCancelar()){
            //modelo.cancelarConexion();
        }
    } 
}
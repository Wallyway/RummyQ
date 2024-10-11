package presentacion;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class FichaPanel extends JButton {
    
    private final String numero;
    private final Color color;

    public FichaPanel(String numero, Color color) {
        this.numero = numero;
        this.color = color;
        initComponents();
    } 
    
    private void initComponents(){
        this.setBackground(new java.awt.Color(255, 204, 153));
              
        this.setText(numero);
        this.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        this.setForeground(color);
        this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));          
        
        setPreferredSize(new Dimension(95, 100));     
        setMaximumSize(new Dimension(95,100));
        
        this.setAlignmentX(CENTER_ALIGNMENT);        
        setVisible(true);        
    }    
}


package GUI;

import java.awt.*;
import javax.swing.*;

/**
 * Erzeugt die runden Buttons
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class ROUNDBUTTON extends JButton{
    
    /**
     * Hier wird die Größe des Buttons und der Name festgelegt
     * @param label 
     * Der Name des Buttons
     */
    public ROUNDBUTTON(String label){
        super(label);
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width,  size.height);
        setPreferredSize(size);
        setContentAreaFilled(false);
    }
    
    /**
     * Hier werden die Hintergründe der Buttons gezeichnet und beim drücken lightGray gefärbt
     * @param g 
     * Graphics
     */
    protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {
      g.setColor(Color.lightGray);
    } 
    else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width-1, 
      getSize().height-1);

    super.paintComponent(g);
  }

/**
 * Zeichent die Vordergründe der Buttons mit Größe etc.
 * @param g 
 * Graphics
 */
  protected void paintBorder(Graphics g) {
    g.setColor(getForeground());
    g.drawOval(0, 0, getSize().width-1, 
      getSize().height-1);
  }
}

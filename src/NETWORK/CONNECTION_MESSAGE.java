
package NETWORK;

import GAME.GAME_VARIABLES;
import GUI.FRAME_GAME;
import MAIN.MAIN;
import static NETWORK.SERVER.ssocket;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 * Diese Klasse gibt die Nachricht "warte auf Client" beim Erstellen eines Servers aus
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class CONNECTION_MESSAGE extends JComponent implements KeyListener{
    private String text;
    protected JFrame message = new JFrame("NETWORK");
    private final int dimx = Toolkit.getDefaultToolkit().getScreenSize().width / 5;
    private final int dimy = Toolkit.getDefaultToolkit().getScreenSize().height / 8;
    protected JLabel l;
    private GAME_VARIABLES gui;
    private MAIN main;
    private Toolkit toolkit;
    private Image image;
    private Cursor c;

    /**
     * Hier wird das JFRame für die Message erstellt
     * @param text
     * Der Text der im Feld steht
     * @param tmp
     * GAME_VARiABLES
     * @param tmp2
     * MAIN VARIABLES
     * @throws IOException 
     * Falls das Bild icon.png nicht gefunden wurde
     */
    public CONNECTION_MESSAGE(String text, GAME_VARIABLES tmp, MAIN tmp2) throws IOException {
                        
        toolkit = Toolkit.getDefaultToolkit();
        URL resource = getClass().getResource("/IMG/cursor.png");
        image = ImageIO.read(resource);                           //Cursor wird gesetzt
        c = toolkit.createCustomCursor(image, new Point(1, 1), "img");
        message.setCursor(c);
                 
        this.text = text;                                                       //Text wird gesetzt
        l = new JLabel(text);
        message.add(l);
        //message.setUndecorated(true);
        l.setHorizontalAlignment(SwingConstants.CENTER);                        //Setzt den Text mittig
        l.setVerticalAlignment(SwingConstants.CENTER);
        l.setBounds(dimx, dimy, dimx, dimx);
        message.setVisible(true);                                               //Setzt das Fenster sichtbar
	message.setSize(dimx, dimy);                                            //und die Größe
	message.setLocationRelativeTo(null);
        message.addKeyListener(this);
        message.setResizable(false);
        resource = getClass().getResource("/IMG/icon.png");
        Image icon = ImageIO.read(resource);
        message.setIconImage(icon) ;           //Setzt das Icon
        gui = tmp;
        main = tmp2;
    }
    
    /**
     * Hier wir der aktuelle Text, der angezeigt wird verändert
     * @param text 
     * der Text
     */
    public void updateText(String text){
        this.text = text;
        l.setText(text);
    }
    
    /**
     * Schließt das Fenster
     */
    public void closeText(){
        message.setVisible(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    /**
     * Sorgt dafür, dass bei ESC alles geschlossen wird
     * @param e 
     * KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gui.getConnected() == false && gui.getIsServer() == true && gui.getFirstMulti() == false){
                gui.setSblock(1);                                                       //Wenn ESC gedrückt wird wird Go wieder false gesetzt und der Sblock auf kein Spiel gestartet gesetzt
                gui.setGo(false);
                gui.setNewgameblock(0);                                                 //Außerdem darf ein Neues Spiel wieder gestartet werden
                message.setVisible(false);
                System.out.println("ESC!");
                JOptionPane.showMessageDialog(null,"Server wurde beendet!", "Server", JOptionPane.INFORMATION_MESSAGE);
                gui.setFirstMulti(true);
                try {
                    ssocket.close();                                                    //Ser Server Socket wird geclosed 
                    main.setPlayer(0);                                                  //Und das Multiplayerspiel auf Neutral geändert
                    main.setOldPlayer(0);
                    gui.setIsServer(false);                                             //Außerdem der isServer false gesetzt
                } catch (IOException ex) {
                    Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

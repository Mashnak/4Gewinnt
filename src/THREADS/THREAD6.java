
package THREADS;

import GAME.LOCAL;
import GAME.GAME_VARIABLES;
import GAME.NETWORK;
import GUI.SPIELER;
import MAIN.MAIN;
import NETWORK.SERVER;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Thread für SERVER
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class THREAD6 extends Thread {
    
    private MAIN main;
    private GAME_VARIABLES gui;
    private SPIELER spieler1, spieler2; 
    private Socket zug;
    private boolean isServer;
    private SERVER ser;
    private int port;
    private NETWORK net;
    
    /**
     * Server wird gestartet
     * @param tmp
     * Main Variablen
     * @param tmp2
     * Game Variablen
     * @param tmp3
     * Spieler1 Variablen
     * @param tmp4
     * Spieler2 Variablen
     * @param tmp5
     * Port
     * @param tmp6
     * Zugriff auf das erstellte Server Objekt in Game
     * @param tmp7 
     * Fenster der Netzwerkeinstellungen
     */
    public THREAD6(MAIN tmp, GAME_VARIABLES tmp2, SPIELER tmp3, SPIELER tmp4, int tmp5, SERVER tmp6, NETWORK tmp7){
        main = tmp;
        gui = tmp2;
        spieler1 = tmp3;
        spieler2 = tmp4;
        port = tmp5;
        ser = tmp6;
        net = tmp7;
    }

    
    @Override
    public void run() {
        try {
            System.out.println("Prozess 6 gestartet");
            ser.server(main, gui, spieler1, spieler2, port, ser, net);
            System.out.println("Prozess 6 beendet");
        } catch (InterruptedException ex) {
            Logger.getLogger(THREAD6.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

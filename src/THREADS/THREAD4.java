
package THREADS;

import GAME.GAME_VARIABLES;
import GAME.LOCAL;
import GAME.NETWORK;
import GUI.FRAME_NETWORK;
import GUI.SPIELER;
import MAIN.MAIN;
import NETWORK.CLIENT;
import NETWORK.SERVER;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread für FRAME_NETWORK
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class THREAD4 extends Thread {
    private MAIN main;
    private SPIELER spieler1;
    private SPIELER spieler2;
    private GAME_VARIABLES gui;
    private SERVER server;
    private CLIENT client;
    private NETWORK net;
    private FRAME_NETWORK Einstellungen;
    private LOCAL loc;
    
    /**
     * Thread 4 um das Fenster der Netzwerkeinstellungen zu öffnen
     * @param tmp
     * Main Variablen
     * @param tmp2
     * Spieler1 Variablen
     * @param tmp3
     * Spieler2 Variablen
     * @param tmp4
     * Game Variablen
     * @param tmp5
     * Server kann geschlossen werden und eingestellt werden
     * @param tmp6
     * Client kann geschlossen werden und eingestellt werden
     * @param tmp7
     * Multiplayerspiel kann gestartet werden
     * @param tmp8
     * Fenster der Netzwerkeinstellungen kann gestartet werden
     * @param tmp9 
     * Singleplayerspiel kann genotifyed werden
     */
    public THREAD4(MAIN tmp, SPIELER tmp2, SPIELER tmp3, GAME_VARIABLES tmp4, SERVER tmp5, CLIENT tmp6, NETWORK tmp7, FRAME_NETWORK tmp8, LOCAL tmp9){
        main = tmp;
        spieler1 = tmp2;
        spieler2 = tmp3;
        gui = tmp4;
        server = tmp5;
        client = tmp6;
        net = tmp7;
        loc = tmp9;
    }
    
    @Override
    public void run() {
        synchronized (getClass()) {
            try {
                System.out.println("Prozess 4 gestartet");
                Einstellungen = new FRAME_NETWORK(main, spieler1, spieler2, gui, server,client, net, loc);
                System.out.println("Prozess 4 beendet");
            } catch (IOException ex) {
                Logger.getLogger(THREAD2.class.getName()).log(Level.SEVERE, null, ex);
                gui.setSeeGame(false);
                
            }
    }
    }
}
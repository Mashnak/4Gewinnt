
package THREADS;

import GAME.GAME_VARIABLES;
import GAME.LOCAL;
import GAME.NETWORK;
import GUI.FRAME_LOCAL;
import GUI.SPIELER;
import MAIN.MAIN;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread für FRAME_LOCAL
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class THREAD2 extends Thread {
    MAIN main;
    SPIELER spieler1;
    SPIELER spieler2;
    GAME_VARIABLES gui;
    LOCAL local;
    NETWORK net;
    
    /**
     * 
     * @param tmp
     * MAIN Varibalen
     * @param tmp2
     * SPIELER1 Variablen
     * @param tmp3
     * SPIELER2 Variablen
     * @param tmp4
     * GAME Varibalen
     * @param tmp5
     * LOCAL Zugriff (Lokales Spiel starten)
     * @param tmp6 
     * NETWORK Zugriff (Network beenden)
     */
    public THREAD2(MAIN tmp, SPIELER tmp2, SPIELER tmp3, GAME_VARIABLES tmp4, LOCAL tmp5, NETWORK tmp6){
        main = tmp;
        spieler1 = tmp2;
        spieler2 = tmp3;
        gui = tmp4;
        local = tmp5;
        net = tmp6;
    }
    
    @Override
    public void run() {
        synchronized (getClass()) {
            try {
                System.out.println("Prozess 2 gestartet");
                FRAME_LOCAL Einstellungen = new FRAME_LOCAL(main, spieler1, spieler2, gui, local, net);
                System.out.println("Player = " + main.getPlayer());
                System.out.println("Prozess 2 beendet");
            } catch (IOException ex) {
                Logger.getLogger(THREAD2.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }
}

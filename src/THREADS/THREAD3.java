
package THREADS;

import GAME.LOCAL;
import GAME.GAME_VARIABLES;
import GUI.SPIELER;
import MAIN.MAIN;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread für LOCAL Spiel
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class THREAD3 extends Thread {
    
    MAIN main;
    GAME_VARIABLES gui;
    SPIELER spieler1, spieler2; 
    LOCAL local;
    
    /**
     * Thread3 um ein Singleplayerspiel zu starten
     * @param tmp
     * Main Variablen
     * @param tmp2
     * Game Variablen
     * @param tmp3
     * Spieler1 Variablen
     * @param tmp4
     * Spieler2 Variablen
     * @param tmp5 
     * LOCAL Spiel starten
     */
    public THREAD3(MAIN tmp, GAME_VARIABLES tmp2, SPIELER tmp3, SPIELER tmp4, LOCAL tmp5){
        main = tmp;
        gui = tmp2;
        spieler1 = tmp3;
        spieler2 = tmp4;
        local = tmp5;
    }

    @Override
    public void run() {
          System.out.println("Prozess 3 gestartet");
        synchronized (getClass()) {
          try {   
            local.singleplayer(main, gui, spieler1, spieler2);
        } catch (InterruptedException ex) {
            Logger.getLogger(THREAD3.class.getName()).log(Level.SEVERE, null, ex);
        }
          System.out.println("Prozess 3 gestopt");
    }
    }
}

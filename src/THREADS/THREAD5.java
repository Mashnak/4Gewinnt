
package THREADS;

import GAME.LOCAL;
import GAME.GAME_VARIABLES;
import GAME.NETWORK;
import GUI.SPIELER;
import MAIN.MAIN;
import static NETWORK.SERVER.ssocket;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Thread für NETWORK
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class THREAD5 extends Thread {
    
    private MAIN main;
    private GAME_VARIABLES gui;
    private SPIELER spieler1, spieler2; 
    private Socket zug;
    private boolean isServer;
    private NETWORK net;
    
    /**
     * Startet das Multiplayerspiel
     * @param tmp
     * Main Variablen
     * @param tmp2
     * Game Variablen
     * @param tmp3
     * Spieler1 Variablen
     * @param tmp4
     * Spieler2 Variablen
     * @param tmp5 
     * Network kann geschlossen werden/Züge empfangen/Züge senden
     */
    public THREAD5(MAIN tmp, GAME_VARIABLES tmp2, SPIELER tmp3, SPIELER tmp4, NETWORK tmp5){
        main = tmp;
        gui = tmp2;
        spieler1 = tmp3;
        spieler2 = tmp4;
        net = tmp5;
    }

    public THREAD5() {
    }
    
    @Override
    public void run() {
          System.out.println("Prozess 5 gestartet");
        synchronized (getClass()) {
              try {
                  System.out.println("Hallo");
                  net.multiplayer(main, gui, spieler1, spieler2);
              } catch (InterruptedException ex) {
                  //Logger.getLogger(THREAD5.class.getName()).log(Level.SEVERE, null, ex);
                  gui.setSeeGame(false);
                  net.t.stop();
                  JOptionPane.showMessageDialog(null,"Connection lost...", "Connection Problems", JOptionPane.INFORMATION_MESSAGE);
                  gui.setNewgameblock(0);
                  gui.setSblock(1);
                  try {
                      ssocket.close();
                  } catch (IOException ex1) {
                      Logger.getLogger(THREAD5.class.getName()).log(Level.SEVERE, null, ex1);
                  }
                  synchronized(net) {
                         net.notifyAll();
                    }
                  System.out.println("1");
              } catch (IOException ex) {
                  //Logger.getLogger(THREAD5.class.getName()).log(Level.SEVERE, null, ex);
                  gui.setSeeGame(false);
                  net.t.stop();
                  JOptionPane.showMessageDialog(null,"Connection lost...", "Connection Problems", JOptionPane.INFORMATION_MESSAGE);
                  gui.setNewgameblock(0);
                  gui.setSblock(1);
                  try {
                      if(gui.getIsServer() == true){
                            ssocket.close();
                      }
  
                  } catch (IOException ex1) {
                  }
                  synchronized(net) {
                         net.notifyAll();
                    }
              }
            System.out.println("Prozess 5 beendet\n");
    }
    }
}

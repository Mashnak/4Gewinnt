
package NETWORK;

import GAME.GAME_VARIABLES;
import static NETWORK.SERVER.serve;
import java.io.IOException;
import java.net.ServerSocket;


/**
 * Hier wird in einem neuen Thread auf den Client gewartet, dass dieser connected
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class WAIT_CLIENT extends Thread{
    private final ServerSocket server;
    private final GAME_VARIABLES gui;
    private final SERVER ser;
    
    /**
     * Erhält die benötigten Objekte vom Server geliefert
     * @param tmp
     * Den ServerSocket
     * @param tmp2
     * Die GAME_VARIABLES
     * @param tmp3 
     * Den Server
     */
    public WAIT_CLIENT(ServerSocket tmp, GAME_VARIABLES tmp2, SERVER tmp3){
        server = tmp;
        gui = tmp2;
        ser = tmp3;
    }
    
    @Override
    /**
     * In einem neuen Thread wird hier auf den Client gewartet
     */
    public void run(){
        System.out.println("Warte aug Client!");
        try {
            serve = server.accept();                                            //Warte auf den Client
            gui.setConnected(true);                                             //Connected = true, wenn client connected ist
            synchronized(ser) {                                                 //Sobald ein Client connected ist, wird synchronisiert
                ser.notifyAll();
            }
        } catch (IOException ex) {
            System.out.println("Server wurde geschlossen!");
        }
    }
}


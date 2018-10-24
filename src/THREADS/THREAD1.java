package THREADS;

import GUI.FRAME_GAME;
import MAIN.MAIN;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Thread für FRAME_GAME
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 * Thread1 ist der Prozess für die GUI
 * try catch fängt den Fehler ab
 */
public class THREAD1 extends Thread {


    FRAME_GAME game;
    
    @Override
    /**
     * Hier wird das FRAME_GAME (GUI) gestartet
     */
    public void run() {
        //System.out.println("Prozess 1 gestartet");
        try {
            MAIN main = new MAIN();
            game = new FRAME_GAME(main);
        } catch (IOException ex) {
            Logger.getLogger(THREAD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(THREAD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(THREAD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(THREAD1.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Prozess 1 gestopt");
    }
    
}

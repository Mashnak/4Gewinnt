
package GAME;

import FILEMANAGEMENT.SAVE;
import static GAME.CLASS_FUNCTIONS.ausgabe;
import static GAME.CLASS_FUNCTIONS.spielfeld;
import GUI.FRAME_GAME;
import GUI.SPIELER;
import MAIN.MAIN;
import static NETWORK.CLIENT.client;
import NETWORK.MACHE_ZUG;
import static NETWORK.SERVER.serve;
import static NETWORK.SERVER.ssocket;
import NETWORK.WARTE_ZUG;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Diese Klasse steuert den Multiplayer Spielablauf
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class NETWORK extends ZUG implements ActionListener {
    
    private Socket socket;
    MAIN tmp;
    GAME_VARIABLES tmp2;
    public Timer t = new Timer(60000, this);
    /**
     * Diese Methode regelt den Multiplayer Spielablauf.
     * @param main
     * Zugriff auf alle Variablen der Klasse MAIN
     * @param gui
     * Zugriff auf alle Variablen der Klasse GAME_VARIABLES
     * @param spieler1
     * Zugriff über das Objekt spieler1 auf Spieler
     * @param spieler2
     * Zugriff über das Objekt spieler 2 auf Spieler
     * @throws InterruptedException
     * Für die wait Funktion in Zeile 104
     * @throws IOException 
     * Falls der Autosave nicht erfolgreich war
     */
    public synchronized void multiplayer(MAIN main, GAME_VARIABLES gui, SPIELER spieler1, SPIELER spieler2) throws InterruptedException, IOException{
        spieler1.setSeeStone(false);
        gui.setAbbrechen(false);                //Da ein Neues Spiel erstellt wurde, wird Abbrechen false gesetzt
        main.setPlayer(2);                      //Und Player als Multiplayerspiel gekenzzeichnet (2)
        tmp = main;
        tmp2 = gui;
        ausgabe(main.getFgi(), main.getFgj());
        SAVE save = new SAVE();                 //Objekt für Speichern, Spielzüge und die künstliche Intelligenz werden erstellt
        main.setOldPlayer(main.getPlayer());
        main.setStartbedingung(true);
	gui.setNewgameblock(0);
        int k = 0, x, z = 0; 
        int count = 1;                          //Der Spielzugcount wird 0 gesetzt
        if (gui.getIsServer() == true){         //Falls der Spieler Server ist, bekommt er den Socket vom Server (wegen dem Spielabbruch)
            socket = serve;
        }
        else{                                   //Sonst den Cliet Socket
            socket = client;
        }
        System.out.println("Neues Spiel");
        System.out.println("Anfangsspieler = " + main.getSpieler());
        System.out.println("IsServer ist = " + gui.getIsServer());
	do{     
                if (gui.getIsServer() == true){
                    try {
                        save.savefile("autosave", main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl(), main.getPlayer(), main.getPc(), main.getRekstufe(), spieler1.getR(), spieler2.getR(), spieler1.getG(), spieler2.getG(), spieler1.getB(), spieler2.getB(), spieler1.getSpielstand(), spieler2.getSpielstand(), gui.getPlayBomb(), spieler1.getAbomb(), spieler2.getAbomb());
                    } catch (IOException ex) {
                        Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (gui.getGo() == true){                                                   //go ist true, wenn es erlaubt ist, Züge zumachen. Wenn z.B. einer gewinnt, wird go = false gesetzt d.h das Spiel wird gestoppt
                    if ((gui.getIsServer() == true && main.getSpieler() == 1) || (gui.getIsServer() == false && main.getSpieler() == -1)){  //Hier wird geschaut, ob der Server oder Client am Zug ist
                        if(main.getPc() == 1){                                                                                              //Ist der Computer auf 1 gesetzt wird
                            gui.setWarten(true);
                            if (count == 0) x = main.getFgj() / 2;
                            else{
                                long start = System.currentTimeMillis();
                                x = pczug(main.getFgi(), main.getFgj(), main.getGwzahl(), 0, main.getRekstufe(), main.getSpieler(), gui, count);   //Der Zug des Computer berechnet (siehe ZUG.pczug)
                                long time = System.currentTimeMillis() - start;
                                System.out.println("Die KI hat " + time + "ms gebraucht bei Rekstufe " + main.getRekstufe());
                                if (time < (2000 / (9 + z - main.getRekstufe())) && time != 0){
                                    main.setRekstufe(main.getRekstufe() + 1);
                                    z++;
                                }
                            }
                        }
                        else{
                            spieler1.setSeeStone(true);
                            gui.setWarten(false);                                           //warten wird false gesetzt, damit dem Spieler erlaubt wird, den Stein zu bewegen und einzuwerfen       
                            this.wait();                                                    //Wartet bis Zug ausgewählt wrde
                            if(main.getPlayer() == 0){                                      //Wurde ein Neues Spiel gestartet und Enter gedrückt, bricht es dieses Teilprogramm ab, damit ein neues gestartet werden kann
                                gui.setAbbrechen(true);
                                k = 3;                                                      //k wird drei gesetzt, damit keine Gewinnmeldung ausgegeben wird
                                System.out.println("Das Spiel wird angehalten");
                            } 
                            x = gui.getEinwurfX();  
                        }                                                                                                   //Zug vom Einwurf wird in x gespeichert
                        if (gui.getAbbrechen() == false){
                            zug(x, main.getSpieler(), main.getFgi(), gui);                                              //Wird der Zug ausgeführt (entweder Pczug, oder der Zug vom Spieler)
                            spielfeld(main.getFgi(), main.getFgj());                                                        //Das feld, worin der Pc seinen Zug berechnet, wird dem Spielfeld übergeben
                            spielzug(gui.getLzugx(), gui.getLzugy(), main.getSpieler());                                //Hier wird der letzte Zug makiert
                            k = gewonnen(main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl());            //Es wird überprüft, ob gewonnen wurde
                            try{
                            MACHE_ZUG.macheZug(main, gui, spieler1, spieler2, socket, count, x, k);                         //Und über das Netzwrk verschickt
                            }catch(NullPointerException ex){
                                main.setPlayer(0);                                                                          //Falls etwas schiefläuft wird das Spiel sofort abgebrochen
                                gui.setAbbrechen(true);
                            }
                            count++;                                                                                        //Der Count wird um eins erhöht
                        }
                        
                        }   
                    else {                                                                                              //Wenn man im Multiplayer nicht am Zug ist
                            t.start();                                                                                  //Wartet man. Der Timer gibt an wie lange gewartet wurd, bevor abgebrochen wird
                            gui.setNewgameblock(1);
                            gui.setSblock(3);
                            try{
                            x = WARTE_ZUG.warteZug(main, gui, spieler1, spieler2, socket, count);                       //Wird auf ein Zug gewartet, der über das Netzwerk versendet wird
                            }catch(NullPointerException ex){
                                System.out.println("Nullpointer");                                                      //Falls ein Fehler auftritt, wird ebenfalls das Spiel sofort abgebrochen und Connection
                                x = 1;                                                                                  //lost ausgeben
                                main.setPlayer(0);
                                gui.setSeeGame(false);
                                JOptionPane.showMessageDialog(null,"Connection lost...", "Connection Problems", JOptionPane.INFORMATION_MESSAGE);
                                k = 3;
                                if (gui.getIsServer() == true){
                                    ssocket.close();
                                }
                                gui.setSblock(1);
                                gui.setNewgameblock(0);
                                gui.setGo(false);
                                gui.setWarten(false);
                                gui.setConnected(false);
                                socket.close();
                                return;
                            }
                            gui.setNewgameblock(0);
                            gui.setSblock(((gui.getIsServer() == true)? 0: 4));
                            t.stop();
                            zug(x, main.getSpieler(), main.getFgi(), gui);                                      //Wird der Zug ausgeführt (entweder Pczug, oder der Zug vom Spieler)
                                    spielfeld(main.getFgi(), main.getFgj());                                        //Das feld, worin der Pc seinen Zug berechnet, wird dem Spielfeld übergeben
                            spielzug(gui.getLzugx(), gui.getLzugy(), main.getSpieler());                        //Hier wird der letzte Zug makiert
                            k = gewonnen(main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl());    //Es wird überprüft, ob gewonnen wurde
                            count++;                                                                                //in x gespeichert und der count erhöht
                            System.out.println("Client ist dran!");
                            spieler1.setSeeStone(true);
                        }  
                     //}
                    if (gui.getAbbrechen() == false){                                       //Wenn keine Fehler aufgetreten sind bzw. nicht abgebrochen wurde                                                                                                       //Falls alles okay ist wird der Spieler getauscht
                            spieler1.setSeeStone(false);
                            main.setSpieler(main.getSpieler() * (-1));                      //Und falls es der Server ist ein Autosave durchgeführt   
                            ausgabe(main.getFgi(), main.getFgj());   
                        if (zaehler(main.getFgi(), main.getFgj()) == main.getFgi() * main.getFgj() && k != 1){   //Falls das Spielfeld voll ist und dabei niemand gewonnen hat --> Unenstchieden, dann wird k = 2 gesetzt und abgebrochen
                            k = 2;
                        }
                    }
                }
        } while (k != 1 && k != 2 && k != 3);                                                                       //Falls gewonnen wurde, wird k mit 1 versehen und die Schleife wird verlassen, falls Unentschieden mit 2 und bei Abbrechen mit 3
        main.setRekstufe(main.getRekstufe() - z);
        if (k == 2){
            gui.setGewonnen(-1);                                                                                    //Damit nacher die Richtige Meldung ausgeben wird (siehe FRAME_GAME Z. 235)
        }
        else if (k == 3){
            System.out.print("Spiel wurd abbgebrochen!");
        }
        else{
            spieler1.setSeeStone(false);
            main.setSpieler(main.getSpieler() * (-1));                                                              //Spieler wird getauscht, für die korrekte Gewinnmeldung
            System.out.println("Spieler " + main.getSpieler() + " hat gewonnen!\n");
            gui.setGewonnen(1);                                                                                     //Gewonnen wird 1 gesetzt, damit die FRAME_GAME ebenfalls anhählt
            agewonnen(main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl());                       //Die Stelle, an der Gewonnen wurde, wird makiert
            spielfeld(main.getFgi(), main.getFgj());                    
            ausgabe(main.getFgi(), main.getFgj());
            main.setPlayer(0);                                                                                      //Das Spiel wird nun abgebrochen (MAIN.player = 0)
            System.out.println("Das Spiel wurd beendet!");
            if (main.getPlayer() == 0){                                                                             //Das Autosave wird bei beendetem Spiel gelöscht
                    File datei = new File("./autosave");
                    System.out.println(datei);
                    if (datei.exists()) {
                        datei.delete();
                    }
                }
        }
        socket.close();
        gui.setNewgameblock(0);
        gui.setConnected(false);
    }
    
    /**
     * Ist für das korrekte Abbrechen des Spieles nach dem Timeout zuständig
     * @param e 
     * ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(null, "Der Gegner antwortet nicht mehr!\n Das Spiel wird nun geschlossen.","Timeout",JOptionPane.INFORMATION_MESSAGE);
         t.stop();                              //Falls die Zeit abgelaufen ist wird eine Fehelermeldung ausgeben und der Timer gestoppt
         if (tmp2.getIsServer() == true){       //Falls der Server gewartet hat
             try {
                 ssocket.close();               //Wird der ServerSocket geschlossen
             } catch (IOException ex) {
                 Logger.getLogger(NETWORK.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
        try {
            socket.close();                     //Ansonsten wird der Socket (entweder vom Server oder Client) geschlossen
            tmp.setPlayer(0);
        } catch (IOException ex) {              //Und Fehler gecatched
            Logger.getLogger(NETWORK.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

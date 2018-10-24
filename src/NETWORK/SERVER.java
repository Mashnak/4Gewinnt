package NETWORK;

import GAME.GAME_VARIABLES;
import GAME.KI;
import static GAME.CLASS_FUNCTIONS.spielfeld;
import GAME.NETWORK;
import GUI.SPIELER;
import MAIN.MAIN;
import THREADS.THREAD5;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * Hier wird ein ServerSocket erstellt und auf den Client, durch WAIT_CLIENT gewartet
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class SERVER {
    public static Socket serve;
    public static ServerSocket ssocket;
    public static boolean ok = false;
	
	
    /**
     * Neuer Server wird erstellt, auf den Client gewartet und schließlich alle Spieleinstellungen übertragen
     * @param main
     * Zugriff auf MAIN Variablen
     * @param gui
     * Zugriff auf GAME_VARIABLES
     * @param spieler1
     * Zugriff auf Spieler1
     * @param spieler2
     * Zugriff auf Spieler2
     * @param port
     * Der Port
     * @param ser
     * Das Objekt SERVER selbst
     * @param net
     * Zugriff auf das Netzwerkspiel
     * @return
     * - true: Die Übertragung war erfolgreich
     * return - false: Es ist etwas schief gelaufen
     * @throws InterruptedException 
     * Für die Zeile 66 this.wait(), falls die WAIT_CLIENT Klasse den Client empfangen hat
     */
    public synchronized boolean server(MAIN main, GAME_VARIABLES gui, SPIELER spieler1, SPIELER spieler2, int port, SERVER ser, NETWORK net) throws InterruptedException {
		try {  
                    gui.setFirstMulti(false);                                                       //Spielfeld wird unsichtabr gemacht (falls es sichtbar war)
                    gui.setSeeGame(false);
                    System.out.println(main.getSpieler());
                        ssocket = new ServerSocket();                                               //Der Server Socket wird auf übergebenem Port geöffnet
                        System.out.println("Versuche Server zu starten!");
			ssocket = new ServerSocket(port);
			System.out.println("Server gestartet!");                                    //Und die Connection Message gestartet
                        CONNECTION_MESSAGE m = new CONNECTION_MESSAGE("Warte auf Client! Drücke ESC, um den Server zu beenden!", gui, main);
			//JOptionPane.showMessageDialog(null,"Warte auf Client...\nDrücke ESC, um den Server zu beenden!", "Server", JOptionPane.INFORMATION_MESSAGE);
                        gui.setNewgameblock(1);                                                     //Nun darf kein weiteres Spiel gestartet werden
                        gui.setSblock(3);                                                           //Und weder geladen nocht gepsichert werden
                        new WAIT_CLIENT(ssocket, gui, ser).start();                                 //WAIT_CLIENT wird gestartet und wartet, bis ein Client connected ist
                        this.wait();
                        JOptionPane.getRootFrame().dispose();
                        System.out.println("Client accepted!");
                        //JOptionPane.showMessageDialog(null,"Client ist connected!\nÜbertrage Daten...", "Server", JOptionPane.INFORMATION_MESSAGE);
                        m.updateText("Client ist connected! Übertrage Daten...");
			
			//Streams                                                                   //Wenn der Client connected ist, wird Writer und Reader intialisiert und gestartet
			OutputStream out = (OutputStream) serve.getOutputStream();
			PrintWriter writer = new PrintWriter(out);
			
			InputStream in = (InputStream) serve.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			//_____________________________________________
			
			String s;                                               //Ganzen Strings werden übertragen!
                        s = "INIT-BEGIN\n";
                        m.updateText(s);
                        writer.write(s);
                        writer.flush();
                        s = "TIMEOUT-60000\n";                                  //Timeout
                        m.updateText(s);
                        writer.write(s);
                        writer.flush();
                        s = "BOARDSIZE-C" + main.getFgj() + "-R" + main.getFgi() + "\n"; //Spielfeldhöhe und Breite
                        m.updateText(s);
                        System.out.println("Länge = " + s.length());
                        writer.write(s);
                        writer.flush();
                        s = "WINSIZE-" + main.getGwzahl() + "\n";               //Gewinnanzahl
                        m.updateText(s);
                        writer.write(s);
                        writer.flush();
                        s = "STARTPLAYER-P" + ((main.getSpieler() == -1)? 2: 1) + "\n"; //Anfangsspieler
                        m.updateText(s);
                        writer.write(s);
                        writer.flush();
                        for (int j = 0; j < main.getFgj(); j++){                //Das Feld, inwiefern eins gekaden wurde
                            for (int i = main.getFgj() - 1; i >= 0; i--){
                                if (KI.feld[i][j] != 0){
                                    s = "LOAD-P" + KI.feld[i][j] + "-C" + (j + 1) + "\n";
                                    m.updateText(s);
                                    writer.write(s);
                                    writer.flush();
                                }
                            }
                        }
                        s = "INITCHECK\n";                                      //Dem Client wird mitgeteilt, die Daten zu überprüfen
                        m.updateText(s);
                        writer.write(s);
                        writer.flush();
                        s = reader.readLine();
                        System.out.println(s);
                        //writer.flush();
                        //System.out.println(s);
                        if (s.equals("CHECK-OK")){                              //Wenn der Client CHECK_OK zurück gesendet hat wird INIT beendet
                            s = ("INIT-END\n");
                            m.updateText(s);
                            System.out.println("7");
                            gui.setConnected(true);                             //Connected auf TRUE gesetzt
                            writer.write(s);
                            writer.flush();
			System.out.println("Alles erfolgreich übertragen!");
                        m.updateText("ENDE");
                        m.closeText();
                        JOptionPane.showMessageDialog(null,"Daten erfolgreich übertragen!", "Server", JOptionPane.INFORMATION_MESSAGE);
                        spielfeld(main.getFgi(), main.getFgj());                                                      //Das feld wird an spielfeld für das Zeichnen übergeben
                        gui.setGo(true);                                        //Singnalisiert das es gleich beginnt
                        main.setPlayer(2);                                      //Multiplayerspiel ausgewählt
                        gui.setAbbrechen(false);        
                        gui.setNewgameblock(0);                                 //Neues Spiel beginnen wieder erlaubt
                        //gui.setDontreload(false);    
                        gui.setSeeGame(true);                                   //Das Spielfeld wird sichtbar gemacht
                        gui.setSblock(0);                                       //Und der Speicherblock wird deaktiviert
                        new THREAD5(main, gui, spieler1, spieler2, net).start();//Anschließend wird das NETWORK über den Thread5 gestartet und true zurück geben (ALLES WAR OK)
                                return true;
                        }
                        else{                                                   //Wenn CHECK_FAILED zurück kam wird die signalisiert und false zurück gegeben
                            JOptionPane.showMessageDialog(null,"Ein Fehler ist aufgetreten!", "Server", JOptionPane.INFORMATION_MESSAGE);
                            ssocket.close();
                            gui.setIsServer(false);
                            System.out.println("FEHLER!!!");
                            return false;
                        }
                         }catch (IOException e) {
                             System.out.println("Dieser Port ist leider schon vergeben!"); //Falls der ServerSocket nicht gestartet werden konnte, weil der Server schon existiert
                             JOptionPane.showMessageDialog(null,"Dieser Port ist leider schon vergeben!", "Server", JOptionPane.INFORMATION_MESSAGE);
                         }
		return false;
        }
}


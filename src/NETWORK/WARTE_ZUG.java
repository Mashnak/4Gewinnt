
package NETWORK;

import GAME.GAME_VARIABLES;
import GUI.SPIELER;
import MAIN.MAIN;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Hier wird versucht mit einem Client zu kommunizieren, wenn dieser connected ist, um ihm alle wichtigen Daten zu übertragen!
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class WARTE_ZUG {

    /**
     * 
     * @param main
     * Zugriff auf das Objekt der Klasse MAIN
     * @param gui
     * Zugriff auf das Objekt der Klasse GUI
     * @param spieler1
     * Zugriff auf das Objekt der Klasse Spieler (1)
     * @param spieler2
     * Zugriff auf das Objekt der Klasse Spieler (2)
     * @param socket
     * Zugriff auf den Socket
     * @param count
     * Die Nummer des aktuellen Spielzugs
     * @return
     * Gibt die x Koordinate des Spielzugs zurück
     * @throws IOException 
     * Exception für den Writer/Reader
     */
    public static int warteZug(MAIN main, GAME_VARIABLES gui, SPIELER spieler1, SPIELER spieler2, Socket socket, int count) throws IOException{
        gui.setWarten(true);
        int x = 0;
        int y = 0;
        String s;
        OutputStream out = (OutputStream) socket.getOutputStream(); //Writer und REader werden intialisiert und gestartet
	PrintWriter writer = new PrintWriter(out);
			
	InputStream in = (InputStream) socket.getInputStream();
	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        s= reader.readLine();
        while (s.equals("INIT-END")){                       //Wartet solange, bis der Zug angekommen ist(davor ist s INIT_END)
        s= reader.readLine();
        if (main.getPlayer() == 0){                         //falls das Spiel abgebrochen wurde, wirdhier beendet
            break;
        }
        }
        if (main.getPlayer() == 2){
        if (s.charAt(1) - 48 >= 0 && s.charAt(1) - 48 < 10){    //Liest den Zug aus
            y++;
        }
        x = s.charAt(11 + y) - 48;  
        if (s.charAt(12 + y) - 48 >= 0 && s.charAt(12 + y) - 48 < 10){
            x = (s.charAt(11 + y) - 48) * 10 + (s.charAt(12 + y) - 48);
        }
        System.out.println(s);
        gui.setWarten(false);
        }
        return x;                                               //gibt den richtigen X Wert zurück, wo der Zug ausgeführt werden soll
    }
}

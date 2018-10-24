
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
 * Hier wird der Zug übertragen, egal ob von Client, oder Server
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class MACHE_ZUG {
    /**
     * Der Zug der ausgewählt wird, wird über das Netzwerk verschickt
     * @param main
     * Zugriff auf MAIN Variablen
     * @param gui
     * Zugriff auf GAME_VARIABLES
     * @param spieler1
     * Zugriff auf Spieler1
     * @param spieler2
     * Zugriff auf Spieler2
     * @param socket
     * Zugriff auf das Socket (egal ob Server oder Client (wird dementsprechend richtig verschickt))
     * @param count
     * Der Spielcounter
     * @param x
     * x Koordinate des Zuges
     * @param gewonnen
     * wenn durch einen Zug gewonnnen wurde, wird das hier mitgeliefert und eine andere Message gesendet
     * @throws IOException 
     * Für Reader/Writer Probleme
     */
    public static void macheZug(MAIN main, GAME_VARIABLES gui, SPIELER spieler1, SPIELER spieler2, Socket socket, int count, int x, int gewonnen) throws IOException{
        System.out.println("Test");
        OutputStream out = (OutputStream) socket.getOutputStream();             //Printer und Writer werden intialisiert und gestartet
	PrintWriter writer = new PrintWriter(out);
			
	InputStream in = (InputStream) socket.getInputStream();
	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        if (main.getPlayer() == 2){                                             //Der Zug wird übertragen
            String s = "";                                                      //Sende Zug bzw. Gewonnennaricht
            if (gewonnen == 1){
                s += count + "-PLAY-P" + ((main.getSpieler() == 1)? 1: 2) + "-C" + x + "-WIN_" + ((main.getSpieler() == 1)? 1: 2) + " \n";
            }
            else{
                s += count + "-PLAY-P" + ((main.getSpieler() == 1)? 1: 2) + "-C" + x + "-CONT\n";
            }
            System.out.println(s);
            writer.write(s);
            writer.flush();
            System.out.println("Zug gesendet!");
        }
    }
}

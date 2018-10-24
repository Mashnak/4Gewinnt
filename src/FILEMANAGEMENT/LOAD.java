
package FILEMANAGEMENT;

import static GAME.CLASS_FUNCTIONS.*;
import static GAME.KI.feld;
import GAME.GAME_VARIABLES;
import GAME.LOCAL;
import GAME.NETWORK;
import GUI.FRAME_NETWORK;
import GUI.SPIELER;
import MAIN.MAIN;
import NETWORK.CLIENT;
import NETWORK.SERVER;
import THREADS.*;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;


/**
 * In dieser Klasse wird das Laden des Spieles übernommen!
 * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
 * @version 6.0
 */
public class LOAD{  
     public int laden = 0;
    /**
     * Diese Funktion übernimmt das Laden einer Datei.
     * @param name
     * Der Name der Datei, die geladen werden soll
     * @param main
     * Zugriff auf das Objekt der Klasse MAIN
     * @param spieler1
     * Zugriff auf das Objekt der Klasse Spieler (1)
     * @param spieler2
     * Zugriff auf das Objekt der Klasse Spieler (2)
     * @param gui
     * Zugriff auf das Objekt der Klasse Spieler GAME_VARIABLES
     * @param server
     * Bekommt den Server, um diesen zu staren, falls es sich um ein Multiplayer Spiel handelt
     * @param client
     * Bekommt den Client um diesen beim Laden schließen zu können
     * @param local
     * Damit kann das Singleplayerspiel gestartet werden
     * @param net
     * Damit kann ein Multiplayerspiel gestartet werden
     * @param Einstellungen
     * Zugriff auf Port und Ip
     * @return
     * false, falls etwas schief gelaufen ist<br>
     * true, wenn alles erfolgreich geladen wurde
     * @throws FileNotFoundException
     * Falls die Datei nicht exisitiert, wird eine Exception geworfen
     * @throws IOException 
     * Falls ein Fehler beim FileInputStream auftritt, wird eine Exception gworfen
     * @throws InterruptedException
     * Falls der Server nicht gestartet werden kann, wird eine Exception geworfen
     */
    public boolean loadfile(String name, MAIN main, SPIELER spieler1, SPIELER spieler2, GAME_VARIABLES gui, SERVER server, CLIENT client, LOCAL local, NETWORK net, FRAME_NETWORK Einstellungen) throws FileNotFoundException, IOException, InterruptedException{                                           //Spielstand laden
       byte zeichen;

       int check = 0,  tmp;
       FileInputStream load = new FileInputStream("./SAVE/" + name);                                                                                //Es wird nach der Datei gesucht, die ausgewählt wurde
         for (int i = 0; i < 4; i++){                                                                                                   //Hier wird überprüft, ob es sich um eine .sav Datei handelt vom Spiel
             zeichen = (byte)load.read();
             check = +(int)zeichen;
         }
         if (check != 75){
             System.out.print("Ein Fehler beim Laden der Datei ist aufgetreten!\n");                                                    //Falls eine falsche .sav Datei geladen werden soll, gibt es ein Error
             UIManager UI=new UIManager();  
             UI.put("OptionPane.background",new ColorUIResource(255,255, 255));
             UI.put("Panel.background",new ColorUIResource(255,255,255));
             UIManager.put("Button.background", Color.white);
             JOptionPane.showMessageDialog(null,"Laden fehlgeschlagen!", "Laden", JOptionPane.INFORMATION_MESSAGE);                     //und eine Fehlermeldung wird ausgegeben
             return false;                                                                                                              //Außerdem wird false zurück gegeben
         }
         else{
            zeichen = (byte)load.read();                                                                                                 //Hier wird die Datei Byte für Byte den entsprechenden Variablen
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();                                                                                                 //zugeordnet (MAIN main, SPIELER spieler, GAME_VARIABLES gui)
            main.setSpieler((int)zeichen - 48);
            if (main.getSpieler() == 2){
                 main.setSpieler(-1);
            }
            zeichen = (byte)load.read();
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            main.setFgi((int)zeichen - 48 + (tmp * 10));                                                                                 //Spielstand wird Zeichen für Zeichen ausgelesen

            zeichen = (byte)load.read();                                                                                                 //Spielfeldbreite
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            main.setFgj((int)zeichen - 48 + (tmp * 10));

            zeichen = (byte)load.read();                                                                                                //Spielfeldhöhe
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            main.setGwzahl((int)zeichen - 48 + (tmp * 10));

            zeichen = (byte)load.read();                                                                                                //Startspieler
            main.setPlayer((int)zeichen - 48);

            zeichen = (byte)load.read();
            main.setPc((int)zeichen - 48);

            zeichen = (byte)load.read();                                                                                                //Reursionsstufe
            main.setRekstufe((int)zeichen - 48);

            zeichen = (byte)load.read();                                                                                                //Farbe Spieler 1
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            tmp = (int)zeichen - 48 + (tmp * 10);
            zeichen = (byte)load.read();
            spieler1.setR((int)zeichen - 48 + (tmp * 10));

            zeichen = (byte)load.read();                                                                                                
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            tmp = (int)zeichen - 48 + (tmp * 10);
            zeichen = (byte)load.read();
            spieler2.setR((int)zeichen - 48 + (tmp * 10));

            zeichen = (byte)load.read();
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            tmp = (int)zeichen - 48 + (tmp * 10);
            zeichen = (byte)load.read();
            spieler1.setG((int)zeichen - 48 + (tmp * 10));

            zeichen = (byte)load.read();                                                                                                //Farbe Spieler 2
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            tmp = (int)zeichen - 48 + (tmp * 10);
            zeichen = (byte)load.read();
            spieler2.setG((int)zeichen - 48 + (tmp * 10));

            zeichen = (byte)load.read();
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            tmp = (int)zeichen - 48 + (tmp * 10);
            zeichen = (byte)load.read();
            spieler1.setB((int)zeichen - 48 + (tmp * 10));

            zeichen = (byte)load.read();
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            tmp = (int)zeichen - 48 + (tmp * 10);
            zeichen = (byte)load.read();
            spieler2.setB((int)zeichen - 48 + (tmp * 10));

            zeichen = (byte)load.read();                                                                                                //Spielstand Spieler 1   
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            spieler1.setSpielstand((int)zeichen - 48 + (tmp * 10));

            zeichen = (byte)load.read();                                                                                                //Spielstand Spieler 2
            tmp = (int)zeichen - 48;
            zeichen = (byte)load.read();
            spieler2.setSpielstand((int)zeichen - 48 + (tmp * 10));
            
            zeichen = (byte)load.read();                                                                                                //Bombing aktiviert
            gui.setPlayBomb((int)zeichen - 48);
            
            zeichen = (byte)load.read();                                                                                                //Anzahl Bomben Spieler 1
            spieler1.setAbomb((int)zeichen - 48);
            
            zeichen = (byte)load.read();                                                                                                //Anzahl Bomben Spieler 2
            spieler2.setAbomb((int)zeichen - 48);

            for (int i = 0; i< main.getFgi(); i++){                                                                                     //Das Spielfeld wird eingelesen
                for(int j = 0; j < main.getFgj(); j++){
                    zeichen = (byte)load.read();
                    feld[i][j] = (int)zeichen - 48;
                }
            } 
            gui.setRadius(((gui.getDimx() * 2) / 3) / (3 * main.getFgi() / 2 + main.getFgj()));
            gui.setOvalx(gui.getDimx() / 2 - ((main.getFgj() * gui.getRadius()) / 2));
            System.out.println("Erfolgreich geladen!\n");                                                                                   //Falls alles erfolgreich war
            System.out.println("Spieler = " + main.getSpieler() + " FGI = " + main.getFgi() +  " FGJ = " + main.getFgj() + " Gwzahl = " + main.getGwzahl() + "\n");                                                                             //Position für den Ball oben wird gesetzt
            UIManager UI=new UIManager();
            UI.put("OptionPane.background",new ColorUIResource(255,255, 255));
            UI.put("Panel.background",new ColorUIResource(255,255,255));
            UIManager.put("Button.background", Color.white);
            if (main.getPlayer() == 1){
                JOptionPane.showMessageDialog(null,"Singleplayer Spiel:\nLaden erfolgreich!", "Laden", JOptionPane.INFORMATION_MESSAGE);    //Ein Singleplayerspiel wurde geladen
                spielfeld(main.getFgi(), main.getFgj());
                this.laden = 1;                                                                                                             //Signalisiert der FRAME_GAME, dass das Spiel geladen wurde                                       
                gui.setWarten(false);                                                                                                       //und dass Sie nun das Spielfeld zeichnen kann!
                new THREAD3(main, gui, spieler1, spieler2, local).start();  
                gui.setSeeGame(true);                                                                                                       //Und somit wird Thread 3 gestartet
            }
            else if (main.getPlayer() == 2){
                JOptionPane.showMessageDialog(null,"Multiplayer Spiel:\nLaden erfolgreich!", "Laden", JOptionPane.INFORMATION_MESSAGE);     //Ein Multiplayerspiel wurd geladen
                gui.setSeeGame(false);
                gui.setWarten(true);
                new THREAD6(main, gui, spieler1, spieler2, 50001, server, net).start();                                                     //Der Server wird also gestartet                                                                                  //und alle Steine werden dem Spielfeld übergeben                                                                         //Und somit Thread 5 gestartet
            }                                                                                                                                   
        }
                //gui.setWarten(false);                                                                                                                                                                                                    
         return true;                                                                                                                       //War alles erfolgreich, wird true zurück gegeben      
    }
}



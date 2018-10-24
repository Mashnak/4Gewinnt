
package GAME;


import FILEMANAGEMENT.SAVE;
import static GAME.CLASS_FUNCTIONS.*;
import GUI.FRAME_GAME;
import GUI.SPIELER;
import MAIN.MAIN;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Diese Klasse steuert den Singleplayer Spielablauf
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class LOCAL extends ZUG{
    MAIN main = null;
    
    /**
     * Hier wird der gesammte Spielablauf des Singleplayers bzw des lokalen Spieles gereglt
     * @param main
     * Zugriff auf alle Variablen der Klasse MAIN 
     * @param gui
     * Zugriff auf alle Variablen der Klasse GAME_VARIABLES
     * @param spieler1
     * Spielerfarbe 1
     * @param spieler2
     * Spielerfarbe 2
     * @throws InterruptedException 
     * Für LOCAL.class.wait() (Zeile 55)
     */    
    public synchronized void singleplayer(MAIN main, GAME_VARIABLES gui, SPIELER spieler1, SPIELER spieler2) throws InterruptedException{
        spieler1.setSeeStone(false);
        main.setPlayer(1);                                                          //Singleplayer wird gesetzt
        gui.setAbbrechen(false);
        System.out.println("Spieler = " + main.getSpieler() + " Pc = " + main.getPc());
        SAVE save = new SAVE();                                                     //Objekt für Speicher, Laden und künstliche Intiligenz werden erzeugt
        main.setStartbedingung(true);
        main.setOldPlayer(main.getPlayer());
	gui.setNewgameblock(0);
        int k = 0, x = -1, z = 0, count = 1;                                        //k gibt an, ob ein Spieler gewonnen hat, dann wird die zweite Schleife (das eigentliche Spiel) verlassen
        ausgabe(main.getFgi(), main.getFgj());
        System.out.println("Neues Spiel");
        do{     
                try {
                    save.savefile("autosave", main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl(), main.getPlayer(), main.getPc(), main.getRekstufe(), spieler1.getR(), spieler2.getR(), spieler1.getG(), spieler2.getG(), spieler1.getB(), spieler2.getB(), spieler1.getSpielstand(), spieler2.getSpielstand(), gui.getPlayBomb(), spieler1.getAbomb(), spieler2.getAbomb());
                } catch (IOException ex) {                              //Nach jedem Zug wird ein autosave gemacht
                Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                            }
                if (gui.getGo() == true){                                           //go ist true, wenn es erlaubt ist, Züge zumachen. Wenn z.B. einer gewinnt, wird go = false gesetzt d.h das Spiel
                    if (main.getPc() == 1 && main.getSpieler() == -1){              //Wenn im Singleplayer gegen einen PC gespielt wird, dann ist PC = 1 und automatisch PC = Spieler -1
                        gui.setWarten(true);                                        //Hier wird dem Spieler dagegen verboten, den Stein zu bewegen bzw. einzuwefen!
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
                    else if (((main.getSpieler() == 1 || main.getSpieler() == -1) && main.getPc() == 0) || (main.getSpieler() == 1 && main.getPc() == 1)){  //Hier wird geschaut ,ob Spieler vs. Spieler oder Spieler vs. Computer spielt (pc = 0 heißt gegen Spieler)    
                        spieler1.setSeeStone(true);
                        gui.setWarten(false);                                       //GUI_GAME.warten wird false gesetzt, damit dem Spieler erlaubt wird, den Stein zu bewegen und einzuwerfen 
                        //while (FRAME_GAME.ok == 0){                             
                        System.out.println("System wartet");
                        this.wait();                                                //Wartet bis Zug ausgewählt wrde
                        System.out.println("System wurde genotifyed mit Player = " + main.getPlayer());
                        if(main.getPlayer() == 0){                                  //Wurde ein Neues Spiel gestartet und Enter gedrückt, bricht es dieses Teilprogramm ab, damit ein neues gestartet werden kann
                            gui.setAbbrechen(true);
                            k = 3;                                                  //k wird drei gesetzt, damit keine Gewinnmeldung ausgegeben wird
                            System.out.println("Das Spiel wird angehalten");
                        } 
                        x = gui.getEinwurfX();                                      //x wird der Einwurf beim drücken von Enter bzw. der Maus zugwiesen.
                    }
                    if (gui.getAbbrechen() == false && gui.getBomb() == false){     //Nur wenn das Spiel nicht abbgebrochen wurde, oder die Bombe nicht aktiviert wurde                                       
                        if (check(x, main.getSpieler(), main.getFgi()) == -9){   //Es wird geschaut, ob der Zug ausführbar ist               
                                                                                    //Wenn nicht, wird der Spieler nicht gewechselt und er darf noch einmal ziehen
                        }
                        else{                                                       //Falls alles okay ist
                            count++;
                            zug(x, main.getSpieler(), main.getFgi(), gui);      //Wird der Zug ausgeführt (entweder Pczug, oder der Zug vom Spieler)
                            spielfeld(main.getFgi(), main.getFgj());                //Das feld, worin der Pc seinen Zug berechnet, wird dem Spielfeld übergeben
                            spielzug(gui.getLzugx(), gui.getLzugy(), main.getSpieler());                        //Hier wird der letzte Zug makiert                                                                              
                            k = gewonnen(main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl());    //Es wird überprüft, ob gewonnen wurde
                            spieler1.setSeeStone(false);
                            main.setSpieler(main.getSpieler() * (-1));              //Und der Spieler getauscht
                            ausgabe(main.getFgi(), main.getFgj());   
                        }
                        if (zaehler(main.getFgi(), main.getFgj()) == main.getFgi() * main.getFgj() && k != 1){  //Falls das Spielfeld voll ist und dabei niemand gewonnen hat --> Unenstchieden, dann wird k = 2 gesetzt und abgebrochen
                            k = 2;
                        }
                    }
                    else if (gui.getAbbrechen() == false && gui.getBomb() == true){ //Wenn nicht Abbgebrochen wurde und die Bombe gesetzt wurde
                        KI.feld[gui.getEinwurfY()][gui.getEinwurfX()] = 0;          //Die Ausgewählte Stelle wird zu einer 0 = leer
                        CLASS_FUNCTIONS.bomb(gui.getEinwurfX(), gui.getEinwurfY());  //Im Spielfeld werden die Lücken geschlossen
                        spielfeld(main.getFgi(), main.getFgj());                    //Und auf das Anzeigespielfeld übertragen
                        ausgabe(main.getFgi(), main.getFgj()); 
                        System.out.println(main.getSpieler());
                        k = gewonnen(main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl()); //Es wird überprüft ob jemand gewonnen hat
                        System.out.println("Spieler = " + main.getSpieler());
                        System.out.println("k = " + k);
                        if (k == 0){                                                //Wenn k = ist wird nur der Spieler getauscht = niemand hat gewonnen
                             main.setSpieler(main.getSpieler() * (-1));
                             k = gewonnen(main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl()); 
                             if (k == 0){                                           //Anschließend wird beim anderen Spieler ebenfalls übrprüft ob dieser gewonne hat
                                 main.setSpieler(main.getSpieler() * (-1));         //Und falls nciht wieder der Spiel getauscht und Autogesaved
                                try {
                                save.savefile("autosave", main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl(), main.getPlayer(), main.getPc(), main.getRekstufe(), spieler1.getR(), spieler2.getR(), spieler1.getG(), spieler2.getG(), spieler1.getB(), spieler2.getB(), spieler1.getSpielstand(), spieler2.getSpielstand(), gui.getPlayBomb(), spieler1.getAbomb(), spieler2.getAbomb());
                                } catch (IOException ex) {                              //Nach jedem Zug wird ein autosave gemacht
                                Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                                }
                             }
                        } 
                        if (k == 1){                                                //Falls doch jemand gewonnen hat, wird der Spieler getauscht
                            main.setSpieler(main.getSpieler() * (-1));
                        }
                        gui.setBomb(false);                                         //Bombing für diese Runde wird deaktiviert
                    }
                }
                count++;
            } while (k != 1 && k != 2 && k != 3);                                   //Falls gewonnen wurde, wird k mit 1 versehen und die Schleife wird verlassen, falls Unentschieden mit 2 und bei Abbrechen mit 3
            main.setRekstufe(main.getRekstufe() - z);
        if (k == 2){
            gui.setGewonnen(-1);                                                    //Damit nacher die Richtige Meldung ausgeben wird (siehe FRAME_GAME Z. 235)
        }
        else if (k == 3){
        }
        else{
            spieler1.setSeeStone(false);
            gui.setBomb(false);
            main.setSpieler(main.getSpieler() * (-1)); 
            System.out.println("Spieler " + main.getSpieler() + " hat gewonnen!\n");
            gui.setGewonnen(1);                                                     //Gewonnen wird 1 gesetzt, damit die FRAME_GAME ebenfalls anhählt
            agewonnen(main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl());                   //Die Stelle, an der Gewonnen wurde, wird makiert
            spielfeld(main.getFgi(), main.getFgj());                    
            ausgabe(main.getFgi(), main.getFgj());
            main.setOldPlayer(main.getPlayer());                                    //Das Spiel (hier 1 das Singleplayer) wird gebackuped
            main.setPlayer(0); 
            gui.setNewgameblock(0);//Das Spiel wird nun abgebrochen (MAIN.player = 0)
            System.out.println("Das Spiel wurd beendet!");
        if (main.getPlayer() == 0){                                             
                File datei = new File("./autosave");
                System.out.println(datei);
                if (datei.exists()) {                                               //Das autosave, wird nachdem ein Spiel beendet wurde gelöscht
                    datei.delete();
                }
            }
        }
    }
}
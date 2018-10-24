
package MAIN;

import THREADS.THREAD1;
import java.io.*;

 /**
 * Hier sind die MAIN Variablen gepeichert, die zum Spielen benötigt werden
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
*/
public class MAIN {
    
   
    private  int spieler = 1, pc = 0, fgi = 6, fgj = 7, gwzahl = 4, rekstufe = 7, player = 0, oldplayer = 0;
    private boolean startbedingung;
 
    
    /**
     * Anfangsspieler wird gesetzt
     * @param spieler 
     * - 1: Spieler 1 ist an der Reihe<br>
     * spieler - -1: Spieler -1/2 ist an der Reihe
     */
    public void setSpieler(int spieler) {
        this.spieler = spieler;
    }

    /**
     * Pc aktivieren
     * @param pc 
     * - 1: Pc ist aktiviert<br>
     * pc - 0: Pc ist deaktiviert
     */
    public void setPc(int pc) {
        this.pc = pc;
    }

    /**
     * Spielfeldhöhe
     * @param fgi 
     * Höhe
     */
    public void setFgi(int fgi) {
        this.fgi = fgi;
    }

    /**
     * Spielfeldbreite
     * @param fgj 
     * Breite
     */
    public void setFgj(int fgj) {
        this.fgj = fgj;
    }

    /**
     * Die Gewinnanzahl
     * @param gwzahl 
     * Gewinnanzahl
     */
    public void setGwzahl(int gwzahl) {
        this.gwzahl = gwzahl;
    }
    
    /**
     * Schwierigkeits- bzw. Rekursionsstufe für die KI
     * @param rekstufe 
     * Schwirgkeitsgrad
     */
    public void setRekstufe(int rekstufe) {
        this.rekstufe = rekstufe;
    }

    /**
     * Singleplayer, oder Multiplayerspiel
     * @param player 
     * - 0: Kein Spiel aktiv
     * player - 1: Singleplayerspiel aktiv
     * player - 2: Multiplayerpspiel aktiv
     */
    public void setPlayer(int player) {
        this.player = player;
    }
    
    /**
     * Der letzte Spieltyp wird gespeichert
     * @param oldplayer 
     * - 0: Zuvor wurde kein Spiel gestartet<br>
     * oldplayer - 1: Zuvor war ein Singleplayerspiel<br>
     * oldplayer - 2: Zuvor war ein Multiplayerspiel
     */
    public void setOldPlayer(int oldplayer) {
        this.oldplayer = oldplayer;
    }

    /**
     * Variable, ob es das erste Spiel ist, das gespielt wird, bzw. ob schon eins gespielt wurde
     * @param startbedingung 
     * - true: Es wurde schon eins gespielt
     * - false: Es wurde noch keins gespielt
     */
    public void setStartbedingung(boolean startbedingung) {
        this.startbedingung = startbedingung;
    }

    /**
     * Gibt den aktuellen Spielder der an der Reihe ist aus
     * @return 
     * Spieler
     */
    public int getSpieler() {
        return spieler;
    }

    /**
     * Gibt aus, ob der Computer eingeschaltet ist
     * @return 
     * Pc ON/OFF
     */
    public int getPc() {
        return pc;
    }

    /**
     * Gibt die Feldhöhe aus
     * @return 
     * Höhe
     */
    public int getFgi() {
        return fgi;
    }

    /**
     * Gibt die Feldbreite aus
     * @return 
     * Breite
     */
    public int getFgj() {
        return fgj;
    }
    
    /**
     * Gibt die Gewinnanzahl aus
     * @return 
     * Gewinnanzahl
     */
    public int getGwzahl() {
        return gwzahl;
    }

    /**
     * Gibt die Rekursionsstufe aus
     * @return 
     * Rekstufe
     */
    public int getRekstufe() {
        return rekstufe;
    }

    /**
     * Gibt aus, ob es sich um ein Multiplayerspiel oder oder Singleplayerspiel handelt
     * @return 
     * Spieltyp
     */
    public int getPlayer() {
        return player;
    }
    
    /**
     * Gibt an, ob das letzte Spiel ein Multiplayerspiel oder ein Singleplayerspiel war
     * @return 
     * letzter Spieltyp
     */
    public int getOldPlayer() {
        return oldplayer;
    }
    
    /**
     * Gibt an, ob es sich um das erste Spiel handelt
     * @return 
     * Erstes Spiel
     */
    public boolean isStartbedingung() {
        return startbedingung;
    }
    
    /**
     * Standardwerte der MAIN Varibalen
     */
    public MAIN(){
        this.player = 0;
        this.gwzahl = 4;
        this.startbedingung = false;
        this.fgi = 6;
        this.fgj = 7;
        this.pc = 0;
        this.rekstufe = 7;
        this.spieler = 1;
    }
    
    /**
     * Hier wird das Spiel gestartet, indem ein neuer Thread mit der GUI gestartet wird
     * @param args
     * Main Arguments
     */
    public static void main(String[] args){
        File dir = new File("./SAVE");
        File help = new File("./HELP");
        dir.mkdir();
        help.mkdir();
        new THREAD1().start();
    }
}

   

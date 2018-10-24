
package GAME;

import MAIN.MAIN;
import java.awt.Toolkit;

/**
 * Hier befinden sich alle Variablen, die im Spiel benötigt werden, um ein Fehlerfreies spieln zu ermöglichen
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class GAME_VARIABLES extends MAIN{

    private int dimx;                                                                               
    private int dimy; 
    private int ovalx;                                                       
    private int ovaly;                                                         
    private int radius;                                                                  
    private int ballX;                                                                                        
    private int ballY; 
    private boolean go,
                    abbrechen,
                    connected,
                    isServer, 
                    firstMulti,
                    seeGame,
                    bomb;
    private int einwurfx,
                einwurfy,
                port,
                timeout;
    private boolean warten;
    private int sblock;                                                 
    private int lzugx, lzugy, 
                newgameblock;
    private int gewonnen,
                playBomb;
    private String IP;
    
    /**
     * Setzt letzten Zug X
     * @param lzugx 
     * Die x Koordinate des letzten Zugs wird hier gespeichert
     */

    public void setLzugx(int lzugx) {
        this.lzugx = lzugx;
    }
    
    /**
     * Setzt letzten Zug Y
     * @param lzugy 
     * Die y Koordinate des letzten Zugs wird hier gespeichert
     */
    
    public void setLzugy(int lzugy) {
        this.lzugy = lzugy;
    }
    
    /**
     * Setzt den newgameblock
     * @param newgameblock
     * Falls das Localframe aufgerufen wurde, oder der Computer am Zug ist, kann kein Neues Spiel gestartet werden
     */
    
    public void setNewgameblock(int newgameblock) {
        this.newgameblock = newgameblock;
    }
    
    /**
     * Setzt Dim X
     * @param dimx 
     * Hier wird die MAIN GUI Breite ermittelt und gespeichert
     */
    public void setDimx(int dimx) {
        this.dimx = dimx;
    }
    
    /**
     * Setzt Dim Y
     * @param dimy 
     * Hier wird die MAIN GUI Höhe ermittelt und gespeichert
     */
    public void setDimy(int dimy) {
        this.dimy = dimy;
    }
    
    /**
     * Setzt Oval X
     * @param ovalx 
     * Die Anfangsposition x, an der die einzelnen Felder auf der GUI gezeichnet werden, wird hier ermittelt und gespeichert
     */
    public void setOvalx(int ovalx) {
        this.ovalx = ovalx;
    }
    
    /**
     * Setzt Oval Y
     * @param ovaly 
     * Die Anfangsposition y, an der die einzelnen Felder auf der GUI gezeichnet werden, wird hier ermittelt und gespeichert
     */
    public void setOvaly(int ovaly) {
        this.ovaly = ovaly;
    }
    
    /**
     * Setzt den Radius
     * @param radius 
     * Der Radius wird anhand der dimx und dimy berechnet und gespeichert
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    
    /**
     * Setzten die Ball X Koordinate
     * @param ballX 
     * Die Position x für den Stein, den man verschieben und hineinwerfen kann
     */
    public void setBallX(int ballX) {
        this.ballX = ballX;
    }
    
    /**
     * Setzt die Ball Y Koordinate
     * @param ballY 
     * Die Position y für den Stein, den man verschieben und hineinwerfen kann
     */
    public void setBallY(int ballY) {
        this.ballY = ballY;
    }
    
    /**
     * Setzten des Einwurfs X
     * @param einwurfx
     * Die Stelle, an der der Stein hineingeworfen werden soll (wenn Enter gedrückt wird)
     */
    public void setEinwurfX(int einwurfx) {
        this.einwurfx = einwurfx;
    }
    
    /**
     * Setzten des Einwurfs Y
     * @param einwurfy 
     * Die Höhe, an der sich der Mauszeiger bzw. man sich durch Drücken den Pfeiltasten befindet
     */
    public void setEinwurfY(int einwurfy) {
        this.einwurfy = einwurfy;
    }
    
    /**
     * Setzt die warten Variable
     * @param warten 
     * true | Falls der Computer am Zug ist, oder das Spiel beendet wurde, darf kein Zug ausgeführt werden <br>
     * warten - false | Man hat die Erlaubnis den Stein zu bewegen und hineinzuwerfen
     */
    public void setWarten(boolean warten) {
        this.warten = warten;
    }
    
    /**
     * Setzt den Speicherblock
     * @param sblock 
     * - 0 | Man darf das Spiel speichern unt laden <br>
     * sblock - 1 | Kein Spiel wurde bisher begonnen, speichern verboten, laden erlaubt<br>
     * sblock - 2 | Das Spiel wurde beendet, speichern verboten, laden erlaubt<br>
     * sblock - 3 | Der Computer ist gerade am Zug, speichern und laden verboten
     * sblock - 4 | Der Client darf nicht speichern
     */
    public void setSblock(int sblock) {
        this.sblock = sblock;
    }
    
    /**
     * Setzt das Spiel-Go
     * @param go 
     * - true | Das Spiel wurde erfolgreich erstellt und kann jetzt beginnen<br>
     * go - false | Das Spiel wurde noch nicht erstellt
     */
        public void setGo(boolean go) {
        this.go = go;
    }
    
    /**
     * Setzt die Gewonnen Variable
     * @param gewonnen 
     * - -1 | Das Spiel endete unentschieden<br>
     * gewonnen - 0 | Das Spiel wurde noch nicht beendet<br>
     * gewonnen - 1 | Einer der Spieler hat gewonnen
     */    
    public void setGewonnen(int gewonnen) {
        this.gewonnen = gewonnen;
    }
    
    /**
     * Setzt die Abbrechen Variable
     * @param abbrechen 
     * false | Das Spiel wird nicht abgebrochen<br>
     * abbrechen - true | Das Spiel wird abgebrochen
     */
    public void setAbbrechen(boolean abbrechen) {
        this.abbrechen = abbrechen;
    }
    
    /**
     * Setzt die ist Connected Variable
     * @param connected 
     * - true, falls eine Verbindung zwischen Server und Client aufgebaut wurde<br>
     * - false, falls die Verbindung zwischen Server und Client nicht aufgebaut werden konnte
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    } 
    
    /**
     * Setzt die ist Server Variable
     * @param isServer 
     * - true, falls der Spieler Server ist<br>
     * - false, falls der Spieler Client ist
     */
    public void setIsServer(boolean isServer) {
        this.isServer = isServer;
    } 

    /**
     * Setzt die erstes Multiplayerspiel Variable
     * @param firstMuli 
     * - true, falls es das erste Multiplayspiel ist, seitdem der Prozess offen ist<br>
     * - false, falls schon mindestens ein Spiel begonnen wurde
     */
    public void setFirstMulti(boolean firstMuli) {
        this.firstMulti = firstMuli;
    }
    
    /**
     * Setzt die Bomben Variable
     * @param bomb 
     * - true, falls der Spielmodus Bombing ausgewählt wurde<br>
     * bomb - false, falls ein anderer Spielmodus ausgewählt wurde
     */
    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }
    
    /**
     * Setzt die Play Bomb Variable
     * @param playBomb 
     * - 1 - falls Bombing noch aktiv ist<br>
     * playBomb - 0, falls Bombing deaktiviert wurde
     */
    public void setPlayBomb(int playBomb) {
        this.playBomb = playBomb;
    }
    
    /**
     * Setzt die Sichtbarkeit des Spielfeldes
     * @param seeGame 
     * - true, falls das Spielfeld angzeigt wird<br>
     * - false, falls das Spielfeld nicht angzeigt wird
     */
    public void setSeeGame(boolean seeGame) {
        this.seeGame = seeGame;
    }
    
    /**
     * Setzt den Port
     * @param port 
     * Hier wird der Port bei Netzwerkspielen zwischengespeichert
     */
    public void setPort(int port){
        this.port = port;
    }
    
    /**
     * Setzt die IP
     * @param IP 
     * Hier wird die IP bei Netzwerrkspielen zwischengespeichert
     */
    public void setIP(String IP){
        this.IP = IP;
    }
    
    /**
     * Setzt das Timeout
     * @param timeout 
     * Hier wird das Timeout bei Netzwerkspielen zwischengespeichert
     */
    public void setTimeout(int timeout){
        this.timeout = timeout;
    }
    
        /**
     * Hier kann die IP, für eine Revanche etc. abgefragt werden
     * @return 
     * Die IP
     */
    public String getIP(){
        return this.IP;
    }
    
    /**
     * Hier kann der Port für eine Revanche etc. abgefragt werden
     * @return 
     * Der Port
     */
    public int getPort(){
        return this.port;
    }
    
    /**
     * Hie wird der Letzte Zug (x Kooridnate) abgefragt
     * @return 
     * x Koordinate
     */
    public int getLzugx() {
        return lzugx;
    }
    
    /**
     * Hie wird der Letzte Zug (y Kooridnate) abgefragt
     * @return 
     * y Koordinate
     */
    public int getLzugy() {
        return lzugy;
    }

    /**
     * Hier wird abgefragt, ob ein neues Spiel gestartet werden darf
     * @return 
     * boolean Newgame
     */
    public int getNewgameblock() {
        return newgameblock;
    }

    /**
     * Die Größe des MAIN Fensters (x Koordinate)
     * @return 
     * x Koordinate
     */
    public int getDimx() {
        return dimx;
    }
    
    /**
     * Die Größe des MAIN Fensters (y Koordinate)
     * @return 
     * y Koordinate
     */
    public int getDimy() {
        return dimy;
    }
    
    /**
     * Die Position, an der angefangen wird zu zeichnen, wird hier abgefragt (x Koordinate)
     * @return 
     * x Koordinate
     */
    public int getOvalx() {
        return ovalx;
    }

    /**
     * Die Position, an der angefangen wird zu zeichnen, wird hier abgefragt (y Koordinate)
     * @return 
     * y Koordinate
     */
    public int getOvaly() {
        return ovaly;
    }

    /**
     * Größe der Spielsteine wird zurück gegeben
     * @return 
     * Größe der Spielsteine
     */
    public int getRadius() {
        return radius;
    }
    
    /**
     * Position für den Ball der bewegt wird (x Koordinate)
     * @return 
     * x Kooridnate
     */
    public int getBallX() {
        return ballX;
    }

    /**
     * Position für den Ball der bewegt wird (y Koordinate)
     * @return 
     * y Kooridnate
     */
    public int getBallY() {
        return ballY;
    }

    /**
     * Position an der eingeworfen wird (x Kooridnate)
     * @return 
     * x Koordinate
     */
    public int getEinwurfX() {
        return einwurfx;
    }
    
    /**
     * Possition an der eingeworfen wird (y Kooridnate: Für Bombing)
     * @return 
     * y Kooridnate
     */
    public int getEinwurfY() {
        return einwurfy;
    }
    
    /**
     * Gibt an, ob der Bombing Modus aktiv ist
     * @return 
     * Bombing
     */
    public boolean getBomb() {
        return bomb;
    }
    
    /**
     * Gibt an, ob gewartet werden soll (Spielstein darf nicht bewegt werden)
     * @return 
     * warten
     */
    public boolean getWarten() {
        return warten;
    }

    /**
     * Gibt an, ob gespeichert werden darf
     * @return 
     * sBlock
     */
    public int getSblock() {
        return sblock;
    }
    
    /**
     * Gibt an, ob das Spiel bereits begonnen hat
     * @return 
     * go
     */
    public boolean getGo() {
        return go;
    }

    /**
     * Gibt an, ob jemand gewonnen hat und welcher Spieler
     * @return 
     * Gewinner
     */
    public int getGewonnen() {
        return gewonnen;
    }

    /**
     * Gibt an, ob das Spiel abgebrochen werden soll
     * @return 
     * Abbruch
     */
    public boolean getAbbrechen() {
        return abbrechen;
    }
    
    /**
     * Gibt an, ob eine Verbindung mit einem Client/Server besteht
     * @return 
     * Verbindung
     */
    public boolean getConnected() {
        return connected;
    }
    
    /**
     * Gibt an ob man Server, oder Client ist
     * @return 
     * Server oder Client
     */
    public boolean getIsServer() {
        return isServer;
    }
       
    /**
     * Gibt an, ob es sich um das erste Multiplayerspiel handelt
     * @return 
     * Erstes Spiel
     */
    public boolean getFirstMulti() {
        return firstMulti;
    }
    
    /**
     * Gibt an, ob das Spielfeld gesehen werden darf
     * @return 
     * Sichtbarkeit Spielfeld
     */
    public boolean getSeegame() {
        return seeGame;
    }

    /**
     * Gibt an, ob Bombing noch aktiv ist
     * @return 
     * Bombing
     */
    public int getPlayBomb() {
        return playBomb;
    }
    
    /**
     * Gibt an, ob der Client/Server einen Timeout hat
     * @return 
     * Timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Intialisiert die Game Variablen auf die Standart Werte
     */
    public GAME_VARIABLES(){
        this.dimx = Toolkit.getDefaultToolkit().getScreenSize().width;                 
        this.dimy = Toolkit.getDefaultToolkit().getScreenSize().height;                                                        
        this.ovaly = dimy / 4;                                                         
        this.ballX = ovalx;                                                                                                                                                                                                               
        this.einwurfx = 0;  
        this.einwurfy = 0;
        this.warten = false; 
        this.sblock = 1;  
        this.go = false;
        this.gewonnen = 0;
        this.abbrechen = false;
        this.connected = false;
        this.isServer = true;
        this.firstMulti = true;
        this.seeGame = false;
        this.bomb = false;
        this.playBomb = 0;
        this.port = 50001;
        this.IP = "localhost";
        this.timeout = 60000;
    }
}

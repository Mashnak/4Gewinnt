
package GUI;

/**
 * Die Klasse Spieler dient dafür, zwei Spieler zu erstellen, die gegeneinander spielen können.
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class SPIELER {

    private int r, g, b;                    //Hier sind die Standartfarben der Steine
    private int spielstand;
    private int abomb;
    private boolean seeStone;
    
    /**
     * Der Farbeanteil Rot des Spieler wird gesetzt
     * @param r 
     * Rot
     */
    public void setR(int r) {
        this.r = r;
    }

    /**
     * Der Farbanteil Grün des Spieler wird gesetzt
     * @param g 
     * Grün
     */
    public void setG(int g) {
        this.g = g;
    }

    /**
     * Der Farbanteil Blau des Spieles wird gesetzt
     * @param b 
     * Blau
     */
    public void setB(int b) {
        this.b = b;
    }
    
    /**
     * Die Anzahl der Bomben, falls der Spielmodus aktiviert wurde, wird gesetzt
     * @param abomb 
     * Anzahl der Bomben
     */
    public void setAbomb(int abomb) {
        this.abomb = abomb;
    }
    
    /**
     * Gibt an, ob der Stein für den Spieler angezeigt wird
     * @param stone 
     * - true: Stein wird gezeigt
     * stone - false; Stein wird nicht gezeigt
     */
    public void setSeeStone(boolean stone) {
        seeStone = stone;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }
        
    public int getAbomb() {
        return abomb;
    }
    
    public boolean getSeeStone() {
        return seeStone;
    }
    
    /**
     * Der Spielstand wird gesetzt
     * @param spielstand 
     * Spielstand
     */
    public void setSpielstand(int spielstand) {
        this.spielstand = spielstand;
    }

    public int getSpielstand() {
        return spielstand;
    }
        
    /**
     * Hier wird der Spieler erstellt
     * @param fa
     * Rotanteil
     * @param fb
     * Grünanteil
     * @param fc
     * Blauanteil
     */
    public SPIELER(int fa, int fb, int fc){
            this.r = fa;
            this.g = fb;
            this.b = fc;
            this.spielstand = 0;
            this.abomb = 1;
            this.seeStone = true;
        }
}

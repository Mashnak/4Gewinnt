
package GAME;

/**
 * Erbt von WIN
 * Diese Klasse übernimmt die Zugberechnung, falls man gegen den Computer spielt!
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class KI extends WIN{
    public static int[][] feld = new int [30][30];
    protected long[] werfen = new long [30];
    private int g1 = 0, g2 = 0, u1, u2;

    /**
     * Die Min Funktion, des Minimax-Algorithmus, der den Zug berechnet
     * @param spieler
     * Der Spieler (1 oder -1)
     * @param limit
     * Das Anfangslimit von 0 wird bei jeder Stufe erhöht, bis zum angegebenen Rekursionsabbruch
     * @param fgi
     * Die Feldhöhe
     * @param fgj
     * Die Feldbreite
     * @param gwzahl
     * Die Gewinnanzahl
     * @param vorher
     * Der vorherige Zug
     * @param stufe
     * Die Schwierigkeits-/Rekursionsstufe
     * @param zug
     * Bekommt das Zug Objekt, um einen Zug ausführen zu können
     */
    public void min(int spieler, int limit, int fgi, int fgj, int gwzahl, int vorher, int stufe, ZUG zug){		
        int z, i;
	z = zaehler(fgi, fgj);                                                          //Es wird geschaut, ob das Spielfeld schon voll ist
	if (z == fgi * fgj || limit == stufe){                                          //Wenn es voll ist, oder die Rekursionstufe erreicht wurde wird abgebrochen
	}
	else{
            for (i = 0; i < fgj; i++){                                                  //Es wird in Feld 0 bis zur maximalen Feldbreite reingeworfen (rekursiv)
		if (limit == 0){                                                        //Hier wird in einer static int die Position des ersten Wurfes (limit = 0) gespeichert
                    u1 = i;
		}
		g1 = check(i, spieler, fgi);                                            //Es wird geschaut ob das Feld, wo man als nächstes reinwerfen möchte, noch frei ist
		if (g1 != -9){                                                          //Wenn nein wird eine -9 zurückgegeben und i wird um eins erhäht (for Schleife)
                    zug.zug(i, spieler, fgi, this);
                    if (gewonnen(spieler, fgi, fgj, gwzahl) == 1){                  //Es wird geschaut, ob der Computer auf jeder Rekursionsstufe (bis limit)
			werfen[u1] += 1000000000 / (Math.pow(15.0, (limit)));           //gewinnen kann. Wenn ja wird das entsprechende Anfangsfeld, je nach
                    }                                                                   //Rekursionsstufe bewertet und der Algorithmus wird abgebrochen
                        else{
                            if (gewonnen(spieler, fgi, fgj, gwzahl - 1) == 1){
				werfen[u1] += 1000 / (Math.pow(15.0, (limit)));
                            }
                            max(-spieler, limit + 1, fgi, fgj, gwzahl, u1, stufe, zug); //Ansonsten wird die max Funktions ausgeführt (rekursiv), der denn Gegenspieler
                        }                                                               //Zug sumiliert und auf die gleiche weise auswertet, nur wird dort subtrahiert!
			zug.rzug(i, spieler, fgi);                                      //Trifft eine Abbruchbedigung zu, wird der vorherige Zug rückgängig gemacht
		}
            }
	}
    }
    
    /**
     * Die Max Funktion, des Minimax-Algorithmus, der den Zug berechnet
     * @param spieler
     * Der Spieler (1 oder -1)
     * @param limit
     * Das Anfangslimit von 0 wird bei jeder Stufe erhöht, bis zum angegebenen Rekursionsabbruch
     * @param fgi
     * Die Feldhöhe
     * @param fgj
     * Die Feldbreite
     * @param gwzahl
     * Die Gewinnanzahl
     * @param vorher
     * Der vorherige Zug
     * @param stufe
     * Die Schwierigkeits-/Rekursionsstufe
     * @param gui 
     * Zugriff auf alle GAME_VARIABLES
     */
    private void max(int spieler, int limit, int fgi, int fgj, int gwzahl, int vorher, int stufe, ZUG zug){         //(siehe die Funktion min() --> selber Ablauf
        int z, i;
	z = zaehler(fgi, fgj);
	if (z == fgi * fgj || limit == stufe){

	}
	else{
            for (i = 0; i < fgj; i++){
		g2 = check(i, spieler, fgi);
		if (g2 != -9){
                    zug.zug(i, spieler, fgi, this);
                    if (gewonnen(spieler, fgi, fgj, gwzahl) == 1){
			werfen[vorher] -= 1000000000 / (Math.pow(15.0, (limit - 1)));
                    }
                    else{
                        if (gewonnen(spieler, fgi, fgj, gwzahl - 1) == 1){
                            werfen[vorher] -= 1000 / (Math.pow(15.0, (limit - 1)));
			}
			min(-spieler, limit + 1, fgi, fgj, gwzahl, i, stufe, zug);
                    }
                    zug.rzug(i, spieler, fgi);
		}
            }
	}
    }
    
    /**
     * Zählt, wieviele Felder noch frei sind (z.B. um Unentschieden zu erkennen)
     * @param fgi
     * Feldhöhe
     * @param fgj
     * Feldbreite
     * @return 
     * Die Anzahl der belgten Felder wird zurück gegeben
     */
    public int zaehler(int fgi, int fgj){
	int z = 0, i, j;                            //Diese Hilfsfunktion zählt, wie viele Felder schon besetzt sind
	for (i = 0; i < fgi; i++){
		for (j = 0; j < fgj; j++){
			if (feld[i][j] != 0){
				z++;
			}
		}
	}
	return z;
    }
    
    /**
     * Überprüft die Spalte, in die man hineinwerfen möchte auf freie Felder
     * @param x
     * Die x Koordinate des Zuges
     * @param spieler
     * Der Spieler, der gerade an der Reihe ist
     * @param fgi
     * Feldhöhe
     * @return 
     * -9 für sie Spalte ist voll
     * 1 für der Zug kann ausgeführt werden
     */
    public int check(int x, int spieler, int fgi){
	int z = 0, y;
	for (y = 0; y < fgi; y++){
	if(feld[y][x] == 0){            //Diese Funktion überprüft, ob das ausgewählte Feld in min() bzw max() noch zur Verfügung steht und gibt
		z++;                    //dann entweder 1, falls frei, oder -9 falls belegt zurück
		}
	}
	if (z == 0){
		return -9;
	}
	else{
		return 1;
	}
    }
}

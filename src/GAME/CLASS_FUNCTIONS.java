
package GAME;

import static GAME.KI.feld;
import static GUI.FRAME_GAME.spielfeld;

/**
 * Hier sind wichtige Klassenmethoden, die während dem Spielablauf (LOCAL UND NETWORK) benötigt werden!
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class CLASS_FUNCTIONS {
    
    /**
     * Das Feld wird in der Konsole ausgegeben.
     * @param fgi
     * Feldhöhe
     * @param fgj 
     * Feldbreite
     */
    public static void ausgabe(int fgi, int fgj){                           
	int i, j;
	for (i = 0; i < fgi; i++){                      //Das Spielfeld wird in einer for Schleife ausgeben
		for (j = 0; j < fgj; j++){
			System.out.print(feld[i][j]);
		}
		System.out.print("\n");
	}
	System.out.print("-------\n");
	System.out.print("1234567\n");
}
    
    /**
     * Das Feld wird vor Beginn des Spieles auf Null gesetzt (alle Felder sind somit leer)
     * @param fgi
     * Feldhöhe
     * @param fgj 
     * Feldbreite
     */
    public static void feld_gleich_null(int fgi, int fgj){                      //Das Feld wird vor beginn des Spieles auf Null gesetzt (alle Felder sind somit leer)
	int i, j;
	for (i = 0; i < fgi; i++){
		for (j = 0; j < fgj; j++){
			feld[i][j] = 0;
		}
	}
    }
    
    /**
     * Der Computer rechnet im Feld "feld", dass dem Ausgabefeld "spielfeld" übertragen werden muss
     * @param fgi
     * Feldhöhe
     * @param fgj 
     * Feldbreite
     */
    public static void spielfeld(int fgi, int fgj){                             //Hier wird das Feld, das der Computer zum Rechnen benutzt hat, dem Feld was nacher
	int i, j;                                                               //angezeigt wird übergeben
	for (i = 0; i < fgi; i++){
		for (j = 0; j < fgj; j++){
			spielfeld[i][j] = feld[i][j];
		}
	}
    }
    
    public static void bomb(int einwurfX, int einwurfY){
        for (int i = einwurfY; i > 0; i--){                                     //Das Spielfeld wird wenn eine Bombe aktiviert wurde nach unten verschoben,
            KI.feld[i][einwurfX] = KI.feld[i - 1][einwurfX];                    //also die leere Stelle geschlossen
        }
        KI.feld[0][einwurfX] = 0;
    }
}

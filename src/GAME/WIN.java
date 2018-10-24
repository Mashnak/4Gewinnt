
package GAME;
import static GAME.KI.feld;
/**
 * Erbt von GAME_VARIABLES
 * In dieser Klasse befinden sich Methoden, mit dennen überprüft wird, ob schon jemand gewonnen hat!
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class WIN extends GAME_VARIABLES{

    /**
     * Überprüft in alle Richtungen und  nach jedem Zug, ob ein Spieler gewonnen hat, also die Gewinnzahl erreicht wurde
     * @param spieler
     * Der Spieler, der an der Reihe ist
     * @param fgi
     * Feldhöhe
     * @param fgj
     * Feldbreite
     * @param gwzahl
     * Gewinnanzahl
     * @return 
     * 1 für der Spieler hat gewonnen
     * 0 für niemand hat bisher gewonnen
     */
    public int gewonnen(int spieler, int fgi, int  fgj, int gwzahl){
	int i, j, k, z1 = 0, z2 = 0, z3 = 0, z4 = 0;
	if (spieler == -1){
		spieler = 2;
	}
	for (i = fgi - 1; i >= 0; i--){                                         //Überprüfung auf 4 gewinnt waagerecht
		for (j = 0; j < fgj - gwzahl + 1; j++){
			for (k = 0; k < gwzahl; k++){
				if (feld[i][j + k] == spieler){
					z1++;
				}
			}
			if (z1 == gwzahl){
				return 1;
			}
			z1 = 0;
		}
	}
	for (i = fgi - 1; i >= gwzahl - 1; i--){				//Überprüfung auf 4 gewinnt senkrecht	
		for (j = 0; j < fgj; j++){
			for (k = 0; k < gwzahl; k++){
				if (feld[i - k][j] == spieler){
					z2++;
				}
			}
			if (z2 == gwzahl){
				return 1;
			}
			z2 = 0;
		}
	}

	for (i = fgi - 1; i >= gwzahl - 1; i--){				//Überprüfung auf 4 gewinnt Diagonal rechts oben
		for (j = 0; j < (fgj - gwzahl + 1); j++){
			for (k = 0; k < gwzahl; k++){
				if (feld[i - k][j + k] == spieler){
					z3++;
				}
			}
			if (z3 == gwzahl){
				return 1;
			}
			z3 = 0;
		}
	}

	for (i = (fgi - 1); i >= gwzahl - 1; i--){				//Überprüfung auf 4 gewinnt Diagonal links oben
		for (j = (fgj - 1); j >= gwzahl - 1; j--){
			for (k = 0; k < gwzahl; k++){
				if (feld[i - k][j - k] == spieler){
					z4++;
				}
			}
			if (z4 == gwzahl){
				return 1;
			}
			z4 = 0;
		}
	}
	return 0;
}
    
    
    /**
     * Diese Funktion makiert, falls ein Spieler gewonnen hat, die Gewinnnstelle
     * @param spieler
     * Der  Spieler, der an der Reihe ist
     * @param fgi
     * Feldhöhe
     * @param fgj
     * Feldbreite
     * @param gwzahl
     * Gewinnanzahl
     */
    
    public void agewonnen(int spieler, int fgi, int  fgj, int gwzahl){
	int i, j, k, z1 = 0, z2 = 0, z3 = 0, z4 = 0, c;
	if (spieler == -1){
		spieler = 2;
	}
	for (i = fgi - 1; i >= 0; i--){                                         //Überprüfung auf 4 gewinnt waagerecht
		for (j = 0; j < fgj - gwzahl + 1; j++){
			for (k = 0; k < gwzahl; k++){
				if (feld[i][j + k] == spieler){
					z1++;
				}
			}
			if (z1 == gwzahl){
                            for (int x = 0; x < fgi; x++){
                                for (int y = 0; y < fgj; y++){                  //Alle Felder werden auf 3 bzw 4 gesetzt, damit die GUI_GAME diese grauer färbt
                                    if (feld[x][y] == 1)
                                        feld[x][y] = 3;
                                    if (feld[x][y] == 2)
                                        feld[x][y] = 4;
                                }
                            }
                            for (c = gwzahl - 1; c >= 0; c--){
                                feld[i][j + c] = spieler;                       //die Gewinnkombination wird auf die Frabe des Spielers gesetzt um diese hervorzuheben
                                }
			}
			z1 = 0;
		}
	}
	for (i = fgi - 1; i >= gwzahl - 1; i--){                                //Überprüfung auf 4 gewinnt senkrecht	
		for (j = 0; j < fgj; j++){                                                          //Siehe oben!
			for (k = 0; k < gwzahl; k++){
				if (feld[i - k][j] == spieler){
					z2++;
				}
			}
			if (z2 == gwzahl){
                            for (int x = 0; x < fgi; x++){
                                for (int y = 0; y < fgj; y++){
                                    if (feld[x][y] == 1)
                                        feld[x][y] = 3;
                                    if (feld[x][y] == 2)
                                        feld[x][y] = 4;
                                }
                            }
				for (c = gwzahl - 1; c >= 0; c--){
                                    feld[i - c][j] = spieler;
                                }
			}
			z2 = 0;
		}
	}

	for (i = fgi - 1; i >= gwzahl - 1; i--){                                //Überprüfung auf 4 gewinnt Diagonal rechts oben
		for (j = 0; j < (fgj - gwzahl + 1); j++){
			for (k = 0; k < gwzahl; k++){
				if (feld[i - k][j + k] == spieler){
					z3++;
				}
			}
			if (z3 == gwzahl){
                            for (int x = 0; x < fgi; x++){
                                for (int y = 0; y < fgj; y++){
                                    if (feld[x][y] == 1)
                                        feld[x][y] = 3;
                                    if (feld[x][y] == 2)
                                        feld[x][y] = 4;
                                }
                            }
                            for (c = gwzahl - 1; c >= 0; c--){
                                feld[i - c][j + c] = spieler;
                                }
			}
			z3 = 0;
		}
	}

	for (i = (fgi - 1); i >= gwzahl - 1; i--){				//Überprüfung auf 4 gewinnt Diagonal links oben
		for (j = (fgj - 1); j >= gwzahl - 1; j--){
			for (k = 0; k < gwzahl; k++){
				if (feld[i - k][j - k] == spieler){
					z4++;
				}
			}
			if (z4 == gwzahl){
                            for (int x = 0; x < fgi; x++){
                                for (int y = 0; y < fgj; y++){
                                    if (feld[x][y] == 1)
                                        feld[x][y] = 3;
                                    if (feld[x][y] == 2)
                                        feld[x][y] = 4;
                                }
                            }
                            for (c = gwzahl - 1; c >= 0; c--){
                                feld[i - c][j - c] = spieler;
                                }
			}
			z4 = 0;
		}
	}
}
}

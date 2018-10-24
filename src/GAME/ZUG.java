package GAME;


import static GUI.FRAME_GAME.spielfeld;

/**
 * Erbt von KI
 * Wenn ein Zug ausgeführt, berechnet, oder rückgängig gemacht werden soll, gibt es hier die entsprechenden Methoden dafür
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class ZUG extends KI{

    
    /**
     * Makiert den letzten Spielzug
     * @param x
     * x Koordinate des letzten Zuges
     * @param y
     * y Koordinate des letzten Zuges
     * @param spieler 
     * Der Spieler, der an der Reihe ist
     */
    public void spielzug(int x, int y, int spieler){
        if (spieler == 1){
        spielfeld[x][y] = 5;                        //der letzte Spielzug wird hier auf 5 bzw. 6 gesetztm damit die FRAME_GAME diese hervorheben kann
        }
        else{
        spielfeld[x][y] = 6;  
        }
    }
    
    /**
     * Macht den zuletzt gemachten Zug rückgängig (für die KI von Bedeutung)
     * @param x
     * x Koordinate des Zuges
     * @param spieler
     * Der Spieler, der an der Reihe ist
     * @param fgi 
     * Feldhöhe
     */
    public void rzug(int x, int spieler, int fgi){
	int y = 0;
	while (KI.feld[y][x] == 0 && y < fgi){              //Hier wird geschaut wie viele Felder in der Höhe schon verwendet werden
		y++;                                        //Solange bis ein freies gefunden wurde
        }                                                   //Anschließend wird das Feld wieder 0 gemacht, also der Zug wird rückgängig gemacht
	KI.feld[y][x] = 0;
    }
    
    /**
     * Schaut anhand der x Koordinate, wo der Stein in y Höhe plaziert werden muss, wenn der Zug ausgeführt wird bzw. führt den Zug aus
     * @param x
     * x Koordinate des Zuges
     * @param spieler
     * Der Spieler, der an der Reihe ist
     * @param fgi
     * Feldhöhe
     * @param gui
     * Zugriff auf das Objekt gui von FRAME_GAME
     */
    public void zug(int x, int spieler, int fgi, GAME_VARIABLES gui){
	int y = fgi - 1;
	while (KI.feld[y][x] != 0){                         //Hier wird geschaut wie viele Felder in der Höhe schon verwendet werden
		y--;                                        //Solange bis ein freies gefunden wurde
		if (y < 0){			
		}
	}
	if (spieler == 1){                                  //Anschließend wird das freie Feld auf die Spielernummer gesetzt
		KI.feld[y][x] = 1;
	}
	else {
		KI.feld[y][x] = 2;
	}
        gui.setLzugy(x);                                    //Der letzte Zug wird gesetzt
        gui.setLzugx(y);
    }
    
    /**
     * Gibt die Nächste freie x Position für den nächsten Einwurf
     * @param fgi
     * Spielfeldhöhe
     * @param x
     * Die Spalte
     * @return 
     * Die x Koordinate des freien Platzes
     */
    public int getX(int fgi, int x){
        int y = fgi - 1;
        while(y >= 0 && KI.feld[y][x] != 0){
            y--;
        }       
        return y;
    }
    
    /**
     * Eine Funktion für die KI, damit der Computer, falls er die Möglichkeit hat zu gewinnen, sofort den Stein entsprechend hineinsetzt, <br>
     * oder falls der Gegner im nächsten Zug gewinnen kann, dies zu verhindern (Für bessere Rechenzeit)
     * @param fgi
     * Feldhöhe
     * @param fgj
     * Feldbreite
     * @param gwzahl
     * Gewinnanzahl
     * @param spieler
     * Der Spieler, der an der Reihe ist
     * @return 
     * Gibt die x Koordinate des Zuges zurück, der eventuell gemacht werden muss
     */
    public int viererkennung(int fgi, int fgj, int gwzahl, int spieler){
            int i, j, k, z1 = 0, z2 = 0, z3 = 0, z4 = 0, x = 0, y = 0;
            if (spieler == -1){
                spieler = 2;
            }
	for (i = fgi - 1; i >= 0; i--){                                                 //Überprüfung auf 4 Gewinnt Waagerecht
		for (j = 0; j < (fgj - gwzahl + 1); j++){
			for (k = 0; k < gwzahl; k++){
				if (KI.feld[i][j + k] == spieler){                      //Spieler sagt ob geblockt wird, oder nach einer Gewinnmöglichkeit gesucht wird
					z1++;
				}
				else if (KI.feld[i][j + k] == 0){                       //Findet die Freie Null neben drei gleichen einsen oder zweien
					z1 += 10;
					x = j + k;                                      //speichert die Stelle in x
				}
			}
			if (z1 == 10 + gwzahl - 1){                                     //Nur wenn 3 einsen oder zweien und eine leere Stelle 0 gefunden wurde, dann geht er hier rein
				if (i + 1 <= fgi - 1 && KI.feld[i + 1][x] != 0){        //Es wird überprüft ob die freie Stelle erreichbar ist
                                    return x;
				}
				else if (i == fgi - 1){                                 //oder ob Sie am Boden liegt
                                    return x;
				}
			}
			z1 = 0;
		}
	}

	for (i = fgi - 1; i >= gwzahl - 1; i--){                                        //Überprüfung auf 4 Gewinnt Senkrecht
		for (j = 0; j < fgj; j++){
			for (k = 0; k < gwzahl; k++){
				if (KI.feld[i - k][j] == spieler){                      //Gleiche Vorgehensweise, wie bei der waagerechten Überprüfung
					z2++;
				}
				else if (KI.feld[i - k][j] == 0){
					z2 += 10;
					x = j;
				}
			}
			if (z2 == (10 + gwzahl - 1)){
                            return x;
			}
			z2 = 0;
		}
	}

	for (i = fgi - 1; i >= gwzahl - 1; i--){                                        //Überprüfung auf 4 Gewinnt Diagonal
		for (j = 0; j < (fgj - gwzahl + 1); j++){
			for (k = 0; k < gwzahl; k++){                                   //Gleiche Vorgehensweise, wie bei der waagerechten Überprüfung
				if (KI.feld[i - k][j + k] == spieler){		
					z3++;
				}
				else if (KI.feld[i - k][j + k] == 0){		
					z3 += 10;
					x = j + k;						
					y = i - k;
				}
			}
			if (z3 == 10 + gwzahl - 1){
  	
				if (y + 1 <= fgi - 1 && KI.feld[y + 1][x] != 0){		
					return x;
				}
				else if (y == fgi - 1){				
                                    return x;
				}
			}
			z3 = 0;
		}
	}

	for (i = (fgi - 1); i >= gwzahl - 1; i--){                                      //Überprüfung auf 4 Gewinnt Diagonal
		for (j = (fgj - 1); j >= gwzahl - 1; j--){
			for (k = 0; k < gwzahl; k++){
				if (KI.feld[i - k][j - k] == spieler){                  //Gleiche Vorgehensweise, wie bei der waagerechten Überprüfung
					z4++;
				}
				else if (KI.feld[i - k][j - k] == 0){		
					z4 += 10;
					x = j - k;						
					y = i - k;
				}
			}
			if (z4 == 10 + gwzahl - 1){	
				if (y + 1 <= fgi - 1 && KI.feld[y + 1][x] != 0){	
					return x;
				}
				else if (y == fgi - 1){				
                                    return x;
				}
			}
			z4 = 0;
		}
	}
	return -1;
}
    
    /**
     * Hier wird der Computerzug berechnet und ausgeführt (mit Hilfe der Viererkennung und min/max Funktion)
     * @param fgi
     * Feldhöhe
     * @param fgj
     * Feldbreite
     * @param gwzahl
     * Gewinnanzahl
     * @param limit
     * Das Anfangslimit von 0 wird bei jeder Stufe erhöht, bis zum angegebenen Rekursionsabbruch
     * @param stufe
     * Die Schwierigkeits-/Rekursionsstufe
     * @param spieler
     * Der Spieler, der an der Reihe ist
     * @param gui
     * Game Variablen
     * @param count
     * Zahl der bisherigen Spielzüge
     * @return 
     * Der Zug, der gemacht wird, wird zurückgegeben
     */
    public int  pczug(int fgi, int fgj, int gwzahl, int limit, int stufe, int spieler, GAME_VARIABLES gui, int count){
	int z , i;                                                                                              //Hier wird die Stelle z für den Computer berechnet
	if (count == 1) return fgj / 2;
        gui.setNewgameblock(1);
        gui.setSblock(3);                                                                                       //Solange der Pc seinen Zug berechnet, wird das speichern, laden und das erstellen eines Neuen  Spieles geblockt
        z = viererkennung(fgi, fgj, gwzahl, spieler);                                                           //Zuerst wird geschaut, Gewinnsituation besteht, um das Spiel zu beenden
	if (z == -1){                                                                       
		z = viererkennung(fgi, fgj, gwzahl, spieler * (-1));                                            //Anschließend, ob eine Gefahr besteht, um nicht zu verlieren
		if (z == -1){   
                        min(spieler, limit, fgi, fgj, gwzahl, -1, stufe, this);                          //Nur wenn beides nicht zutrifft, startet die Bewertungfunktion min()
			z = 0;
			while (check(z, -1, fgi) == -9){                                                     //Zuerst wird eine nicht volle Spalte ausgewählt
				z++;                                                        
			}
			for (i = 1; i < fgj; i++){                                                              //Die höchste Zahl, also der beste Zug wird hier nun ausgewählt
				if (werfen[z] < werfen[i] && werfen[i] != 0){					
					z = i;									
				}
				if (werfen[z] == werfen[i]){                                              //Wenn zwei Felder die gleiche Bewertung haben, wird zufällig ausgwählt, welches Feld genommen wird
					if (rand() % 2 == 1){
						z = i;
					}
				}
			}
		}
	}
        werfen_null();                                                                                          //Das Feld "Werfen" wird nun wieder auf Null zurück gesetzt und die Position
	gui.setNewgameblock(0);                                                                                 //Speichern, Laden und Neues Spiel wird wieder freigegeben
        gui.setSblock(0);  
        return z;                                                                                               //wird zurück gege
}
   
    /**
     * Eine zufällige Zahl wird generiert
     * @return 
     * Eine Zufallszahl wird zurück gegeben
     */
    public int rand() {
		return (int) (Math.random() * (999999999 - 1) + 1);                                             //Generiert eine zufällige Zahl, für die Funktion pczug()
	}               
    
    /**
     * Das Feld, in dem die Berechnungen abgespeichert werden, wird nach jedem Zug zurückgesetzt
     */
    public void werfen_null(){
	int i;                                                                                                  //Das Feld "Werfen" muss nach jeder Runde wieder auf 0 gesetzt werden, für die neue Bewertungs Funktion
	for (i = 0; i < 30; i++){
		werfen[i] = 0;
	}
}
}

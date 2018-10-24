package FILEMANAGEMENT;

import java.io.*;
import static GAME.KI.feld;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
 * Hier wird das ganze Speichern von Dateien gemanaged
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class SAVE {
    /**
     * Hier wird ein Name und einige Parameter übergeben und alles in einer Datei mit der Endung .sav gespeichert
     * @param name
     * Eingegebener Name der Datei
     * @param spieler
     * Spieler, der am Zug ist
     * @param fgi
     * Feldhöhe
     * @param fgj
     * Feldbreite
     * @param gwzahl
     * Gewinnanzahl
     * @param player
     * Singleplayer-, oder Multiplayerspiel
     * @param pc
     * Ob der Computer aktiviert war
     * @param stufe
     * Mit welcher Rekursionsstufe die KI arbeitet
     * @param r1
     * Rotanteil Spieler 1
     * @param r2
     * Rotanteil Spieler 2
     * @param g1
     * Grünanteil Spieler 1
     * @param g2
     * Grünanteil Spieler 2
     * @param b1
     * Blauanteil Spieler 1
     * @param b2
     * Blauanteil Spieler 2
     * @param s1
     * Spielstand Spieler 1
     * @param s2
     * Spielstand Spieler2
     * @param bomb
     * Ob Bombing aktiviert war
     * @param sb1
     * Anzahl der übrigen Bomben Player1
     * @param sb2
     * Anzahl der übrigen Bomben Player2
     * @throws FileNotFoundException
     * Falls die Datei nicht gefunden werden kann bzw angelegt werden kann, wird eine Exception geworfen
     * @throws IOException 
     * Falls ein Fehler beim FileOutStream auftritt, wird eine Exception geworfen
     */
    public void savefile(String name, int spieler, int fgi, int fgj, int gwzahl, int player, int pc, int stufe, int r1, int r2, int g1, int g2, int b1, int b2, int s1, int s2, int bomb, int sb1, int sb2) throws FileNotFoundException, IOException{  //Hier wird ein Spielstand gespeichert
        FileOutputStream save;
        if (name != null){
            String n1 = "";                                                         //Stings, für den Fall, dass das Spielfeld größer als 9x9 ist (für die.sav Datei)
            String n2 = "";
            String n3 = "";                                                         //String für den Fall, dass die Gwzahl größer 9 ist
            String n4 = "";                                                         //String für den Fall, falls die r,g,b größer 10 bzw größer 100 sind                
            String n5 = "";                                                                 
            String n6 = "";
            String n7 = "";
            String n8 = "";
            String n9 = "";
            String na = "";
            String nb = "";
            if (spieler == -1){                                                     //Spieler -1 = 2
                spieler = 2;
            }
            if (fgi < 9){                                                           //Siehe oben
                n1 = "0";
            }
            if (fgj < 9){
                n2 = "0";
            }
            if (gwzahl < 9){
                n3 = "0";
            }
            if (r1 < 100){                                                           //Siehe oben
                n4 = "0";
            }
            if (r2< 100){
                n5 = "0";
            }
            if (g1 < 100){
                n6 = "0";
            }
            if (g2 < 100){                                                           //Siehe oben
                n7 = "0";
            }
            if (b1 < 100){
                n8 = "0";
            }
            if (b2 < 100){
                n9 = "0";
            }
            if (r1 < 10){                                                           //Siehe oben
                n4 = "00";
            }
            if (r2< 10){
                n5 = "00";
            }
            if (g1 < 10){
                n6 = "00";
            }
            if (g2 < 10){                                                           //Siehe oben
                n7 = "00";
            }
            if (b1 < 10){
                n8 = "00";
            }
            if (b2 < 10){
                n9 = "00";
            }
            if (s1 < 10){
                na = "0";
            }
            if (s2 < 10){
                nb = "0";
            }
            String tmp = "";
            for (int u = 0; u < fgi; u++){
                for (int q = 0; q < fgj; q++){
                    tmp += feld[u][q];
                }
            }
            String test = "BAMK" + "0" + spieler + n1 + fgi + n2 + fgj + n3 + gwzahl + player + pc + stufe + n4 + r1 + n5 + r2 + n6 + g1 + n7 + g2 + n8 + b1 + n9 + b2 + na + s1 + nb + s2 + bomb + sb1 + sb2 + tmp;                                                           //Und in eine Datei hintereinander reingelesen
            if (name.equals("autosave")){                                           //Der autosave, während des Spieles, wird nach jedem Zug durchgeührt
                save =  new FileOutputStream("./SAVE/autosave"); 
            }
            else if(name.length() < 4){                                             //Der String der gespeichert werden soll wird initialisiert
                save =  new FileOutputStream("./SAVE/" + name + ".sav");                        //Wie in GUI_GAME, das .sav wird angehängt, falls es nicht vorhanden ist
            }   
            else if(name.charAt(name.length() - 4) == '.' && name.charAt(name.length() - 3) == 's' && name.charAt(name.length() - 2) == 'a' && name.charAt(name.length() - 1) == 'v'){
                save =  new FileOutputStream("./SAVE/" + name);                                 //falls doch wird es weggelassen
            }
            else {
                save =  new FileOutputStream("./SAVE/" + name + ".sav");
            }
            for (int c=0; c < test.length(); c++){
                save.write((byte)test.charAt(c));                                   //Der String wird nun geschrieben
            }
            save.close();                                                          //Und die Datei geschlossen
            if (name.equals("autosave")){                                               //Bei tmp wird auch keine Meldung angezeigt
            }
            else{
            System.out.println("Erfolgreich gespeichert: Spieler = " + spieler + " FGI = " + fgi +  " FGJ = " + fgj + " Gwzahl = " + gwzahl);
            UIManager UI=new UIManager();                                          //war alles erfolgreich, wird eine Message ausgegeben
            UIManager.put("OptionPane.background",new ColorUIResource(255,255, 255));
            UIManager.put("Panel.background",new ColorUIResource(255,255,255));
            UIManager.put("Button.background", Color.white);
            JOptionPane.showMessageDialog(null,"Speichern erfolgreich!", "Speichern", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}



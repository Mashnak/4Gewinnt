/**
 * Im Package GUI liegen alle Klassen, die etwas mit der grafischen Oberfläche des Spielfeldes zu tun haben.
 */
package GUI;

import GAME.GAME_VARIABLES;
import THREADS.THREAD3;
import static GAME.CLASS_FUNCTIONS.feld_gleich_null;
import static GAME.CLASS_FUNCTIONS.spielfeld;
import GAME.LOCAL;
import GAME.NETWORK;
import MAIN.MAIN;
import static NETWORK.SERVER.ssocket;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;


/**
 * Erbt von FRAME_SETTINGS <br>
 * Hier wird das LOCAL Einstellungsfenster geöffnet und Änderungen gespeichert
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class FRAME_LOCAL extends FRAME_SETTINGS implements ActionListener,ItemListener {
    private final SPIELER tspieler1 = new SPIELER(255, 255, 0);
    private final SPIELER tspieler2 = new SPIELER(255, 0, 0);
    private final GAME_VARIABLES tgui = new GAME_VARIABLES();
    private final MAIN tmain = new MAIN();
    private final LOCAL loc;
    private final JComboBox modus;
    private final JLabel modusL;
    private final String[] MD={"Standart","Bombing", "Greymod"};
    private final NETWORK net;
    private boolean grey = false;
    private Color farbe1 = new Color(255, 255, 0);
    private Color farbe2 = new Color(255, 0, 0);
    
    /**
     * Dieser Konstruktor ist für die Einstellungen des Singleplaymoduses. Erbt 
     * @param tmp
     * Zugriff über das Objekt auf die Variablen von MAIN
     * @param tmp2
     * Zugriff über das Objekt auf die Variablen von SPIELER (spieler1)
     * @param tmp3
     * Zugriff über das Objekt auf die Variablen von SPIELER (spieler2)
     * @param tmp4
     * Zugriff über das Objekt auf die GAME_VARIABLES
     * @param tmp5
     * Zugriff über das Objekt auf den Singleplayermodus
     * @param tmp6
     * Zugriff auf das Netzwerk, um den Socket etc. zu schließen
     * @throws IOException 
     * Falls der ServerSocket nicht geschlossen werden kann
     */
    public FRAME_LOCAL(MAIN tmp, SPIELER tmp2, SPIELER tmp3, GAME_VARIABLES tmp4, LOCAL tmp5, NETWORK tmp6) throws IOException {
        super(tmp, tmp2, tmp3, tmp4);

        local.setTitle("Spiel-Einstellungen \"Lokal\"");
                //Farbe1 und Farbe2
                color1 = new ROUNDBUTTON("");
                color2 = new ROUNDBUTTON("");
                //COLOR1 = new JComboBox(COL);
                //COLOR2 = new JComboBox(COL);
                modus = new JComboBox(MD);
                //local.add(COLOR2);
                //local.add(COLOR1);
                local.add(color1);
                local.add(color2);
                local.add(modus);
                modus.setBounds(dimxf*40/100, dimyf*50/100, dimxf*20/100, dimyf*8/100);
		color2.setBounds(dimxf*70/100, dimyf*50/100, dimxf*10/100, dimxf*10/100);
                color1.setBounds(dimxf*70/100, dimyf*20/100, dimxf*10/100, dimxf*10/100);
                COLOR1L =new JLabel("Spielerfarbe 1:");
                local.add(COLOR1L);
                modusL =new JLabel("Spieltyp:");
                local.add(modusL);
		COLOR2L =new JLabel("Spielerfarbe 2:");		
                local.add(COLOR2L);
		COLOR2L.setBounds(dimxf*69/100, dimyf*40/100, dimxf*20/100, dimyf*8/100);
                COLOR1L.setBounds(dimxf*69/100, dimyf*10/100, dimxf*20/100, dimyf*8/100);
                modusL.setBounds(dimxf*40/100, dimyf*40/100, dimxf*20/100, dimyf*8/100);
                //local.getContentPane().add(COLOR2);
                //local.getContentPane().add(COLOR1);
                //COLOR2.setSelectedItem("ROT");
                //COLOR2.addItemListener(this);
                modus.addItemListener(this);
                color1.addActionListener(this);
                color2.addActionListener(this);
                color1.setBackground(new Color(tspieler1.getR(), tspieler1.getG(), tspieler1.getB()));
                color2.setBackground(new Color(tspieler2.getR(), tspieler2.getG(), tspieler2.getB()));
                //COLOR1.addItemListener(this);
                loc = tmp5;
                net = tmp6;
                farbe1 = new Color(255, 255, 0);
                farbe2 = new Color(255, 0, 0);
                
    }
        
    /**
     * Hier werden die ausgewählten Einstellungen auf das Singleplayerspiel übernommen
     */
        private void initGame(){
            gui.setPlayBomb(tgui.getPlayBomb());
            System.out.println("PlayBomb = " + tgui.getPlayBomb());
            gui.setAbbrechen(tgui.getAbbrechen());
            gui.setNewgameblock(tgui.getNewgameblock());
            gui.setIsServer(tgui.getIsServer());
            main.setFgi(tmain.getFgi());
            main.setFgj(tmain.getFgj());
            main.setGwzahl(tmain.getGwzahl());
            main.setRekstufe(tmain.getRekstufe());
            main.setPc(tmain.getPc());
            main.setSpieler(tmain.getSpieler());
            spieler1.setR(tspieler1.getR()); 
            spieler1.setG(tspieler1.getG()); 
            spieler1.setB(tspieler1.getB()); 
            spieler2.setR(tspieler2.getR()); 
            spieler2.setG(tspieler2.getG()); 
            spieler2.setB(tspieler2.getB()); 
            spieler1.setSpielstand(0);
            spieler2.setSpielstand(0);
            spieler1.setAbomb(tspieler1.getAbomb());
            spieler2.setAbomb(tspieler2.getAbomb());
        }
        
        
        /**
         * Hintergrund wird gezeichnet
         * @param g 
         * paintComponent
         */
    @Override
	protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, gui.getDimx(), gui.getDimy(),  this);
        }
       
        
    @Override
    /**
     * Hier werden alle Änderungen in den Spieleinstellungen bestätigt, oder abgebrochen
     */
	public void actionPerformed(ActionEvent e){
		String s=(String) Level.getSelectedItem();
		
                if(e.getSource() == abbrechen){
                        if (gui.getGo() == true){
                            gui.setWarten(false);                                    //Spielstein zur Bewegung freigegeben
                            gui.setSeeGame(true);                                    //Spielfeld sichtbar gemacht (falls es sichtabr war)
                            gui.setNewgameblock(0);                                  //Es darf ein neues Spiel wieder gestartet werden
                        }
                        local.setVisible(false);                                 //Einstellungsfenster wird ausgeblendet
		}
                
                if (e.getSource() == color1){                                   //JColorChooser um Spielerfarbe 1 auszuwählen
                    try{
                    farbe1 = JColorChooser.showDialog(this, "Farbauswahl", null); 
                    if(!(farbe1.equals(farbe2))){                               //Gleiche Farbe auswählen wird verhindert
                        System.out.println("Color1 " + farbe1 + " Color2 " + farbe2);
                    tspieler1.setR(farbe1.getRed());
                    tspieler1.setG(farbe1.getGreen());
                    tspieler1.setB(farbe1.getBlue());
                    color1.setBackground(new Color(tspieler1.getR(), tspieler1.getG(), tspieler1.getB()));
                    }                                                           //Hintergrund es Buttons nach Spielerfarbe 1 färben
                    }catch(NullPointerException ex){                  
                    }
                }
                
                if (e.getSource() == color2){                                   //JColorChooser um Spielerfarbe 2 auszuwählen
                    try{
                    farbe2 = JColorChooser.showDialog(this, "Farbauswahl", null); 
                    if (!(farbe1.equals(farbe2))){                              //Gleiche Farbe auswählen wird verhindert
                        System.out.println("Color1 " + farbe1 + " Color2 " + farbe2);
                    tspieler2.setR(farbe2.getRed());
                    tspieler2.setG(farbe2.getGreen());
                    tspieler2.setB(farbe2.getBlue());
                    color2.setBackground(new Color(tspieler2.getR(), tspieler2.getG(), tspieler2.getB()));
                    }                                                           //Hintergrund es Buttons nach Spielerfarbe 2 färben
                    }catch(NullPointerException ex){
                        
                    }
                }
                
		if(e.getSource() == ok){                                        //Wenn das Spiel bestätig wurde
                    net.t.stop();                                               //Wird der Timer von Network gestoppt, falls dieser noch am laufen war
                    main.setPlayer(0);                                          //Und sowohl alte Multiplayer, als auch Singleplayer spiele abgebrochen
                    gui.setAbbrechen(true);
                    gui.setIsServer(false);
                    synchronized(loc) {
                         loc.notifyAll();
                    }
                    synchronized(net) {
                         net.notifyAll();
                    }
                    if (gui.getFirstMulti() == false){                          //Natürlich wird nur versucht die Sockets zu schließen, falls es nicht das erste Multiplayer Spiel ist
                        try {
                            if (gui.getIsServer() == true){
                                System.out.println("Socket geschlossen" + "");
                                ssocket.close();                                //Serversocket geschlossen, fall er davor einen geöffnet hatte
                            }
                        } catch (IOException ex) {                              //Fehler abfangen
                        Logger.getLogger(FRAME_NETWORK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }   
                    }
                        gui.setSeeGame(true);                                   //Spiel wird sichtbar gemacht
                        initGame();                                                                                     //Die ausgewählten Spieleinstellungen werden übertragen
                        feld_gleich_null(main.getFgi(), main.getFgj());                                                 //Das Spielfeld wird mit Nullen aufgefüllt (neutral)
                        spielfeld(main.getFgi(), main.getFgj());                                                        //Das feld wird an spielfeld für das Zeichnen übergeben
			gui.setGo(true);
                        if (main.getPc() == 1){                                                                         //Falls man gegen den Computer spielt, wird die Komplementärfarbe gewählt
                            spieler2.setR(255 - spieler1.getR());                                                       
                            spieler2.setG(255 - spieler1.getG()); 
                            spieler2.setB(255 - spieler1.getB()); 
                            gui.setBomb(false);                                                                         //Und Bombing auf jeden Fall deaktiviert
                            grey = false;                                                                               //Genau so den Grey Modus
                        }
                        if (grey == true){                                                                              //Falls grey true ist, werden die Farben Grau gesetzt
                               spieler1.setR(125);
                               spieler1.setG(125);
                               spieler1.setB(125);
                               spieler2.setR(95);
                               spieler2.setG(95);
                               spieler2.setB(95); 
                        }
                        gui.setSblock(0);                                                                               //Es wird erlaubt zu speichern
                        gui.setAbbrechen(false);                                                                        //Abbrechen wird false gesetzt
                        gui.setNewgameblock(0);                                                                         //Genau so den Neues Spiel Block
                        local.setVisible(false);                                                                        //Einstellungsfenster wird ausgeblendet
                        gui.setWarten(false);                                                                           //Erlaubt den Spielstein zu bewegen
                        gui.setSeeGame(true);                                                                           //Das Spielfeld sichtbar gemacht
                        gui.setRadius(((gui.getDimx() * 2) / 3) / (3 * main.getFgi() / 2 + main.getFgj()));
                        gui.setOvalx(gui.getDimx() / 2 - ((main.getFgj() * gui.getRadius()) / 2));
                        new THREAD3(main, gui, spieler1, spieler2, loc).start();                                        //Und das SPiel gestartet (LOCAL)
		}
	}
		
        
        /**
         * Hier werden alle Änderungen erfasst, die in den Spieleinstellungen vom Benutzer getätigt wurden
         * @param evt 
         * Ausgewählte Dropbox
         */
    @Override
	public void itemStateChanged(ItemEvent evt) {
                            
                            String selectedItem1 = (String)VSC.getSelectedItem();
                            String selectedItem5 = (String)Level.getSelectedItem();
                            String selectedItem2 = (String)AFS.getSelectedItem();
                            String selectedItem6 = (String)modus.getSelectedItem();
                            String selectedItem3 = (String)Combogwzahl.getSelectedItem();
                         

			    
	if (null != selectedItem1) switch (selectedItem1) {
            case "Spieler 1 vs. Spieler 2":                                     //Hier wird erkannt ob man gegen den Computer, oder gegen einen anderen Spieler spielen möchte
                Level.setVisible(false);                                        //Und dem enstprechen Elemente ein und ausgeblendet bzw gesetzt 
                LevelL.setVisible(false);
                AFS.setVisible(false);
                AFSL.setVisible(false);
                color2.setVisible(true);
                COLOR2L.setVisible(true);
                tmain.setPc(0);
                modus.setVisible(true);
                modusL.setVisible(true);
                break;
            case "Spieler vs. Computer":
                Level.setVisible(true);
                LevelL.setVisible(true);
                AFS.setVisible(true);
                AFSL.setVisible(true);
                color2.setVisible(false);
                COLOR2L.setVisible(false);
                tmain.setPc(1);
                modus.setVisible(false);
                modusL.setVisible(false);
                grey = false;
                break;
        }
                            
        if (null != selectedItem6) switch (selectedItem6) {                     //Spielmodus wird ausgewählt und Elemente ein/ausgeblendet und gesetzt
            case "Standart":
                //color2.setVisible(true);
                tgui.setPlayBomb(0);
                tspieler1.setAbomb(0);
                tspieler2.setAbomb(0);
                color1.setVisible(true);
                COLOR1L.setVisible(true);
                //COLOR2L.setVisible(true);
                grey = false;
                break;
            case "Bombing":
                if (VSC.getSelectedIndex() == 1){
                    tgui.setPlayBomb(0);
                    tspieler1.setAbomb(0);
                    tspieler2.setAbomb(0);
                    color1.setVisible(true);
                    color2.setVisible(false);
                    COLOR1L.setVisible(true);
                    COLOR2L.setVisible(false);
                    grey = false;  
                }
                else{
                    tgui.setPlayBomb(1);
                    tspieler1.setAbomb(1);
                    tspieler2.setAbomb(1);
                    color1.setVisible(true);
                    color2.setVisible(true);
                    COLOR1L.setVisible(true);
                    COLOR2L.setVisible(true);
                    grey = false;
                }
                break;
            case "Greymod":

                if (VSC.getSelectedIndex() == 1){
                    grey = false;
                    color1.setVisible(true );
                    COLOR1L.setVisible(true);
                    
                }
                else{
                    tgui.setPlayBomb(0);
                    tspieler1.setAbomb(0);
                    tspieler2.setAbomb(0);
                    color1.setVisible(false);
                    color2.setVisible(false);
                    COLOR1L.setVisible(false);
                    COLOR2L.setVisible(false);
                    grey = true;
                }
                break;
        }
                            
        tmain.setFgj(SPX.getSelectedIndex() + 4);                               //Auswahl der Spielfeldbreite
        tmain.setFgi(SPY.getSelectedIndex() + 4);                               //Auswahl der Spielfeldhöhe
        tmain.setGwzahl(Combogwzahl.getSelectedIndex() + 4);                    //Auswahl der Gewinnanzahl
   
                            
        
        if (tmain.getFgi() >= tmain.getFgj() && tmain.getGwzahl() > tmain.getFgi()){    //Hier wird abgefangen, das die Gwzahl größer als Spielfeldhöhe/breite sein kann
            tmain.setGwzahl(tmain.getFgi());
            Combogwzahl.setSelectedIndex(tmain.getFgi() - 4);   
        }
        else if (tmain.getFgi() <= tmain.getFgj() && tmain.getGwzahl() > tmain.getFgj()){
            tmain.setGwzahl(tmain.getFgj());
            Combogwzahl.setSelectedIndex(tmain.getFgj() - 4);
        }
                            
        if (null != selectedItem5) switch (selectedItem5) {                     //Falls Computer ausgewählt wurde, kann hier die Schwirigkeit dynamisch eingestellt werden
            case "Leicht":
                tmain.setRekstufe(6);
                if (tmain.getFgj()>10){
                    tmain.setRekstufe(5);
                }
                if (tmain.getFgj()>14){
                    tmain.setRekstufe(4);
                }
                if (tmain.getFgj()>20){
                    tmain.setRekstufe(3);
                }
                if (tmain.getFgj()>25){
                    tmain.setRekstufe(2);
                }
                break;
            case "Mittel":
                tmain.setRekstufe(7);
                if (tmain.getFgj()>10){
                    tmain.setRekstufe(6);
                }
                if (tmain.getFgj()>14){
                    tmain.setRekstufe(5);
                }
                if (tmain.getFgj()>20){
                    tmain.setRekstufe(4);
                }
                if (tmain.getFgj()>25){
                    tmain.setRekstufe(3);
                }
                break;
            case "Schwer":
                tmain.setRekstufe(8);
                if (tmain.getFgj()>10){
                    tmain.setRekstufe(7);
                }
                if (tmain.getFgj()>14){
                    tmain.setRekstufe(6);
                }
                if (tmain.getFgj()>20){
                    tmain.setRekstufe(5);
                }
                if (tmain.getFgj()>25){
                    tmain.setRekstufe(4);
                }
                break;
        }
                            
                            
        if (null != selectedItem2) switch (selectedItem2) {         //Anfangsspieler bei Spieler vs.Computer wird ausgewählt
            case "Spieler":
                tmain.setSpieler(1);
                break;
            case "Computer":
                tmain.setSpieler(-1);
                break;
        }
                            

			
	}

}

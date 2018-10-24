/**
 * Im Package GUI liegen alle Klassen, die etwas mit der grafischen Oberfläche des Spielfeldes zu tun haben
 */
package GUI;

import GAME.GAME_VARIABLES;
import GAME.LOCAL;
import static GAME.CLASS_FUNCTIONS.feld_gleich_null;
import static GAME.CLASS_FUNCTIONS.spielfeld;
import GAME.NETWORK;
import MAIN.MAIN;
import NETWORK.CLIENT;
import NETWORK.SERVER;
import static NETWORK.SERVER.ssocket;
import THREADS.THREAD6;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Erbt von FRAME_SETTINGS
 * Hier wird das NETZWERK Einstellungsfenster geöffnet und Änderungen gespeichert
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class FRAME_NETWORK extends FRAME_SETTINGS{
    private final JLabel SoCL;
    private final String[] SoCS={"Server","Client"};
    private final JComboBox SoC = new JComboBox(SoCS);
    private final SPIELER tspieler1 = new SPIELER(255, 255, 0);
    private final SPIELER tspieler2 = new SPIELER(255, 0, 0);
    private final JTextField IP = new JTextField("localhost",15);
    private final JTextField Port = new JTextField("50001",15);
    private final GAME_VARIABLES tgui = new GAME_VARIABLES();
    private final MAIN tmain = new MAIN();
    private final SERVER ser;
    private final CLIENT cli;
    private final NETWORK net;
    private final JLabel IPL;
    private final LOCAL loc;
    private String ip;
    private int timeout, port;
    private final JButton IPAD;
    private Color farbe1;
    private Color farbe2;

    /**
     * Konstruktor zum Bekommen aller benötigten Daten
     * @param tmp
     * Zugriff auf alle Main Variablen
     * @param tmp2
     * Zugriff auf Spieler1 Variablen
     * @param tmp3
     * Zugriff aus Spieler2 Variablen
     * @param tmp4
     * Zugriff auf GAME_VARUABLES
     * @param tmp5
     * Zugriff auf den Server
     * @param tmp6
     * Zugriff auf den Client
     * @param tmp7
     * Zugriff auf das Netzwerkspiel
     * @param tmp8
     * Zugriff auf das Singleplayerspiel
     * @throws IOException 
     * Falls der ServerSocket nicht geschlossen werden kann
     */
    public FRAME_NETWORK(MAIN tmp, SPIELER tmp2, SPIELER tmp3, GAME_VARIABLES tmp4, SERVER tmp5, CLIENT tmp6, NETWORK tmp7, LOCAL tmp8) throws IOException {
        super(tmp, tmp2, tmp3, tmp4);
        
        local.setTitle("Spiel-Einstellungen \"Netzwerk\"");
        
                 //Farbe1 und Farbe2
        //COLOR1 = new JComboBox(COL);                                      
        //COLOR2 = new JComboBox(COL);
        color1 = new ROUNDBUTTON("");
        color2 = new ROUNDBUTTON("");
        COLOR1L =new JLabel("Server:");	
        COLOR2L =new JLabel("Client:");	
        local.add(color1);
        local.add(color2);
        COLOR1L.setBounds(dimxf*70/100, dimyf*10/100, dimxf*20/100, dimyf*8/100);
        COLOR2L.setBounds(dimxf*70/100, dimyf*40/100, dimxf*20/100, dimyf*8/100);
        local.add(COLOR1L);
        local.add(COLOR2L);
	color2.setBounds(dimxf*68/100, dimyf*50/100, dimxf*10/100, dimxf*10/100);
        color1.setBounds(dimxf*68/100, dimyf*20/100, dimxf*10/100, dimxf*10/100);
        //COLOR2.setSelectedItem("ROT");
        color1.addActionListener(this);
        color2.addActionListener(this);
        color1.setBackground(new Color(tspieler1.getR(), tspieler1.getG(), tspieler1.getB()));
        color2.setBackground(new Color(tspieler2.getR(), tspieler2.getG(), tspieler2.getB()));
        Beginner = new JComboBox(Beg);
        local.add(Beginner);
        Beginner.setBounds(dimxf*100/100, dimyf*40/100, dimxf*20/100, dimyf*8/100);
        BeginnerL = new JLabel("Beginner");
        local.add(BeginnerL);
        BeginnerL.setBounds(dimxf*100/100, dimyf*30/100, dimxf*20/100, dimyf*8/100);
        Beginner.addItemListener(this);
        
        local.add(IPAD = new JButton("IP erhalten"));
        IPAD.setBounds(dimxf*100/100, dimyf*72/100, dimxf*20/100, dimyf*10/100);
	IPAD.addActionListener(this);
	local.getContentPane().add(IPAD);
        
	local.add(SoC);     //Server oder Client Dropbox
	local.setSize(dimxf*5/4,dimyf);
        SoC.setBounds(dimxf*100/100, dimyf*20/100, dimxf*20/100, dimyf*8/100);
	//local.setLayout(null);
	SoCL =new JLabel("Server oder Client ?");
	local.add(SoCL);
	SoCL.setBounds(dimxf*100/100, dimyf*10/100, dimxf*20/100, dimyf*8/100);
        //local.getContentPane().add(SoC);
        SoC.addItemListener(this);
		

	local.add(IP);                                                          //IP Editfeld, damit der Client auf die IP connecten kann
	IP.setBounds(dimxf*100/100, dimyf*40/100, dimxf*20/100, dimyf*8/100);
        IP.setVisible(false);
	//local.setLayout(null);
        IPL = new JLabel("IP-Adresse");
	local.add(IPL);
        IPL.setVisible(false);
	IPL.setBounds(dimxf*100/100, dimyf*30/100, dimxf*20/100, dimyf*8/100);
        //local.getContentPane().add(IP);
        
	local.add(Port);                                                        //Port, den der Client/Server auswählen kann
	Port.setBounds(dimxf*100/100, dimyf*60/100, dimxf*20/100, dimyf*8/100);
	//local.setLayout(null);
        JLabel PortL = new JLabel("Port-Nummer");
	local.add(PortL);
	PortL.setBounds(dimxf*100/100, dimyf*50/100, dimxf*20/100, dimyf*8/100);
        
        ser = tmp5;
        cli = tmp6;
        net = tmp7;
        loc = tmp8;
        
        farbe1 = new Color(255, 255, 0);
        farbe2 = new Color(255, 0, 0);
    }
    
    /**
     * Hintergrund wird gezeichnet
     * @param g 
     * Graphics
     */
    @Override
    protected void paintComponent(Graphics g){
        g.drawImage(backgroundImage, 0, 0, dimxf, dimyf,  this);
    }
    
    /**
     * Falls das Spiel gestartet wird, werden alle Änderungen, die getätigt wurden übernommen
     */
    private void initGame(){
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
        }
    
    /**
     * Hier wird das Spiel bestätigt und begonnen bzw. abgebrochen
     * @param e 
     * ActionEvent
     */
    @Override
    public synchronized void actionPerformed(ActionEvent e){  
        //String s=(String) Level.getSelectedItem();
        boolean sGefunden = true;
	if(e.getSource() == abbrechen || (local.getDefaultCloseOperation() == EXIT_ON_CLOSE)){
            if (gui.getGo() == true){
                gui.setWarten(false);
                gui.setSeeGame(true);
                gui.setNewgameblock(0);                               //Es darf ein neues Spiel wieder gestartet werden                       
            }
            local.setVisible(false);
        }
        
        if (e.getSource() == IPAD){
            try {
                JOptionPane.showMessageDialog(null, InetAddress.getLocalHost(), "IP-Adresse", JOptionPane.INFORMATION_MESSAGE);
            } catch (UnknownHostException ex) {
                Logger.getLogger(FRAME_NETWORK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource() == color1){
                    try{
                    farbe1 = JColorChooser.showDialog(this, "Farbauswahl", null);
                    if(!(farbe1.equals(farbe2))){
                        System.out.println("Color1 " + farbe1 + " Color2 " + farbe2);
                    tspieler1.setR(farbe1.getRed());
                    tspieler1.setG(farbe1.getGreen());
                    tspieler1.setB(farbe1.getBlue());
                    color1.setBackground(new Color(tspieler1.getR(), tspieler1.getG(), tspieler1.getB()));
                    }
                    }catch(NullPointerException ex){
                        
                    }
                }
                
                if (e.getSource() == color2){
                    try{
                    farbe2 = JColorChooser.showDialog(this, "Farbauswahl", null); 
                    if (!(farbe1.equals(farbe2))){
                        System.out.println("Color1 " + farbe1 + " Color2 " + farbe2);
                    tspieler2.setR(farbe2.getRed());
                    tspieler2.setG(farbe2.getGreen());
                    tspieler2.setB(farbe2.getBlue());
                    color2.setBackground(new Color(tspieler2.getR(), tspieler2.getG(), tspieler2.getB()));
                    }
                    }catch(NullPointerException ex){
                        
                    }
                }
        
	if(e.getSource() == ok){
            initGame();
            net.t.stop();
            main.setPlayer(0);
            gui.setAbbrechen(true);
            synchronized(loc) {
                loc.notifyAll();
            }
            synchronized(net) {
                net.notifyAll();
            }
            gui.setSeeGame(false);
            if (gui.getFirstMulti() == false){
                try {
                        ssocket.close();
                    } catch (IOException ex) {
                    Logger.getLogger(FRAME_NETWORK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }   
            }
            local.setVisible(false);
            feld_gleich_null(main.getFgi(), main.getFgj());                                               //Das Spielfeld wird mit Nullen aufgefüllt (neutral)
            System.out.println("First Multi: " + gui.getFirstMulti());
            spielfeld(main.getFgi(), main.getFgj());    
            gui.setRadius(((gui.getDimx() * 2) / 3) / (3 * main.getFgi() / 2 + main.getFgj()));
            gui.setOvalx(gui.getDimx() / 2 - ((main.getFgj() * gui.getRadius()) / 2));
            if (gui.getIsServer() == true){
                gui.setPort(Integer.valueOf(Port.getText()));
                System.out.println("SERVER: THREAD 6 wird gestartet!");
                new THREAD6(main, gui, spieler1, spieler2, gui.getPort(), ser, net).start();
            }
            else if (gui.getIsServer() == false){
                gui.setSblock(4);
                try {
                    gui.setIP(IP.getText());
                    gui.setPort(Integer.valueOf(Port.getText()));
                    System.out.println(gui.getIP());
                    sGefunden = cli.client(main, gui, spieler1, spieler2, gui.getPort(), gui.getIP(), net);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FRAME_NETWORK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
}
	
    /**
     * Hier werden alle Änderungen an den Comboboxen verarbeitet, die der Benutzer ausgewählt hat
     * @param evt 
     * Item
     */
    @Override
    public void itemStateChanged(ItemEvent evt) {
							
	JComboBox VSC = (JComboBox) evt.getSource();
        String selectedItem = (String)VSC.getSelectedItem();
        String selectedItem5 = (String)SoC.getSelectedItem();
        String selectedItem6 = (String)Beginner.getSelectedItem();
        String selectedItem7 = (String)Level.getSelectedItem();
				    
	if (null != selectedItem) switch (selectedItem) {
            case "Spieler 1 vs. Spieler 2":
                Level.setVisible(false);
                LevelL.setVisible(false);
                tmain.setPc(0);
                break;
            case "Spieler vs. Computer":
                Level.setVisible(true);
                LevelL.setVisible(true);
                tmain.setPc(1);
                break;
        }
        
        if (null != selectedItem5) switch (selectedItem5) {
            case "Server":
                Combogwzahl.setVisible(true);
                SPY.setVisible(true);
                SPX.setVisible(true);
                gwanzahlLabel.setVisible(true);
                Y.setVisible(true);
                X.setVisible(true);
                tgui.setIsServer(true);
                Beginner.setVisible(true);
                BeginnerL.setVisible(true);
                IP.setVisible(false);
                IPL.setVisible(false);
                break;
            case "Client":
                Combogwzahl.setVisible(false);
                SPY.setVisible(false);
                SPX.setVisible(false);
                gwanzahlLabel.setVisible(false);
                Y.setVisible(false);
                X.setVisible(false);
                tgui.setIsServer(false);
                Beginner.setVisible(false);
                BeginnerL.setVisible(false);
                IP.setVisible(true);
                IPL.setVisible(true);
                break;
        }
        
        if (null != selectedItem6)switch (selectedItem6) {
            case "Server":
                tmain.setSpieler(1);
                break;
            case "Client":
                tmain.setSpieler(-1);
                break;
        }
        
        tmain.setFgj(SPX.getSelectedIndex() + 4);
        tmain.setFgi(SPY.getSelectedIndex() + 4);
        tmain.setGwzahl(Combogwzahl.getSelectedIndex() + 4);
	
         if (tmain.getFgi() >= tmain.getFgj() && tmain.getGwzahl() > tmain.getFgi()){
            tmain.setGwzahl(tmain.getFgi());
            Combogwzahl.setSelectedIndex(tmain.getFgi() - 4);
        }
        else if (tmain.getFgi() <= tmain.getFgj() && tmain.getGwzahl() > tmain.getFgj()){
            tmain.setGwzahl(tmain.getFgj());
            Combogwzahl.setSelectedIndex(tmain.getFgj() - 4);
        }
        
        if (null != selectedItem7) switch (selectedItem7) {
            case "Leicht":
                tmain.setRekstufe(6);
                if (tmain.getFgj()>10){
                    tmain.setRekstufe(5);
                }   if (tmain.getFgj()>15){
                    tmain.setRekstufe(4);
                }   if (tmain.getFgj()>20){
                    tmain.setRekstufe(3);
                }   if (tmain.getFgj()>25){
                    tmain.setRekstufe(2);
            }   break;
            case "Mittel":
                tmain.setRekstufe(7);
                if (tmain.getFgj()>10){
                    tmain.setRekstufe(6);
                }   if (tmain.getFgj()>15){
                    tmain.setRekstufe(5);
                }   if (tmain.getFgj()>20){
                    tmain.setRekstufe(4);
                }   if (tmain.getFgj()>25){
                    tmain.setRekstufe(3);
                }   break;
            case "Schwer":
                tmain.setRekstufe(8);
                if (tmain.getFgj()>10){
                    tmain.setRekstufe(7);
                }   if (tmain.getFgj()>15){
                    tmain.setRekstufe(6);
                }   if (tmain.getFgj()>20){
                    tmain.setRekstufe(5);
                }   if (tmain.getFgj()>25){
                tmain.setRekstufe(4);
            }   break;
        }
				
    }
   
    
}

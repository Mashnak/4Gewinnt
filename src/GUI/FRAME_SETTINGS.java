package GUI;

import GAME.GAME_VARIABLES;
import MAIN.MAIN;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Diese Abstrakte Klasse dient dazu, gleiche Elemente von LOCAL und NETZWORK FRAME zusammenzufassen
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public abstract class FRAME_SETTINGS extends JComponent implements ActionListener,ItemListener {
	protected JButton ok;
	protected JButton abbrechen, color1, color2;
	protected JComboBox Combogwzahl, SPX, SPY, Level,VSC, AFS, COLOR1, COLOR2, Beginner; 
	private final String[] 
        Gwanzahl,
        XY={"4","5","6","7","8","9","10","11","12","13","14","15"},
	LevelP={"Leicht","Mittel","Schwer"};
	private final String[] VS={"Spieler 1 vs. Spieler 2","Spieler vs. Computer"};
        private final String[] AS={"Spieler", "Computer"};
        protected String[] COL={"GELB", "ROT", "GRÜN", "BLAU", "LILA", "ORANGE"};
        protected String[] Beg={"Server", "Client"};
	protected JLabel gwanzahlLabel,X,Y,LevelL,VSL, AFSL, COLOR1L, COLOR2L,BeginnerL;
        private final int dimx = Toolkit.getDefaultToolkit().getScreenSize().width ;
        private final int dimy = Toolkit.getDefaultToolkit().getScreenSize().height ;
        protected int dimxf = dimx/3;
        protected int dimyf = dimy/3;
        protected Image backgroundImage; 
        protected int tmp1 = 0; 
        protected int tmp2 = 1; 
        protected MAIN main;
        protected SPIELER spieler1;
        protected SPIELER spieler2;
        protected GAME_VARIABLES gui;
        protected JFrame local = new JFrame();
        private Toolkit toolkit;
        private Image image;
        private Cursor c;

	
	
	/**
         * Hier wird das Auswahlfenster für die Spieloptionen (FRAME_LOCAL und FRAME_NETWORK) generiert und dann vererbt
         * @param tmp
         * Zugriff auf main Variablen
         * @param tmp2
         * Zugriff auf Spieler1 Variablen
         * @param tmp3
         * Zugriff auf Spieler2 Variablen
         * @param tmp4
         * Zugriff auf GAME_VARIABLES
         * @throws IOException 
         * Falls die Bilder nicht gefunden wurden
         */
	public FRAME_SETTINGS(MAIN tmp, SPIELER tmp2, SPIELER tmp3, GAME_VARIABLES tmp4) throws IOException{
                                
                toolkit = Toolkit.getDefaultToolkit();
                URL resource = getClass().getResource("/IMG/cursor.png");
                image = ImageIO.read(resource);     
                c = toolkit.createCustomCursor(image, new Point(1, 1), "img");
                local.setCursor(c);
                 
                this.Gwanzahl = new String[]{"4", "5", "6", "7", "8", "9", "10"};
                resource = getClass().getResource("/IMG/settings.jpg");
                backgroundImage = ImageIO.read(resource);
                resource = getClass().getResource("/IMG/icon.png");
                Image icon = ImageIO.read(resource);
                //local.setUndecorated(true);
                local.setContentPane(new BackGroundPane(backgroundImage));
		local.setSize(dimxf, dimyf);
		local.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		local.setLocationRelativeTo(null);
		//OK-Button 
		local.add(ok = new JButton("Bestätigen"));
		ok.setBounds(dimxf*15/100, dimyf*72/100, dimxf*25/100, dimyf*10/100);
		ok.addActionListener(this);
		local.getContentPane().add(ok);
		
		//abbrechen-Button
		local.add(abbrechen = new JButton("Abbrechen"));
		abbrechen.setBounds(dimxf*60/100, dimyf*72/100, dimxf*25/100, dimyf*10/100);
		abbrechen.addActionListener(this);
		local.getContentPane().add(abbrechen);
		
		//Gewinn Zahl Combobox mit Label	MAIN.gwzahl
		Combogwzahl = new JComboBox(Gwanzahl);
		local.add(Combogwzahl);
		Combogwzahl.setBounds(dimxf*10/100, dimyf*50/100, dimxf*20/100, dimyf*8/100);
		setLayout(null);
		gwanzahlLabel =new JLabel("Gewinn Anzahl:");
		local.add(gwanzahlLabel);
		gwanzahlLabel.setBounds(dimxf*10/100, dimyf*40/100, dimxf*20/100, dimyf*8/100);
                Combogwzahl.addItemListener(this);
		local.getContentPane().add(Combogwzahl);
		
		//Wagrechtes Spielfeld groesse		main.getFgi()
		SPX = new JComboBox(XY);
		local.add(SPX);
		SPX.setBounds(dimxf*10/100, dimyf*20/100, dimxf*10/100, dimyf*8/100);
                SPX.setSelectedItem("7");
		setLayout(null);
		X =new JLabel("Feld-Breite:");
		local.add(X);
		X.setBounds(dimxf*10/100, dimyf*10/100, dimxf*15/100, dimyf*8/100);
                SPX.addItemListener(this);
		local.getContentPane().add(SPX);
		
		//Senkrecht Spielfeld groesse			main.getFgi()
		SPY = new JComboBox(XY);
		local.add(SPY);
		SPY.setBounds(dimxf*25/100, dimyf*20/100, dimxf*10/100, dimyf*8/100);
                SPY.setSelectedItem("6");
		setLayout(null);
		Y =new JLabel("Feld-Höhe:");
		local.add(Y);
		Y.setBounds(dimxf*25/100, dimyf*10/100, dimxf*15/100, dimyf*8/100);
		setLayout(null);
                SPY.addItemListener(this);
		local.getContentPane().add(SPY);
		
		//Level Auswahl mit Label			MAIN.rekstufe
		Level = new JComboBox(LevelP);
		local.add(Level);
		Level.setBounds(dimxf*40/100, dimyf*50/100, dimxf*20/100, dimyf*8/100);
                Level.setSelectedItem("Mittel");
		local.setLayout(null);
		LevelL =new JLabel("Schwiergkeit:");
		local.add(LevelL);
		LevelL.setBounds(dimxf*40/100, dimyf*40/100, dimxf*20/100, dimyf*8/100);
		Level.setVisible(false);
		LevelL.setVisible(false);
                Level.addItemListener(this);
		local.getContentPane().add(Level);
		
		//Spieler vs ?						
		VSC = new JComboBox(VS);
		local.add(VSC);
		VSC.setBounds(dimxf*40/100, dimyf*20/100, dimxf*20/100, dimyf*8/100);
		local.setLayout(null);
		VSL =new JLabel("Spielmodus:");
		local.add(VSL);
		VSL.setBounds(dimxf*40/100, dimyf*10/100, dimxf*20/100, dimyf*8/100);
		VSC.addItemListener(this);
		local.getContentPane().add(VSC);
                
                //Anfangsspieler						
		AFS = new JComboBox(AS);
		local.add(AFS);
		AFS.setBounds(dimxf*70/100, dimyf*50/100, dimxf*20/100, dimyf*8/100);
		local.setLayout(null);
		AFSL =new JLabel("Beginner:");
		local.add(AFSL);
                AFS.setVisible(false);
		AFSL.setVisible(false);
		AFSL.setBounds(dimxf*70/100, dimyf*40/100, dimxf*20/100, dimyf*8/100);
		AFS.addItemListener(this);
		local.getContentPane().add(AFS);
                
                local.setResizable(false);
		local.setVisible(true);
                local.setLayout(null);
                local.setIconImage(icon);
                
                main = tmp;
                spieler1 = tmp2;
		spieler2 = tmp3;
                gui = tmp4;
               
                Timer t = new Timer(100, this);
                t.start();
	}
       
        /**
         * Setzt das Hintergrund Bild der Setings Fenster
         */
    public class BackGroundPane extends JPanel {
        Image img = null;
 
        BackGroundPane(Image imagefile) {
            if (imagefile != null) {
                img = imagefile;
                MediaTracker mt = new MediaTracker(this);
                mt.addImage(img, 0);
                try {
                    mt.waitForAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
        }
    }
}

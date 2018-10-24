/**
 * Im Package GUI liegen alle Klassen, die etwas mit der grafischen Oberfläche des Spielfeldes zu tun haben
 */
package GUI;

import GAME.GAME_VARIABLES;
import FILEMANAGEMENT.MYFILTER;
import THREADS.THREAD2;
import FILEMANAGEMENT.SAVE;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JComponent;
import FILEMANAGEMENT.LOAD;
import GAME.KI;
import GAME.LOCAL;
import GAME.CLASS_FUNCTIONS;
import GAME.NETWORK;
import GAME.ZUG;
import MAIN.MAIN;
import NETWORK.CLIENT;
import NETWORK.SERVER;
import static NETWORK.SERVER.ssocket;
import THREADS.THREAD3;
import THREADS.THREAD4;
import THREADS.THREAD6;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
 * Alles was im Spiel gezeichnet, oder angezeigt werden soll, wie Spielfeld, Menüleiste etc. wird hier generiert!
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class FRAME_GAME extends JComponent implements KeyListener, ActionListener, MouseMotionListener, MouseListener{
    private int playMusic = 1;
    public static int[][] spielfeld = new int [30][30];
    private final SPIELER spieler1 = new SPIELER(255, 255, 0);
    private final SPIELER spieler2 = new SPIELER(255, 0, 0);
    private final Image backgroundImage;    
    private final JMenu ngame, help, newgame;                                                       //Hier wird der Menübar erstellt
    private final JMenuItem loadgame, savegame, exit, book, credits, single, multi, tasten;
    private final JMenuBar leiste;
    private final JFrame window = new JFrame("4 Gewinnt");                                          //Hier entsteht das MAIN Fenster
    private final MAIN main;
    private final GAME_VARIABLES gui = new GAME_VARIABLES();
    private boolean stop = false;
    private final SAVE save;
    private final LOAD load = new LOAD();
    private final SERVER ser = new SERVER();
    private final CLIENT cli = new CLIENT();
    private final LOCAL local = new LOCAL();
    private final NETWORK net;
    private final ZUG zug = new ZUG();
    private int kiZugX, kiZugY;
    private boolean showKiZug = false;
    private FRAME_NETWORK Einstellungen;
    private final KI ki = new KI();
    private int bx, by;
    private Toolkit toolkit;
    private Image image;
    private Cursor c;
    MUSIC sound;
    
    /**
     * Der Konstruktor, der die MAIN GUI zeichnet und alle benötigten Variablen initialisiert
     * @param tmp
     * Durch das Objekt tmp(später main) Zugriff auf die Variablen der Klasse MAIN
     * @throws IOException
     * Für das Speichern und Laden, falls der Fileinput/output nicht erfolgreich war/ Bilder nicht gefunden wurden
     * @throws FileNotFoundException
     * Für das Laden und Speichern, falls die Datei nicht gefunden wurde/die Musik Datei nicht gefunden wurde
     * @throws InterruptedException 
     * Für die notifyAll Funktion, um LOCAL und NETWORK wieder vom wait aufzuwecken
     * @throws UnsupportedAudioFileException
     * Falls die Musikdatei nicht unterstütz wird
     * @throws LineUnavailableException 
     * Falls die Musikdatei fehlerhaft ist
     * 
     */

    public FRAME_GAME(MAIN tmp) throws IOException, FileNotFoundException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException{
                
        toolkit = Toolkit.getDefaultToolkit();
        URL resource = getClass().getResource("/IMG/cursor.png");
        image = ImageIO.read(resource);     
        c = toolkit.createCustomCursor(image, new Point(1, 1), "img");
        window.setCursor(c);

        this.net = new NETWORK();
        this.save = new SAVE();
        //Einstellungen.setPort(50001);
        main = tmp;
        resource = getClass().getResource("/IMG/background_final.jpg");
        backgroundImage = ImageIO.read(resource);
        resource = getClass().getResource("/IMG/icon.png");
        Image icon = ImageIO.read(resource);
        Font m = new Font("Arial",Font.BOLD,gui.getDimx()/120);                                 //Hier wird die GUI erstellt
        Font n = new Font("Arial",Font.BOLD,gui.getDimx()/150);
        leiste = new JMenuBar();                                                                //Menüleiste
        ngame = new JMenu("Menü");         
        help = new JMenu("Hilfe");
        newgame = new JMenu("Neues Spiel");
        single = new JMenuItem("Lokal");
        single.setAccelerator(KeyStroke.getKeyStroke( 'O',InputEvent.CTRL_DOWN_MASK ));
        single.setMnemonic(KeyEvent.VK_O);
        single.addActionListener(this);
        multi = new JMenuItem("Netzwerk");
        multi.setAccelerator(KeyStroke.getKeyStroke( 'N',InputEvent.CTRL_DOWN_MASK ));
        multi.setMnemonic(KeyEvent.VK_N);
        multi.addActionListener(this);
        savegame = new JMenuItem("Speichern");
        savegame.setAccelerator(KeyStroke.getKeyStroke( 'S',InputEvent.CTRL_DOWN_MASK ));
        savegame.setMnemonic(KeyEvent.VK_S);
        savegame.addActionListener(this);
        loadgame = new JMenuItem("Laden");
        loadgame.setAccelerator(KeyStroke.getKeyStroke( 'L',InputEvent.CTRL_DOWN_MASK ));
        loadgame.setMnemonic(KeyEvent.VK_L);
        loadgame.addActionListener(this);
        exit = new JMenuItem("Beenden");
        exit.setAccelerator(KeyStroke.getKeyStroke( 'B',InputEvent.CTRL_DOWN_MASK ));
        exit.setMnemonic(KeyEvent.VK_B);
        exit.addActionListener(this);
        book = new JMenuItem("Spielregeln");
        book.setAccelerator(KeyStroke.getKeyStroke( 'R',InputEvent.CTRL_DOWN_MASK ));
        book.setMnemonic(KeyEvent.VK_R);
        book.addActionListener(this);
        credits = new JMenuItem("Credits");
        credits.setAccelerator(KeyStroke.getKeyStroke( 'C',InputEvent.CTRL_DOWN_MASK ));
        credits.setMnemonic(KeyEvent.VK_R);
        credits.addActionListener(this);
        tasten = new JMenuItem("Tastenkürzel");
        tasten.setAccelerator(KeyStroke.getKeyStroke( 'T',InputEvent.CTRL_DOWN_MASK ));
        tasten.setMnemonic(KeyEvent.VK_C);
        tasten.addActionListener(this);
    
        leiste.add(ngame);
        leiste.add(help);
    
        ngame.add(newgame);
        ngame.addSeparator();
        newgame.add(single);
        newgame.add(multi);
        ngame.add(savegame);
        ngame.add(loadgame);
        ngame.addSeparator();
        ngame.add(exit);
    
        help.add(book);
        help.addSeparator();
        help.add(credits);
        help.addSeparator();
        help.add(tasten);
        
    
        leiste.setBackground(new Color(0, 0, 0));
        leiste.setForeground(Color.red);
        leiste.setBorderPainted(false);
        ngame.setFont(m);
        ngame.setForeground(Color.white);
        help.setFont(m);
        help.setForeground(Color.white);
        credits.setFont(n);
        tasten.setFont(n);
        book.setFont(n);
        loadgame.setFont(n);
        savegame.setFont(n);
        exit.setFont(n);
        newgame.setFont(n);
        single.setFont(n);
        multi.setFont(n);

        window.add(leiste, BorderLayout.NORTH);                                         
        window.add(this);                                                                           //Main Fenster
        window.setVisible(true);
        window.setResizable(false);
        window.addKeyListener(this);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addMouseMotionListener(this);
        window.addMouseListener(this);
        window.pack();
        window.setLayout(null);
        window.setIconImage(icon);
        window.setLocationRelativeTo(null);
        //window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        Timer t = new Timer(100, this);
        t.start();                                         //Zeitintervall der Abfrage
        
        File file = new File("./SAVE/autosave");            //Es wird geschaut ob ein autosave vorhanden ist
        if (file.exists()) {                                //Wenn ja wird gefragt, ob dieses geladen werden soll
            int result = JOptionPane.showConfirmDialog(this, "Ihr altes Spiel wurde noch nicht beendet.\n Wollen Sie dieses fortsetzen? ","Autosave",JOptionPane.YES_NO_CANCEL_OPTION);
            if (result ==JOptionPane.YES_OPTION){           //Falls ja, wird dieses geladen
                load.loadfile("autosave", main, spieler1, spieler2, gui, ser, cli, local, net, Einstellungen);
                gui.setGo(true);
                gui.setSblock(0);
                gui.setWarten(false);
            }
            else if (result == JOptionPane.NO_OPTION){      //Falls nein, wird das Autosave gelöscht
                File datei = new File("./SAVE/autosave");
                System.out.println(datei);
                if (datei.exists()) {
                    datei.delete();
                }
            }
        }

    }
    
    /**
     * Hier wird die Spielfeldgröße zurück gegeben
     * @return 
     * Gibt die Spielfeldgröße zurück
     */
    @Override                                                                 
    public Dimension getPreferredSize(){        
        return new Dimension(gui.getDimx(), gui.getDimy());                                                        //Gibt die Fenstergröße an!
    }
    
    /**
     * Zeichnet das Spielfeld
     * @param g 
     * Die Graphics
     */
    @Override
    protected void paintComponent(Graphics g){
        g.drawImage(backgroundImage, 0, 0, gui.getDimx(), gui.getDimy(),  this);                                    //Zeichnet das Hintergrundbild
        if ((gui.getGo() == true || load.laden == 1 || gui.getWarten() == true) && gui.getSeegame() == true){ //Wenn ein Spiel geladen wurde, oder ein neues Spiel gestartet wurde, oder ein Spiel endet
            for (int i = 0;  i < main.getFgi(); i++){                                                                                   //Wird das Spiel auf das Main Fenster gezeichnet
                for (int j = 0; j < main.getFgj(); j++){                                 
                g.setColor(new Color(0,0,0,97));    
                g.fillOval(gui.getOvalx() + (j * gui.getRadius()) - 3, gui.getOvaly() + i * gui.getRadius() - 3, gui.getRadius(), gui.getRadius());   //Hier die leeren Felder mit schwarzem Hintergund (Schatten)
                int sx = gui.getOvaly() + i * gui.getRadius();
                int sy = gui.getOvalx() + (j * gui.getRadius());
                switch(spielfeld[i][j]){
                    case 1:  
                        g.setColor(new Color(spieler1.getR(),spieler1.getG(),spieler1.getB(),97));
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);           //Hier anhand des Feldes "spielfeld" das für Spieler 1 die erste Farbe zeichnet (r1,g1,b1)
                        break;
                    case 2:
                        g.setColor(new Color(spieler2.getR(),spieler2.getG(),spieler2.getB(),97));
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);            //und für Spieler 2 die zweite Farbe zeichnet (r2,g2,b2)
                        break;
                    case 3:                                                                      //Falls wo gewonnen wurde, wird dies bei spielfeld mit 3 hervorgehoben und hier grau gezcihnet
                        g.setColor(new Color(spieler1.getR(),spieler1.getG(),spieler1.getB(),50));
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);
                        g.setColor(new Color(100,100,100, 100)); 
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);
                        break;
                    case 4:                                                                     //Falls wo gewonnen wurde, wird dies bei spielfeld mit 3 hervorgehoben und hier grau gezcihnet
                        g.setColor(new Color(spieler2.getR(),spieler2.getG(),spieler2.getB(),50));
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);
                        g.setColor(new Color(100,100,100, 100)); 
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);
                        break;
                    case 5:                                                                     //Hier wird der letzte Zug von Spieler 1 gezeichnet
                        g.setColor(new Color(spieler1.getR(),spieler1.getG(),spieler1.getB(),97));
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);
                        g.setColor(new Color(255,255,255, 60)); 
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);  
                        break;
                    case 6:                                                                      //Hier wird der letzte Zug von Spieler 2 gezeichnet
                        g.setColor(new Color(spieler2.getR(),spieler2.getG(),spieler2.getB(), 97));
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);   
                        g.setColor(new Color(255,255,255, 60)); 
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);  
                    default:                                                                   //Ansonsten, wenn das Feld leer ist, bleibt es weiß
                        g.setColor(new Color(255,255,255,97));  
                        g.fillOval(sy, sx, gui.getRadius() - 5, gui.getRadius() - 5);
                        Font f = new Font("Arial",Font.BOLD,gui.getDimy()/22); 
                        g.setFont(f);
                        if(main.getPlayer() != 0 || gui.getWarten() == true){                   //Der Spielstand wird dementsprechend gezeichnet
                            g.setColor(new Color(spieler1.getR(),spieler1.getG(),spieler1.getB()));
                            g.drawString("" + spieler1.getSpielstand(), gui.getDimx()*794/1000, gui.getDimy()*9/10);
                            g.setFont(f);
                            g.setColor(new Color(spieler2.getR(),spieler2.getG(),spieler2.getB()));
                            g.drawString("" + spieler2.getSpielstand(), gui.getDimx()*935/1000, gui.getDimy()*9/10);
                        }
                    }
                }
                load.laden = 0;                                                                             //falls ein Spiel geladen wurde, wird diese Variable wieder auf 0 gesetzt (siehe oben if)
            }   
            
            if (main.getSpieler() == -1 && main.getPlayer() != 0 && spieler1.getSeeStone() == true){
                g.setColor(new Color(0,0,0,97));    
                g.fillOval(gui.getBallX() , gui.getBallY(), gui.getRadius() - 3, gui.getRadius() -3);       //Hier werden die Kreise gezeichnet, die schließlich reingeworfen werden für Spieler 2
                g.setColor(new Color(spieler2.getR(),spieler2.getG(),spieler2.getB(),97));
                g.fillOval(gui.getBallX() , gui.getBallY(), gui.getRadius() - 5, gui.getRadius() -5);
            }
            else if (main.getSpieler() == 1 && main.getPlayer() != 0 && spieler1.getSeeStone() == true){
                g.setColor(new Color(0,0,0,97));                                                            //Hier werden die Kreise gezeichnet, die schließlich reingeworfen werden für Spieler 1
                g.fillOval(gui.getBallX() , gui.getBallY(), gui.getRadius() - 3, gui.getRadius() -3);
                g.setColor(new Color(spieler1.getR(),spieler1.getG(),spieler1.getB(),97));
                g.fillOval(gui.getBallX() , gui.getBallY(), gui.getRadius() - 5, gui.getRadius() -5);
            }
            if (gui.getPlayBomb() == 1){
                g.setColor(new Color(255,255,255,80));
                g.fillOval(bx , by, gui.getRadius() - 5, gui.getRadius() -5);
            }
            
            if (showKiZug && main.getSpieler() == 1 && main.getPlayer() != 0){
                g.setColor(new Color(spieler1.getR(),spieler1.getG(),spieler1.getB()));
                g.fillOval(kiZugX, kiZugY, gui.getRadius() - 5, gui.getRadius() - 5);     
            }
            
            if (showKiZug && main.getSpieler() == -1 && main.getPlayer() != 0){
                g.setColor(new Color(spieler2.getR(),spieler2.getG(),spieler2.getB()));
                g.fillOval(kiZugX, kiZugY, gui.getRadius() - 5, gui.getRadius() - 5);     
            }
        }
         
    }
   
    /**
     * Falls Gewonnen wurde, werden hier die Variablen richtig gesetzt
     */
    private void gewonnen(){
        gui.setGewonnen(0);
        gui.setGo(false);
        gui.setWarten(true);
        gui.setSblock(2);
        repaint();
    }
    
    /**
     * Falls eine Revanche gespielt wird, werden hier die Variablen richtig gesetzt
     */
    private void revange(){
        CLASS_FUNCTIONS.feld_gleich_null(main.getFgi(), main.getFgj());
        CLASS_FUNCTIONS.spielfeld(main.getFgi(), main.getFgj());
        gui.setGo(true);
        gui.setWarten(false);                                                                        //Falls warten = true war, also man nichts bewegen konnte, wird dieses wieder auf false gesetzt
        gui.setNewgameblock(0);
        gui.setSblock(0);
        spieler1.setAbomb(1);
        spieler2.setAbomb(1);
        //main.setPlayer(1);
        repaint();
        try {                                                                                       //Das Spiel wird autogesaved  
                save.savefile("autosave", main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl(), main.getPlayer(), main.getPc(), main.getRekstufe(), spieler1.getR(), spieler2.getR(), spieler1.getG(), spieler2.getG(), spieler1.getB(), spieler2.getB(), spieler1.getSpielstand(), spieler2.getSpielstand(), gui.getPlayBomb(), spieler1.getAbomb(), spieler2.getAbomb());
            } catch (IOException ex) {                          
                Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    /**
     * Hier werden die UIManager Narichten in die jeweils richtige Farbe gebracht
     * @param r
     * Rot
     * @param g
     * Grün
     * @param b 
     * Blau
     */
    private void UIcolor(int r, int g, int b){
            UIManager.put("OptionPane.background",new ColorUIResource(r,g,b));
            UIManager.put("Panel.background",new ColorUIResource(r,g,b));
            UIManager.put("Button.background", Color.white);
    }
    
    /**
     * Hier werden alle Aktionen ausgeführt, die in gewissen Spielsituationen ausgeführt werden (Gewonnen, Revanche, etc....)
     * @param object 
     * object auf this bezogen
     */
    @Override
    public void actionPerformed(ActionEvent object) {
        boolean x;
        if (gui.getGewonnen() == 1 && main.getSpieler() == 1){                                  //Falls  Spieler 1 gewinnt, wird eine Gewinnmassage ausgegeben in entsprechender Farbe
            gewonnen();                                                                         //Spiel wird
            spieler1.setSpielstand(spieler1.getSpielstand()+1);                                 //Und der Spielstand wird geupdated
            UIcolor(spieler1.getR(),spieler1.getG(),spieler1.getB());
            JOptionPane.showMessageDialog(this,"Spieler 1 hat gewonnen!", "Gewinnmeldung", JOptionPane.INFORMATION_MESSAGE);
            UIcolor(255,255,255);                                                               //und gefragt, ob eine Revange gespielt werden soll
            int result = JOptionPane.showConfirmDialog(this, "Wollen Sie eine Revange? ","Revange",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){                                              //Falls ja wird ein neues Spiel gestartet 
                main.setSpieler(-1);
                System.out.println("Startspieler = " + main.getSpieler());                      //Und herrausgefunden ob Singleplayer oder Multiplayer gestartet werden muss
                revange();
                if (main.getOldPlayer() == 1){
                     main.setPlayer(1);
                new THREAD3(main, gui, spieler1, spieler2, local).start();
                }
                else{
                    main.setPlayer(2);
                    if (gui.getIsServer() == true){
                        try {
                            ssocket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        new THREAD6(main, gui, spieler1, spieler2, gui.getPort(), ser, net).start();
                    }
                    else{
                        try {
                            cli.client(main, gui, spieler1, spieler2, gui.getPort(), gui.getIP(), net);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
           }
        
        else if (gui.getGewonnen() == 1 && main.getSpieler() == -1){                                 //Falls  Spieler 2 gewinnt, wird eine Gewinnmassage ausgegeben in entsprechender Farbe
            gewonnen();                                                                             //Gleich wie oben
            spieler2.setSpielstand(spieler2.getSpielstand()+1); 
            UIcolor(spieler2.getR(),spieler2.getG(),spieler2.getB());
            JOptionPane.showMessageDialog(this,"Spieler 2 hat gewonnen!", "Gewinnmeldung", JOptionPane.INFORMATION_MESSAGE);
            UIcolor(255,255,255);
            int result = JOptionPane.showConfirmDialog(this, "Wollen Sie eine Revange?","Revange",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){
                main.setSpieler(1);
                System.out.println("Startspieler = " + main.getSpieler());
                revange();
                if (main.getOldPlayer() == 1){
                     main.setPlayer(1);
                new THREAD3(main, gui, spieler1, spieler2, local).start();
                }
                else{
                    main.setPlayer(2);
                    if (gui.getIsServer() == true){
                        try {
                            ssocket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        new THREAD6(main, gui, spieler1, spieler2, gui.getPort(), ser, net).start();;
                    }
                    else{
                        try {
                            cli.client(main, gui, spieler1, spieler2, gui.getPort(), gui.getIP(), net);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
           }
        
        else if (gui.getGewonnen() == -1){                                                 //Falls niemand gewinnt, wird gewonnen = -1 zurückgegeben 
            gewonnen();
            UIcolor(255,255,255);
            JOptionPane.showMessageDialog(null,"Das Spiel endet Unentschieden", "Gewinnmeldung", JOptionPane.INFORMATION_MESSAGE);
            int result = JOptionPane.showConfirmDialog(this, "Wollen Sie eine Revange","Revange",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){
                main.setSpieler((main.getFgi() * main.getFgj() % 2 == 0) ? main.getSpieler() * (-1) : main.getSpieler());
                System.out.println("Startspieler = " + main.getSpieler());
                revange();
                if (main.getOldPlayer() == 1){
                     main.setPlayer(1);
                new THREAD3(main, gui, spieler1, spieler2, local).start();
                }
                else{
                    main.setPlayer(2);
                    if (gui.getIsServer() == true){
                        try {
                            ssocket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        new THREAD6(main, gui, spieler1, spieler2, gui.getPort(), ser, net).start();
                    }
                    else{
                       
                        try {
                            cli.client(main, gui, spieler1, spieler2, gui.getPort(), gui.getIP(), net);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
           }
        
        if (object.getSource() == exit){                                                        //Falls im Tap Beenden gedrückt wird, wird alles geschlossen
            System.exit(0);
          }
           
        if (object.getSource() == credits){                                                     //Hier werden die Credits angezeigt
            UIcolor(255,255,255);
            JOptionPane.showMessageDialog(null,"Benjamin Krickl\nAndreas Eisele\nMarkus Schmidgall\nKarsten Stepanek\n\u00A9\0002015", "Credits", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if (object.getSource() == single){                                                                                                                                                             //Es wird go = 1 gesetzt, d.h SINGLEPLAYER startet seinen Ablauf und das Spielfeld wird gezeichnet
            UIcolor(255,255,255);                                    //Falls der Computer gerade seinen Zug berechnet, oder schon ein Newgamefenster geöffnet ist, gibt es eine Fehlermeldung   
            if (gui.getNewgameblock() == 1){
                 JOptionPane.showMessageDialog(null,"Zurzeit kann kein neues Spiel gestartet werden!", "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                    gui.setWarten(true);
                    gui.setSeeGame(false);                               //Wenn ein Newgamefenster geöffnet ist, wird geblockt, dass man ein weiteres öffnen kann
                    new THREAD2(main, spieler1, spieler2, gui, local, net).start();
            }
        }
        
            
        if (object.getSource() == multi){                                                       //Das Settingsfenster für Singleplayer Spieler vs Computer
            UIcolor(255,255,255);                                   //Falls der Computer gerade seinen Zug berechnet, oder schon ein Newgamefenster geöffnet ist, gibt es eine Fehlermeldung   
            if (gui.getNewgameblock() == 1){
                 JOptionPane.showMessageDialog(null,"Zurzeit kann kein neues Spiel gestartet werden!", "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                    gui.setWarten(true);
                    gui.setSeeGame(false);
                    new THREAD4(main, spieler1, spieler2, gui, ser, cli, net, Einstellungen, local).start();
            }
        }

        
        if (object.getSource() == loadgame){                                                    //Hier wird der Filechooser gestartet, falls ein Spiel geladen werden soll
            UIcolor(255,255,255);
            if (gui.getSblock() == 3){                                                              //Falls ein Spiel schon beendet wurde, wird das speichern geblockt
                JOptionPane.showMessageDialog(null,"Dieses Spiel kann zurzeit nicht geladen werden!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JFileChooser loader = new JFileChooser();                                           //Neuer Filechoose load erstellt
                loader.setFileFilter(new MYFILTER(".sav"));                                         //Es werden durch den Filefilter nur .sav Dateien angezeigt
                loader.setAcceptAllFileFilterUsed(false);
                loader.setCurrentDirectory(new File("./SAVE/"));
                int rueckgabeWert = loader.showOpenDialog(null);
        
            /* Abfrage, ob auf "Öffnen" geklickt wurde */
                if(rueckgabeWert == JFileChooser.APPROVE_OPTION){                                    //Wenn beim Filechooser auf Laden gedrückt wurde versucht er die Datei zu laden   
                    try {
                        x = load.loadfile(loader.getSelectedFile().getName(), main, spieler1, spieler2, gui, ser, cli, local, net,Einstellungen);                      //Die Loadfunktion wird aufgerufen
                        if (x == false){                                                                    //Wenn ein fehler beim Laden aufgetreten ist, passiert nichts

                        }
                        else{
                            gui.setGo(true);                                                                  //Sonst wird das go gegeben, damit das Spielfeld gezeichnet wird und SINGLEPLAYER bzw MULTIPLAYER
                            gui.setSblock(0);                                                                    //gestartet wird. Außerdem wird der Speicherblock aufgehoben
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MAIN.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null,"Die Datei wurde nicht gefunden!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        if (object.getSource() == tasten){
            UIcolor(255,255,255);
            JOptionPane.showMessageDialog(null,"TastenKürzel:\nM: Musik an/aus\nENTF: Spiel sofort abbrechen\nEINFG: Spielzug berechnen lassen\nESC: Server beenden, falls offen\n"
                    + "STRG+O: Lokales Spiel\nSTRG+N: Netzwerk Spiel\nSTRG+S: Savegame\nSTRG+L: Loadgame\nSTRG+B: Spiel beenden\nSTRG+R: Spielregeln\n"
                    + "STRG+C: Credits\nSTRG+T: Tastenkürzel", "Tastenkürzel", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if (object.getSource() == savegame){                                                    //Hier wird das Fenster geöffnet um ein Spiel zu speichern
            UIcolor(255,255,255);
           switch(gui.getSblock()){
                   case 1://Falls noch kein Spiel gestartet wurde, wird das speichern geblockt
                       JOptionPane.showMessageDialog(null,"Dieses Spiel wurde noch nicht angefangen!", "Error", JOptionPane.INFORMATION_MESSAGE);
                       break;
                   case 2:                                                       //Falls ein Spiel schon beendet wurde, wird das speichern geblockt
                       JOptionPane.showMessageDialog(null,"Dieses Spiel wurde schon beendet!", "Error", JOptionPane.INFORMATION_MESSAGE);
                       break;
                   case 3:                                                        //Falls der Computer am zug ist
                       JOptionPane.showMessageDialog(null,"Da der Gegner/Computer am Zug ist, kann zurzeit nicht gespeichert werden!", "Error", JOptionPane.INFORMATION_MESSAGE);
                       break;
                   case 4:                                                          //Falls der Spieler Client ist
                       JOptionPane.showMessageDialog(null,"Da Sie Client sind, kann das Spiel nicht gespeichert werden!", "Error", JOptionPane.INFORMATION_MESSAGE);
                       break;
                   default:
                    int sav = 0;
                        JFileChooser saver = new JFileChooser();
                        saver.setCurrentDirectory(new File("./SAVE/"));                                   //Das Verzeichnis wird gesetzt
                        saver.setDialogTitle("Speichern:");
                        saver.setFileFilter(new MYFILTER(".sav"));                                  //Ebenfalls werden nur .sav Dateien angezeigt
                        saver.setAcceptAllFileFilterUsed(false);
                        String tmp = "";
                        while (sav == 0){                                                           //Solange das Speichern nicht erfolgreich war, oder nicht abgebrochen wurde

                            int userSelection = saver.showSaveDialog(null);
                            if (userSelection == JFileChooser.CANCEL_OPTION) {
                               sav = 1; 
                            }
                            else{
                                File t = saver.getSelectedFile();
                                String name = saver.getSelectedFile().getName();                            //Der Eingegebene Dateiname wird hier in einem String gespeichert
                                if(name.length() < 4){                                                      //Es wird automatisch ein .sav drangehängt, wenn der Name kleiner als 4 ist
                                    tmp = "./SAVE/" + name + ".sav";
                                }   
                                else if(name.charAt(name.length() - 4) == '.' && name.charAt(name.length() - 3) == 's' && name.charAt(name.length() - 2) == 'a' && name.charAt(name.length() - 1) == 'v'){
                                    tmp = "./SAVE/" + name;                                             //Es wird kein .sav angehängt, wenn bereits eines vorhanden ist
                                }
                                else {
                                    tmp = "./SAVE/" + name + ".sav";                                      //Ansonsten wird ein .sav angehängt
                                }
                                File f = new File(tmp);
                                if(f.exists() == true){                                                     //Falls die Datei schon existiert, wird gefragt, ob überschrieben werden soll
                                    int result = JOptionPane.showConfirmDialog(this, "Diese Datei existiert bereits. Wollen Sie die Datei überschreiben? ","Datei. ",JOptionPane.YES_NO_CANCEL_OPTION);
                                    if (result ==JOptionPane.YES_OPTION){
                                        sav = 1;                                                            //Falls ja, wird gespeichert
                                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                                            try {
                                                save.savefile(saver.getSelectedFile().getName(), main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl(), main.getPlayer(), main.getPc(), main.getRekstufe(), spieler1.getR(), spieler2.getR(), spieler1.getG(), spieler2.getG(), spieler1.getB(), spieler2.getB(), spieler1.getSpielstand(), spieler2.getSpielstand(), gui.getPlayBomb(), spieler1.getAbomb(), spieler2.getAbomb());
                                            } catch (IOException ex) {
                                                Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    }
                                    else if (result == JOptionPane.NO_OPTION){
                                        sav = 0;                                                            //Falls nein, wird der Filechooser neu gestartet
                                    }
                                    else{
                                        sav = 1;                                                            //Ansonsten wird abgebrochen
                                    }
                                }
                                else{                                                                       //Falls die Datei nicht exisitiert, wird sie angelegt
                                    sav = 1;    
                                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                                            try {
                                                if (saver.getSelectedFile().getName().equals("autosave") || saver.getSelectedFile().getName().equals("autosave.sav")){
                                                    JOptionPane.showMessageDialog(null,"Dieser Dateiname ist gesperrt!", "Error", JOptionPane.INFORMATION_MESSAGE);
                                                }
                                                else{
                                                    save.savefile(saver.getSelectedFile().getName(), main.getSpieler(), main.getFgi(), main.getFgj(), main.getGwzahl(), main.getPlayer(), main.getPc(), main.getRekstufe(), spieler1.getR(), spieler2.getR(), spieler1.getG(), spieler2.getG(), spieler1.getB(), spieler2.getB() , spieler1.getSpielstand(), spieler2.getSpielstand(), gui.getPlayBomb(), spieler1.getAbomb(), spieler2.getAbomb());
                                                }
                                            } catch (IOException ex) {
                                            Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                    }

                                }
                            }
                        }
            }
            
        }
        
        if(object.getSource() == book){
            InputStream inputStream = this.getClass().getResourceAsStream("/HELP/rules.pdf");

            File tempOutputFile = null; 
            try {
                tempOutputFile = File.createTempFile( "rules", ".pdf" ); // oder in ein Anwendungsverzeichnis!?
//    tempOutputFile.deleteOnExit(); // löscht die Datei wieder, wenn die Anwendung geschlossen wird
            } catch (IOException ex) {
                Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
            }

            FileOutputStream out = null;
            try {
                out = new FileOutputStream("./HELP/rules.pdf");

            byte buffer[] = new byte[ 1024 ];
            int len;
            while( ( len = inputStream.read( buffer ) ) > 0 ) {
                    
                    out.write( buffer, 0, len );
                    
            } // while
            out.close();
            inputStream.close();
                Desktop.getDesktop().open(new File("./HELP/rules.pdf"));
            } catch (IOException | UnsupportedOperationException ex) {
                JOptionPane.showMessageDialog(null,"Ihr System unterstützt das Anzeigen von PDFs nicht auf diese weise!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        repaint();    
    }
    
    
    @Override
    public void keyTyped(KeyEvent ect) {
    }
    
    /**
     * Tastaturabfrage, während des Spieles
     * @param ect 
     * Der Key
     */
    @Override
    public void keyPressed(KeyEvent ect) {
        if (stop == false){
            stop = true;
        if(ect.getKeyCode() == KeyEvent.VK_DELETE && gui.getWarten() == false){
            gui.setSblock(1);
            gui.setNewgameblock(0);
            gui.setSeeGame(false);
            gui.setGo(false);
            gui.setWarten(false);
            load.laden = 0;
            net.t.stop();                                               //Wird der Timer von Network gestoppt, falls dieser noch am laufen war
                    main.setPlayer(0);                                          //Und sowohl alte Multiplayer, als auch Singleplayer spiele abgebrochen
                    gui.setAbbrechen(true);
            synchronized(local) {
                local.notifyAll();
            }
            synchronized(net) {
                net.notifyAll();
            }
            if(gui.getIsServer() == true && gui.getFirstMulti() == false){
                try {
                    ssocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(ect.getKeyCode() == KeyEvent.VK_RIGHT && gui.getWarten() == false){               //Wird die Taste nach rechts gedrückt, verschiebt sich der Spielstein nach rechts
            if (gui.getBallX()  > ((gui.getRadius() * (main.getFgj() - 2)) + gui.getOvalx())){                  //Außer das Spielfeld wird verlassen, dann wird es auf der anderen Seite wieder eingefügt
                gui.setBallX(gui.getOvalx());
                bx = gui.getOvalx();
                gui.setEinwurfX(0);
                gui.setBallY(zug.getX(main.getFgi(), gui.getEinwurfX()) * gui.getRadius() + gui.getOvaly());
            }
            else{
                gui.setBallX(gui.getBallX() + gui.getRadius());
                bx = bx + gui.getRadius();
                gui.setEinwurfX(gui.getEinwurfX() + 1);
                gui.setBallY(zug.getX(main.getFgi(), gui.getEinwurfX()) * gui.getRadius() + gui.getOvaly());
            }
        repaint();
        }
        if(ect.getKeyCode() == KeyEvent.VK_LEFT && gui.getWarten() == false){                //Wird die Taste nach links gedrückt, verschiebt sich der Spielstein nach links
            if (gui.getBallX() <= gui.getOvalx()){                                                //Außer das Spielfeld wird verlassen, dann wird es auf der anderen Seite wieder eingefügt
                gui.setBallX((gui.getRadius() * (main.getFgj() - 1) + gui.getOvalx()));
                bx = gui.getRadius() * (main.getFgj() - 1) + gui.getOvalx();
                gui.setEinwurfX(main.getFgj() - 1);
                gui.setBallY(zug.getX(main.getFgi(), gui.getEinwurfX()) * gui.getRadius() + gui.getOvaly());
            }
            else{
                gui.setBallX(gui.getBallX() - gui.getRadius());
                bx = bx - gui.getRadius();
                gui.setEinwurfX(gui.getEinwurfX() - 1);
                gui.setBallY(zug.getX(main.getFgi(), gui.getEinwurfX()) * gui.getRadius() + gui.getOvaly());
            }
        repaint();
        }
        if(ect.getKeyCode() == KeyEvent.VK_UP && gui.getWarten() == false){
             if (by <= gui.getOvaly()){                                                //Außer das Spielfeld wird verlassen, dann wird es auf der anderen Seite wieder eingefügt
                by = (gui.getRadius() * (main.getFgi() - 1) + gui.getOvaly());
                gui.setEinwurfY(main.getFgi() - 1);
            }
            else{
                 by = (by - gui.getRadius());
                 gui.setEinwurfY(gui.getEinwurfY() - 1);
            }
        repaint();
        }
        if(ect.getKeyCode() == KeyEvent.VK_DOWN && gui.getWarten() == false){
            if (by  > ((gui.getRadius() * (main.getFgi() - 2)) + gui.getOvaly())){                  //Außer das Spielfeld wird verlassen, dann wird es auf der anderen Seite wieder eingefügt
                by = (gui.getOvaly());
                gui.setEinwurfY(0);
            }
            else{
                by = (by + gui.getRadius());
                gui.setEinwurfY(gui.getEinwurfY() + 1);
            }
        }
        
        if(ect.getKeyChar() == 'm'){

                try {
                    if (sound == null) sound = new MUSIC();
                    switch(playMusic){
                        case 1: sound.play(); playMusic = 0; break;
                        case 0: sound.stop(); playMusic = 1; break;
                        default: sound.stop(); playMusic = 1; break;
            }
                } catch (UnsupportedAudioFileException | IOException | IllegalArgumentException | LineUnavailableException ex) {
                    JOptionPane.showMessageDialog(null,"Ihr Gerät unterstützt leider keine Audiowiedergabe!", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
        }
        
        if (ect.getKeyCode() == KeyEvent.VK_INSERT && gui.getWarten() == false && gui.getGo() == true){
             int result = JOptionPane.showConfirmDialog(this, "Soll Ihnen die KI den nächsten Zug berechnen? ","Zugvorschlag",JOptionPane.YES_NO_OPTION);
             if (result ==JOptionPane.YES_OPTION){
                 int x = zug.pczug(main.getFgi(), main.getFgj(), main.getGwzahl(), 0, main.getRekstufe(), main.getSpieler(), gui, 2);
                 x++;
                 JOptionPane.showMessageDialog(null,"Ihr Zug wurde berechnet und ihr Stein wird nun auf die richtige Position (X = " + x + ") bewegt!", "Zugvorschlag", JOptionPane.INFORMATION_MESSAGE);
                 x--;
                kiZugX = gui.getRadius() * x + gui.getOvalx();   
                kiZugY = gui.getOvaly() + (gui.getRadius() * main.getFgi());
                showKiZug = true;
             }
        }
        
        
        if(ect.getKeyCode() == KeyEvent.VK_ENTER && gui.getWarten() == false && ki.check(gui.getEinwurfX(), main.getSpieler(), main.getFgi()) == 1){    //Bei Enter wird durch ok = 1 signalisiert, dass der Zug ausgewählt wurde und nun ausgeführt wird
            showKiZug = false;
            if (main.getPlayer() == 2){
                synchronized(net) {
                net.notifyAll();
                }
            }
            else{
                synchronized(local) {
                local.notifyAll();
            }
            }
            gui.setBallY(gui.getBallY() - gui.getRadius());
        }
        
        if (ect.getKeyCode() == KeyEvent.VK_ESCAPE && gui.getConnected() == false && gui.getIsServer() == true && gui.getFirstMulti() == false){
                gui.setSblock(1);
                gui.setGo(false);
                gui.setNewgameblock(0);
                System.out.println("ESC!");
                JOptionPane.showMessageDialog(null,"Server wurde beendet!", "Server", JOptionPane.INFORMATION_MESSAGE);
                gui.setFirstMulti(true);
                try {
                    ssocket.close();
                    main.setPlayer(0);
                    main.setOldPlayer(0);
                    gui.setIsServer(false);
                } catch (IOException ex) {
                    Logger.getLogger(FRAME_GAME.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
        
        int ok = (main.getSpieler() == 1)? spieler1.getAbomb(): spieler2.getAbomb();
        if(ect.getKeyCode() == KeyEvent.VK_BACK_SPACE && gui.getWarten() == false && gui.getPlayBomb() == 1 && ok == 1 && main.getPc() == 0 && main.getPlayer() == 1){
            System.out.println("X = " + gui.getEinwurfX() + "Y = " + gui.getEinwurfY());
            if (KI.feld[gui.getEinwurfY()][gui.getEinwurfX()] != 0){
                gui.setBomb(true);
                synchronized(local) {
                    local.notifyAll();
                }
                gui.setBallY(gui.getBallY() + gui.getRadius());
                if (main.getSpieler() == 1){
                spieler1.setAbomb(0);
                }
                else{
                    spieler2.setAbomb(0);
                }
            }
        }
        SERVER.ok = true;
        repaint();
        }
    }
    
    /**
     * Funktion, wenn ein Key losgelassen wird
     * @param ect 
     * KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent ect) {                                     //Wird die Taste losgelassen, kann eine neue gedrückt werden
        stop = false;                                                            
    }
    
    /**
     * Funktion, wenn die Maus geklickt wurde
     * @param e 
     * MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {                                    //Auch beim Mausklick, wird ok = 1 gesetzt und der Zug ausgeführt (siehe Enter)
        if(e.getModifiers() == 16 && gui.getWarten() == false && ki.check(gui.getEinwurfX(), main.getSpieler(), main.getFgi()) == 1){
            showKiZug = false;
            if (main.getPlayer() == 2){
            synchronized(net) {
                net.notifyAll();
            }
            }
            else{
            synchronized(local) {
                local.notifyAll();
            }
            }
            gui.setBallY(gui.getBallY() - gui.getRadius());
        }
        SERVER.ok = true;
        int ok = (main.getSpieler() == 1)? spieler1.getAbomb(): spieler2.getAbomb();
        if (e.getModifiers() == 4 && gui.getWarten() == false && gui.getPlayBomb() == 1 && ok == 1 && main.getPc() == 0 && main.getPlayer() == 1){
            System.out.println("X = " + gui.getEinwurfX() + "Y = " + gui.getEinwurfY());
            if (KI.feld[gui.getEinwurfY()][gui.getEinwurfX()] != 0){
                gui.setBomb(true);
                synchronized(local) {
                    local.notifyAll();
                }
                gui.setBallY(gui.getBallY() + gui.getRadius());
            if (main.getSpieler() == 1){
                spieler1.setAbomb(0);
            }
            else{
                spieler2.setAbomb(0);
            }
        }
        }
        repaint();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
    
    /**
     * Funktion, wenn die Maus bewegt wird
     * @param e 
     * MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();                                                       //Hier wird die x Koordinate der maus ausgelesen
        int y = e.getY();
        for (int i = 0; i < main.getFgj(); i++){
            if(gui.getRadius() * i + gui.getOvalx() <= x && x < gui.getRadius() * (i+1) + gui.getOvalx() && gui.getWarten() == false){
                gui.setBallX(gui.getRadius() * i + gui.getOvalx());                                     //und entsprechend auf das Spielfeld übertragen
                bx = gui.getRadius() * i + gui.getOvalx();
                gui.setEinwurfX(i);
                gui.setBallY(zug.getX(main.getFgi(), gui.getEinwurfX()) * gui.getRadius() + gui.getOvaly());
            }
        }
        
        for (int j = 0; j < main.getFgi(); j++){
            if(gui.getRadius() * j + gui.getOvaly() <= y - 50 && y - 50 < gui.getRadius()* (j + 1) + gui.getOvaly() && gui.getWarten() == false){
                by = gui.getRadius() * j + gui.getOvaly();
                gui.setEinwurfY(j);
                gui.setBallY(zug.getX(main.getFgi(), gui.getEinwurfX()) * gui.getRadius() + gui.getOvaly());
            }
        }
        repaint();
    }
}


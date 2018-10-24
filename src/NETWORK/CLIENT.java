package NETWORK;

import GAME.GAME_VARIABLES;
import static GAME.CLASS_FUNCTIONS.feld_gleich_null;
import static GAME.CLASS_FUNCTIONS.spielfeld;
import GAME.NETWORK;
import GAME.ZUG;
import GUI.SPIELER;
import MAIN.MAIN;
import THREADS.THREAD5;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
/**
 * Hier versucht der Client mit dem Server zu kommunizieren, um alle Daten zu erhalten
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class CLIENT {
    
    public static Socket client;


    /**
     * Hier wird in der INIT Phase der Client mit dem Server kommunizieren, um alle Infomationen zu bekommen
     * @param main
     * Zugriff auf Main Variablen
     * @param gui
     * Zugriff auf GAME_VARIABLES
     * @param spieler1
     * Zugriff auf Spieler1
     * @param spieler2
     * Zugriff auf Spieler2
     * @param port
     * Zugriff auf der Port
     * @param ip
     * Zugriff auf die IP
     * @param net
     * Zugriff auf das Netzwerkspiel
     * @return
     * - true: Wenn Server gefunden wurde und alles korrekt übertragen wurde<br>
     * return - false: Wenn ein Fehler aufgetreten ist
     * @throws InterruptedException 
     * Falls ein Fehler auftritt
     */
    public synchronized boolean client(MAIN main, GAME_VARIABLES gui, SPIELER spieler1, SPIELER spieler2, int port, String ip, NETWORK net) throws InterruptedException {
        int x;
        int y = 0;
        ZUG zug = new ZUG();
        String s;
		try {
			gui.setSeeGame(false);                                              //Spiel wird unsichtbar gemacht
                        gui.setConnected(false);                                            //Und die Connection auf false gesetzt = noch nicht connected
			client = new Socket (ip, port);                                     //Neuer Socket für den Client wird erstellt
			System.out.println ("Client gestartet");

			
			//Streams   
			OutputStream out = (OutputStream) client.getOutputStream();         //Wirter und Reader werden erzeugt und gestartet
			PrintWriter writer = new PrintWriter(out);
			
			InputStream in = (InputStream) client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			//_____________________________________________

			//writer.write ("anServer + \n");
			//writer.flush();
			    s = reader.readLine();                                          //Es wird sobald gelesen, sobald ein Server dem entsprechend gefunden wurde
                            //System.out.println(s);
                            JOptionPane.showMessageDialog(null,"Ausgewählter Server gefunden.\nErfolgreich connected!", "Client", JOptionPane.INFORMATION_MESSAGE);
                            if (s.equals("INIT-BEGIN")){                                    //Intiallisiert alle Strings die übergeben werden und ordnet Sie den entsprechnden Variablen zu
                            System.out.println(s);
                            feld_gleich_null(main.getFgi(), main.getFgj());
                            s= reader.readLine();
                            writer.flush();                                                 //Timeout
                            gui.setTimeout((s.charAt(8) - 48 * 10000) + (s.charAt(9) - 48 * 1000) + (s.charAt(10) - 48 * 100) +(s.charAt(11) - 48 * 10) +(s.charAt(12) - 48));
                            System.out.println(s);
                            s= reader.readLine();
                            writer.flush();
                            System.out.println(s);
                            main.setFgj(s.charAt(11) - 48);                                 //Feldbreit/höhe
                            if (s.charAt(12) - 48 >= 0 && s.charAt(12) - 48 < 10){
                                main.setFgj((s.charAt(11) - 48) * 10 + (s.charAt(12) - 48));
                                System.out.println("Fgj = " + main.getFgj());
                                y++;
                            }
                            main.setFgi(s.charAt(14 + y) - 48);
                            if (s.length() == 15 + y + 1){
                                main.setFgi((s.charAt(14 + y) - 48) * 10 + (s.charAt(15 + y) - 48));
                                System.out.println("Fgi = " + main.getFgi());
                            }
                            s= reader.readLine();
                            writer.flush();
                            System.out.println(s);                                          //Gewinnzahl
                            main.setGwzahl(s.charAt(8) - 48);
                            System.out.println("Gwzahl Länge = " + s.length());
                            if (s.length() == 10){
                                main.setGwzahl((s.charAt(8) - 48) * 10 + (s.charAt(9) - 48));
                            }
                            s= reader.readLine();
                            writer.flush();
                            System.out.println(s);    
                            main.setSpieler(s.charAt(13) - 48);                             //Startspieler
                            main.setSpieler((main.getSpieler() == 2)? -1 : 1);
                            s= reader.readLine();
                            //writer.flush();
                            while (s.charAt(0) == 'L'){                                     //Feld wird eingelsen, falls Spielstand geladen wurde
                                int spieler = (s.charAt(6) - 48);
                                spieler = ((spieler == 2)? -1 : 1);
                                x = s.charAt(9) - 48;
                                zug.zug(x - 1, spieler, main.getFgi(), gui);
                                s= reader.readLine();
                                writer.flush();
                                System.out.println(s);
                                //System.out.println(s);
                            }
                            if (s.equals("INITCHECK")){                                     //Wird überprüft, ob alles korrekt erhalten wurde und sich keine Fehler eingeschlichen haben
                                System.out.println("INIT_CHECK Empfangen");
                                System.out.println("Besher empfangen: " + main.getFgi() +" " + main.getFgj() +" " +  main.getGwzahl() +" " + main.getSpieler());
                                if (main.getFgi() >= 4 && main.getFgj() >= 4 && main.getFgi() < 31 && main.getFgj() < 31 &&(main.getGwzahl() <=  main.getFgj() || main.getGwzahl() <= main.getFgi()) && main.getGwzahl() >= 4 || main.getGwzahl() <= 15){
                                  
                                String s1 = "CHECK-OK\n";                                   //Wenn alles ok ist wird CHECK-OK gesendet
                                writer.write(s1);
                                writer.flush();
                                System.out.println(s1);
                                gui.setConnected(true);
                                }
                                else{                                                       //Sonst CHECK_FAILED
                                    String s1 = "CHECK-FAILED\n";
                                    System.out.println("CHECK FAILED");
                                    writer.write(s1);
                                    writer.flush();
                                    gui.setConnected(false); 
                                     JOptionPane.showMessageDialog(null,"Daten waren fehlerhaft!", "Client", JOptionPane.INFORMATION_MESSAGE);
                                     main = new MAIN();
                                     client.close();
                                    return false;
                                }
                                s= reader.readLine();
                                System.out.println(s);
                                if (s.equals("INIT-END")){
                                    JOptionPane.showMessageDialog(null,"Daten erfolgreich empfangen!", "Client", JOptionPane.INFORMATION_MESSAGE);
                                //}
                            }
                        }
                        
                            }                           
		}catch(IOException e){
			e.fillInStackTrace();
		}
                if (gui.getConnected() == true){                                                
                    spielfeld(main.getFgi(), main.getFgj());                                    //Nun wird Connected true gesetzt und die Vorbereitungen für das Spiel gemacht
                                    gui.setGo(true);                                            //Go = true | Spiel beginnt gleich
                                    main.setPlayer(2);                                          //Player = 2 | Multiplayerspiel
                                    gui.setAbbrechen(false);                        
                                    gui.setNewgameblock(0);
                                    //gui.setDontreload(false);    
                                    gui.setSeeGame(true);                                       //Spiel wird sichtbar gemacht
                                    gui.setSblock(4);                                           //Speicherblock auf 4 gesetzt = Client darf nicht speichern
                                    gui.setRadius(((gui.getDimx() * 2) / 3) / (3 * main.getFgi() / 2 + main.getFgj()));
                                    gui.setOvalx(gui.getDimx() / 2 - ((main.getFgj() * gui.getRadius()) / 2));
                                    new THREAD5(main, gui, spieler1, spieler2, net).start();  //Spiel wird über THREAD5 gestartet
                    return true;
                }
                else{
                    JOptionPane.showMessageDialog(null,"Ausgewählter Server nicht gefunden.", "Client", JOptionPane.INFORMATION_MESSAGE);
                    gui.setSeeGame(false);                                                      //Wenn der Server garnicht existiert hat, wird ein Fehler ausgeben
                    gui.setWarten(true);
                    return false;
                }
        }
}

/**
 * Im Package GUI liegen alle Klassen, die etwas mit der grafischen Oberfläche des Spielfeldes zu tun haben.
 */
package GUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Hier wird das Musik File eingelesen und nach Methoden Aufruf abgespielt/gestoppt
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class MUSIC {
    private AudioInputStream ais;
    private Clip clip;

    /**
     * Datei wird geöffnet und eingelesen
     * @throws UnsupportedAudioFileException
     * Wenn das Audio File nicht unterstützt wird
     * @throws IOException
     * Falls etwas bei der Ein oder Ausgabe der Audio Files schief läuft
     * @throws LineUnavailableException 
     * Bei weiteren Problemen mit dem Audio File
     */
    public MUSIC() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL resource = getClass().getResource("/MUSIC/music.wav");
        this.ais = AudioSystem.getAudioInputStream(resource);
        AudioFormat format = ais.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format, ((int) ais.getFrameLength() * format.getFrameSize()));
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(ais);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(1.0F);
    }
    
    public void play(){
        clip.start();
        clip.loop(-1);
    }
    
     public void stop() {
        clip.stop();
    }
}

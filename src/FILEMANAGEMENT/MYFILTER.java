
package FILEMANAGEMENT;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Diese Klasse setzt den Filter beim Filemanagement auf .sav Dateien
  * @author Benjamin Krickl, Andreas Eisele, Markus Schmidgall, Karsten Stepanek
  * @version 6.0
 */
public class MYFILTER extends FileFilter {
    private String sav;
    
    /**
     * Im JFilechooser werden nur Dateien mit der Endung .sav angezeigt
     * @param sav 
     * Endung der Datei
     */
    public MYFILTER(String sav) {
        this.sav = sav;
    }
    
    /**
     * Filtert Dateien mit der Endung .sav
     * @param f
     * Datei die überprüft wird
     * @return 
     * false, falls die Datei nicht vorhanden ist<br>
     * return - true, falls alles geklappt hat
     */
    @Override
    public boolean accept(File f) {                                             //Hier wird festgelegt, dass der JFileChooser nur .sav Dateien anzeigt
        if(f == null){                                                          
        return false;
        }
        if(f.isDirectory()) {
        return true;
        }
        return f.getName().toLowerCase().endsWith(sav);                         //Also sozusagen ein Filter
    }
    /**
     * getDescription legt fest, dass nur Dateien mit der Endung .sav zurückgegeben werden
     * @return sav
     */
    @Override
    public String getDescription() {
        return sav;
    }
    
}

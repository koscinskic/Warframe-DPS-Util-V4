/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.io.*;
import java.util.Scanner;

/**
* The FileManager class currently serves solely to convert Mod files into Mod
* objects.
*
* TODO: FileManager will eventually be depreciated in favor of a more
* comprehensive folder-and-file filter system
*/

public class FileManager {

  /**
  * 
  */

  public static Mod[] retrieveMods(String folderName) {

    File modFolder = new File(folderName);
    File[] mods = modFolder.listFiles();

    Mod[] modCollection = new Mod[mods.length];
    int modCount = 0;

    for (File modFile : mods) {

      modCollection[modCount] = new Mod(modFile);
      modCount++;

    }

    return modCollection;

  }

}

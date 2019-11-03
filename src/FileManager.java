
import java.io.*;
import java.util.Scanner;

public class FileManager {

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

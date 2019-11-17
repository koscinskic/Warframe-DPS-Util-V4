/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.io.*;
import java.util.*;

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

  public static Vector<String> retrieveWeaponNames() {

    String[] currentNames;
    File[] currentFolder;

    Vector<String> weaponNames = new Vector<String>(10, 10);

    File master = new File("../data/weapons");
    File[] masterWeaponFolder = master.listFiles();

    for (File weaponFolder : masterWeaponFolder) {

      currentFolder = weaponFolder.listFiles();
      currentNames = new String[currentFolder.length];

      for (File weapon : currentFolder) {

        weaponNames.addElement(weapon.getName());

      }

    }

    return weaponNames;

  }

  public static String filterName(String input) {

    StringBuilder output = new StringBuilder();
    char l;

    for (int index = 0; index < input.length(); index++) {
      l = input.charAt(index);
      if (l == '.') {
        break;
      } else if (l == '_') {
        output.append(' ');
      } else {
        output.append(l);
      }
    }

    return output.toString();

  }

  public static String expandName(String input) {

    StringBuilder output = new StringBuilder();
    char l;

    for (int index = 0; index < input.length(); index++) {
      l = input.charAt(index);
      if (l == ' ') {
        output.append('_');
      } else {
        output.append(l);
      }
    }

    output.append(".csv");

    return output.toString();

  }

}

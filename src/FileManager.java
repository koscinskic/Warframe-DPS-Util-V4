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

  private static String[] assault_rifle_compatibilities = new String[]{"assault_rifle", "rifle", "primary"};
  private static String[] pistol_compatibilities = new String[]{"pistol"};

  /**
  *
  */

  public static Vector<Mod> retrieveMods(String weaponType) {

    Vector<Mod> modCollection = new Vector<Mod>(30, 5);
    String[] compatibilities = new String[0];

    File modFolder;
    File[] mods;

    switch (weaponType) {

      case ("assault_rifle"):
      compatibilities = assault_rifle_compatibilities;
      break;

      case ("pistol"):
      compatibilities = pistol_compatibilities;
      break;

    }

    for (String compatibility : compatibilities) {

      modFolder = new File("../data/mods/" + compatibility);
      mods = modFolder.listFiles();

      for (File modFile : mods) {
        modCollection.addElement(new Mod(modFile));
      }

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

  public static String resetCaps(String input) {

    StringBuilder output = new StringBuilder();
    output.append(input.charAt(0));
    char l;

    for (int index = 1; index < input.length(); index++) {
      l = input.charAt(index);
      if (l == ' ' || l == '-') {
        output.append(l);
        index++;
        output.append(input.charAt(index));
      } else {
        output.append((char)((int)l + 32));
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

    return output.toString();

  }

  public static String expandName(String input, String ext) {

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

    output.append(ext);

    return output.toString();

  }

}

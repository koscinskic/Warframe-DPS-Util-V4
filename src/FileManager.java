/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.io.*;
import java.util.*;

/**
* The FileManager class currently serves to facilite information transfer
* between files, custom objects, and modified strings. Due to the relatively
* recent expansion of the overall class structure concerning this program,
* redundant variables and methods may be subject to change.
*
* Variables containing "compatibilities" refers to folders that contain mods
* that the given weapon is capable of equipping.
*/

public class FileManager {

  // Variables containing the mod compatibilities (see above)
  private static String[] assault_rifle_compatibilities = new String[]
  {"assault_rifle", "rifle", "primary"};
  private static String[] pistol_compatibilities = new String[]{"pistol"};

  /**
  * Retrieves and collates a mod collection given a specific weapon type
  */

  public static Vector<Mod> retrieveMods(String weaponType) {

    // Variable containg the mod compatibilities for iteration (see above)
    String[] compatibilities = new String[0];

    // Variable containing mod object information
    Vector<Mod> modCollection = new Vector<Mod>(30, 5);

    // Variables containing mod file information
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

  /**
  * Retrieves and collates the full list of weapons present in the file system
  */

  public static Vector<String> retrieveWeaponNames() {

    // Variable containing the full list of weapons present in the file system
    Vector<String> weaponNames = new Vector<String>(10, 10);

    // Variables containing file system organization and retrieval
    File master = new File("../data/weapons");
    File[] masterWeaponFolder = master.listFiles();

    // Variables containing temporary storage information
    String[] currentNames;
    File[] currentFolder;

    for (File weaponFolder : masterWeaponFolder) {

      currentFolder = weaponFolder.listFiles();
      currentNames = new String[currentFolder.length];

      for (File weapon : currentFolder) {

        weaponNames.addElement(weapon.getName());

      }

    }

    return weaponNames;

  }

  /**
  * Restricts the user from establishing impossible filter parameters
  *
  * TODO: Improve data handling so this method does not require access to Main
  */

  public static boolean verifylists(boolean[] whitelist, boolean[] blacklist) {

    // Variable concerning the end result of the verification process
    boolean validlist = true;

    // Variables concerning the quantity of mods within filter lists
    int whitelistCount = 0;
    int blacklistCount = 0;

    for (int index = 0; index < whitelist.length; index++) {
      if (whitelist[index] == true) {
        whitelistCount++;
      }
      if (blacklist[index] == true) {
        blacklistCount++;
      }
    }

    if (whitelistCount > 8) {
      validlist = false;
    }

    // TODO: (See method description)
    if (Main.modFolder.size() - blacklistCount < 8) {
      validlist = false;
    }

    return validlist;

  }

  /**
  * Strips underscores and file extentions from text
  */

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

  /**
  * Forces only the first letter of each word in the text to be capitalized
  */

  public static String resetCaps(String input) {

    // Variables concerning text construction
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

  /**
  * Converts regular text into underscore-linked text
  */

  public static String expandName(String input) {

    // Variables concerning text construction
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

  /**
  * Converts regular text into underscore-linked text with a file extention
  */

  public static String expandName(String input, String ext) {

    // Variables concerning text construction
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

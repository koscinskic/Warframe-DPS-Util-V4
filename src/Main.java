/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.io.*;
import java.util.*;

/**
* The Main class currently serves as a platform for assisting the UI class as
* well as providing the main execution of the program. Due to a recent effort
* intended to partially refactor the program, UI cannot function as a standalone
* class and relies upon the variables contained within this class.
*/

public class Main {

  // Variable containing the main scanner
  public static Scanner inputMain = new Scanner(System.in);

  // Variables containing filter lists
  public static boolean[] blacklist;
  public static boolean[] whitelist;

  // Variable containing mod object information
  public static Vector<Mod> modFolder;

  // DEBUG: All variables past this line were originally contained exlusively
  // within the main method (See class description above)

  // Variable concerning the vanilla weapon for identification and duplication
  public static Weapon baseWeapon = new Weapon();

  // Variable containing the text name of the weapon file
  // TODO: expand UI filter system to make use of the modular file system
  public static String weaponFileName = "";

  // Variables for regulating UI flow
  public static String menuInput = "";
  public static boolean computationReady = false;

  // Variable containing the full list of weapons present in the file system
  public static Vector<String> weaponNames = FileManager.retrieveWeaponNames();

  // Variable containing a list of options for the options menu
  // TODO: Reform and expand UI to read arrays like this from their own file
  public static String[] optionslist = new String[]{"Optimize Burst DPS",
  "Optimize Sustained DPS", "Modify Optimization Priorities",
  "Edit Blacklist", "Edit Whitelist"};

  // Variable containing the optimal mod combination
  // TODO: Depreciate variable in favor of alternate data transfer procedures
  public static Mod[] combination = new Mod[8];

  /**
  * Main Method
  */

  public static void main(String[] args) {

    UI.loopMenu("WeaponList");

    // TODO: Error Handling
    while (true) {

      try {
        File weaponFile = new File("../data/weapons/pistol/" + weaponFileName);
        Scanner tempScanner = new Scanner(weaponFile);
        tempScanner.close();
        baseWeapon = new Weapon(weaponFile);
        break;
      } catch (FileNotFoundException e) {
        // Incorrect Folder ... Attempt Next Block
      }

      try {
        File weaponFile = new File("../data/weapons/assault_rifle/" +
        weaponFileName);
        Scanner tempScanner = new Scanner(weaponFile);
        tempScanner.close();
        baseWeapon = new Weapon(weaponFile);
        break;
      } catch (FileNotFoundException e) {
        // Incorrect Folder
      }

      // TODO: Error Handling - Not anticipated unless user does not consult the
      // README and incorrectly organizes folders

      break;

    }

    modFolder = FileManager.retrieveMods(FileManager.expandName(
    baseWeapon.getType()).toLowerCase());

    blacklist = new boolean[modFolder.size()];
    whitelist = new boolean[modFolder.size()];

    for (int index = 0; index < blacklist.length; index++) {
      blacklist[index] = false;
      whitelist[index] = false;
    }

    while(!computationReady) {
      UI.loopMenu("ModConfig");
    }

  }

}

/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.io.*;
import java.util.*;

/**
* The Menu class serves as the main execution class and contains the UI of the
* program. Due to the relatively recent expansion of the overall class structure
* concerning this program, redundant variables and methods may be subject to
* change.
*/

/**
* TODO: Comment Regarding Whitelist/Blackist Interacting With Mod System
*/

/**
* TODO: Comment Inner Whitelist Code Block
*/

/**
* TODO: README
*/

public class Main {

  // Variables for retaining user entries
  public static Scanner inputMain = new Scanner(System.in);
  public static String userInput;

  // Variables for regulating UI flow
  public static boolean firstRun = true;
  public static boolean validOption = false;

  // Variables containing filter lists
  public static boolean[] blacklist;
  public static boolean[] whitelist;

  // Variable containing mod object information
  public static Vector<Mod> modFolder;



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
  // TODO: contain UI functions and methods in a seperate class
  public static String[] optionslist = new String[]{"Optimize Burst DPS",
  "Optimize Sustained DPS", "Modify Optimization Priorities",
  "Edit Blacklist", "Edit Whitelist"};

  // Variable containing the optimal mod combination
  // TODO: Depreciate variable in favor of alternate data transfer procedures
  public static Mod[] combination = new Mod[8];

  /**
  * Main Method
  *
  * TODO: Currently encompasses proccesses that could be dedicated to other
  * classes and modularized, thus not serving any definite purpose besides
  * UI control.
  */

  public static void main(String[] args) {

    UI.loopMenu("WeaponList");

    validOption = false;

    while (!validOption) {

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

      // TODO Error / Debug Handling
      validOption = true;

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

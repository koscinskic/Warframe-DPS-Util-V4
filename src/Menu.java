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

public class Menu {

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

  /**
  * Main Method
  *
  * TODO: Currently encompasses proccesses that could be dedicated to other
  * classes and modularized, thus not serving any definite purpose besides
  * UI control.
  */

  public static void main(String[] args) {

    // Variable concerning the vanilla weapon for identification and duplication
    Weapon baseWeapon = new Weapon();

    // Variable containing the text name of the weapon file
    // TODO: expand UI filter system to make use of the modular file system
    String weaponFileName = "";

    // Variables for regulating UI flow
    String menuInput = "";
    boolean computationReady = false;

    // Variable containing the full list of weapons present in the file system
    Vector<String> weaponNames = FileManager.retrieveWeaponNames();

    // Variable containing a list of options for the options menu
    // TODO: contain UI functions and methods in a seperate class
    String[] optionslist = new String[]{"Optimize Burst DPS",
    "Optimize Sustained DPS", "Modify Optimization Priorities",
    "Edit Blacklist", "Edit Whitelist"};

    // Variable containing the optimal mod combination
    // TODO: Depreciate variable in favor of alternate data transfer procedures
    Mod[] combination = new Mod[8];

    cls();

    System.out.println("\nWelcome to Warframe-DPS-Util-V4");

    while (!validOption) {

      if (!firstRun) {
        cls();
        System.out.println("\nYour entry \"" + userInput +
        "\" could not be found. Please try again.");
      }

      System.out.println("\nHere are your available weapons:\n");

      for (int index = 0; index < weaponNames.size(); index++) {
        if (index < 9 && weaponNames.size() >= 10) {
          System.out.println("(0" + (index + 1) + ") " +
          FileManager.filterName(weaponNames.elementAt(index)));
        } else {
          System.out.println("(" + (index + 1) + ") " +
          FileManager.filterName(weaponNames.elementAt(index)));
        }
      }

      System.out.print("\nPlease enter the name or number of the weapon" +
      "you want to select:\nWeapon: ");
      userInput = inputMain.nextLine();

      try {
        int selectedWeaponNum = Integer.parseInt(userInput);
        if (selectedWeaponNum <= weaponNames.size() && selectedWeaponNum > 0) {
          weaponFileName = weaponNames.elementAt(selectedWeaponNum - 1);
          validOption = true;
        }
      } catch (NumberFormatException e) {
        weaponFileName = FileManager.expandName(userInput, ".csv");
        if (weaponNames.contains(weaponFileName)) {
          validOption = true;
        }
      }

      firstRun = false;

    }

    cls();

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

      validOption = false;
      firstRun = true;

      while(!validOption) {

        cls();

        System.out.println("\nSelected Weapon: " +
        FileManager.filterName(weaponFileName));

        printModFolder();

        if (!firstRun) {
          System.out.println("\nYour entry \"" + userInput +
          "\" could not be found. Please try again.");
        }

        if (!verifylists()) {
          System.out.println("\nYour current black/whitelist configuration" +
          "cannot produce a full combination." +
          "Please correct your parameters before continuing.");
        }

        System.out.println("\nHere are your available options:\n");

        for (int index = 0; index < optionslist.length; index++) {
          System.out.println("(" + (index + 1) + ") " + optionslist[index]);
        }

        System.out.print("\nPlease enter the title or number" +
        "of the option you want to select:\nOption: ");
        userInput = inputMain.nextLine();

        try {
          int selectedOptionNum = Integer.parseInt(userInput);
          if (selectedOptionNum <= optionslist.length &&
          selectedOptionNum > 0) {
            userInput = optionslist[selectedOptionNum - 1];
            validOption = true;
          }
        } catch (NumberFormatException e) {
          for (String option : optionslist) {
            if (option.equals(userInput)) {
              validOption = true;
              break;
            }
          }
        }

        firstRun = false;

      }

      validOption = false;
      firstRun = true;

      menuInput = userInput;

      while (!validOption) {

        cls();

        System.out.println("\nSelected Option: " + menuInput);

        switch (menuInput) {

          case ("Optimize Burst DPS"):

          computationReady = true;
          validOption = true;

          System.out.println("\nBest Combination:\n");
          combination = Optimizer.optimizeDPS(modFolder, baseWeapon, blacklist,
          whitelist, "BURST");

          for (Mod mod : combination) {
            System.out.println(mod.getName());
          }

          break;

          case ("Optimize Sustained DPS"):

          computationReady = true;
          validOption = true;

          System.out.println("\nBest Combination:\n");
          combination = Optimizer.optimizeDPS(modFolder, baseWeapon, blacklist,
          whitelist, "SUSTAINED");

          for (Mod mod : combination) {
            System.out.println(mod.getName());
          }

          break;

          case ("Modify Optimization Priorities"):

          // TODO Implementation

          break;

          case ("Edit Blacklist"):
          editlist(blacklist);
          break;

          case ("Edit Whitelist"):
          editlist(whitelist);
          break;

        }

      }

    }

  }

  /**
  * Prints the eligable mods with their filter list status
  */

  private static void printModFolder() {

    System.out.println("\nHere are your available mods:\n");

    for (int index = 0; index < modFolder.size(); index++) {
      if (index < 9 && modFolder.size() >= 10) {
        System.out.print("(0" + (index + 1) + ") " + String.format("%-25s",
        FileManager.resetCaps(modFolder.elementAt(index).getName())));
      } else {
        System.out.print("(" + (index + 1) + ") " + String.format("%-25s",
        FileManager.resetCaps(modFolder.elementAt(index).getName())));
      }
      if (blacklist[index] == true) {
        System.out.print(" - BLACKLISTED");
      }
      if (whitelist[index] == true) {
        System.out.print(" - WHITELISTED");
      }
      System.out.println();
    }

  }

  /**
  * Process for altering the whitelist and blacklist
  * TODO: Currently serves exclusively to condense code and will eventually be
  * replaced with more practical and modular procedures
  */

  private static void editlist(boolean[] currentlist) {

    String listName = "white";
    boolean[] altlist = blacklist;

    if (currentlist == blacklist) {
      listName = "black";
      altlist = whitelist;
    }

    printModFolder();

    if (!firstRun) {
      System.out.println("\nYour entry \"" + userInput + "\" could not be " +
      listName + "listed. Please try again.");
    }

    firstRun = false;

    System.out.print("\nPlease enter the name or number of the mod you want to "
    + listName + "list:\n(Selecting a " + listName +
    "listed mod will remove it from the " + listName + "list)\nOption: ");
    userInput = inputMain.nextLine();

    try {
      int selectedModNum = Integer.parseInt(userInput);
      if (selectedModNum <= modFolder.size() && selectedModNum > 0) {
        if (currentlist[selectedModNum - 1] == false) {
          currentlist[selectedModNum - 1] = true;
          altlist[selectedModNum - 1] = false;
          validOption = true;
          firstRun = true;
        } else {
          currentlist[selectedModNum - 1] = false;
          validOption = true;
          firstRun = true;
        }
      }
    } catch (NumberFormatException e) {
      int selectedModNum = searchModFolder(userInput);
      if (selectedModNum != -1) {
        if (currentlist[selectedModNum] == false) {
          currentlist[selectedModNum] = true;
          altlist[selectedModNum] = false;
          validOption = true;
          firstRun = true;
        } else {
          currentlist[modFolder.indexOf(userInput)] = false;
          validOption = true;
          firstRun = true;
        }
      }
    }

  }

  /**
  * Returns a mod's index location given the name
  */

  public static int searchModFolder(String input) {

    // Variable concerning the modID
    // Returns "-1" if the given name does not exist
    int modID = -1;

    for (Mod mod : modFolder) {
      if (input.toUpperCase().equals(mod.getName())) {
        modID = modFolder.indexOf(mod);
        System.out.println("MOD" + modID);
        break;
      }
    }

    return modID;

  }

  /**
  * Restricts the user from establishing impossible filter parameters
  */

  public static boolean verifylists() {

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

    if (modFolder.size() - blacklistCount < 8) {
      validlist = false;
    }

    return validlist;

  }

  /**
  * Method for clearing WindowsOS compatible consoles
  */

  public static void cls() {
    try {
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (IOException e) {
      // Handle Exception Here
    } catch (InterruptedException e2) {
      // Handle Exception Here
    }

  }

}

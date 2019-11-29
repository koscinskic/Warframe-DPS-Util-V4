/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.io.*;
import java.util.*;

/**
* The UI class currently serves as the basis for properly displaying a variety
* of lists, folders, files, et cetera. Due to a recent effort intended to
* partially refactor the program, this class heavily relies upon variables
* contained within the Main class, and will eventually be rewritten to function
* as a standalone class.
*/

public class UI {

  // Variable concerned with retaining user input across methods
  private static String userInput;

  // Variables concerning UI regulation
  private static boolean validOption = false;
  private static boolean firstRun = true;

  /**
  * Continuously reprints a series of lists and messages based upon hard-coded
  * menu options
  *
  * TODO: Improve to function as a standalone class
  */

  public static void loopMenu(String menuType) {

    validOption = false;
    firstRun = true;

    while (!validOption) {

      cls();

      if (menuType.equals("ModConfig")) {

        System.out.println("\nSelected Weapon: " +
        FileManager.filterName(Main.weaponFileName));
        printModFolder(Main.modFolder);

      } else if (menuType.equals("WeaponList")) {

        System.out.println("\nWelcome to Warframe-DPS-Util-V4");

      } else {

        System.out.println("\nSelected Option: " + menuType);

      }

      if (!firstRun) {
        cls();
        System.out.println("\nYour entry \"" + userInput +
        "\" could not be found. Please try again.");
      }

      switch (menuType) {

        case ("WeaponList"):

        printWeaponList(Main.weaponNames);

        System.out.print("\nPlease enter the name or number of the weapon " +
        "you want to select:\nWeapon: ");

        userInput = Main.inputMain.nextLine();

        try {
          int selectedWeaponNum = Integer.parseInt(userInput);
          if (selectedWeaponNum <= Main.weaponNames.size() &&
          selectedWeaponNum > 0) {
            Main.weaponFileName = Main.weaponNames.elementAt(
            selectedWeaponNum - 1);
            validOption = true;
          }
        } catch (NumberFormatException e) {
          Main.weaponFileName = FileManager.expandName(userInput, ".csv");
          if (Main.weaponNames.contains(Main.weaponFileName)) {
            validOption = true;
          }
        }

        break;

        case ("ModConfig"):

        if (!FileManager.verifylists(Main.whitelist, Main.blacklist)) {
          System.out.println("\nYour current black/whitelist configuration " +
          "cannot produce a full combination. " +
          "Please correct your parameters before continuing.");
        }

        printMenuOptions(Main.optionslist);

        System.out.print("\nPlease enter the title or number " +
        "of the option you want to select:\nOption: ");

        userInput = Main.inputMain.nextLine();

        try {
          int selectedOptionNum = Integer.parseInt(userInput);
          if (selectedOptionNum <= Main.optionslist.length &&
          selectedOptionNum > 0) {
            userInput = Main.optionslist[selectedOptionNum - 1];
            validOption = true;
          }
        } catch (NumberFormatException e) {
          for (String option : Main.optionslist) {
            if (option.equals(userInput)) {
              validOption = true;
              break;
            }
          }
        }

        loopMenu(userInput);

        break;

        case ("Optimize Burst DPS"):

        Main.computationReady = true;
        validOption = true;

        System.out.println("\nBest Combination:\n");
        Main.combination = Optimizer.optimizeDPS(Main.modFolder,
        Main.baseWeapon, Main.blacklist, Main.whitelist, "BURST");

        for (Mod mod : Main.combination) {
          System.out.println(mod.getName());
        }

        break;

        case ("Optimize Sustained DPS"):

        Main.computationReady = true;
        validOption = true;

        System.out.println("\nBest Combination:\n");
        Main.combination = Optimizer.optimizeDPS(Main.modFolder,
        Main.baseWeapon, Main.blacklist, Main.whitelist, "SUSTAINED");

        for (Mod mod : Main.combination) {
          System.out.println(mod.getName());
        }

        break;

        case ("Modify Optimization Priorities"):

        // TODO: Implementation - Low Priority

        break;

        case ("Edit Blacklist"):
        editlist(Main.blacklist);
        break;

        case ("Edit Whitelist"):
        editlist(Main.whitelist);
        break;

      }

      firstRun = false;

    }

  }

  /**
  * Prints a series of options based on a predefined array
  *
  * TODO: This method implies that each option has a case within the loopMenu
  * method, and will eventually depreciated when the class is improved
  */

  private static void printMenuOptions(String[] menuOptions) {

    System.out.println("\nHere are your available options:\n");

    for (int index = 0; index < menuOptions.length; index++) {
      System.out.println("(" + (index + 1) + ") " +  menuOptions[index]);
    }

  }

  /**
  * Prints the eligable mods with their filter list status
  */

  private static void printModFolder(Vector<Mod> modFolder) {

    System.out.println("\nHere are your available mods:\n");

    for (int index = 0; index < modFolder.size(); index++) {
      if (index < 9 && modFolder.size() >= 10) {
        System.out.print("(0" + (index + 1) + ") " + String.format("%-25s",
        FileManager.resetCaps(modFolder.elementAt(index).getName())));
      } else {
        System.out.print("(" + (index + 1) + ") " + String.format("%-25s",
        FileManager.resetCaps(modFolder.elementAt(index).getName())));
      }
      if (Main.blacklist[index] == true) {
        System.out.print(" - BLACKLISTED");
      }
      if (Main.whitelist[index] == true) {
        System.out.print(" - WHITELISTED");
      }
      System.out.println();
    }

  }

  /**
  * Prints all weapons contained within the file system
  */

  private static void printWeaponList(Vector<String> weaponNames) {

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

  }

  /**
  * Process for altering the whitelist and blacklist
  *
  * TODO: Currently serves exclusively to condense code and will eventually be
  * replaced with more practical and modular procedures
  */

  private static void editlist(boolean[] currentlist) {

    String listName = "white";
    boolean[] altlist = Main.blacklist;

    if (currentlist == Main.blacklist) {
      listName = "black";
      altlist = Main.whitelist;
    }

    printModFolder(Main.modFolder);

    // DEBUG: Contains the original error message prior to refactoring
    // if (!firstRun) {
    //   System.out.println("\nYour entry \"" + userInput + "\" could not be " +
    //   listName + "listed. Please try again.");
    // }

    firstRun = false;

    System.out.print("\nPlease enter the name or number of the mod you want to "
    + listName + "list:\n(Selecting a " + listName +
    "listed mod will remove it from the " + listName + "list)\nOption: ");
    userInput = Main.inputMain.nextLine();

    try {
      int selectedModNum = Integer.parseInt(userInput);
      if (selectedModNum <= Main.modFolder.size() && selectedModNum > 0) {
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
          currentlist[Main.modFolder.indexOf(userInput)] = false;
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

    for (Mod mod : Main.modFolder) {
      if (input.toUpperCase().equals(mod.getName())) {
        modID = Main.modFolder.indexOf(mod);
        System.out.println("MOD" + modID);
        break;
      }
    }

    return modID;

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

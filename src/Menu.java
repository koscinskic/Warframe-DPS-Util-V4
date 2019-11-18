/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.io.*;
import java.util.*;

/**
* TODO: Expand Program
* As of the current moment, Menu is a highly volatile and subject-to-change
* class that will eventually serve as the central UI and access to the project.
*/

public class Menu {

  // Variable for retaining user entries
  public static String userInput;

  /**
  * Main Method
  */

  public static void main(String[] args) {

    // User Input Scanner
    Scanner inputMain = new Scanner(System.in);
    String userInput = "";
    String weaponFileName = "";
    String menuInput = "";
    boolean validOption = false;
    boolean firstRun = true;
    boolean computationReady = false;

    Vector<String> weaponNames = FileManager.retrieveWeaponNames();
    String[] optionslist = new String[]{"Optimize Burst DPS", "Optimize Sustained DPS", "Modify Optimization Priorities", "Edit Blacklist", "Edit Whitelist"};

    Weapon baseWeapon = new Weapon();
    //File weaponFile;

    cls();

    System.out.println("\nWelcome to Warframe-DPS-Util-V4");

    while (!validOption) {

      if (!firstRun) {
        cls();
        System.out.println("\nYour entry \"" + userInput + "\" could not be found. Please try again.");
      }

      System.out.println("\nHere are your available weapons:\n");

      for (int index = 0; index < weaponNames.size(); index++) {
        if (index < 9 && weaponNames.size() >= 10) {
          System.out.println("(0" + (index + 1) + ") " + FileManager.filterName(weaponNames.elementAt(index)));
        } else {
          System.out.println("(" + (index + 1) + ") " + FileManager.filterName(weaponNames.elementAt(index)));
        }
      }

      System.out.print("\nPlease enter the name or number of the weapon you want to select:\nWeapon: ");
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
        // Incorrect Folder
      }

      try {
        File weaponFile = new File("../data/weapons/assault_rifle/" + weaponFileName);
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

    Vector<Mod> modFolder = FileManager.retrieveMods(FileManager.expandName(baseWeapon.getType()).toLowerCase());
    boolean[] blacklist = new boolean[modFolder.size()];
    boolean[] whitelist = new boolean[modFolder.size()];

    for (int index = 0; index < blacklist.length; index++) {
      blacklist[index] = false;
      whitelist[index] = false;
    }

    firstRun = true;

    while(!computationReady) {

      validOption = false;

      while(!validOption) {

        cls();

        System.out.println("\nSelected Weapon: " + FileManager.filterName(weaponFileName));

        System.out.println("\nHere are your available mods:\n");

        for (int index = 0; index < modFolder.size(); index++) {
          if (index < 9 && modFolder.size() >= 10) {
            System.out.print("(0" + (index + 1) + ") " + String.format("%-25s", FileManager.resetCaps(modFolder.elementAt(index).getName())));
          } else {
            System.out.print("(" + (index + 1) + ") " + String.format("%-25s", FileManager.resetCaps(modFolder.elementAt(index).getName())));
          }
          if (blacklist[index] == true) {
            System.out.print(" - BLACKLISTED");
          }
          if (whitelist[index] == true) {
            System.out.print(" - WHITELISTED");
          }
          System.out.println();
        }

        if (!firstRun) {
          System.out.println("\nYour entry \"" + userInput + "\" could not be found. Please try again.");
        }

        System.out.println("\nHere are your available options:\n");

        for (int index = 0; index < optionslist.length; index++) {
          System.out.println("(" + (index + 1) + ") " + optionslist[index]);
        }

        System.out.print("\nPlease enter the title or number of the option you want to select:\nOption: ");
        userInput = inputMain.nextLine();

        try {
          int selectedOptionNum = Integer.parseInt(userInput);
          if (selectedOptionNum <= optionslist.length && selectedOptionNum > 0) {
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

          break;

          case ("Optimize Sustained DPS"):

          break;

          case ("Modify Optimization Priorities"):

          break;

          case ("Edit Blacklist"):

          System.out.println("\nHere are your available mods:\n");

          for (int index = 0; index < modFolder.size(); index++) {
            if (index < 9 && modFolder.size() >= 10) {
              System.out.print("(0" + (index + 1) + ") " + String.format("%-25s", FileManager.resetCaps(modFolder.elementAt(index).getName())));
            } else {
              System.out.print("(" + (index + 1) + ") " + String.format("%-25s", FileManager.resetCaps(modFolder.elementAt(index).getName())));
            }
            if (blacklist[index] == true) {
              System.out.print(" - BLACKLISTED");
            }
            if (whitelist[index] == true) {
              System.out.print(" - WHITELISTED");
            }
            System.out.println();
          }

          if (!firstRun) {
            System.out.println("\nYour entry \"" + userInput + "\" could not be blacklisted. Please try again.");
          }

          System.out.print("\nPlease enter the name or number of the mod you want to blacklist:\n(Selecting a blacklisted mod will remove it from the blacklist)\nOption: ");
          userInput = inputMain.nextLine();

          try {
            int selectedModNum = Integer.parseInt(userInput);
            if (selectedModNum <= modFolder.size() && selectedModNum > 0) {
              if (blacklist[selectedModNum - 1] == false) {
                blacklist[selectedModNum - 1] = true;
                whitelist[selectedModNum - 1] = false;
                validOption = true;
                firstRun = true;
              } else {
                blacklist[selectedModNum - 1] = false;
                validOption = true;
                firstRun = true;
              }
            }
          } catch (NumberFormatException e) {
            if (modFolder.contains(userInput)) {
              if (blacklist[modFolder.indexOf(userInput)] == false) {
                blacklist[modFolder.indexOf(userInput)] = true;
                whitelist[modFolder.indexOf(userInput)] = false;
                validOption = true;
                firstRun = true;
              } else {
                blacklist[modFolder.indexOf(userInput)] = false;
                validOption = true;
                firstRun = true;
              }
            }
          }

          break;

          case ("Edit Whitelist"):

          System.out.println("\nHere are your available mods:\n");

          for (int index = 0; index < modFolder.size(); index++) {
            if (index < 9 && modFolder.size() >= 10) {
              System.out.print("(0" + (index + 1) + ") " + String.format("%-25s", FileManager.resetCaps(modFolder.elementAt(index).getName())));
            } else {
              System.out.print("(" + (index + 1) + ") " + String.format("%-25s", FileManager.resetCaps(modFolder.elementAt(index).getName())));
            }
            if (blacklist[index] == true) {
              System.out.print(" - BLACKLISTED");
            }
            if (whitelist[index] == true) {
              System.out.print(" - WHITELISTED");
            }
            System.out.println();
          }

          if (!firstRun) {
            System.out.println("\nYour entry \"" + userInput + "\" could not be whitelisted. Please try again.");
          }

          System.out.print("\nPlease enter the name or number of the mod you want to whitelist:\n(Selecting a whitelisted mod will remove it from the whitelist)\nOption: ");
          userInput = inputMain.nextLine();

          try {
            int selectedModNum = Integer.parseInt(userInput);
            if (selectedModNum <= modFolder.size() && selectedModNum > 0) {
              if (whitelist[selectedModNum - 1] == false) {
                whitelist[selectedModNum - 1] = true;
                blacklist[selectedModNum - 1] = false;
                validOption = true;
                firstRun = true;
              } else {
                whitelist[selectedModNum - 1] = false;
                validOption = true;
                firstRun = true;
              }
            }
          } catch (NumberFormatException e) {
            if (modFolder.contains(userInput)) {
              if (whitelist[modFolder.indexOf(userInput)] == false) {
                whitelist[modFolder.indexOf(userInput)] = true;
                blacklist[modFolder.indexOf(userInput)] = false;
                validOption = true;
                firstRun = true;
              } else {
                whitelist[modFolder.indexOf(userInput)] = false;
                validOption = true;
                firstRun = true;
              }
            }
          }

          break;

        }

      }

    }

    // TODO Correct Below

    // Hard-Coded for standard mod collection access
    Mod[] modSet = new Mod[8];
    int n = modFolder.size();
    int r = 8;

    // Initial Combination
    int[] combo = Math_Utility.generateCombination(n, r);

    // Variable for traversing the ModSet array
    int index = 0;

    // Retrieves mods from collection
    for (int i : combo) {
      modSet[index] = modFolder.elementAt(i - 1);
      index++;
    }

    // Duplication of base weapon that serves as a template for applying mod
    // effects
    Weapon moddedWeapon = new Weapon(baseWeapon);
    moddedWeapon.modifyWeapon(modSet);

    /**
    * TODO: Temporary optimization of DPS for proof-of-concept
    * All below will eventually be organized into a seperate class
    */

    double highestDPS = DPS_Calculator.calculateSustainedDPS(moddedWeapon);
    double tempVal = 0;

    int[] highestCombo = new int[8];

    int ti = 0;
    for (int i : combo) {
      highestCombo[ti] = i;
      ti++;
    }
    ti = 0;

    double comboCount = 1;

    while (combo[0] != ((n - r) + 1)) {

      combo = Math_Utility.incrementCombination(n, combo);

      index = 0;

      for (int i : combo) {
        modSet[index] = modFolder.elementAt(i - 1);
        index++;
      }

      moddedWeapon = new Weapon(baseWeapon);
      moddedWeapon.modifyWeapon(modSet);

      tempVal = DPS_Calculator.calculateInstantDPS(moddedWeapon);

      if (tempVal > highestDPS) {

        highestDPS = tempVal;

        for (int i : combo) {
          highestCombo[ti] = i;
          ti++;
        }

        ti = 0;

      }

      comboCount++;

    }

    double check = Math_Utility.maxCombination((double)n, (double)r);

    if (comboCount == check) {

      System.out.println("\nAll " + comboCount + " combinations accounted for.");
      System.out.println("\nHighest DPS: " + highestDPS);
      System.out.println("\nBuild: ");
      //Math_Utility.printCombo(highestCombo);

      for (int i : highestCombo) {
        System.out.print("\n" + modFolder.elementAt(i - 1).getName());
      }

      System.out.println();

    }

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

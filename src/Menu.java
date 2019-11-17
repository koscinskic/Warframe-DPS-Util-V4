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
    String weaponFile = "";
    boolean validOption = false;
    boolean firstRun = true;

    Vector<String> weaponNames = FileManager.retrieveWeaponNames();

    Weapon baseWeapon = new Weapon();

    cls();

    System.out.println("\nWelcome to Warframe-DPS-Util-V4");

    while (!validOption) {

      if (!firstRun) {
        cls();
        System.out.println("\nYour entry \"" + userInput + "\" could not be found. Please try again.");
      }

      System.out.println("\nHere are your available weapons:");

      for (int index = 0; index < weaponNames.size(); index++) {
        System.out.println("(" + (index + 1) + ") " + FileManager.filterName(weaponNames.elementAt(index)));
      }

      System.out.print("\nPlease enter the name or number of the weapon you want to modify:\nWeapon: ");
      userInput = inputMain.nextLine();

      try {
        int selectedWeaponNum = Integer.parseInt(userInput);
        if (selectedWeaponNum <= weaponNames.size() && selectedWeaponNum > 0) {
          weaponFile = weaponNames.elementAt(selectedWeaponNum - 1);
          validOption = true;
        }
      } catch (NumberFormatException e) {
        weaponFile = FileManager.expandName(userInput);
        if (weaponNames.contains(weaponFile)) {
          validOption = true;
        }
      }

      firstRun = false;

    }

    cls();

    System.out.println("\nSelected Weapon: " + FileManager.filterName(weaponFile));

    validOption = false;

    while (!validOption) {

      try {
        baseWeapon = new Weapon("../data/weapons/Pistol/" + weaponFile);
        break;
      } catch (Error e) {
        // Incorrect Folder
      }

      try {
        baseWeapon = new Weapon("../data/weapons/Assault_Rifle/" + weaponFile);
        break;
      } catch (Error e) {
        // Incorrect Folde
      }

      // TODO Error / Debug Handling
      validOption = true;

    }

    Mod[] modFolder = FileManager.retrieveMods("../data/mods/" + baseWeapon.getType());

    // TODO

    // Hard-Coded for standard mod collection access
    Mod[] modSet = new Mod[8];
    int n = modFolder.length;
    int r = 8;

    // Initial Combination
    int[] combo = Math_Utility.generateCombination(n, r);

    // Variable for traversing the ModSet array
    int index = 0;

    // Retrieves mods from collection
    for (int i : combo) {
      modSet[index] = modFolder[i - 1];
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
        modSet[index] = modFolder[i - 1];
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
        System.out.print("\n" + modFolder[i - 1].getName());
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

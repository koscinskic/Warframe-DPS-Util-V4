/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.io.*;
import java.util.Scanner;

/**
* TODO: Expand Program
* As of the current moment, Menu is a highly volatile and subject-to-change
* class that will eventually serve as the central UI and access to the project.
*/

public class Menu {

  // Variable for program-wide debug features
  public static boolean debug = false;

  // Variable for retaining user entries
  public static String userInput;

  /**
  * Main Method
  */

  public static void main(String[] args) {

    // User Input Scanner
    Scanner inputMain = new Scanner(System.in);

    cls();

    System.out.println("\nWelcome to Warframe-DPS-Util-V4");

    /* TODO: Debug Functionality
    System.out.println("\nWould you like to enable debug mode?");
    if (inputMain.nextLine().toUpperCase().contains("YES")) {
      debug = true;
    }
    */

    /* TODO: Calculation Time Statistics
    System.out.println("\nPrint every (X) intervals...");
    System.out.print("X = ");
    int interval = Integer.parseInt(inputMain.nextLine());
    */

    // Hard-Coded Mod Folder and Weapon
    Mod[] modFolder = FileManager.retrieveMods("../data/mods/rifle");
    Weapon baseWeapon = new Weapon("../data/weapons/rifle/SomaPrime.csv");

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

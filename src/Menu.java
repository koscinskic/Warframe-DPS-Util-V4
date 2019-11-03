
import java.io.*;
import java.util.Scanner;

public class Menu {

  public static boolean debug = false;
  public static String userInput;

  public static void main(String[] args) {

    Scanner inputMain = new Scanner(System.in);

    System.out.println("\nWelcome to Warframe-DPS-Util-V4");

    System.out.println("\nWould you like to enable debug mode?");
    if (inputMain.nextLine().toUpperCase().contains("YES")) {
      debug = true;
    }

    /*

    int n = FileManager.retrieveMods("../data/mods/pistol").length;
    int r = 8;

    int[] combo = Math_Utility.generateCombination(n, r);
    double comboCount = 1;

    while (combo[0] != ((n - r) + 1)) {
      combo = Math_Utility.incrementCombination(n, r, combo);
      comboCount++;
    }

    System.out.println("\n" + comboCount);
    double check = Math_Utility.maxCombination((double)n, (double)r);

    if (comboCount == check) {
      System.out.println("\nTrue");
    }

    */

    Mod[] modFolder = FileManager.retrieveMods("../data/mods/pistol");

    for (Mod currentMod : modFolder) {
      currentMod.printMod();
    }

  }

  public static void cls() {
    try {
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (IOException e) {
      //
    } catch (InterruptedException e2) {
      //
    }

  }

}


import java.io.IOException;
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

    Weapon testWep = new Weapon("../data/weapons/pistol/PyranaPrime.csv");

    System.out.println("\nInstant: " + DPS_Calculator.calculateInstantDPS(testWep));
    System.out.println("\nSustained: " + DPS_Calculator.calculateSustainedDPS(testWep));

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

/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

/**
* The Math_Utility class provides methods for the basic calculation and
* manipulation of mathematical combinations.
*
* "n" denotes the quantity of items available for any given combination
* "r" denotes the quantity of items a combination is composed of
*
* Consequentially, the combination follows the form nCr
*/

public class Math_Utility {

  /**
  * Generates an initial combination in numerical order given "n" and "r".
  */

  public static int[] generateCombination(int n, int r) {

    int[] combo = new int[r];

    for (int i = 0; i < combo.length; i++) {
      combo[i] = i + 1;
    }

    return combo;

  }

  /**
  * Increments the given combination by one value in numerical order.
  * Requires the maximum value possible for any index as "n".
  */

  public static int[] incrementCombination(int n, int[] combo) {

    int r = combo.length;
    int indexPos = r - 1;

    // Determines which index to increment
    for (int i = r - 1; i >= 0; i--) {
      if (combo[i] + 1 <= (n - r) + i + 1) {
        indexPos = i;
        i = 0;
      }
    }

    // Increments said index
    combo[indexPos]++;

    // Resets the combination in an ascending order from the said index if the
    // index has changed since the previous increment.
    if (indexPos != (r - 1)) {
      for (int i = indexPos + 1; i < combo.length; i++) {
        combo[i] = combo[i - 1] + 1;
      }
    }

    return combo;

  }

  /**
  * Method that displays the combination
  */

  public static void printCombo(int[] combo) {
    System.out.println();
    for (int i : combo) {
      System.out.print(i + " ");
    }
  }

  /**
  * Recursive factorial calculation
  */

  private static double factorial(double value) {
    if (value == 1.0) {
      return 1.0;
    } else {
      return (value * factorial(value - 1.0));
    }
  }

  /**
  * Formulaic combination calculation using above factorial method
  */

  public static double maxCombination(double valueN, double valueR) {
    return (factorial(valueN) / (factorial(valueN - valueR) *
    factorial(valueR)));
  }

}

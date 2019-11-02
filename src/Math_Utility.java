
public class Math_Utility {

  public static int[] generateCombination(int n, int r) {

    int[] combo = new int[r];

    for (int i = 0; i < combo.length; i++) {
      combo[i] = i + 1;
    }

    return combo;

  }

  public static int[] incrementCombination(int n, int r, int[] combo) {

    int indexPos = r - 1;

    for (int i = r - 1; i >= 0; i--) {
      if (combo[i] + 1 <= (n - r) + i + 1) {
        indexPos = i;
        i = 0;
      }
    }

    combo[indexPos]++;

    if (indexPos != (r - 1)) {
      for (int i = indexPos + 1; i < combo.length; i++) {
        combo[i] = combo[i - 1] + 1;
      }
    }

    return combo;

  }

  public static void printCombo(int[] combo) {
    System.out.println();
    for (int i : combo) {
      System.out.print(i + " ");
    }
  }

  private static double factorial(double value) {
    if (value == 1.0) {
      return 1.0;
    } else {
      return (value * factorial(value - 1.0));
    }
  }

  public static double maxCombination(double valueN, double valueR) {
    return (factorial(valueN) / (factorial(valueN - valueR) * factorial(valueR)));
  }

}

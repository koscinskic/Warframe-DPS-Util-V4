/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.util.*;

/**
*
*/

public class Optimizer {

  private static boolean firstRun = true;

  private static Weapon moddedWeapon;

  private static Mod[] comboSet = new Mod[8];
  private static Mod[] reqSet;
  private static Mod[] modSet;
  private static int setIndex = 0;

  private static int n;
  private static int r;

  private static int[] combo;

  private static int whitelistCount = 0;
  private static int blacklistCount = 0;

  public static int[] optimizeDPS(Vector<Mod> modFolder, Weapon baseWeapon, boolean[] blacklist, boolean[] whitelist, String mode) {

    int[] highestCombo = new int[8];
    double highestDPS = 0;
    double currentDPS = 0;

    double comboCount = 0;

    for (int index = 0; index < whitelist.length; index++) {
      if (whitelist[index] == true) {
        whitelistCount++;
      }
      if (blacklist[index] == true) {
        blacklistCount++;
      }
    }

    n = modFolder.size() - (whitelistCount + blacklistCount);
    r = 8 - whitelistCount;

    reqSet = new Mod[whitelistCount];
    modSet = new Mod[n];

    for (int index = 0; setIndex < whitelistCount; index++) {
      if (whitelist[index] == true) {
        reqSet[setIndex] = modFolder.elementAt(index);
        setIndex++;
      }
    }

    setIndex = 0;

    for (int index = 0; setIndex < n; index++) {
      if (whitelist[index] == false && blacklist[index] == false) {
        modSet[setIndex] = modFolder.elementAt(index);
        setIndex++;
      }
    }

    setIndex = 0;

    do {

      if (firstRun) {
        combo = Math_Utility.generateCombination(n, r);
        firstRun = false;
      } else {
        combo = Math_Utility.incrementCombination(n, combo);
      }

      comboCount++;

      for (int modID : combo) {
        comboSet[setIndex] = modSet[modID - 1];
        setIndex++;
      }

      for (int reqIndex = 0; setIndex < 8; reqIndex++) {
        comboSet[setIndex] = reqSet[reqIndex];
        setIndex++;
      }

      setIndex = 0;

      moddedWeapon = new Weapon(baseWeapon);
      moddedWeapon.modifyWeapon(comboSet);

      switch (mode) {

        case ("BURST"):
        currentDPS = DPS_Calculator.calculateInstantDPS(moddedWeapon);
        break;

        case ("SUSTAINED"):
        currentDPS = DPS_Calculator.calculateSustainedDPS(moddedWeapon);
        break;

      }

      if (currentDPS > highestDPS) {

        highestDPS = currentDPS;

        for (int modID : combo) {
          highestCombo[setIndex] = modID;
          setIndex++;
        }

        setIndex = 0;

      }

    } while (comboCount < Math_Utility.maxCombination(n, r));

    return highestCombo;

  }

  public static Mod[] getModSet() {
    return modSet;
  }

}

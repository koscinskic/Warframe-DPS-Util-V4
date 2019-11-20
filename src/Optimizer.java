/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.util.*;

/**
* The Optimizer class currently serves to faciliate information transfer between
* the various classes within the program in the form of a cohesive damage
* optimization process. Due to the relatively recent expansion of the overall
* class structure concerning this program, redundant variables and methods may
* be subject to change.
*/

public class Optimizer {

  public static Mod[] optimizeDPS(Vector<Mod> modFolder, Weapon baseWeapon,
  boolean[] blacklist, boolean[] whitelist, String mode) {

    // Variable concerning combination loop alteration
    boolean firstRun = true;

    // Variable containing the weapon with altered stats
    Weapon moddedWeapon;

    // Variables concerning DPS tracking
    double highestDPS = 0;
    double currentDPS = 0;

    // Variable containing the mathematical combination for modIDs
    int[] combo = new int[8];

    // Variables containing mod object combinations
    Mod[] highestCombo = new Mod[8];
    Mod[] comboSet = new Mod[8];

    // Variable containing the mods eligable for combination processing
    Mod[] modSet;

    // Variable containing the whitelisted mods
    Mod[] reqSet;

    // Variable concerning indices regarding loop iteration
    int setIndex = 0;

    // Variable containing the quantity of combinations processed
    int comboCount = 0;

    // Variables concerning combination paramters
    int n;
    int r;

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

        for (Mod mod : comboSet) {
          highestCombo[setIndex] = mod;
          setIndex++;
        }

        setIndex = 0;

      }

    } while (comboCount + 1 < Math_Utility.maxCombination(n, r));

    return highestCombo;

  }

}

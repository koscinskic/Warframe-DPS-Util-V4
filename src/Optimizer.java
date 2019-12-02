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
*
* The calculation works based off of a full 8 mod loadout. Consequentially, the
* default configuration (no blacklist or whitelist) draws from the full mod
* collection in order to fulfill every 8 mod combination possible. Blacklisting
* mods rejects them from any combination, while whitelisting mods requires them
* in every combination. Should the user whitelist 8 mods, there would only be
* 1 calculation possible.
*
* Within the code, the full mod loadout is referred to as "comboSet", which is
* a function of both procedurely generated combinations from the total set of
* non-blacklisted / non-whitelisted mods ("modSet") appended to "reqSet", which
* is composed of the whitelisted mods. These 2 sets combined will always add up
* to 8.
*
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

    // Populates the reqSet array (Whitelisted mods)
    for (int index = 0; setIndex < whitelistCount; index++) {
      if (whitelist[index] == true) {
        reqSet[setIndex] = modFolder.elementAt(index);
        setIndex++;
      }
    }

    setIndex = 0;

    // Populates the modSet array (Non-Whitelisted and Non-Blacklisted mods)
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

      // Adds the procedurely generated combination to the set
      for (int modID : combo) {
        comboSet[setIndex] = modSet[modID - 1];
        setIndex++;
      }

      // Adds the whitelisted mods to the set
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

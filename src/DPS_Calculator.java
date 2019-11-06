/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

/**
* The DPS_Calculator class approximates the instantaneous and sustained DPS of
* a given weapon.
*
* Variable "Critical Tier" refers to the highest critical multiplier possible,
* where each value refers to the following:
*
* 0 - No criticals possible
* 1 - Yellow criticals possible
* 2 - Orange criticals possible
* 3 - Red criticals possible
*
* Additional tiers scale accordingly with no color change
*/

public class DPS_Calculator {

  /**
  * Calculates Instant DPS
  */

  public static double calculateInstantDPS(Weapon currentWeapon) {

    // Variables concerning the Critical Tier (see above)
    int criticalTier = 1;
    double criticalRemainder = currentWeapon.getCriticalChance();

    // Variables concerning the Critical Multiplier
    double criticalMultiplierLow = 1;
    double criticalMultiplierHigh = currentWeapon.getCriticalMultiplier();

    // Assigns and calculates the appropriate critical values
    if (currentWeapon.getCriticalChance() >= 100) {

      criticalRemainder = currentWeapon.getCriticalChance() % 100;
      criticalTier = (int)((currentWeapon.getCriticalChance() -
      criticalRemainder) / 100) + 1;

      criticalMultiplierLow = 1 + ((criticalTier - 1) *
      (currentWeapon.getCriticalMultiplier() - 1));
      criticalMultiplierHigh = 1 + (criticalTier *
      (currentWeapon.getCriticalMultiplier() - 1));

    }

    // Converts the remainder to percentage form
    criticalRemainder /= 100;

    // Leverages critical chances and multipliers to approximate the damage of
    // an everage shot
    double damageAverage = (criticalRemainder * (criticalMultiplierHigh *
    currentWeapon.getDamagePerShot())) + ((1 - criticalRemainder) *
    (criticalMultiplierLow * currentWeapon.getDamagePerShot()));

    double instantDPS = damageAverage * currentWeapon.getFireRate();

    return instantDPS;

  }

  /**
  * Calculates Sustained DPS
  */

  public static double calculateSustainedDPS(Weapon currentWeapon) {

    double instantDPS = calculateInstantDPS(currentWeapon);

    // How long it takes to empty the magazine
    double magazineLifetime = currentWeapon.getMagazineCapacity() /
    currentWeapon.getFireRate();

    double sustainedDPS = (magazineLifetime / (magazineLifetime +
    currentWeapon.getReloadSpeed())) * instantDPS;

    return sustainedDPS;

  }

}

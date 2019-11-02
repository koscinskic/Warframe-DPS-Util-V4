
public class DPS_Calculator {

  public static double calculateInstantDPS(Weapon currentWeapon) {

    double criticalRemainder = currentWeapon.getCriticalChance();
    int criticalTier = 1;

    double criticalMultiplierLow = 1;
    double criticalMultiplierHigh = currentWeapon.getCriticalMultiplier();

    if (currentWeapon.getCriticalChance() >= 100) {

      criticalRemainder = currentWeapon.getCriticalChance() % 100;
      criticalTier = (int)((currentWeapon.getCriticalChance() - criticalRemainder) / 100) + 1;

      criticalMultiplierLow = 1 + ((criticalTier - 1) * (currentWeapon.getCriticalMultiplier() - 1));
      criticalMultiplierHigh = 1 + (criticalTier * (currentWeapon.getCriticalMultiplier() - 1));

    }

    criticalRemainder /= 100;

    double damageTotal = currentWeapon.getImpact() + currentWeapon.getPuncture() + currentWeapon.getSlash();
    double damageAverage = (criticalRemainder * (criticalMultiplierHigh * damageTotal)) + ((1 - criticalRemainder) * (criticalMultiplierLow * damageTotal));

    double instantDPS = damageAverage * currentWeapon.getFireRate();

    return instantDPS;

  }

  public static double calculateSustainedDPS(Weapon currentWeapon) {

    double instantDPS = calculateInstantDPS(currentWeapon);

    double magazineLifetime = currentWeapon.getMagazineCapacity() / currentWeapon.getFireRate();
    double sustainedDPS = (magazineLifetime / (magazineLifetime + currentWeapon.getReloadSpeed())) * instantDPS;

    return sustainedDPS;

  }

}

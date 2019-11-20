/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.io.*;
import java.util.Scanner;

/**
* The Weapon class serves to interpret and hold the data contained within any
* weapon-oriented csv file.
* Additionally, the process of applying mods to any given weapon is contained
* within the class as well.
*/

public class Weapon {

  // Variables concerning file organization
  private String name;
  private String type;

  // Variables containing nearly universal weapon properties
  // TODO: Weapons missing one or more of these fundamental properties do not
  // currently work with the system
  private double criticalChance;
  private double criticalMultiplier;
  private double fireRate;
  private int magazineCapacity;
  private double reloadSpeed;
  private int maximumAmmoReserves;
  private String ammoType;
  private double statusChance;

  // Variables containing the basic physical and elemental damage properties
  // TODO: Weapons with innate dual-elements do not currently work with the
  // system
  private double impact = 0;
  private double puncture = 0;
  private double slash = 0;
  private double cold = 0;
  private double electricity = 0;
  private double heat = 0;
  private double toxin = 0;

  // Variable containing the summation of all previous damage values
  private double damagePerShot;

  /**
  * Empty Constructor
  */

  public Weapon() {

    // No current usage

  }

  /**
  * Constructor designed to clone a preexisting weapon
  */

  public Weapon(Weapon base) {

    this.setName(base.getName());
    this.setType(base.getType());
    this.setCriticalChance(base.getCriticalChance());
    this.setCriticalMultiplier(base.getCriticalMultiplier());
    this.setFireRate(base.getFireRate());
    this.setMagazineCapacity(base.getMagazineCapacity());
    this.setReloadSpeed(base.getReloadSpeed());
    this.setStatusChance(base.getStatusChance());
    this.setImpact(base.getImpact());
    this.setPuncture(base.getPuncture());
    this.setSlash(base.getSlash());
    this.setHeat(base.getHeat());
    this.setCold(base.getCold());
    this.setElectricity(base.getElectricity());
    this.setToxin(base.getToxin());
    this.setDamagePerShot(base.getDamagePerShot());

  }

  /**
  * Constructor designed to fully convert a csv into a weapon object
  */

  public Weapon(File weaponFile) {

    // Temporary Scanner
    Scanner weaponScanner = new Scanner(System.in);

    // Variables concerning csv translation
    String entryLine;
    String entryContent;
    String entryType;

    try {
      weaponScanner = new Scanner(weaponFile);
    } catch (FileNotFoundException e) {

    }

    while (weaponScanner.hasNext()) {

      entryLine = weaponScanner.nextLine();
      entryContent = entryLine.substring(entryLine.indexOf(",") + 1,
      entryLine.length()).toUpperCase();
      entryType = entryLine.substring(0, entryLine.indexOf(",")).toUpperCase();

      switch (entryType) {

        case ("NAME"):
        this.setName(entryContent);
        break;

        case ("TYPE"):
        this.setType(entryContent);
        break;

        case ("CRITICAL CHANCE"):
        this.setCriticalChance(Double.parseDouble(entryContent));
        break;

        case ("CRITICAL MULTIPLIER"):
        this.setCriticalMultiplier(Double.parseDouble(entryContent));
        break;

        case ("FIRE RATE"):
        this.setFireRate(Double.parseDouble(entryContent));
        break;

        case ("MAGAZINE CAPACITY"):
        this.setMagazineCapacity(Integer.parseInt(entryContent));
        break;

        case ("RELOAD SPEED"):
        this.setReloadSpeed(Double.parseDouble(entryContent));
        break;

        case ("MAXIMUM AMMO RESERVES"):
        this.setMaximumAmmoReserves(Integer.parseInt(entryContent));
        break;

        case ("AMMO TYPE"):
        this.setAmmoType(entryContent);
        break;

        case ("IMPACT"):
        this.setImpact(Double.parseDouble(entryContent));
        break;

        case ("PUNCTURE"):
        this.setPuncture(Double.parseDouble(entryContent));
        break;

        case ("SLASH"):
        this.setSlash(Double.parseDouble(entryContent));
        break;

        case ("COLD"):
        this.setCold(Double.parseDouble(entryContent));
        break;

        case ("ELECTRICITY"):
        this.setElectricity(Double.parseDouble(entryContent));
        break;

        case ("HEAT"):
        this.setHeat(Double.parseDouble(entryContent));
        break;

        case ("TOXIN"):
        this.setToxin(Double.parseDouble(entryContent));
        break;

        // TODO: Temporary solution for calculating raw DPS for dual-element
        // weapons
        // For the sake of calculating raw DPS, this system is functional, but
        // does not accurately reflect elemental modifiers

        case ("BLAST"):
        this.setHeat(this.getHeat() + (Double.parseDouble(entryContent) / 2));
        this.setCold(this.getCold() + (Double.parseDouble(entryContent) / 2));
        break;

        case ("CORROSIVE"):
        this.setElectricity(this.getElectricity() + (Double.parseDouble(entryContent) / 2));
        this.setToxin(this.getToxin() + (Double.parseDouble(entryContent) / 2));
        break;

        case ("GAS"):
        this.setHeat(this.getHeat() + (Double.parseDouble(entryContent) / 2));
        this.setToxin(this.getToxin() + (Double.parseDouble(entryContent) / 2));
        break;

        case ("MAGNETIC"):
        this.setElectricity(this.getElectricity() + (Double.parseDouble(entryContent) / 2));
        this.setCold(this.getCold() + (Double.parseDouble(entryContent) / 2));
        break;

        case ("RADIATION"):
        this.setHeat(this.getHeat() + (Double.parseDouble(entryContent) / 2));
        this.setElectricity(this.getElectricity() + (Double.parseDouble(entryContent) / 2));
        break;

        case ("VIRAL"):
        this.setToxin(this.getToxin() + (Double.parseDouble(entryContent) / 2));
        this.setCold(this.getCold() + (Double.parseDouble(entryContent) / 2));
        break;

        case ("STATUS CHANCE"):
        this.setStatusChance(Double.parseDouble(entryContent));
        break;

      }

    }

    // Combines all other damage types into a singular value
    this.setDamagePerShot(this.getImpact() + this.getPuncture() +
    this.getSlash() + this.getHeat() + this.getCold() + this.getElectricity() +
    this.getToxin());

    weaponScanner.close();

  }

  /**
  * Method that modifies a weapon given a combination of mods
  */

  public void modifyWeapon(Mod[] modFolder) {

    // Variables containing mod effects that directly alter base weapon
    // statistics and properties
    double criticalChanceMOD = 0;
    double criticalMultiplierMOD = 0;
    double fireRateMOD = 0;
    double magazineCapacityMOD = 0;
    double reloadSpeedMOD = 0;
    double statusChanceMOD = 0;
    double impactMOD = 0;
    double punctureMOD = 0;
    double slashMOD = 0;
    double heatMOD = 0;
    double coldMOD = 0;
    double electricityMOD = 0;
    double toxinMOD = 0;

    // Variables containing mod effects that rely on other mods for
    // calculations
    double damageMOD = 0;
    double multishotMOD = 0;

    // Variables containing the summation of all previous respective damage
    // values
    double damagePhysical;
    double damageElemental;
    double damageTotal;

    // Variable temporarily storing the value of a mod's effect
    double effectValue;

    for (Mod currentMod : modFolder) {

      effectValue = currentMod.getModValues()[1][currentMod.getRanks() - 1];

      switch (currentMod.getEffect1()) {

        case ("CRITICAL CHANCE"):
        criticalChanceMOD += effectValue;
        break;

        case ("CRITICAL MULTIPLIER"):
        criticalMultiplierMOD += effectValue;
        break;

        case ("FIRE RATE"):
        fireRateMOD += effectValue;
        break;

        case ("MAGAZINE CAPACITY"):
        magazineCapacityMOD += effectValue;
        break;

        case ("RELOAD SPEED"):
        reloadSpeedMOD += effectValue;
        break;

        case ("STATUS CHANCE"):
        statusChanceMOD += effectValue;
        break;

        case ("IMPACT"):
        impactMOD += effectValue;
        break;

        case ("PUNCTURE"):
        punctureMOD += effectValue;
        break;

        case ("SLASH"):
        slashMOD += effectValue;
        break;

        case ("DAMAGE"):
        damageMOD += effectValue;
        break;

        case ("MULTISHOT"):
        multishotMOD += effectValue;
        break;

        case ("COLD"):
        coldMOD += effectValue;
        break;

        case ("ELECTRICITY"):
        electricityMOD += effectValue;
        break;

        case ("HEAT"):
        heatMOD += effectValue;
        break;

        case ("TOXIN"):
        toxinMOD += effectValue;
        break;

      }

      if (currentMod.getEffect2() != null) {

        effectValue = currentMod.getModValues()[2][currentMod.getRanks() - 1];

        switch (currentMod.getEffect2()) {

          case ("CRITICAL CHANCE"):
          criticalChanceMOD += effectValue;
          break;

          case ("CRITICAL MULTIPLIER"):
          criticalMultiplierMOD += effectValue;
          break;

          case ("FIRE RATE"):
          fireRateMOD += effectValue;
          break;

          case ("MAGAZINE CAPACITY"):
          magazineCapacityMOD += effectValue;
          break;

          case ("RELOAD SPEED"):
          reloadSpeedMOD += effectValue;
          break;

          case ("STATUS CHANCE"):
          statusChanceMOD += effectValue;
          break;

          case ("IMPACT"):
          impactMOD += effectValue;
          break;

          case ("PUNCTURE"):
          punctureMOD += effectValue;
          break;

          case ("SLASH"):
          slashMOD += effectValue;
          break;

          case ("DAMAGE"):
          damageMOD += effectValue;
          break;

          case ("MULTISHOT"):
          multishotMOD += effectValue;
          break;

          case ("HEAT"):
          heatMOD += effectValue;
          break;

          case ("COLD"):
          coldMOD += effectValue;
          break;

          case ("ELECTRICITY"):
          electricityMOD += effectValue;
          break;

          case ("TOXIN"):
          toxinMOD += effectValue;
          break;

        }

      }

    }

    // Converts all mods to percentage form
    criticalChanceMOD /= 100;
    criticalMultiplierMOD /= 100;
    fireRateMOD /= 100;
    magazineCapacityMOD /= 100;
    reloadSpeedMOD /= 100;
    statusChanceMOD /= 100;
    impactMOD /= 100;
    punctureMOD /= 100;
    slashMOD /= 100;
    damageMOD /= 100;
    multishotMOD /= 100;
    heatMOD /= 100;
    coldMOD /= 100;
    electricityMOD /= 100;
    toxinMOD /= 100;

    // Apply base damage increase to physical damage
    impact *= (1 + damageMOD);
    puncture *= (1 + damageMOD);
    slash *= (1 + damageMOD);

    // Apply base damage increase to elemental damage
    damageElemental = (heat + cold + electricity + toxin) * (1 + damageMOD);

    // Combine physical damage
    damagePhysical = impact + puncture + slash;

    // Apply elemental damage increase to total damage
    damageElemental = (damagePhysical + damageElemental) *
    (1 + heatMOD + coldMOD + electricityMOD + toxinMOD);

    // Apply individual physical damage increases to physical damages
    // respectively
    impact *= (1 + impactMOD);
    puncture *= (1 + punctureMOD);
    slash *= (1 + slashMOD);

    // Recombine physical damage
    damagePhysical = impact + puncture + slash;

    // Apply multishot damage increase to total damage
    // TODO: Include status chance for multishot
    damageTotal = (damagePhysical + damageElemental) * (1 + multishotMOD);

    // Save temporary variable as final value for DPS calculation
    this.setDamagePerShot(damageTotal);

    // Apply other DPS-relevant effects
    criticalChance *= (1 + criticalChanceMOD);
    criticalMultiplier *= (1 + criticalMultiplierMOD);
    fireRate *= (1 + fireRateMOD);
    magazineCapacity *= (1 + magazineCapacityMOD);
    reloadSpeed *= (1 + reloadSpeedMOD);
    statusChance *= (1 + statusChanceMOD);

  }

  /**
  * Method that displays the relevant information for the weapon in the console
  */

  public void printWeapon() {

    System.out.print("\nName: " + this.getName());
    System.out.print("\nType: " + this.getType());
    System.out.print("\nCC: " + this.getCriticalChance());
    System.out.print("\nCM: " + this.getCriticalMultiplier());
    System.out.print("\nFR: " + this.getFireRate());
    System.out.print("\nMag: " + this.getMagazineCapacity());
    System.out.print("\nReload: " + this.getReloadSpeed());
    System.out.print("\nStatus: " + this.getStatusChance());
    System.out.print("\nImpact: " + this.getImpact());
    System.out.print("\nPuncture: " + this.getPuncture());
    System.out.print("\nSlash: " + this.getSlash());
    System.out.print("\nDamage: " + this.getDamagePerShot());

  }

  /**
  * Setters & Getters below
  */

  public void setName(String n) {name = n;}

  public void setType(String t) {type = t;}

  public void setCriticalChance(double cc) {criticalChance = cc;}

  public void setCriticalMultiplier(double cm) {criticalMultiplier = cm;}

  public void setFireRate(double fr) {fireRate = fr;}

  public void setMagazineCapacity(int mc) {magazineCapacity = mc;}

  public void setReloadSpeed(double rs) {reloadSpeed = rs;}

  public void setMaximumAmmoReserves(int ar) {maximumAmmoReserves = ar;}

  public void setAmmoType(String at) {ammoType = at;}

  public void setStatusChance(double ss) {statusChance = ss;}

  public void setImpact(double im) {impact = im;}

  public void setPuncture(double pu) {puncture = pu;}

  public void setSlash(double sl) {slash = sl;}

  public void setHeat(double h) {heat = h;}

  public void setCold(double c) {cold = c;}

  public void setElectricity(double e) {electricity = e;}

  public void setToxin(double t) {toxin = t;}

  public void setDamagePerShot(double ps) {damagePerShot = ps;}

  public String getName() {return name;}

  public String getType() {return type;}

  public double getCriticalChance() {return criticalChance;}

  public double getCriticalMultiplier() {return criticalMultiplier;}

  public double getFireRate() {return fireRate;}

  public int getMagazineCapacity() {return magazineCapacity;}

  public double getReloadSpeed() {return reloadSpeed;}

  public int getMaximumAmmoReserves() {return maximumAmmoReserves;}

  public String getAmmoType() {return ammoType;}

  public double getStatusChance() {return statusChance;}

  public double getImpact() {return impact;}

  public double getPuncture() {return puncture;}

  public double getSlash() {return slash;}

  public double getHeat() {return heat;}

  public double getCold() {return cold;}

  public double getElectricity() {return electricity;}

  public double getToxin() {return toxin;}

  public double getDamagePerShot() {return damagePerShot;}

}

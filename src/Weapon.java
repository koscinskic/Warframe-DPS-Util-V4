
import java.io.*;
import java.util.Scanner;

public class Weapon {

  private String name;
  private String type;

  private int criticalChance;
  private double criticalMultiplier;

  private double fireRate;
  private int magazineCapacity;
  private double reloadSpeed;

  private int statusChance;

  private double impact;
  private double puncture;
  private double slash;

  public Weapon() {

    //

  }

  public Weapon(String fileName) {

    Scanner weaponScanner = new Scanner(System.in);

    String entryLine;
    String entryContent;
    String entryType;

    try {
      File weaponFile = new File(fileName);
      weaponScanner = new Scanner(weaponFile);
    } catch (FileNotFoundException noFile) {
      //
    }

    while (weaponScanner.hasNext()) {

      entryLine = weaponScanner.nextLine();
      entryContent = entryLine.substring(entryLine.indexOf(",") + 1, entryLine.length()).toUpperCase();
      entryType = entryLine.substring(0, entryLine.indexOf(",")).toUpperCase();

      switch (entryType) {

        case ("NAME"):
        this.setName(entryContent);
        break;

        case ("TYPE"):
        this.setType(entryContent);
        break;

        case ("CRITICAL CHANCE"):
        this.setCriticalChance(Integer.parseInt(entryContent));
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

        case ("IMPACT"):
        this.setImpact(Double.parseDouble(entryContent));
        break;

        case ("PUNCTURE"):
        this.setPuncture(Double.parseDouble(entryContent));
        break;

        case ("SLASH"):
        this.setSlash(Double.parseDouble(entryContent));
        break;

        case ("STATUS CHANCE"):
        this.setStatusChance(Integer.parseInt(entryContent));
        break;

      }

    }

  }

  public void modifyWeapon() {

    //

  }

  public void setName(String n) {
    name = n;
  }

  public void setType(String t) {
    type = t;
  }

  public void setCriticalChance(int cc) {
    criticalChance = cc;
  }

  public void setCriticalMultiplier(double cm) {
    criticalMultiplier = cm;
  }

  public void setFireRate(double fr) {
    fireRate = fr;
  }

  public void setMagazineCapacity(int mc) {
    magazineCapacity = mc;
  }

  public void setReloadSpeed(double rs) {
    reloadSpeed = rs;
  }

  public void setStatusChance(int ss) {
    statusChance = ss;
  }

  public void setImpact(double im) {
    impact = im;
  }

  public void setPuncture(double pu) {
    puncture = pu;
  }

  public void setSlash(double sl) {
    slash = sl;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public int getCriticalChance() {
    return criticalChance;
  }

  public double getCriticalMultiplier() {
    return criticalMultiplier;
  }

  public double getFireRate() {
    return fireRate;
  }

  public int getMagazineCapacity() {
    return magazineCapacity;
  }

  public double getReloadSpeed() {
    return reloadSpeed;
  }

  public int getStatusChance() {
    return statusChance;
  }

  public double getImpact() {
    return impact;
  }

  public double getPuncture() {
    return puncture;
  }

  public double getSlash() {
    return slash;
  }

}

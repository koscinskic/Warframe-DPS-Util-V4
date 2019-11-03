
import java.io.*;
import java.util.Scanner;

public class Mod {

  private String name;
  private String type;
  private String polarity;
  private String effect1;
  private String effect2;

  private int ranks;
  private double[][] modValues;

  private boolean conditional = false;

  public Mod() {

    //

  }

  public Mod(File modFile) {

    Scanner modScanner = new Scanner(System.in);

    String entry;
    String[] entryLine;

    String entryType;

    int currentRank = 0;

    try {
      modScanner = new Scanner(modFile);
    } catch (FileNotFoundException noFile) {
      //
    }

    while (modScanner.hasNext()) {

      entry = modScanner.nextLine().toUpperCase();

      if (!entry.equals(",") && !entry.equals(",,")) {

        entryLine = entry.split(",");

        entryType = entryLine[0];

        if (entryType.contains("NAME")) {

          this.setName(entryLine[1]);

        } else if (entryType.length() > 2) {

          switch (entryType) {

            case ("TYPE"):
            this.setType(entryLine[1]);
            break;

            case ("POLARITY"):
            this.setPolarity(entryLine[1]);
            break;

            case ("EFFECT1"):
            this.setEffect1(entryLine[1]);
            break;

            case ("EFFECT2"):
            this.setEffect2(entryLine[1]);
            break;

            case ("RANKS"):
            this.setRanks(Integer.parseInt(entryLine[1]));

            if (this.getEffect2() != null) {
              modValues = new double[3][this.getRanks()];
            } else {
              modValues = new double[2][this.getRanks()];
            }

            break;

            case ("CONDITIONAL"):
            conditional = true;
            break;

          }

        } else {

          modValues[0][currentRank] = Integer.parseInt(entryType);
          modValues[1][currentRank] = Double.parseDouble(entryLine[1].substring(0, entryLine[1].indexOf("%")));

          if (this.getEffect2() != null) {
            modValues[2][currentRank] = Double.parseDouble(entryLine[2].substring(0, entryLine[2].indexOf("%")));
          }

          currentRank++;

        }

      }

    }

  }

  public void printMod() {

    System.out.print("\nMod: " + name);
    System.out.print("\nType: " + type);
    System.out.print("\nPolarity: " + polarity);
    System.out.print("\nEffect: " + effect1);

    if (effect2 != null) {
      System.out.print("\nEffect: " + effect2);
    }

    System.out.print("\nRanks: " + ranks);

    if (conditional == true) {
      System.out.print("\nConditional");
    }

    System.out.println("\nCost & Effect:");

    int index = 0;

    if (effect2 != null) {

      while (index < modValues[0].length) {
        System.out.println(modValues[0][index] + " | " + modValues[1][index] + " | " + modValues[2][index]);
        index++;
      }

    } else {

      while (index < modValues[0].length) {
        System.out.println(modValues[0][index] + " | " + modValues[1][index]);
        index++;
      }

    }

  }

  public void setName(String n) {
    name = n;
  }

  public void setType(String t) {
    type = t;
  }

  public void setPolarity(String p) {
    polarity = p;
  }

  public void setEffect1(String e1) {
    effect1 = e1;
  }

  public void setEffect2(String e2) {
    effect2 = e2;
  }

  public void setRanks(int r) {
    ranks = r;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getPolarity() {
    return polarity;
  }

  public String getEffect1() {
    return effect1;
  }

  public String getEffect2() {
    return effect2;
  }

  public int getRanks() {
    return ranks;
  }

  public double[][] getModValues() {
    return modValues;
  }

}

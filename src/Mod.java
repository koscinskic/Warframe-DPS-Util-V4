/**
* @author Caden Koscinski
*
* This work is mine unless otherwise cited.
*/

import java.io.*;
import java.util.Scanner;

/**
* The Mod class serves to interpret and hold the data contained within any
* mod-oriented csv file.
*/

public class Mod {

  // Variables concerning file organization
  private String name;
  private String type;

  // TODO: Variable that is currently unused...will eventually be incorporated in
  // order to determine the overall drain of a mod configuration.
  private String polarity;

  // Variables serving to indicate the property that is altered by the mod.
  // The majority of mods are governed either by a singular beneficial effect,
  // a beneficial and detrimental effect, or two beneficial effects. In the
  // case that a mod only has one effect, "effect2" will remain null.
  private String effect1;
  private String effect2;

  // Variables serving to hold the various different states of each mod
  private int ranks;
  private double[][] modValues;
  private int currentRank = 0;

  // TODO: Variable that is only partially implemented...will eventually be
  // incorporated in order to filter impractical or situational builds.
  private boolean conditional = false;

  /**
  * Empty Constructor
  */

  public Mod() {

    // No current usage

  }

  /**
  * Constructor designed to fully convert a csv into a mod object
  */

  public Mod(File modFile) {

    // Temporary Scanner
    Scanner modScanner = new Scanner(System.in);

    // Variables concerning csv translation
    String entry;
    String[] entryLine;
    String entryType;

    try {
      modScanner = new Scanner(modFile);
    } catch (FileNotFoundException noFile) {
      // Handle Exception Here
    }

    while (modScanner.hasNext()) {

      entry = modScanner.nextLine().toUpperCase();

      // Prevents a null case where splitting a comma-only line returns an
      // array with 0 dimensions
      if (!entry.equals(",") && !entry.equals(",,")) {

        entryLine = entry.split(",");
        entryType = entryLine[0];

        // TODO: BOM removal is not currently implemented, thus the first line cannot
        // be included as a switch case
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
              this.setModValues(new double[3][this.getRanks()]);
            } else {
              this.setModValues(new double[2][this.getRanks()]);
            }

            break;

            case ("CONDITIONAL"):
            conditional = true;
            break;

          }

        } else {

          // TODO: Format to adhere to S&G structure
          modValues[0][this.getCurrentRank()] = Integer.parseInt(entryType);
          modValues[1][this.getCurrentRank()] = Double.parseDouble(
          entryLine[1].substring(0, entryLine[1].indexOf("%")));

          if (this.getEffect2() != null) {
            modValues[2][this.getCurrentRank()] = Double.parseDouble(
            entryLine[2].substring(0, entryLine[2].indexOf("%")));
          }

          this.setCurrentRank(this.getCurrentRank() + 1);

        }

      }

    }

  }

  /**
  * Method that displays the relevant information for the mod in the console
  */

  public void printMod() {

    // Variable for traversing the ModValues array
    int index = 0;

    System.out.print("\nMod: " + this.getName());
    System.out.print("\nType: " + this.getType());
    System.out.print("\nPolarity: " + this.getPolarity());
    System.out.print("\nEffect: " + this.getEffect1());

    if (effect2 != null) {
      System.out.print("\nEffect: " + this.getEffect2());
    }

    System.out.print("\nRanks: " + this.getRanks());

    if (conditional == true) {
      System.out.print("\nConditional");
    }

    System.out.println("\nCost & Effect:");

    if (effect2 != null) {

      while (index < this.getModValues()[0].length) {
        System.out.println(this.getModValues()[0][index] + " | " +
        this.getModValues()[1][index] + " | " + this.getModValues()[2][index]);
        index++;
      }

    } else {

      while (index < modValues[0].length) {
        System.out.println(this.getModValues()[0][index] + " | " +
        this.getModValues()[1][index]);
        index++;
      }

    }

  }

  /**
  * Setters & Getters below
  */

  public void setName(String n) {name = n;}

  public void setType(String t) {type = t;}

  public void setPolarity(String p) {polarity = p;}

  public void setEffect1(String e1) {effect1 = e1;}

  public void setEffect2(String e2) {effect2 = e2;}

  public void setRanks(int r) {ranks = r;}

  public void setCurrentRank(int cr) {currentRank = cr;}

  public void setModValues(double[][] mv) {modValues = mv;}

  public String getName() {return name;}

  public String getType() {return type;}

  public String getPolarity() {return polarity;}

  public String getEffect1() {return effect1;}

  public String getEffect2() {return effect2;}

  public int getRanks() {return ranks;}

  public int getCurrentRank() {return currentRank;}

  public double[][] getModValues() {return modValues;}

}

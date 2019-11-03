
import java.io.*;
import java.util.Scanner;

public class Mod {

  private String name;
  private String type;
  private String polarity;
  private String benefit;

  private int ranks;

  private int[][] modValues;

  public Mod() {

    //

  }

  public Mod(String fileName) {

    Scanner modScanner = new Scanner(System.in);

    String entryLine;
    String entryContent;
    String entryType;

    int currentRank = 0;

    try {
      File modFile = new File(fileName);
      modScanner = new Scanner(modFile);
    } catch (FileNotFoundException noFile) {
      //
    }

    while (modScanner.hasNext()) {

      entryLine = modScanner.nextLine();
      entryType = entryLine.substring(0, entryLine.indexOf(",")).toUpperCase();

      if (entryType.length() > 2) {

        entryContent = entryLine.substring(entryLine.indexOf(",") + 1, entryLine.length()).toUpperCase();

        switch (entryType) {

          case ("NAME"):
          this.setName(entryContent);
          break;

          case ("TYPE"):
          this.setType(entryContent);
          break;

          case ("POLARITY"):
          this.setPolarity(entryContent);
          break;

          case ("BENEFIT"):
          this.setBenefit(entryContent);
          break;

          case ("RANKS"):
          this.setRanks(Integer.parseInt(entryContent));
          modValues = new int[2][this.getRanks()];
          break;

        }

      } else if (!entryLine.equals(",")) {

        entryContent = entryLine.substring(entryLine.indexOf(",") + 1, entryLine.indexOf("%")).toUpperCase();

        modValues[0][currentRank] = Integer.parseInt(entryType);
        modValues[1][currentRank] = Integer.parseInt(entryContent);

        currentRank++;

      }

    }

  }

  public void printMod() {

    int index = 0;

    while (index < modValues[0].length) {
      System.out.println(modValues[0][index] + " | " + modValues[1][index]);
      index++;
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

  public void setBenefit(String b) {
    benefit = b;
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

  public String getBenefit() {
    return benefit;
  }

  public int getRanks() {
    return ranks;
  }

  public int[][] getModValues() {
    return modValues;
  }

}

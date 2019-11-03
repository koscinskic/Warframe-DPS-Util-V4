
import java.io.*;
import java.util.Scanner;

public class FileManager {

  public static File[] retrieveMods(String folderName) {

    File modFolder = new File(folderName);
    File[] mods = modFolder.listFiles();

    return mods;

  }

}

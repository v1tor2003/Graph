package files;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.File;

public abstract class FileManipulation {
  private final static String PATTERN = "adj\\d+\\.txt";
  private final static String ADJACENT_SET_NAME = "adjSet.txt";
  private final static String ANSWER_NAME = "answer.txt";
  private final static String FILES_DESTINATION = "files\\";

  @SuppressWarnings("unchecked")
  public static <T> T[][] turnInto2DArray(List<T> uniArray){
    int n = (int)((float) Math.sqrt(uniArray.size()));
    if(n * n != uniArray.size()) throw new IllegalArgumentException();
    int k = 0;
    T[][] twoDArray = (T[][]) new Object[n][n]; 

    for(int i = 0; i < n; i++)
      for(int j = 0; j < n; j++)
        twoDArray[i][j] = uniArray.get(k++);
    
    uniArray = null;
    return twoDArray;
  }

  public static <T> boolean isInputValid(List<T> elements,int n){
    return elements.size() == (n * n);
  }

  @SuppressWarnings("unchecked")
  public static <T> List<T> readFile(String path){
    try {
      File file = new File(path);
      Scanner scanner = new Scanner(file);
      List<T> elements = new ArrayList<T>();
      int n = scanner.nextInt();

      while (scanner.hasNext()) {
        String rawDimension = scanner.next();
        T element = (T) parse(rawDimension, Integer.class);
        elements.add(element);
      }

      scanner.close();
      if (!isInputValid(elements, n))
        throw new IllegalArgumentException("The quantity of values expected does not match the file.");
      return elements;
    } catch (FileNotFoundException e) {
      System.out.printf("An error occurred trying to read %s.\n", path);
      e.printStackTrace();
    }
    return new ArrayList<T>();
  }

  public static void createAdjacentFilesSet(int[] matricesDegrees, int matriceElementBoundary){
    for(int degree : matricesDegrees)
      generateRandomAdjacentFile(degree);
    List<File> fileSet = getAdjacentFilesSet(FILES_DESTINATION + ADJACENT_SET_NAME);
    int i = 0;
    for(File file : fileSet)
      createRandomData(FILES_DESTINATION + file.toString(), matricesDegrees[i++], matriceElementBoundary);
  }

  public static List<File> readFromAdjacentFilesSet(String adjSetPath){
    List<File> fileSet = getAdjacentFilesSet(adjSetPath);
    return fileSet;
  }

  public static void saveAnswer(String graphsAsStr){
    try (FileWriter writer = new FileWriter(FILES_DESTINATION + ANSWER_NAME)) {
      writer.write(graphsAsStr);
      System.out.println("File write operation completed.");
    } catch (IOException e) {
      System.out.println("An error occurred while writing the file: " + e.getMessage());
    }
  }

  private static void generateRandomAdjacentFile(int n){
    try (FileWriter adjacentSetWriter = new FileWriter(FILES_DESTINATION + ADJACENT_SET_NAME, true)) {
      String fileName = new String("adj" + n +".txt");
      adjacentSetWriter.write(fileName+ "\n");
      System.out.println("File write operation completed.");
    } catch (IOException e) {
      System.out.println("An error occurred while writing the file: " + e.getMessage());
    }
  }

  private static void createRandomData(String fileName, int n, int boundary){
    try (FileWriter writer = new FileWriter(fileName)) {
      String matriceData = generateAdjacentMatrice(n, boundary);
      writer.write(n+"\n"+matriceData);
      System.out.println("File write operation completed.");
    } catch (IOException e) {
      System.out.println("An error QUEBROU AQ occurred while writing the file: " + e.getMessage());
    }
  }

  private static String generateAdjacentMatrice(int n, int randomBoundary){
    String output = "";
    Random random = new Random();

    for (int i = 0; i < n; i++){
      for (int j = 0; j < n; j++){
        if(i == j)
          output += "0 ";
        else
          output += random.nextInt(randomBoundary)+" ";
      }
      output += "\n";
    }
        
    return output;
  }

  private static List<File> getAdjacentFilesSet(String adjacentFilesSetPath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(adjacentFilesSetPath))) {
      String line = "";
      List<File> files = new ArrayList<File>();
      while ((line = reader.readLine()) != null) {
        if(adjacentFileNamePartten(line.toString()))
          files.add(new File(line));
      }
      return files;
    } catch (IOException e) {
      System.out.println("An error occurred while reading the file: " + e.getMessage());
    }
    return new ArrayList<>();
  }

  private static boolean adjacentFileNamePartten(String fileName){
    Pattern regexPattern = Pattern.compile(PATTERN);
    Matcher matcher = regexPattern.matcher(fileName);
    return matcher.matches();
  }

  private static <T> T parse(String rawValue, Class<T> clazz) {
    if (rawValue == null) throw new IllegalArgumentException("Invalid input: null");
    if (Integer.class.equals(clazz)) {
      try {
        Object parsedValue = Integer.valueOf(rawValue);
        return clazz.cast(parsedValue);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid integer value: " + rawValue);
      }
    }
    throw new UnsupportedOperationException("Parsing for type T not implemented");
  }

}

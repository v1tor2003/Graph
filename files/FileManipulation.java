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
  private final static String PATTERN = "adj\\d+\\.txt"; // Regular expression pattern for matching adjacent file names.
  private final static String ADJACENT_SET_NAME = "adjSet.txt"; // The name of the adjacent set file.
  private final static String ANSWER_NAME = "answer.txt"; // The name of the answer file
  private final static String FILES_DESTINATION = "files\\"; // The path of destination to save the files

  /**
    Converts a one-dimensional list into a two-dimensional array.

    Desc: This method takes a one-dimensional list (`uniArray`) and converts it into a two-dimensional array (`twoDArray`). 
    The size of the input list is expected to be a perfect square, as it represents a square matrix.
    The method calculates the size `n` of one side of the square matrix by taking the square root of the list size. 
    If the size is not a perfect square, an `IllegalArgumentException` is thrown.
    A counter `k` is used to iterate over the elements of the input list, and the values are assigned to the corresponding positions in the two-dimensional array.
    Finally, the input list is set to `null` to release the reference and return the converted two-dimensional array.
    By calling this method and passing a one-dimensional list, you can obtain a two-dimensional array representing a square matrix with the values from the list.

    @param uniArray The one-dimensional list to be converted.
    @return The converted two-dimensional array.
    @throws IllegalArgumentException if the size of the input list is not a perfect square.
  */

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

  /**
    Checks if the input list is valid for creating an n x n square matrix.

    Desc: This method takes an input list of elements (`elements`) and a desired size of a square matrix (`n`). 
    It checks if the size of the input list is equal to n * n, which is the expected size for creating an n x n square matrix.
    The method returns true if the size of the input list is valid, indicating that it contains enough elements to create the desired square matrix. Otherwise, it returns false.
    You can use this method to validate the input list before performing any operations that require a specific size of a square matrix.

    @param elements The input list of elements.
    @param n The desired size of the square matrix.
    @return true if the size of the input list is equal to n * n, false otherwise.
  */

  public static <T> boolean isInputValid(List<T> elements,int n){
    return elements.size() == (n * n);
  }

  /**
    Reads a file containing elements and returns them as a list.
    
    Desc: This method reads a file located at the specified `path` and returns the elements from the file as a list. 
    The file is expected to have the following format:
      - The first line should contain an integer `n`, representing the size of the square matrix.
      - The subsequent lines should contain the elements of the matrix.
    The method reads the file, parses the elements, and adds them to the `elements` list. 
    It then checks if the quantity of values read matches the expected size `n * n` using the `isInputValid` method. 
    If the quantities do not match, an `IllegalArgumentException` is thrown.

    Note: This implementation assumes that the file contains valid input and follows the specified format. Error handling for invalid file formats or data inconsistencies is not included in this code.

    @param path The path to the file.
    @return A list containing the elements read from the file.
    @throws IllegalArgumentException If the quantity of values read does not match the expected size.
  */

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

  /**
    Creates a set of adjacent files based on the specified matrix degrees and element boundary.

    Desc: This method creates a set of adjacent files based on the specified `matricesDegrees` and `matriceElementBoundary` parameters. 
    It generates random adjacent files by calling the `generateRandomAdjacentFile` method for each degree in the `matricesDegrees` array. 
    Then, it retrieves the set of generated files using the `getAdjacentFilesSet` method. 
    Finally, it iterates over the file set and creates random data for each file by calling the `createRandomData` method with the appropriate parameters.

    Note: The implementation of the `generateRandomAdjacentFile`, `getAdjacentFilesSet`, and `createRandomData` methods is not provided in this code snippet, as they are external methods and their implementations are specific.

    @param matricesDegrees An array containing the degrees of the matrices.
    @param matriceElementBoundary The boundary value for the elements of the matrices.
  */

  public static void createAdjacentFilesSet(int[] matricesDegrees, int matriceElementBoundary){
    for(int degree : matricesDegrees)
      generateRandomAdjacentFile(degree);

    List<File> fileSet = getAdjacentFilesSet(FILES_DESTINATION + ADJACENT_SET_NAME);
    int i = 0;
    for(File file : fileSet)
      if(i < matricesDegrees.length)
        createRandomData(FILES_DESTINATION + file.toString(), matricesDegrees[i++], matriceElementBoundary);
  }

  /**
    Reads the list of files from the specified adjacent files set.

    Desc: This method reads the list of files from the specified adjacent files set. 
    It calls the `getAdjacentFilesSet` method, passing the `adjSetPath` as the parameter, to retrieve the list of files in the adjacent files set. 
    The method then returns the obtained file set.

    @param adjSetPath The path of the adjacent files set.
    @return The list of files in the adjacent files set.
  */

  public static List<File> readFromAdjacentFilesSet(String adjSetPath){
    List<File> fileSet = getAdjacentFilesSet(adjSetPath);
    return fileSet;
  }

  /**
    Saves the provided graph string representation to a file.

    Desc: This method saves the provided graph string representation to a file. 
    It creates a `FileWriter` and specifies the file path to be `FILES_DESTINATION + ANSWER_NAME`. 
    It then writes the `graphsAsStr` to the file using the `write` method of the `FileWriter`. 
    If an error occurs during the file writing process, an error message is printed.

    @param graphsAsStr The graph string representation to be saved.
  */

  public static void saveAnswer(String graphsAsStr){
    try (FileWriter writer = new FileWriter(FILES_DESTINATION + ANSWER_NAME)) {
      writer.write(graphsAsStr);
    } catch (IOException e) {
      System.out.println("An error occurred while writing the file: " + e.getMessage());
    }
  }

  /**
    Generates a random adjacent file with the specified number of vertices.

    Desc: This method generates a random adjacent file with the specified number of vertices. 
    It creates a `FileWriter` with the file path `FILES_DESTINATION + ADJACENT_SET_NAME` and sets the `append` parameter to `true` so that the file is opened in append mode. 
    It generates a file name based on the number of vertices and writes the file name followed by a newline character to the `adjacentSetWriter`. 
    If an error occurs during the file writing process, an error message is printed.

    @param n The number of vertices for the adjacent file.
  */

  private static void generateRandomAdjacentFile(int n){
    try (FileWriter adjacentSetWriter = new FileWriter(FILES_DESTINATION + ADJACENT_SET_NAME, true)) {
      String fileName = new String("adj" + n +".txt");
      adjacentSetWriter.write(fileName+ "\n");
    } catch (IOException e) {
      System.out.println("An error occurred while writing the file: " + e.getMessage());
    }
  }

  /**
    Creates random data and writes it to the specified file.

    Desc: This method creates random data for an adjacency matrix with the specified number of vertices and writes it to the specified file. 
    It creates a `FileWriter` with the given file name and writes the number of vertices followed by a newline character, and then writes the generated adjacency matrix data. 
    If an error occurs during the file writing process, an error message is printed.

    @param fileName The name of the file to write the data to.
    @param n The number of vertices for the adjacency matrix.
    @param boundary The boundary value for generating random weights in the adjacency matrix.
  */

  private static void createRandomData(String fileName, int n, int boundary){
    try (FileWriter writer = new FileWriter(fileName)) {
      String matriceData = generateAdjacentMatrice(n, boundary);
      writer.write(n+"\n"+matriceData);
    } catch (IOException e) {
      System.out.println("An error QUEBROU AQ occurred while writing the file: " + e.getMessage());
    }
  }

  /**
    Generates a random adjacency matrix as a string.

    Desc: This method generates a random adjacency matrix as a string. 
    It takes the number of vertices (`n`) and the random boundary (`randomBoundary`) as parameters. 
    It uses a `StringBuilder` to construct the output string. 
    It iterates over the rows and columns of the matrix and generates random weights within the specified boundary. 
    The diagonal elements are set to 0 to represent the absence of self-loops. 
    The generated matrix is returned as a string.

    @param n The number of vertices for the adjacency matrix.
    @param randomBoundary The boundary value for generating random weights in the adjacency matrix.
    @return The generated adjacency matrix as a string.
  */

  private static String generateAdjacentMatrice(int n, int randomBoundary){
    String output = "";
    Random random = new Random();

    for (int i = 0; i < n; i++){
      for (int j = 0; j < n; j++){
        if(i == j)
          output += "0 ";
        else
          output += (random.nextInt(randomBoundary)+1)+" ";
      }
      output += "\n";
    }
        
    return output;
  }

  /**
    Retrieves the list of adjacent files from the specified file set.

    Desc: This method reads the file set from the specified `adjacentFilesSetPath` and retrieves the list of adjacent files. 
    It uses a `BufferedReader` to read the file line by line. 
    For each line, it checks if the file name matches the desired pattern using the `adjacentFileNamePattern` method. 
    If it matches, it adds the file to the list. Finally, it returns the list of adjacent files. 
    
    @param adjacentFilesSetPath The path to the file set containing the adjacent file names.
    @return The list of adjacent files.
  */

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

  /**
    Checks if the given file name matches the pattern for an adjacent file.
 
    @param fileName The file name to check.
    @return True if the file name matches the pattern, false otherwise.
  */

  private static boolean adjacentFileNamePartten(String fileName){
    Pattern regexPattern = Pattern.compile(PATTERN);
    Matcher matcher = regexPattern.matcher(fileName);
    return matcher.matches();
  }

  /**
    Parses the raw value into the specified class type.

    Desc: This method parses a raw value into the specified class type. 
    It supports parsing for Integer values. 
    If the raw value is null or cannot be parsed, an IllegalArgumentException is thrown. 
    If parsing for the specified class type is not implemented, an UnsupportedOperationException is thrown.

    @param rawValue The raw value to be parsed.
    @param clazz The class type to parse the value into.
    @return The parsed value of the specified class type.
    @throws IllegalArgumentException If the raw value is null or cannot be parsed.
    @throws UnsupportedOperationException If parsing for the specified class type is not implemented.
  */

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

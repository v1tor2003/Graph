package files;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public abstract class FileManipulation {

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

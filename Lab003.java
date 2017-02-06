import java.io.*;
import java.util.Scanner;

public class Lab003{
  
  private final boolean DEBUG = true;
  
  public static void main(String args[]){
    String[] keywords = {""};
    try {
      keywords = keywordsInput();
      if ( DEBUG ) {
        
      }
    } catch ( KeyWordInputException e ) {
      System.out.println("Keyword input failed"); 
    }
    
    String[] output;
    for ( String iterator : args ) {
      try {
        output = parse(iterator, keywords);
        if ( output.length > 1 ) {
          System.out.print("Unable to parse " + iterator + "possible matches are: "); 
          for ( String matches : output ) {
            System.out.print(matches + " ");
          }
          System.out.println("");
        } else {
          System.out.println("Parsed " + iterator + " to match " + output[0]);
        } 
      } catch ( KeyWordInputException e ) {
        continue; 
      }
    }
  }
  
  public static String[] parse(String input, String[] keywords) throws KeyWordInputException{
    boolean hasMatch = false;
    String processingInput = "";
    String result[];
    input = input.toLowerCase();
    
    for ( String keyword : keywords ) {
      if (input.length() != 0 && input.substring( 0 , Math.min(input.length(), keyword.length() ) ).equalsIgnoreCase(keyword.substring( 0 , Math.min(input.length(), keyword.length() ) ) ) ){
        hasMatch = true;
        processingInput += keyword + " ";
      }
    }
    if ( !hasMatch ){
      System.out.println("Failed to parse " + input );
      throw new KeyWordInputException();
    }
    try { 
      result = processingInput.split(" +"); 
    } catch ( Exception e ) {
      result = new String[1];
      result[0] = processingInput.substring(0, processingInput.length() - 1);
      return result;
    }
    return result;
  }
  
  public static String[] keywordsInput() throws KeyWordInputException{
    String result[];
    String processingInput = "";
    try{
      Scanner input = new Scanner(new File("keywords.txt"));
      
      while (input.hasNext()){
        processingInput += input.nextLine() + " ";
      }
      
      result = processingInput.split(" +");
      return result;
    } catch ( IOException e ) {
      System.out.println("File Exception, problem with keywords.txt, does it exist?");
    }
    throw new KeyWordInputException();
    
  }
  
}

class KeyWordInputException extends Exception{
  public KeyWordInputException(){
    super();
  }
}
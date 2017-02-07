import java.io.*;
import java.util.Scanner;


/**
 * This program simply takes input from the coandline arguments
 *  and parses it to see if it matches any of the keywords in the
 *  key words.txt file.
 *
 * @author  Jason Ivey
 * @version 1.0
 * @since   2017-02-06
 * 
 */


public class Lab003{
  
  private static final boolean DEBUG = false; // Boolean var for dev purposes 
  
  
  
  /**
   * Main method to accept args as input and call helper methods. 
   * @param   args[] String arguments from the command line
   * 
   */
  
  public static void main(String args[]){
    String[] keywords = {""}; //has to init keywords.
    try {
      keywords = keywordsInput(); //gets keywords
      
      
      //Debug statement 
      if ( DEBUG ) {
        for ( String iterator : keywords ) {
          System.out.print(iterator + " "); 
        }
        System.out.println(" are the Keywords. " );
        
      }
    } catch ( KeyWordInputException e ) {
      System.out.println("Keyword input failed"); 
    }
    
    String[] output; //Declartion outside loop for this scope
    for ( String iterator : args ) {
      
      try {
        output = parse(iterator, keywords); //calling main parse method, may throw an exception
        
        //Where we have more than one match
        if ( output.length > 1 ) {
          System.out.print("Unable to parse " + iterator + " possible matches are: "); 
          for ( String matches : output ) {
            
            //debug statement
            if ( DEBUG ) System.out.println("output = " + matches);
            
            System.out.print(iterator.toUpperCase() + "[" + matches.replace(iterator.toUpperCase() , "")+ "] ");
          }
          System.out.println("");
        } else {          
          System.out.println("Parsed " + iterator + " to match " + output[0]);
        } 
        
      } catch ( KeyWordInputException e ) {
        
        //debug statement
        if ( DEBUG ) System.out.println("KeyWordInputException e thrown ");
        
        continue; //No valid input continue
      }
    }
  }
  
  
  /**
   * Parse method to check for keywords in input
   * @param     input String input from the command line
   * @exception KeyWordInputException
   * @param     keywords[] String[] of the keywords to check for
   */
  
  public static String[] parse(String input, String[] keywords) throws KeyWordInputException{
    
    if ( DEBUG ){  //Debug statement
      System.out.println("parse called with input = " + input + " and keywords " );
      for ( String iterator : keywords ) {
        System.out.print(iterator + " " ); 
      }
      System.out.println("");
    }
    
    boolean hasMatch = false; //Checks if this input triggered a match
    String processingInput = ""; //Intermediate string
    String result[]; //file result
    input = input.toLowerCase(); 
    
    for ( String keyword : keywords ) {
      if (input.length() != 0 && input.substring( 0 , Math.min(input.length(), keyword.length() ) ).equalsIgnoreCase(keyword.substring( 0 , Math.min(input.length(), keyword.length() ) )  ) ) //main comparison statement
      {
        hasMatch = true;
        processingInput += keyword + " ";
      }
    }
    if ( !hasMatch ){ //If this input has no match then declare that
      System.out.println("Failed to parse " + input );
      throw new KeyWordInputException();
    }
    try { 
      result = processingInput.split(" +"); //Spliting on regular expression space
    } catch ( Exception e ) {
      result = new String[1];
      result[0] = processingInput.substring(0, processingInput.length() - 1);
      return result;
    }
    return result;
  }
  
  
  /**
   * Gets input  from keywords.txt file
   * @exception  KeyWordInputException
   * @return     returns string[] of keywords from file. 
   */
  
  public static String[] keywordsInput() throws KeyWordInputException{
    //Debug statement
    if ( DEBUG ) {
      System.out.println("keywordsInput called");
    }
    
    String result[];
    String processingInput = "";
    
    try{
      Scanner input = new Scanner(new File("keywords.txt"));
      
      while (input.hasNext()){
        processingInput += input.nextLine() + " ";
      }
      
      result = processingInput.split(" +");
      return result;
    } catch ( IOException e ) { //Needed inorder to do file.io
      System.out.println("File Exception, problem with keywords.txt, does it exist?");
    }
    throw new KeyWordInputException();
    
  }
  
}


// Custom exception specific to this lab
class KeyWordInputException extends Exception{
  public KeyWordInputException(){
    super();
  }
}
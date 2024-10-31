package com.ReasoningTechnology.Mosaic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class Util{

  // Linear search with a predicate
  public static <T> T find( T[] elements ,Predicate<T> predicate ){
    for( T element : elements ){
      if( predicate.test( element )) return element; // Return the first match
    }
    return null; // Return null if no element satisfies the predicate
  }

  // True when it does a search and finds a true value; otherwise false.
  public static Boolean exists( Object[] elements ){
    return elements.length > 0 && find( elements ,element -> (element instanceof Boolean) && (Boolean) element ) != null;
  }

  // True when it does a search and does not find a false value; otherwise false.
  public static Boolean all( Object[] elements ){
    return elements.length > 0 && find( elements ,element -> !(element instanceof Boolean) || !(Boolean) element ) == null;
  }

  public static void all_set_false( Boolean[] condition_list ){
    int i = 0;
    while(i < condition_list.length){
      condition_list[i] = false;
      i++;
    }
  }

  public static void all_set_true( Boolean[] condition_list ){
    int i = 0;
    while(i < condition_list.length){
      condition_list[i] = true;
      i++;
    }
  }

  public static String iso_utc_time(){
    return Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
  }

  // used to report if a test completed with data still on an output streams
  public static void log_output(String test_name ,String stream ,String output_data){
    try(FileWriter log_writer = new FileWriter("test_log.txt" ,true)){  // Append mode
      log_writer.write("\n" + iso_utc_time() + " -----------------------------------------------------------\n");
      log_writer.write("Test: " + test_name + "\n");
      log_writer.write("Stream: " + stream + "\n");
      log_writer.write("Output:\n" + output_data + "\n");
    } catch(IOException e) {
      System.err.println("Error writing to log for test: " + test_name + ", stream: " + stream);
      e.printStackTrace(System.err);
    }
  }

  // used to log a general message about a test
  public static void log_message(String test_name ,String message){
    try(FileWriter log_writer = new FileWriter("test_log.txt" ,true)){  // Append mode
      log_writer.write("\n" + iso_utc_time() + " -----------------------------------------------------------\n");
      log_writer.write("Test: " + test_name + "\n");
      log_writer.write("Message:\n" + message + "\n");
    } catch(IOException e){
      System.err.println
        (
         "Error writing message \"" 
         + message 
         + "\" to log for test \'"
         + test_name
         + "\'"
         );
      e.printStackTrace(System.err);
    }
  }

}

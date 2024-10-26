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

public class Util{

  // typically used to gather results before a return
  public static boolean all(boolean[] conditions){
    for( boolean condition : conditions ){
      if( !condition ){
        return false;
      }
    }
    return true;
  }
  public static void all_set_false(boolean[] conditions){
    for( boolean condition : conditions ) condition = false;
  }
  public static void all_set_true(boolean[] conditions){
    for( boolean condition : conditions ) condition = true;
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

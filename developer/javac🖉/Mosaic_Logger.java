package com.ReasoningTechnology.Mosaic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mosaic_Logger{

  private static final Logger LOGGER = LoggerFactory.getLogger(Mosaic_Logger.class);

  // Formats and logs an output related to a specific test
  public static void output(String test_name, String stream, String output_data){
    String timestamp = Mosaic_Time.iso_UTC_time();
    String formatted_log = String.format(
      "\n%s -----------------------------------------------------------\n" +
      "Test: %s\n" +
      "Stream: %s\n" +
      "Output:\n%s\n",
      timestamp, test_name, stream, output_data
    );

    LOGGER.info(formatted_log);
  }

  // Logs a general message for a test
  public static void message(String test_name, String message){
    String timestamp = Mosaic_Time.iso_UTC_time();
    String formatted_log = String.format(
      "\n%s -----------------------------------------------------------\n" +
      "Test: %s\n" +
      "Message:\n%s\n",
      timestamp, test_name, message
    );

    LOGGER.info(formatted_log);
  }

  public static void error(String test_name, String message, Throwable error){
    String timestamp = Mosaic_Time.iso_UTC_time();
    String formatted_log = String.format(
    "\n%s -----------------------------------------------------------\n" +
    "Test: %s\n" +
    "Message:\n%s\n" +
    "Error:\n",
    timestamp, test_name, message
    );

    // Pass the Throwable 'error' as the last argument to LOGGER.error.
    // This automatically logs the stack trace at the ERROR level.
    LOGGER.error(formatted_log, error);
  }

}

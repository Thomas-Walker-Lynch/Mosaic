package com.ReasoningTechnology.Mosaic;
/*
  The primary purpose of this class is to redirect I/O to buffers,
  sot that a test can check the I/O behavior of a function under test.
*/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileDescriptor;
import java.io.PrintStream;
import java.io.InputStream;

public class IO{

  private PrintStream original_out;
  private PrintStream original_err;
  private InputStream original_in;

  private ByteArrayOutputStream out_content;
  private ByteArrayOutputStream err_content;
  private ByteArrayInputStream in_content;
  private boolean streams_foobar = false;
  private boolean uninitialized = true;


  // IO currently has no constructors defined, uses default


  // Redirects IO streams, logs and handles errors if redirection fails.
  //
  // Most tests do not do I/O checks, so rather than throwing an error
  // it will set the streams_foobar flag, then throw an error if the I/O
  // functions are used.
  //
  // This is the only method that can set the streams_foobar flag.
  public boolean redirect(){

    try{
      original_out = System.out;
      original_err = System.err;
      original_in = System.in;

      out_content = new ByteArrayOutputStream();
      err_content = new ByteArrayOutputStream();
      in_content = new ByteArrayInputStream(new byte[0]);

      System.setOut( new PrintStream(out_content) );
      System.setErr( new PrintStream(err_content) );
      System.setIn(in_content);

      uninitialized = false;
      return true;

    } catch(Exception e){
      restore_hard();
      streams_foobar = true;
      return false;

    }
  }

  // Hard restore of the streams, resetting to system defaults
  public void restore_hard(){
    System.setOut(new PrintStream( new FileOutputStream(FileDescriptor.out)) );
    System.setErr(new PrintStream( new FileOutputStream(FileDescriptor.err))) ;
    System.setIn(new FileInputStream(FileDescriptor.in));
  }

  // Restores original IO streams, ensuring foobar and uninitialized states are checked.
  // If anything goes wrong reverse to restore_hard.
  public void restore(){
    if(uninitialized || streams_foobar){
      restore_hard();
      return;
    }
    try{
      System.setOut(original_out);
      System.setErr(original_err);
      System.setIn(original_in);
    } catch(Throwable e){
      restore_hard();
    }
  }

  // Clears output, error, and input buffers, checks for foobar state only.
  public void clear_buffers(){
    if(streams_foobar){
      throw new IllegalStateException("Cannot clear buffers: IO object is in foobar state.");
    }
    out_content.reset();
    err_content.reset();
    in_content = new ByteArrayInputStream( new byte[0] ); // Reset to EOF
    System.setIn(in_content);
  }

  public boolean has_out_content(){
    if(streams_foobar){
      throw new IllegalStateException
        (
         "Cannot access stdout content: IO object is in foobar state."
         );
    }
    return out_content.size() > 0;
  }
  public String get_out_content(){
    if(streams_foobar){
      throw new IllegalStateException
        (
         "Cannot access stdout content: IO object is in foobar state."
         );
    }
    return out_content.toString();
  }

  public boolean has_err_content(){
    if(streams_foobar){
      throw new IllegalStateException
        (
         "Cannot access stderr content: IO object is in foobar state."
         );
    }
    return err_content.size() > 0;
  }
  public String get_err_content(){
    if(streams_foobar){
      throw new IllegalStateException
        (
         "Cannot access stderr content: IO object is in foobar state."
         );
    }
    return err_content.toString();
  }

  // Pushes input string onto stdin, checks foobar state only.
  public void push_input(String input_data){
    if(streams_foobar){
      throw new IllegalStateException("Cannot push input: IO object is in foobar state.");
    }
    in_content = new ByteArrayInputStream( input_data.getBytes() );
    System.setIn(in_content);
  }
}

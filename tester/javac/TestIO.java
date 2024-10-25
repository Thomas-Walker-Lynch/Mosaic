package com.ReasoningTechnology.Mosaic.Test;
import com.ReasoningTechnology.Mosaic.IO;
import com.ReasoningTechnology.Mosaic.Util;

public class TestIO{

  public static int fut(){
    try{
      // Echo some characters from stdin to stdout
      System.out.print((char) System.in.read());
      System.out.print((char) System.in.read());

      // Echo some more characters from stdin to stderr
      System.err.print((char) System.in.read());
      System.err.print((char) System.in.read());

      // Count remaining characters until EOF
      int count = 0;
      while( System.in.read() != -1 ){
        count++;
      }

      return count;
    } catch( Exception e ){
      e.printStackTrace();
      return -1; // Error case
    }
  }

  public static int run(){
    IO io = new IO();
    String input_data = "abcdefg"; // Sample input
    boolean[] condition = new boolean[3];

    // Redirect IO streams
    io.redirect_io(input_data);

    // Execute function under test
    int result = fut();

    // Check stdout content
    String stdout = io.get_out_content().toString();
    condition[0] = stdout.equals("ab");

    // Check stderr content
    String stderr = io.get_err_content().toString();
    condition[1] = stderr.equals("cd");

    // Check returned character count (3 remaining characters: 'e' ,'f' ,'g')
    condition[2] = result == 3;

    // Restore original IO streams
    io.restore_io();

    if( !Util.all(condition) ){
      System.out.println("TestIO failed");
      return 1;
    }
    System.out.println("TestIO passed");
    return 0;
  }

  // Main function to provide a shell interface for running tests
  public static void main(String[] args){
    int return_code = run();
    System.exit(return_code); 
    return;
  }

}

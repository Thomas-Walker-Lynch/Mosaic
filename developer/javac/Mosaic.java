package com.ReasoningTechnology.Mosaic;
import com.ReasoningTechnology.Mosaic.Util;

/*
Mosaic currently does not have shell commands.

*/


public class Mosaic{

  public static boolean test_is_true(){
    return true;
  }
  
  public static int run(){
    System.out.println("Mosic currently does not have a shell user interface.");
    return 0;
  }

  // Main function to provide a shell interface for running tests
  public static int main(String[] args){
    // currently accepts no arguments or options
    return run();
  }
}    

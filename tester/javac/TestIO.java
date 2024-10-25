package com.ReasoningTechnology.Mosaic.Test;
import com.ReasoningTechnology.Mosaic.Util;

/*
Testing the IO class.

*/


public class TestIO{

  public static boolean test_is_true(){
    return true;
  }
  
  public static int run(){
    boolean[] condition = new boolean[5];
    conditionn[0] = test_is_true();

    int i = 0;
    if( !Util.all(condition) ){
      System.out.println("TestIO failed");
      return 1;
    }
    return 0;
  }

  // Main function to provide a shell interface for running tests
  public static int main(String[] args){
    // tests currently accepts no arguments or options
    return run(); // Calls the method to run all tests
  }
}    

package com.ReasoningTechnology.Mosaic;
import com.ReasoningTechnology.Mosaic.Util;

/*
Mosaic currently does not have shell commands.

*/


public class Mosaic{

  public static Boolean test_is_true(){
    return true;
  }
  
  public static int run(){
    System.out.println("Main function placeholder.  Currently Mosaic is used by extending the TestBench class.");
    return 0;
  }

  public static void main(String[] args){
    int return_code = run();
    System.exit(return_code); 
    return;
  }

}    

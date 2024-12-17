package com.ReasoningTechnology.Mosaic;

/*
  These are used for testing that Mosaic can be used for white box
  testing.  Mosaic tests for Mosaic itself access each of these as
  part of regression. Users are welcome to also check accessing these
  when debugging any access problems that might arise.
*/

// Public class with public and private methods
public class Mosaic_TestClasses_1 {

  private int i;

  public Mosaic_TestClasses_1(){
    i = 0;
  }

  public Mosaic_TestClasses_1(int a){
    i = a;
  }

  public Mosaic_TestClasses_1(int a ,int b){
    i = a + b;
  }

  public int get_i() {
    return i;
  }

}

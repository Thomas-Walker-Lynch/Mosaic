package com.ReasoningTechnology.Mosaic;

/*
  These are used for testing that Mosaic can be used for white box
  testing.  Mosaic tests for Mosaic itself access each of these as
  part of regression. Users are welcome to also check accessing these
  when debugging any access problems that might arise.
*/

// Public class with public and private methods
public class Mosaic_TestClasses_0 {
  public boolean a_public_method_1() {
    return true;
  }

  private boolean a_private_method_2() {
    return true;
  }

  public class PublicClass {
    public boolean a_public_method_3() {
      return true;
    }

    private boolean a_private_method_4() {
      return true;
    }
  }

  private class PrivateClass {
    public boolean a_public_method_5() {
      return true;
    }

    private boolean a_private_method_6() {
      return true;
    }
  }
}

// Default (package-private) class with public and private methods
class DefaultClass {
  public boolean a_public_method_7() {
    return true;
  }

  private boolean a_private_method_8() {
    return true;
  }
}

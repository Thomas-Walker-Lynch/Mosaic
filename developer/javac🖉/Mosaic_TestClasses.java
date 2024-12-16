package com.ReasoningTechnology.Mosaic;

/*
  These are used for testing that Mosaic can be used for white box
  testing.  Mosaic tests for Mosaic itself access each of these as
  part of regression. Users are welcome to also check accessing these
  when debugging any access problems that might arise.
*/

// Public class with public and private methods
public class Mosaic_TestClasses {
  public boolean publicMethod() {
    return true;
  }

  private boolean privateMethod() {
    return true;
  }

  public class PublicClass {
    public boolean publicMethod() {
      return true;
    }

    private boolean privateMethod() {
      return true;
    }
  }

  private class PrivateClass {
    public boolean publicMethod() {
      return true;
    }

    private boolean privateMethod() {
      return true;
    }
  }
}

// Default (package-private) class with public and private methods
class DefaultClass {
  public boolean publicMethod() {
    return true;
  }

  private boolean privateMethod() {
    return true;
  }
}

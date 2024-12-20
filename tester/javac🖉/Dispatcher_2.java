import com.ReasoningTechnology.Mosaic.Mosaic_Dispatcher;

import tester.TestClasses_2;

public class Dispatcher_2{

  private static Mosaic_Dispatcher dispatcher;

  static{
    dispatcher = new Mosaic_Dispatcher(TestClasses_2.class);
  }

  public static boolean test_publicStaticField(){
    try{
      Integer value = dispatcher.read(Integer.class, "i_200");
      return value != null && value == 200; // Replace 200 with initialized value
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_privateStaticField(){
    try{
      String value = dispatcher.read(String.class, "s_201");
      return value != null && value.equals("Test"); // Replace "Test" with initialized value
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_publicInstanceField(){
    try{
      TestClasses_2 instance = dispatcher.make();
      Integer value = dispatcher.read(instance, "i_202");
      return value != null && value == 202; // Replace 202 with initialized value
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_privateInstanceField(){
    try{
      TestClasses_2 instance = dispatcher.make();
      Integer value = dispatcher.read(instance, "i_203");
      return value != null && value == 203; // Replace 203 with initialized value
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean run(){
    try{
      boolean result = true;

      System.out.println("");
      System.out.println("running test: publicStaticField");
      if(Boolean.TRUE.equals(test_publicStaticField())){
        System.out.println("PASSED");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: privateStaticField");
      if(Boolean.TRUE.equals(test_privateStaticField())){
        System.out.println("PASSED");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: publicInstanceField");
      if(Boolean.TRUE.equals(test_publicInstanceField())){
        System.out.println("PASSED");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: privateInstanceField");
      if(Boolean.TRUE.equals(test_privateInstanceField())){
        System.out.println("PASSED");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      return result;

    }catch(Exception e){
      System.out.println("Exception in Dispatcher_2 test:");
      e.printStackTrace();
      return false;
    }
  }

  public static boolean logPass(){
    System.out.println("PASSED");
    return true;
  }

  public static boolean logFail(){
    System.out.println("FAILED");
    return false;
  }

  public static void main(String[] args){
    if(run()){
      System.exit(0);
    }else{
      System.exit(1);
    }
  }
}

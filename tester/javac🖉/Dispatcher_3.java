import com.ReasoningTechnology.Mosaic.Mosaic_Dispatcher;
import com.ReasoningTechnology.Mosaic.Mosaic_Util;

import tester.TestClasses_0;

public class Dispatcher_3{

  private static Mosaic_Dispatcher dispatcher;

  static{
    dispatcher = new Mosaic_Dispatcher(TestClasses_0.class);
  }

  public static boolean test_privateClassPublicMethod_5(){
    try{
      Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher(TestClasses_0.APrivateClass_02.class);
      Object nested_instance = nested_dispatcher.make();
      return nested_dispatcher.dispatch(nested_instance, boolean.class, "a_public_method_5");
    } catch(Exception e){
      e.printStackTrace();
      return false;
    }
  }

  public static boolean test_privateClassPrivateMethod_6(){
    try{
      Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher(TestClasses_0.APrivateClass_02.class);
      Object nested_instance = nested_dispatcher.make();
      return nested_dispatcher.dispatch(nested_instance, boolean.class, "a_private_method_6");
    } catch(Exception e){
      e.printStackTrace();
      return false;
    }
  }

  public static boolean test_publicDefaultClassField() {
    try {
      Class<?> defaultClass = dispatcher.getDefaultClass(); // Assuming `getDefaultClass` exists
      Integer value = dispatcher.read(Integer.class, "d_300");
      return value != null && value == 300; // Replace 300 with initialized value
    } catch (Throwable t) {
      t.printStackTrace();
      return false;
    }
  }

  

  public static boolean run(){
    try{
      boolean result = true;

      System.out.println("");
      System.out.println("running test: privateClassPublicMethod_5");
      if(Boolean.TRUE.equals(test_privateClassPublicMethod_5())){
        System.out.println("PASSED");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: privateClassPrivateMethod_6");
      if(Boolean.TRUE.equals(test_privateClassPrivateMethod_6())){
        System.out.println("PASSED");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: publicDefaultClassField");
      if(Boolean.TRUE.equals(test_publicDefaultClassField())){
        System.out.println("PASSED");
      }else{
        System.out.println("FAILED");
        result = false;
      }


      System.out.println("");
      return result;

    }catch(Exception e){
      System.out.println("Exception in Dispatcher_1 test:");
      e.printStackTrace();
      return false;
    }
  }

  private static boolean logPass(){
    System.out.println("PASSED");
    return true;
  }

  private static boolean logFail(){
    System.out.println("FAILED");
    return false;
  }

  public static void main(String[] args){
    System.exit(run() ? 0 : 1);
  }
}

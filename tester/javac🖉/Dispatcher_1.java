import com.ReasoningTechnology.Mosaic.Mosaic_Dispatcher;
import com.ReasoningTechnology.Mosaic.Mosaic_Util;

import tester.TestClasses_0;

public class Dispatcher_1{

  private static Mosaic_Dispatcher dispatcher;

  static{
    dispatcher = new Mosaic_Dispatcher(TestClasses_0.class);
  }

  public static boolean test_publicMethod_1(){
    TestClasses_0 instance = new TestClasses_0();
    return dispatcher.dispatch(instance, boolean.class, "a_public_method_1");
  }

  public static boolean test_privateMethod_2(){
    TestClasses_0 instance = new TestClasses_0();
    return dispatcher.dispatch(instance, boolean.class, "a_private_method_2");
  }

  public static boolean test_nestedPublicMethod_3(){
    try{
      TestClasses_0 outer = new TestClasses_0();
      TestClasses_0.APublicClass_01 nested = outer.new APublicClass_01();
      Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher(TestClasses_0.APublicClass_01.class);
      return nested_dispatcher.dispatch(nested, boolean.class, "a_public_method_3");
    } catch(Exception e){
      e.printStackTrace();
      return false;
    }
  }

  public static boolean test_nestedPrivateMethod_4(){
    try{
      TestClasses_0 outer = new TestClasses_0();
      TestClasses_0.APublicClass_01 nested = outer.new APublicClass_01();
      Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher(TestClasses_0.APublicClass_01.class);
      return nested_dispatcher.dispatch(nested, boolean.class, "a_private_method_4");
    } catch(Exception e){
      e.printStackTrace();
      return false;
    }
  }

  public static boolean run(){
    try{
      boolean result = true;

      System.out.println("");
      System.out.println("running test: publicMethod_1");
      if(Boolean.TRUE.equals(test_publicMethod_1())){
        System.out.println("PASSED");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: privateMethod_2");
      if(Boolean.TRUE.equals(test_privateMethod_2())){
        System.out.println("PASSED");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: nestedPublicMethod_3");
      if(Boolean.TRUE.equals(test_nestedPublicMethod_3())){
        System.out.println("PASSED");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: nestedPrivateMethod_4");
      if(Boolean.TRUE.equals(test_nestedPrivateMethod_4())){
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

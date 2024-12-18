import com.ReasoningTechnology.Mosaic.Mosaic_Dispatcher;
import com.ReasoningTechnology.Mosaic.Mosaic_IsPrimitive;
import com.ReasoningTechnology.Mosaic.Mosaic_Util;

import tester.TestClasses_0;
import tester.TestClasses_1;

public class Dispatch_0{

  private static Mosaic_Dispatcher dispatcher;

  static{
    // Initialize the dispatcher for TestClasses_0
    Mosaic_Dispatcher.test_switch(true);
    dispatcher = new Mosaic_Dispatcher(TestClasses_0.class);
  }

  // Test method to access the public method of the public class
  public static boolean test_publicClass_publicMethod(){
    Object instance = new TestClasses_0();
    boolean result = dispatcher.dispatch
      (
       instance   // target instance
       ,boolean.class  // return type
       ,"a_public_method_1"  // method name
       );

    return result;
  }

  public static boolean test_make_0(){
    Boolean[] condition_list = new Boolean[4];
    Mosaic_Util.all_set_false(condition_list);
    int i = 0;

    Mosaic_Dispatcher d1 = new Mosaic_Dispatcher(TestClasses_1.class);

    TestClasses_1 tc0 = new TestClasses_1();
    condition_list[i++] = tc0.get_i() == 0;
    
    TestClasses_1 tc1 = (TestClasses_1) d1.make();
    condition_list[i++] = tc1.get_i() == 0;

    TestClasses_1 tc2 = (TestClasses_1) d1.make(new Mosaic_IsPrimitive(7));
    condition_list[i++] = tc2.get_i() == 7;
    
    TestClasses_1 tc3 = (TestClasses_1) d1.make(new Mosaic_IsPrimitive(21) ,new Mosaic_IsPrimitive(17) );
    condition_list[i++] = tc3.get_i() == 38;

    return Mosaic_Util.all(condition_list);
  }

  // Test public static method
  public static boolean test_publicStaticMethod_7(){
    boolean result = dispatcher.dispatch(
      boolean.class,       // return type
      "a_public_static_method_7" // method name
    );
    return result;
  }

  // Test private static method
  public static boolean test_privateStaticMethod_9(){
    boolean result = dispatcher.dispatch(
      boolean.class,       // return type
      "a_private_static_method_9" // method name
    );
    return result;
  }

  // Extend the run method to include static method tests
  public static boolean run(){
    try{
      boolean result = true;

      /*
      System.out.println("");
      System.out.println("running test: publicClass_publicMethod");
      if (Boolean.TRUE.equals(test_publicClass_publicMethod())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result = false;
      }
      */

      System.out.println("");
      System.out.println("running test: make_0");
      if (Boolean.TRUE.equals(test_make_0())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      /*
      System.out.println("");
      System.out.println("running test: publicStaticMethod_7");
      if (Boolean.TRUE.equals(test_publicStaticMethod_7())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: privateStaticMethod_9");
      if (Boolean.TRUE.equals(test_privateStaticMethod_9())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result = false;
      }
      */

      System.out.println("");
      return result;

    }catch (Exception e){
      System.out.println("Exception in Dispatch_0 test:");
      e.printStackTrace();
      return false;
    }
  }

  public static void main(String[] args){
    // Execute the run method and return its result as the exit code
    if( run() ) 
      System.exit(0);
    else
      System.exit(1);
  }

}

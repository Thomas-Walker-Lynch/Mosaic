import com.ReasoningTechnology.Mosaic.Mosaic_Dispatcher;
import com.ReasoningTechnology.Mosaic.Mosaic_Util;

import tester.TestClasses_0;

public class Dispatch_1{

  private static Mosaic_Dispatcher dispatcher;

  static{
    // Initialize the dispatcher for TestClasses_0
    dispatcher = new Mosaic_Dispatcher(TestClasses_0.class);
    // Test messages are disabled for now
  }

  // Test public method in the public class
  public static boolean test_publicMethod_1(){
    Object instance = new TestClasses_0();
    boolean result = dispatcher.dispatch(
      instance,          // target instance
      boolean.class,     // return type
      "a_public_method_1" // method name
    );
    return result;
  }

  // Test private method in the public class
  public static boolean test_privateMethod_2(){
    Object instance = new TestClasses_0();
    boolean result = dispatcher.dispatch(
      instance,          // target instance
      boolean.class,     // return type
      "a_private_method_2" // method name
    );
    return result;
  }

  public static boolean test_nestedPublicMethod_3(){
    try{

      // Create a dispatcher for the nested public class
      Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher(TestClasses_0.APublicClass_01.class);

      // Create an instance of the outer class
      TestClasses_0 outer_instance = new TestClasses_0();

      // Create an instance of the nested public class
      TestClasses_0.APublicClass_01 nested_instance = outer_instance.new APublicClass_01();

      // Dispatch the public method call on the nested class
      boolean result = nested_dispatcher.dispatch(
        nested_instance,  // Target instance
        boolean.class,   // Return type
        "a_public_method_3" // Method name
      );

      return result;

    } catch (Exception e){
      System.out.println("Exception in test_nestedPublicMethod_3");
      e.printStackTrace();
      return false;
    }
  }

  // Test private method in the nested public class
  public static boolean test_nestedPrivateMethod_4(){

    // Create a dispatcher for the nested public class
    Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher(TestClasses_0.APublicClass_01.class);

    // Create an instance of the outer class
    TestClasses_0 outer_instance = new TestClasses_0();

    // Create an instance of the nested public class
    TestClasses_0.APublicClass_01 nested_instance = outer_instance.new APublicClass_01();

    boolean result = nested_dispatcher.dispatch(
      nested_instance,    // target instance
      boolean.class,     // return type
      "a_private_method_4" // method name
    );
    return result;
  }

  // Test public method in the nested private class
  public static boolean test_private_class_public_method_5(){
    
    // Use Mosaic_Dispatch to access the private class
    Class<?> private_class_metadata = Mosaic_Dispatch.resolve_class(
      TestClasses_0.class ,"tester.TestClasses_0$APrivateClass_02"
    );
    
    // Create a dispatcher for the private class
    Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher(private_class_metadata);
    
    // Instance of the private class is created via the dispatcher
    Object nested_instance = nested_dispatcher.make();
    
    boolean result = nested_dispatcher.dispatch(
       nested_instance
      ,boolean.class
      ,"a_public_method_5"
    );
    
    return result;
  }


  // Test public method in the nested private class
  public static boolean test_privateClassPublicMethod_5(){

    // Create a dispatcher for the nested public class
    Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher(TestClasses_0.APrivateClass_02.class);

    // Instance of the private class is created via the dispatcher
    Object nested_instance = nested_dispatcher.make();
    boolean result = nested_dispatcher.dispatch(
      nested_instance,    // target instance
      boolean.class,     // return type
      "a_public_method_5" // method name
    );
    return result;
  }

  // Test private method in the nested private class
  public static boolean test_privateClassPrivateMethod_6(){
    Object nested_instance = dispatcher.make(
      TestClasses_0.class,
      "APrivateClass_02",
      null
    );
    boolean result = dispatcher.dispatch(
      nested_instance,
      boolean.class,
      "a_private_method_6"
    );
    return result;
  }

  // Run method to execute all tests
  public static boolean run(){
    try{
      boolean result = true;

      /*
      System.out.println("\nRunning test: publicMethod_1");
      if (Boolean.TRUE.equals(test_publicMethod_1())){
        System.out.println("PASSED");
      } else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("\nRunning test: privateMethod_2");
      if (Boolean.TRUE.equals(test_privateMethod_2())){
        System.out.println("PASSED");
      } else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("\nRunning test: nestedPublicMethod_3");
      if (Boolean.TRUE.equals(test_nestedPublicMethod_3())){
        System.out.println("PASSED");
      } else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("\nRunning test: nestedPrivateMethod_4");
      if (Boolean.TRUE.equals(test_nestedPrivateMethod_4())){
        System.out.println("PASSED");
      } else{
        System.out.println("FAILED");
        result = false;
      }
      */

      System.out.println("\nRunning test: privateClassPublicMethod_5");
      if (Boolean.TRUE.equals(test_privateClassPublicMethod_5())){
        System.out.println("PASSED");
      } else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("\nRunning test: privateClassPrivateMethod_6");
      if (Boolean.TRUE.equals(test_privateClassPrivateMethod_6())){
        System.out.println("PASSED");
      } else{
        System.out.println("FAILED");
        result = false;
      }

      return result;
    } catch (Exception e){
      System.out.println("Exception in Dispatch_1 test:");
      e.printStackTrace();
      return false;
    }
  }

  public static void main(String[] args){
    if (run()){
      System.exit(0);
    } else{
      System.exit(1);
    }
  }
}

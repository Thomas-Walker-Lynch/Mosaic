import com.ReasoningTechnology.Mosaic.Mosaic_Dispatcher;

import tester.TestClasses_0;

public class Dispatcher_3{

  public static boolean test_privateNestedClassPublicMethod(){
    try{
      TestClasses_0 outer_instance = new TestClasses_0();
      Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher("tester.TestClasses_0$APrivateClass_02");
      Object nested_instance = nested_dispatcher.make(new Object[]{outer_instance});
      boolean result = nested_dispatcher.dispatch(
        nested_instance ,
        boolean.class ,
        "a_public_method_5"
      );
      return result;
    }catch(Throwable t){
      System.out.println("Exception in test_privateNestedClassPublicMethod:");
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_privateNestedClassPrivateMethod(){
    try{
      TestClasses_0 outer_instance = new TestClasses_0();
      Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher("tester.TestClasses_0$APrivateClass_02");
      Object nested_instance = nested_dispatcher.make(new Object[]{outer_instance});
      boolean result = nested_dispatcher.dispatch(
        nested_instance ,
        boolean.class ,
        "a_private_method_6"
      );
      return result;
    }catch(Throwable t){
      System.out.println("Exception in test_privateNestedClassPrivateMethod:");
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_publicNestedClassPublicMethod(){
    try{
      TestClasses_0 outer = new TestClasses_0();
      TestClasses_0.APublicClass_01 nested_instance = outer.new APublicClass_01();
      Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher(TestClasses_0.APublicClass_01.class);
      boolean result = nested_dispatcher.dispatch(
        nested_instance ,
        boolean.class ,
        "a_public_method_3"
      );
      return result;
    }catch(Throwable t){
      System.out.println("Exception in test_publicNestedClassPublicMethod:");
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_publicNestedClassPrivateMethod(){
    try{
      TestClasses_0 outer = new TestClasses_0();
      TestClasses_0.APublicClass_01 nested_instance = outer.new APublicClass_01();
      Mosaic_Dispatcher nested_dispatcher = new Mosaic_Dispatcher(TestClasses_0.APublicClass_01.class);
      boolean result = nested_dispatcher.dispatch(
        nested_instance ,
        boolean.class ,
        "a_private_method_4"
      );
      return result;
    }catch(Throwable t){
      System.out.println("Exception in test_publicNestedClassPrivateMethod:");
      t.printStackTrace();
      return false;
    }
  }

  public static boolean run(){
    try{
      boolean result = true;

      System.out.println("");
      System.out.println("running test: privateNestedClassPublicMethod");
      if(Boolean.TRUE.equals(test_privateNestedClassPublicMethod())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: privateNestedClassPrivateMethod");
      if(Boolean.TRUE.equals(test_privateNestedClassPrivateMethod())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: publicNestedClassPublicMethod");
      if(Boolean.TRUE.equals(test_publicNestedClassPublicMethod())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      System.out.println("running test: publicNestedClassPrivateMethod");
      if(Boolean.TRUE.equals(test_publicNestedClassPrivateMethod())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result = false;
      }

      System.out.println("");
      return result;

    }catch(Exception e){
      System.out.println("Exception in Dispatcher_3 test:");
      e.printStackTrace();
      return false;
    }
  }

  public static void main(String[] args){
    System.exit(run() ? 0 : 1);
  }
}

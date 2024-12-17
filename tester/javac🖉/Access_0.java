import com.ReasoningTechnology.Mosaic.Mosaic_Dispatcher;
import com.ReasoningTechnology.Mosaic.Mosaic_IsPrimitive;
import com.ReasoningTechnology.Mosaic.Mosaic_TestClasses_0;
import com.ReasoningTechnology.Mosaic.Mosaic_TestClasses_1;
import com.ReasoningTechnology.Mosaic.Mosaic_Util;

public class Access_0{

  private static Mosaic_Dispatcher dispatcher;

  static{
    // Initialize the dispatcher for Mosaic_TestClasses_0
    dispatcher = new Mosaic_Dispatcher(Mosaic_TestClasses_0.class);
    dispatcher.test_switch(true); // Enable test messages for debugging
  }

  // Test method to access the public method of the public class
  public static boolean test_publicClass_publicMethod(){
      System.out.println("\nRunning test: test_publicClass_publicMethod");

      Object instance = new Mosaic_TestClasses_0();

      boolean result = dispatcher.dispatch
        (
         instance   // target instance
         ,boolean.class  // return type
         ,"a_public_method_1"  // method name
         );

      return result;

  }

  public static boolean test_make_0(){
    System.out.println("\nRunning test: test_make_0");
    Boolean[] condition_list = new Boolean[4];
    Mosaic_Util.all_set_false(condition_list);
    int i = 0;

    Mosaic_Dispatcher d1 = new Mosaic_Dispatcher(Mosaic_TestClasses_1.class);

    Mosaic_TestClasses_1 tc0 = new Mosaic_TestClasses_1();
    condition_list[i++] = tc0.get_i() == 0;
    
    Mosaic_TestClasses_1 tc1 = (Mosaic_TestClasses_1) d1.make();
    condition_list[i++] = tc1.get_i() == 0;

    Mosaic_TestClasses_1 tc2 = (Mosaic_TestClasses_1) d1.make(new Mosaic_IsPrimitive(7));
    condition_list[i++] = tc2.get_i() == 7;
    
    Mosaic_TestClasses_1 tc3 = (Mosaic_TestClasses_1) d1.make(new Mosaic_IsPrimitive(21) ,new Mosaic_IsPrimitive(17) );
    condition_list[i++] = tc3.get_i() == 38;

    return Mosaic_Util.all(condition_list);
  }

  // Run method to execute all tests
  public static boolean run(){
    try{

      // Run the individual test(s)
      boolean result = true;
      if( !test_publicClass_publicMethod() ){
        System.out.println("test_publicClass_publicMethod failed");
        result = false;
      }else{
        System.out.println("test_publicClass_publicMethod passed");
      }
      if( !test_make_0() ){
        System.out.println("test_make_0() failed");
        result = false;
      }else{
        System.out.println("test_make_0() passed");
      }
        
      return result;

    }catch(Exception e){
      System.out.println("Exception in test Accept_0");
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

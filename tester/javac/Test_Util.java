import com.ReasoningTechnology.Mosaic.Util;

/*
Test_Util

*/

public class Test_Util{

  public static boolean test_all(){
    // Test with zero conditions
    boolean[] conditions0 = {};
    boolean result = !Util.all(conditions0);  // Empty conditions list is false.

    // Test with one condition
    boolean[] conditions1_true = {true};
    boolean[] conditions1_false = {false};
    result &= Util.all(conditions1_true);    // should return true
    result &= !Util.all(conditions1_false);  // should return false

    // Test with two conditions
    boolean[] conditions2_true = {true, true};
    boolean[] conditions2_false1 = {true, false};
    boolean[] conditions2_false2 = {false, true};
    boolean[] conditions2_false3 = {false, false};
    result &= Util.all(conditions2_true);     // should return true
    result &= !Util.all(conditions2_false1);  // should return false
    result &= !Util.all(conditions2_false2);  // should return false
    result &= !Util.all(conditions2_false3);  // should return false

    // Test with three conditions
    boolean[] conditions3_false1 = {true, true, false};
    boolean[] conditions3_true = {true, true, true};
    boolean[] conditions3_false2 = {true, false, true};
    boolean[] conditions3_false3 = {false, true, true};
    boolean[] conditions3_false4 = {false, false, false};
    result &= !Util.all(conditions3_false1); // should return false
    result &= Util.all(conditions3_true);    // should return true
    result &= !Util.all(conditions3_false2); // should return false
    result &= !Util.all(conditions3_false3); // should return false
    result &= !Util.all(conditions3_false4); // should return false

    return result;
  }

  public static boolean test_all_set_false(){
    boolean[] conditions = {true, true, true};
    Util.all_set_false(conditions);
    return !Util.all(conditions);  // Should return false after setting all to false
  }

  public static boolean test_all_set_true(){
    boolean[] conditions = {false, false, false};
    Util.all_set_true(conditions);
    return Util.all(conditions);  // Should return true after setting all to true
  }
  
  public static int run(){
    boolean[] condition = new boolean[3];
    condition[0] = test_all();
    condition[1] = test_all_set_false();
    condition[2] = test_all_set_true();

    if( !Util.all(condition) ){
      System.out.println("Test_Util failed");
      return 1;
    }
    System.out.println("Test_Util passed");
    return 0;
  }

  // Main function to provide a shell interface for running tests
  public static void main(String[] args){
    int return_code = run();
    System.exit(return_code); 
    return;
  }
}

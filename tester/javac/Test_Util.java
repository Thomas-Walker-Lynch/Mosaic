import com.ReasoningTechnology.Mosaic.Mosaic_Util;

/*
Test_Util

*/

public class Test_Util{

  public static Boolean test_all(){
    // Test with zero condition
    Boolean[] condition0 = {};
    Boolean result = !Mosaic_Util.all(condition0);  // Empty condition list is false.

    // Test with one condition
    Boolean[] condition1_true = {true};
    Boolean[] condition1_false = {false};
    result &= Mosaic_Util.all(condition1_true);    // should return true
    result &= !Mosaic_Util.all(condition1_false);  // should return false

    // Test with two condition
    Boolean[] condition2_true = {true, true};
    Boolean[] condition2_false1 = {true, false};
    Boolean[] condition2_false2 = {false, true};
    Boolean[] condition2_false3 = {false, false};
    result &= Mosaic_Util.all(condition2_true);     // should return true
    result &= !Mosaic_Util.all(condition2_false1);  // should return false
    result &= !Mosaic_Util.all(condition2_false2);  // should return false
    result &= !Mosaic_Util.all(condition2_false3);  // should return false

    // Test with three condition
    Boolean[] condition3_false1 = {true, true, false};
    Boolean[] condition3_true = {true, true, true};
    Boolean[] condition3_false2 = {true, false, true};
    Boolean[] condition3_false3 = {false, true, true};
    Boolean[] condition3_false4 = {false, false, false};
    result &= !Mosaic_Util.all(condition3_false1); // should return false
    result &= Mosaic_Util.all(condition3_true);    // should return true
    result &= !Mosaic_Util.all(condition3_false2); // should return false
    result &= !Mosaic_Util.all(condition3_false3); // should return false
    result &= !Mosaic_Util.all(condition3_false4); // should return false

    return result;
  }

  public static Boolean test_all_set_false(){
    Boolean[] condition_list = {true, true, true};
    Mosaic_Util.all_set_false(condition_list);
    return !condition_list[0] && !condition_list[1] && !condition_list[2]; 
  }

  public static Boolean test_all_set_true(){
    Boolean[] condition_list = {false, false, false};
    Mosaic_Util.all_set_true(condition_list);
    return condition_list[0] && condition_list[1] && condition_list[2]; 
  }
  
  public static int run(){
    Boolean[] condition_list = new Boolean[3];
    condition_list[0] = test_all();
    condition_list[1] = test_all_set_false();
    condition_list[2] = test_all_set_true();

    if( 
       !condition_list[0] 
       || !condition_list[1] 
       || !condition_list[2] 
      ){
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

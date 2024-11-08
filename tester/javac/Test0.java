import com.ReasoningTechnology.Mosaic.Mosaic_Util;

/*
Test Zero

Plug it in, see if there is smoke. There usually is.

*/

public class Test0{

  public static Boolean test_is_true(){
    return true;
  }
  
  public static int run(){
    Boolean[] condition = new Boolean[1];
    condition[0] = test_is_true();

    int i = 0;
    if( !Mosaic_Util.all(condition) ){
      System.out.println("Test0 failed");
      return 1;
    }
    System.out.println("Test0 passed");
    return 0;
  }

  // Main function to provide a shell interface for running tests
  public static void main(String[] args){
    int return_code = run();
    System.exit(return_code); 
    return;
  }

}    

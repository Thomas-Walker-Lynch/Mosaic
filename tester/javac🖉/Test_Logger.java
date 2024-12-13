import com.ReasoningTechnology.Mosaic.Mosaic_IO;
import com.ReasoningTechnology.Mosaic.Mosaic_Logger;

public class Test_Logger{

  public class TestSuite{
    public Boolean smoke_test_logging(Mosaic_IO io){
      try{
        Mosaic_Logger logger = new Mosaic_Logger();
        logger.message("smoke_test_logging", "This is a smoke test for logging.");
        return true;
      }catch (Exception e){
        e.printStackTrace();
        return false;
      }
    }
  }

  public static void main(String[] args){
    TestSuite suite = new Test_Logger().new TestSuite();
    boolean result = suite.smoke_test_logging(null);

    if(result){
      System.out.println("Test passed: 'smoke_test_logging'");
      System.exit(0);
    }else{
      System.err.println("Test failed: 'smoke_test_logging'");
      System.exit(1);
    }
  }
}

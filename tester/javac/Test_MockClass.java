/* --------------------------------------------------------------------------------
   Integration tests directly simulate the use cases for TestBench.
   Each test method validates a specific feature of TestBench ,including pass,
   fail ,error handling ,and I/O interactions.
*/

import java.util.Scanner;
import com.ReasoningTechnology.Mosaic.IO;
import com.ReasoningTechnology.Mosaic.TestBench;

public class Test_MockClass{

  public class TestSuite{

    public TestSuite() {
      // no special initialization of data for this test
    }

    public Boolean test_failure_0(IO io){
      return false;
    }

    // returns a non-Boolean
    public Object test_failure_1(IO io){
      return 1;
    }

    // has an uncaught error
    public Boolean test_failure_2(IO io) throws Exception {
      throw new Exception("Intentional exception for testing error handling");
    }

    // extraneous characters on stdout
    public Boolean test_failure_3(IO io) throws Exception {
      System.out.println("Intentional extraneous chars to stdout for testing");
      return true;
    }

    // extraneous characters on stderr
    public Boolean test_failure_4(IO io) throws Exception {
      System.err.println("Intentional extraneous chars to stderr for testing.");
      return true;
    }

    public Boolean test_success_0(IO io){
      return true; 
    }

    // pushing input for testing

    public Boolean test_success_1(IO io){
      io.push_input("input for the fut");

      Scanner scanner = new Scanner(System.in);
      String result = scanner.nextLine();
      scanner.close();

      Boolean flag = result.equals("input for the fut");
      return flag;
    }

    // checking fut stdout
    public Boolean test_success_2(IO io){
      System.out.println("fut stdout"); // suppose the fut does this:
      String peek_at_futs_output = io.get_out_content();
      Boolean flag0 = io.has_out_content();
      Boolean flag1 = peek_at_futs_output.equals("fut stdout\n");
      io.clear_buffers(); // otherwise extraneous chars will cause an fail
      return flag0 && flag1;
    }

    // checking fut stderr
    public Boolean test_success_3(IO io){
      System.err.print("fut stderr"); // suppose the fut does this:
      String peek_at_futs_output = io.get_err_content();
      Boolean flag0 = io.has_err_content();
      Boolean flag1 = peek_at_futs_output.equals("fut stderr");
      io.clear_buffers(); // otherwise extraneous chars will cause an fail
      return flag0 && flag1;
    }

  }

  public static void main(String[] args) {
    Test_MockClass outer = new Test_MockClass();
    TestSuite suite = outer.new TestSuite(); // Non-static instantiation

    /* for debug
    IO io = new IO();
    io.redirect();
    suite.test_success_2(io);
    */

    int result = TestBench.run(suite); // Pass the suite instance to TestBench
    System.exit(result);
  }

}

import java.lang.reflect.Method;
import com.ReasoningTechnology.Mosaic.IO;
import com.ReasoningTechnology.Mosaic.TestBench;

public class Test_TestBench {

  /* --------------------------------------------------------------------------------
     Test methods to validate TestBench functionality
     Each method tests a specific aspect of the TestBench class, with a focus on
     ensuring that well-formed and ill-formed test cases are correctly identified
     and handled.
  */

  // Tests if a correctly formed method is recognized as well-formed by TestBench
  public static Boolean test_method_is_wellformed_0(IO io) {
    try {
      Method validMethod = Test_TestBench.class.getMethod("dummy_test_method", IO.class);
      return Boolean.TRUE.equals(TestBench.method_is_wellformed(validMethod));
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  // Tests if a method with an invalid return type is identified as malformed by TestBench
  public static Boolean test_method_is_wellformed_1(IO io) {
    try {
      Method invalidReturnMethod = Test_TestBench.class.getMethod("dummy_invalid_return_method", IO.class);
      return Boolean.FALSE.equals(TestBench.method_is_wellformed(invalidReturnMethod));
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  // Tests if a valid test method runs successfully with the TestBench
  public static Boolean test_run_test_0(IO io) {
    try {
      Method validMethod = Test_TestBench.class.getMethod("dummy_test_method", IO.class);
      return Boolean.TRUE.equals(TestBench.run_test(new Test_TestBench(), validMethod, io));
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  /* Dummy methods for testing */
  public Boolean dummy_test_method(IO io) {
    return true; // Simulates a passing test case
  }

  public void dummy_invalid_return_method(IO io) {
    // Simulates a test case with an invalid return type
  }

  /* --------------------------------------------------------------------------------
     Manually run all tests and summarize results without using TestBench itself.
     Each test's name is printed if it fails, and only pass/fail counts are summarized.
  */
  public static int run() {
    int passed_tests = 0;
    int failed_tests = 0;
    IO io = new IO();

    if (test_method_is_wellformed_0(io)) passed_tests++; else { System.out.println("test_method_is_wellformed_0"); failed_tests++; }
    if (test_method_is_wellformed_1(io)) passed_tests++; else { System.out.println("test_method_is_wellformed_1"); failed_tests++; }
    if (test_run_test_0(io)) passed_tests++; else { System.out.println("test_run_test_0"); failed_tests++; }

    // Summary for all the tests
    System.out.println("Total tests run: " + (passed_tests + failed_tests));
    System.out.println("Total tests passed: " + passed_tests);
    System.out.println("Total tests failed: " + failed_tests);

    return (failed_tests > 0) ? 1 : 0;
  }

  /* --------------------------------------------------------------------------------
     Main method for shell interface, sets the exit status based on test results
  */
  public static void main(String[] args) {
    int exitCode = run();
    System.exit(exitCode);
  }
}

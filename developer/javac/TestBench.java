package com.ReasoningTechnology.Mosaic;

import java.lang.reflect.Method;

public class TestBench {

  /* --------------------------------------------------------------------------------
    Validate the structure of a test method
  */
  public static Boolean method_is_wellformed(Method method){
    // Check if the method returns Boolean
    if(!method.getReturnType().equals(Boolean.class)){
      System.out.println("Structural problem: " + method.getName() + " does not return Boolean.");
      return false;
    }

    // Check if the method has exactly one argument of type IO
    Class<?>[] parameterTypes = method.getParameterTypes();
    if(parameterTypes == null || parameterTypes.length != 1 || !parameterTypes[0].equals(IO.class)){
      System.out.println("Structural problem: " + method.getName() + " does not accept a single IO argument.");
      return false;
    }

    return true;
  }

  /* --------------------------------------------------------------------------------
    Run a single test method
  */
  public static Boolean run_test(Object test_suite, Method method, IO io){
    String test_name = method.getName();

    // Tracking possible test failures
    Boolean fail_malformed = false;
    Boolean fail_reported = false;
    Boolean fail_exception = false;
    Boolean fail_extraneous_stdout = false;
    Boolean fail_extraneous_stderr = false;
    String exception_string = "";

    // Validate method structure
    if(!method_is_wellformed(method)){
      System.out.println("Error: " + test_name + " has an invalid structure.");
      return false;
    }

    // Redirect I/O
    Boolean successful_redirect = io.redirect();
    if(successful_redirect){
      io.clear_buffers();  // Start each test with empty buffers
    } else {
      Util.log_message(test_name, "Error: I/O redirection failed before running the test.");
      System.out.println("Warning: Failed to redirect I/O for test: " + test_name);
    }

    // Run the test and catch any exceptions
    try{
      Object result = method.invoke(test_suite, io);
      fail_reported = !Boolean.TRUE.equals(result); // Test passes only if it returns exactly `true`
      fail_extraneous_stdout = io.has_out_content();
      fail_extraneous_stderr = io.has_err_content();
    } catch(Exception e){
      fail_exception = true;
      exception_string = e.toString();
    } finally{
      io.restore();
    }

    // Report results
    if(fail_reported) System.out.println("Test failed: '" + test_name + "' reported failure.");
    if(fail_exception) System.out.println("Test failed: '" + test_name + "' threw an exception: " + exception_string);
    if(fail_extraneous_stdout){
      System.out.println("Test failed: '" + test_name + "' produced extraneous stdout.");
      Util.log_output(test_name, "stdout", io.get_out_content());
    }
    if(fail_extraneous_stderr){
      System.out.println("Test failed: '" + test_name + "' produced extraneous stderr.");
      Util.log_output(test_name, "stderr", io.get_err_content());
    }

    // Determine final test result
    return !(fail_reported || fail_exception || fail_extraneous_stdout || fail_extraneous_stderr);
  }

  /* --------------------------------------------------------------------------------
    Run all tests in the test suite
  */
  public static int run(Object test_suite){
    int failed_tests = 0;
    int passed_tests = 0;
    Method[] methods = test_suite.getClass().getDeclaredMethods();
    IO io = new IO();

    for(Method method : methods){
      if(run_test(test_suite, method, io)) passed_tests++; else failed_tests++;
    }

    // Summary of test results
    System.out.println("Total tests run: " + (passed_tests + failed_tests));
    System.out.println("Total tests passed: " + passed_tests);
    System.out.println("Total tests failed: " + failed_tests);

    return (failed_tests > 0) ? 1 : 0;
  }

}

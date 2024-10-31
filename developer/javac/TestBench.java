package com.ReasoningTechnology.Mosaic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Map;

public class TestBench{

  /* --------------------------------------------------------------------------------
    Static Data
  */

  private static PrintStream original_out;
  private static PrintStream original_err;
  private static InputStream original_in;
  
  private static ByteArrayOutputStream out_content;
  private static ByteArrayOutputStream err_content;
  private static InputStream in_content;

  public static Boolean method_is_wellformed(Method method) {
    // Check if the method returns Boolean
    if(!method.getReturnType().equals(Boolean.class)){
      System.out.println("Structural problem: " + method.getName() + " does not return Boolean.");
      return false;
    }

    // Check if the method has exactly three arguments
    Class<?>[] parameterTypes = method.getParameterTypes();
    if(parameterTypes == null || parameterTypes.length != 3){
      System.out.println("Structural problem: " + method.getName() + " does not have three arguments.");
      return false;
    }

    // Check that all parameters are ByteArrayOutputStream
    if(
       !parameterTypes[0].equals(ByteArrayOutputStream.class) // Check first parameter
       || !parameterTypes[1].equals(ByteArrayOutputStream.class) // Check second parameter
       || !parameterTypes[2].equals(ByteArrayOutputStream.class) // Check third parameter
       ){
      System.out.println("Structural problem: " + method.getName() + " has incorrect argument types.");
      return false;
    }

    return true;
  }

  public static Boolean run_test(Object test_suite ,Method method ,IO io){
    String test_name = method.getName();

    // Ways a test can fail, these are not generally singularly exclusive.
    Boolean fail_TestBench = false;
    Boolean fail_malformed = false;
    Boolean fail_reported = false;
    Boolean fail_exception = false;
    Boolean fail_extraneous_stdout = false;
    Boolean fail_extraneous_stderr = false;

    String exception_string = "";

    // `method_is_wellformed` prints more information about type signature mismatch failures
    if( !method_is_wellformed(method) ){
      System.out.println
        (
         "Mosaic::TestBench::run test \'" 
         + test_name 
         + "\' has incorrect type signature for a TestBench test, calling it a failure."
         );
      return false;
    }

    // redirect I/O to an io instance
    Boolean successful_redirect = io.redirect();
    if( successful_redirect ){
      io.clear_buffers(); // start each test with nothing on the I/O buffers
    }else{
      // Surely a redirect failure is rare, but it is also rare that tests make
      // use of IO redirection. A conundrum. So we log the error an wait utnil
      // latter only throwing an error if IO redirection is made use of.
      Util.log_message
        (
         test_name
         ,"Mosaic::TestBench::run redirect I/O failed before running this test."
         );
      System.out.println
        (
         "Mosaic::TestBench::run Immediately before running test, \""
         + test_name
         + "\' I/O redirect failed."
         );
    }

    // Finally the gremlins run the test!
    try{
      Object result = method.invoke(test_suite ,in_content ,out_content ,err_content);
      fail_reported = !Boolean.TRUE.equals(result); // test passes if ,and only if ,it returns exactly 'true'.
      fail_extraneous_stdout = io.has_out_content();
      fail_extraneous_stderr = io.has_err_content();
    } catch(Exception e){
      fail_exception = true;
      // We report the actual error after the try block.
      exception_string = e.toString();
    } finally{
      io.restore();
    }

    // report results
    if(fail_reported) System.out.println("failed: \'" + test_name + "\' by report from test.");
    if(fail_exception) System.out.println("failed: \'" + test_name + "\' due to unhandled exception: " + exception_string);
    if(fail_extraneous_stdout){
      System.out.println("failed: \'" + test_name + "\' due extraneous stdout output ,see log.");
      Util.log_output(test_name ,"stdout" ,io.get_out_content());
    }
    if(fail_extraneous_stderr){
      System.out.println("failed: \'" + test_name + "\' due extraneous stderr output ,see log.");
      Util.log_output(test_name ,"stderr" ,io.get_err_content());
    }

    // return condition
    Boolean test_failed =
      fail_reported 
      || fail_exception 
      || fail_extraneous_stdout
      || fail_extraneous_stderr
      ;
    return !test_failed;
  }


  public static void run(Object test_suite){
    int failed_test = 0;
    int passed_test = 0;
    Method[] methods = test_suite.getClass().getDeclaredMethods();
    IO io = new IO();
    for(Method method : methods){
      if( run_test(test_suite ,method ,io) ) passed_test++; else failed_test++;
    }
    // summary for all the tests
    System.out.println("Total tests run: " + (passed_test + failed_test));
    System.out.println("Total tests passed: " + passed_test);
    System.out.println("Total tests failed: " + failed_test);
  }

} // end of class TestBench

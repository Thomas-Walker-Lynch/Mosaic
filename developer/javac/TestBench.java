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

  public static boolean method_is_wellformed(Method method) {
    // Check if the method returns boolean
    if(!method.getReturnType().equals(boolean.class)){
      System.out.println("Structural problem: " + method.getName() + " does not return boolean.");
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

  public static boolean run_test(){
    String test_name = method.getName()


      // Ways a test can fail ,not exclusive
      boolean fail_malformed = false;
    boolean fail_reported = false;
    boolean fail_exception = false;
    boolean fail_extraneous_stdout = false;
    boolean fail_extraneous_stderr = false;

    if( !io.redirect() ){
      Util.log_message
        (
         "Mosaic::TestBench::run redirect I/O failed before running test \'"
         + test_name
         "\', so most class IO methods will throw an uncaught error if called."
         );
      Ssytem.out.println
        (
         "Mosaic::TestBench::run Immediately before running test, \""
         + test 
         + "\' I/O redirect failed."
         );
    }else{
      io.clear_buffers();
    }

    // the method_is_wellformed prints more specific messages than found here
    if( !method_is_wellformed(method) ){
      System.out.println
        (
         "Mosaic::TestBench::run test \'" 
         + test_name 
         + "\' has incorrect type signature for a TestBench test, calling it a failure."
         );
      failed_test++;
      continue;
    }

    // Finally the gremlins run the test!
    try{

      Object result = method.invoke(test_suite ,in_content ,out_content ,err_content);
      fail_reported = !Boolean.TRUE.equals(result); // test passes if ,and only if ,it returns exactly 'true'.

      // A test fails when there is extraneous output
      fail_extraneous_stdout = out_content.size() > 0;
      fail_extraneous_stderr = err_content.size() > 0;

      // We keep it to log it
      if(fail_extraneous_stdout){ stdout_string = out_content.toString(); }
      if(fail_extraneous_stderr){ stderr_string = err_content.toString(); }

    } catch(Exception e){

      // A test fails when there is an unhandled exception.
      fail_exception = true;

      // We keep it to report it
      exception_string = e.toString();

    } finally{
        
      // Restore original stdin ,stdout ,and stderr
      System.setOut(original_out);
      System.setErr(original_err);
      System.setIn(original_in);
    }

    if(fail_reported) System.out.println("failed: \'" + test_name + "\' by report from test.");
    if(fail_exception) System.out.println("failed: \'" + test_name + "\' due to unhandled exception: " + exception_string);
    if(fail_extraneous_stdout){
      System.out.println("failed: \'" + test_name + "\' due extraneous stdout output ,see log.");
      log_output(test_name ,"stdout" ,stdout_string);
    }
    if(fail_extraneous_stderr){
      System.out.println("failed: \'" + test_name + "\' due extraneous stderr output ,see log.");
      log_output(test_name ,"stderr" ,stderr_string);
    }

    boolean test_failed =
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
    io = new IO();

    for(Method method : methods){

      // Ways a test can fail ,not exclusive
      boolean fail_malformed = false;
      boolean fail_reported = false;
      boolean fail_exception = false;
      boolean fail_extraneous_stdout = false;
      boolean fail_extraneous_stderr = false;

      if( !io.redirect() ){
        Util.log_message
          (
           "Mosaic::TestBench::run redirect I/O failed before running test \'"
           + test_name
           "\', so most class IO methods will throw an uncaught error if called."
           );
        Ssytem.out.println
          (
           "Mosaic::TestBench::run Immediately before running test, \""
           + test 
           + "\' I/O redirect failed."
           );
      }else{
        io.clear_buffers();
      }

      // the method_is_wellformed prints more specific messages than found here
      if( !method_is_wellformed(method) ){
        System.out.println
          (
           "Mosaic::TestBench::run test \'" 
           + test_name 
           + "\' has incorrect type signature for a TestBench test, calling it a failure."
           );
        failed_test++;
        continue;
      }

      // Finally the gremlins run the test!
      try{

        Object result = method.invoke(test_suite ,in_content ,out_content ,err_content);
        fail_reported = !Boolean.TRUE.equals(result); // test passes if ,and only if ,it returns exactly 'true'.

        // A test fails when there is extraneous output
        fail_extraneous_stdout = out_content.size() > 0;
        fail_extraneous_stderr = err_content.size() > 0;

        // We keep it to log it
        if(fail_extraneous_stdout){ stdout_string = out_content.toString(); }
        if(fail_extraneous_stderr){ stderr_string = err_content.toString(); }

      } catch(Exception e){

        // A test fails when there is an unhandled exception.
        fail_exception = true;

        // We keep it to report it
        exception_string = e.toString();

      } finally{
        
        // Restore original stdin ,stdout ,and stderr
        System.setOut(original_out);
        System.setErr(original_err);
        System.setIn(original_in);
      }

      // Report the test result.
      if(
         fail_reported 
         || fail_exception 
         || fail_extraneous_stdout
         || fail_extraneous_stderr
         ){

        failed_test++;

        if(fail_reported) System.out.println("failed: \'" + test_name + "\' by report from test.");
        if(fail_exception) System.out.println("failed: \'" + test_name + "\' due to unhandled exception: " + exception_string);
        if(fail_extraneous_stdout){
          System.out.println("failed: \'" + test_name + "\' due extraneous stdout output ,see log.");
          log_output(test_name ,"stdout" ,stdout_string);
        }
        if(fail_extraneous_stderr){
          System.out.println("failed: \'" + test_name + "\' due extraneous stderr output ,see log.");
          log_output(test_name ,"stderr" ,stderr_string);
        }

      } else{
        passed_test++;
      }

    }

    // Summarize all the test results
    System.out.println("Total tests run: " + (passed_test + failed_test));
    System.out.println("Total tests passed: " + passed_test);
    System.out.println("Total tests failed: " + failed_test);
  }

}

-----------------
  package com.ReasoningTechnology.Mosaic;

import java.lang.reflect.Method;

public class TestBench {

    public static boolean method_is_wellformed(Method method) {
        // Check if the method returns boolean
        if (!method.getReturnType().equals(boolean.class)) {
            System.out.println("Structural problem: " + method.getName() + " does not return boolean.");
            return false;
        }

        // Check if the method has exactly three arguments
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes == null || parameterTypes.length != 3) {
            System.out.println("Structural problem: " + method.getName() + " does not have three arguments.");
            return false;
        }

        // Check that all parameters are ByteArrayOutputStream
        if (
            !parameterTypes[0].equals(ByteArrayOutputStream.class) ||
            !parameterTypes[1].equals(ByteArrayOutputStream.class) ||
            !parameterTypes[2].equals(ByteArrayOutputStream.class)
        ) {
            System.out.println("Structural problem: " + method.getName() + " has incorrect argument types.");
            return false;
        }

        return true;
    }

    public static void run(Object test_suite) {
        int failed_test = 0;
        int passed_test = 0;

        Method[] methods = test_suite.getClass().getDeclaredMethods();
        IO io = new IO(); // Create an instance of IO

        for (Method method : methods) {
            if (!method_is_wellformed(method)) {
                System.out.println("TestBench: malformed test counted as a failure: \'" + method.getName() + "\'");
                failed_test++;
                continue;
            }

            try {
                // Redirect I/O with an empty input string for now
                io.redirectIO("");
            } catch (Throwable e) {
                failed_test++;
                System.out.println("TestBench:: Error during test preparation: " + e.toString());
                continue;
            }

            String stdout_string = "";
            String stderr_string = "";

            try {
                // Invoke the test method
                Object result = method.invoke(test_suite, io.getInContent(), io.getOutContent(), io.getErrContent());
                boolean fail_reported = !Boolean.TRUE.equals(result);

                // Check for extraneous output
                stdout_string = io.getOutContent().toString();
                stderr_string = io.getErrContent().toString();
                boolean fail_extraneous_stdout = stdout_string.length() > 0;
                boolean fail_extraneous_stderr = stderr_string.length() > 0;

                // Handle failures
                if (fail_reported || fail_extraneous_stdout || fail_extraneous_stderr) {
                    failed_test++;
                    if (fail_reported) System.out.println("failed: \'" + method.getName() + "\' by report from test.");
                    if (fail_extraneous_stdout) {
                        System.out.println("failed: \'" + method.getName() + "\' due extraneous stdout output, see log.");
                        log_output(method.getName(), "stdout", stdout_string);
                    }
                    if (fail_extraneous_stderr) {
                        System.out.println("failed: \'" + method.getName() + "\' due extraneous stderr output, see log.");
                        log_output(method.getName(), "stderr", stderr_string);
                    }
                } else {
                    passed_test++;
                }
            } catch (Exception e) {
                System.out.println("failed: \'" + method.getName() + "\' due to unhandled exception: " + e.toString());
                failed_test++;
            } finally {
                // Restore I/O
                io.restoreIO();
            }
        }

        // Summarize all the test results
        System.out.println("Total tests run: " + (passed_test + failed_test));
        System.out.println("Total tests passed: " + passed_test);
        System.out.println("Total tests failed: " + failed_test);
    }
}

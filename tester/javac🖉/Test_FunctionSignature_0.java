import com.ReasoningTechnology.Mosaic.Mosaic_AllMethodsPublicProxy;
import com.ReasoningTechnology.Mosaic.Mosaic_IO;
import com.ReasoningTechnology.Mosaic.Mosaic_Testbench;

public class Test_FunctionSignature_0 {

  public static class TestSuite {

    private static Mosaic_AllMethodsPublicProxy proxy;

    static {
      try {
        proxy = new Mosaic_AllMethodsPublicProxy("com.ReasoningTechnology.Mosaic.FunctionSignature");
      } catch (ClassNotFoundException e) {
        System.err.println("Failed to initialize proxy: " + e.getMessage());
      }
    }

    public Boolean smoke_test_0(Mosaic_IO io) {
      try {
        // Create a FunctionSignature instance via the proxy constructor
        Object signature = proxy.construct("<init>", "testMethod", new Class<?>[]{});

        // Call the toString method on the proxy instance
        String result = (String) proxy.invoke(signature, "toString");

        // Check expected output
        return "testMethod()".equals(result);
      } catch (Exception e) {
        System.err.println("Test failed: " + e.getMessage());
        e.printStackTrace();
        return false;
      }
    }
  }

  public static void main(String[] args) {
    TestSuite suite = new TestSuite();
    int result = Mosaic_Testbench.run(suite);
    System.exit(result);
  }
}

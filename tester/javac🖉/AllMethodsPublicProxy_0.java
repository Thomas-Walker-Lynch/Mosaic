import com.ReasoningTechnology.Mosaic.Mosaic_AllMethodsPublicProxy;
import com.ReasoningTechnology.Mosaic.Mosaic_IO;
import com.ReasoningTechnology.Mosaic.Mosaic_Testbench;
import com.ReasoningTechnology.Mosaic.TestClasses;

public class AllMethodsPublicProxy_0 {

  public static class TestSuite {

    private static Mosaic_AllMethodsPublicProxy publicProxy;
    private static Mosaic_AllMethodsPublicProxy defaultProxy;
    private static Mosaic_AllMethodsPublicProxy privateProxy;

    static {
      try {
        // Initialize proxies for public, default, and private classes
        publicProxy = new Mosaic_AllMethodsPublicProxy(PublicClass.class);
        defaultProxy = new Mosaic_AllMethodsPublicProxy(DefaultClass.class);
        privateProxy = new Mosaic_AllMethodsPublicProxy(PrivateClass.class);
      } catch (Exception e) {
        System.err.println("Failed to initialize proxies: " + e.getMessage());
        e.printStackTrace();
      }
    }

    public Boolean test_publicClass_publicMethod(Mosaic_IO io) {
      try {
        Object instance = publicProxy.construct("<init>");
        return (Boolean) publicProxy.invoke(instance, "publicMethod");
      } catch (Exception e) {
        System.err.println("Test failed: " + e.getMessage());
        e.printStackTrace();
        return false;
      }
    }

    public Boolean test_publicClass_privateMethod(Mosaic_IO io) {
      try {
        Object instance = publicProxy.construct("<init>");
        return (Boolean) publicProxy.invoke(instance, "privateMethod");
      } catch (Exception e) {
        System.err.println("Test failed: " + e.getMessage());
        e.printStackTrace();
        return false;
      }
    }

    public Boolean test_defaultClass_publicMethod(Mosaic_IO io) {
      try {
        Object instance = defaultProxy.construct("<init>");
        return (Boolean) defaultProxy.invoke(instance, "publicMethod");
      } catch (Exception e) {
        System.err.println("Test failed: " + e.getMessage());
        e.printStackTrace();
        return false;
      }
    }

    public Boolean test_defaultClass_privateMethod(Mosaic_IO io) {
      try {
        Object instance = defaultProxy.construct("<init>");
        return (Boolean) defaultProxy.invoke(instance, "privateMethod");
      } catch (Exception e) {
        System.err.println("Test failed: " + e.getMessage());
        e.printStackTrace();
        return false;
      }
    }

    public Boolean test_privateClass_publicMethod(Mosaic_IO io) {
      try {
        Object instance = privateProxy.construct("<init>");
        return (Boolean) privateProxy.invoke(instance, "publicMethod");
      } catch (Exception e) {
        System.err.println("Test failed: " + e.getMessage());
        e.printStackTrace();
        return false;
      }
    }

    public Boolean test_privateClass_privateMethod(Mosaic_IO io) {
      try {
        Object instance = privateProxy.construct("<init>");
        return (Boolean) privateProxy.invoke(instance, "privateMethod");
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

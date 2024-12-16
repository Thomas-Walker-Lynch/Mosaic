import com.ReasoningTechnology.Mosaic.Mosaic_AllMethodsPublicProxy;
import com.ReasoningTechnology.Mosaic.Mosaic_IO;
import com.ReasoningTechnology.Mosaic.Mosaic_Testbench;
import com.ReasoningTechnology.Mosaic.Mosaic_TestClasses;

public class Test_Access_0{

  public static class TestSuite{

    private static Mosaic_AllMethodsPublicProxy publicProxy;
    //    private static Mosaic_AllMethodsPublicProxy nestedPublicProxy;
    //    private static Mosaic_AllMethodsPublicProxy nestedPrivateProxy;
    //    private static Mosaic_AllMethodsPublicProxy topPrivateProxy;

    static{
      // Initialize proxies using class metadata and path strings
      publicProxy = new Mosaic_AllMethodsPublicProxy(Mosaic_TestClasses.class);
      //    nestedPublicProxy = new Mosaic_AllMethodsPublicProxy("com.ReasoningTechnology.Mosaic.Mosaic_TestClasses$PublicClass");
      //    nestedPrivateProxy = new Mosaic_AllMethodsPublicProxy("com.ReasoningTechnology.Mosaic.Mosaic_TestClasses$PrivateClass");
      //    topPrivateProxy = new Mosaic_AllMethodsPublicProxy("com.ReasoningTechnology.Mosaic.PrivateClass");
    }

    public Boolean test_publicClass_publicMethod(Mosaic_IO io){
      Object instance = publicProxy.construct("<init>");
      return true;
      //      return(Boolean) publicProxy.invoke(instance, "publicMethod");
    }

    /*

    public Boolean test_publicClass_privateMethod(Mosaic_IO io){
      Object instance = publicProxy.construct("<init>");
      return(Boolean) publicProxy.invoke(instance, "privateMethod");
    }

    public Boolean test_nestedPublicClass_publicMethod(Mosaic_IO io){
      Object outerInstance = publicProxy.construct("<init>");
      Object instance = nestedPublicProxy.construct("<init>", outerInstance);
      return(Boolean) nestedPublicProxy.invoke(instance, "publicMethod");
    }

    public Boolean test_nestedPublicClass_privateMethod(Mosaic_IO io){
      Object outerInstance = publicProxy.construct("<init>");
      Object instance = nestedPublicProxy.construct("<init>", outerInstance);
      return(Boolean) nestedPublicProxy.invoke(instance, "privateMethod");
    }

    public Boolean test_nestedPrivateClass_publicMethod(Mosaic_IO io){
      Object outerInstance = publicProxy.construct("<init>");
      Object instance = nestedPrivateProxy.construct("<init>", outerInstance);
      return(Boolean) nestedPrivateProxy.invoke(instance, "publicMethod");
    }

    public Boolean test_nestedPrivateClass_privateMethod(Mosaic_IO io){
      Object outerInstance = publicProxy.construct("<init>");
      Object instance = nestedPrivateProxy.construct("<init>", outerInstance);
      return(Boolean) nestedPrivateProxy.invoke(instance, "privateMethod");
    }
    */
  }

  public static void main(String[] args){
    TestSuite suite = new TestSuite();
    int result = Mosaic_Testbench.run(suite);
    System.exit(result);
  }
}

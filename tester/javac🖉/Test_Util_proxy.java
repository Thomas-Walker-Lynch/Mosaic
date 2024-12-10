import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import com.ReasoningTechnology.Mosaic.Mosaic_IO;
import com.ReasoningTechnology.Mosaic.Mosaic_Testbench;
import com.ReasoningTechnology.Mosaic.Mosaic_Util;
import com.ReasoningTechnology.Mosaic.Mosaic_Logger;

public class Test_Util_proxy{

  public class TestSuite{

    public Boolean create_proxy_non_public_class( Mosaic_IO io ){
      Boolean[] conditions = new Boolean[2];
      int i = 0;

      try{
        Object proxy = Mosaic_Util.make_all_public_proxy( NonPublicClass.class );
        conditions[i++] = proxy != null;
        conditions[i++] = Proxy.isProxyClass( proxy.getClass() );
      }catch( Exception e ){
        Mosaic_Logger.error("Test_Util_proxy" ,"create_proxy_non_public_class" ,e);
        return false;
      }

      return Mosaic_Util.all( conditions );
    }

    public Boolean invoke_proxied_non_public_method(Mosaic_IO io) {
        Boolean[] conditions = new Boolean[2];
        int i = 0;

        try {
            NonPublicInterface proxy = (NonPublicInterface) Mosaic_Util.make_all_public_proxy(NonPublicClass.class);
            String result = proxy.hidden_method();
            conditions[i++] = result.equals("Accessed hidden method!");
            conditions[i++] = Proxy.isProxyClass(proxy.getClass());
        } catch (Exception e) {
            Mosaic_Logger.error("invoke_proxied_non_public_method", "Unexpected exception: " + e.getMessage(), e);
            return false;
        }

        return Mosaic_Util.all(conditions);
    }

    public Boolean create_and_invoke_public_class_proxy( Mosaic_IO io ){
      Boolean[] conditions = new Boolean[2];
      int i = 0;

      try{
        PublicInterface proxy = (PublicInterface) Mosaic_Util.make_all_public_proxy( PublicClass.class );
        String result = proxy.say_hello();
        conditions[i++] = result.equals( "Hello from PublicClass!" );
        conditions[i++] = Proxy.isProxyClass( proxy.getClass() );
      }catch( Exception e ){
        Mosaic_Logger.error("Test_Util_proxy" ,"create_and_invoke_public_class_proxy" ,e);
        return false;
      }

      return Mosaic_Util.all( conditions );
    }

  }

  public interface PublicInterface{
    String say_hello();
  }

  public static class PublicClass implements PublicInterface{
    @Override
    public String say_hello(){
      return "Hello from PublicClass!";
    }
  }

  interface NonPublicInterface{
    String hidden_method();
  }

  static class NonPublicClass implements NonPublicInterface{
    @Override
    public String hidden_method(){
      return "Accessed hidden method!";
    }
  }

  public static void main( String[] args ){
    Test_Util_proxy outer = new Test_Util_proxy();
    TestSuite suite = outer.new TestSuite();
    int result = Mosaic_Testbench.run( suite );
    System.exit( result );
  }
}

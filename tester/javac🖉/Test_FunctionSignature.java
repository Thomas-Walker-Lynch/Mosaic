import com.ReasoningTechnology.Mosaic.*;

public class Test_FunctionSignature{

  public class TestSuite{

    public Boolean test_basic_signature(Mosaic_IO io){
      Boolean[] conditions = new Boolean[3];
      int i = 0;

      // Test resolve_parameter_types with primitives and non-primitives
      Mosaic_FunctionSignature sig1 = new Mosaic_FunctionSignature("test", new Object[]{42, "hello"});
      conditions[i++] = sig1.get_parameter_types()[0].equals(int.class);
      conditions[i++] = sig1.get_parameter_types()[1].equals(String.class);

      // Test method name retrieval
      conditions[i++] = sig1.get_method_name().equals("test");

      return Mosaic_Util.all(conditions);
    }

    public Boolean test_signature_equality(Mosaic_IO io){
      Boolean[] conditions = new Boolean[2];
      int i = 0;

      Mosaic_FunctionSignature sig1 = new Mosaic_FunctionSignature("test", new Object[]{42, "hello"});
      Mosaic_FunctionSignature sig2 = new Mosaic_FunctionSignature("test", new Object[]{42, "hello"});

      // Test equality and hash code
      conditions[i++] = sig1.equals(sig2);
      conditions[i++] = sig1.hashCode() == sig2.hashCode();

      return Mosaic_Util.all(conditions);
    }

    public Boolean test_null_arguments(Mosaic_IO io){
      Boolean[] conditions = new Boolean[2];
      int i = 0;

      Mosaic_FunctionSignature sig = new Mosaic_FunctionSignature("test", new Object[]{null});

      // Test parameter types for null argument
      conditions[i++] = sig.get_parameter_types()[0] == null;

      // Test method name retrieval
      conditions[i++] = sig.get_method_name().equals("test");

      return Mosaic_Util.all(conditions);
    }

    public Boolean test_to_string(Mosaic_IO io){
      Boolean[] conditions = new Boolean[1];
      int i = 0;

      Mosaic_FunctionSignature sig = new Mosaic_FunctionSignature("example", new Object[]{42, "text"});
      String expected = "example(int, String)";

      // Test string representation
      conditions[i++] = sig.toString().equals(expected);

      return Mosaic_Util.all(conditions);
    }

    public Boolean test_method_constructor(Mosaic_IO io){
      Boolean[] conditions = new Boolean[2];
      int i = 0;

      class TestClass{
        public void sampleMethod(int a, String b){}
      }

      try{
        Method method = TestClass.class.getDeclaredMethod("sampleMethod", int.class, String.class);
        Mosaic_FunctionSignature sig = new Mosaic_FunctionSignature(method);

        // Test method name
        conditions[i++] = sig.get_method_name().equals("sampleMethod");

        // Test parameter types
        conditions[i++] = sig.get_parameter_types()[0].equals(int.class) &&
                  sig.get_parameter_types()[1].equals(String.class);
      } catch (NoSuchMethodException e){
        return false; // This shouldn't happen in a controlled test
      }

      return Mosaic_Util.all(conditions);
    }
  }

  public static void main(String[] args){
    TestSuite suite = new Test_FunctionSignature().new TestSuite();
    int result = Mosaic_Testbench.run(suite);
    System.exit(result);
  }
}

/* --------------------------------------------------------------------------------
   Integration tests directly simulate the use cases for Mosaic_Testbench.
   Each test method validates a specific feature of Mosaic_Testbench ,including pass,
   fail ,error handling ,and I/O interactions.
*/
import java.util.Scanner;

import com.ReasoningTechnology.Mosaic.Mosaic_IO;
import com.ReasoningTechnology.Mosaic.Mosaic_IsPrimitive;
import com.ReasoningTechnology.Mosaic.Mosaic_Testbench;

import com.ReasoningTechnology.Mosaic.Mosaic_IO;
import com.ReasoningTechnology.Mosaic.Mosaic_Testbench;
import com.ReasoningTechnology.Mosaic.Mosaic_IsPrimitive;

public class IsPrimitive{

  public class TestSuite{


    public Boolean test_int_type(Mosaic_IO io){
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make(42);
      return mip.get_type().equals(int.class);
    }

    public Boolean test_boolean_type(Mosaic_IO io){
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make(true);
      return mip.get_type().equals(boolean.class);
    }

    public Boolean test_double_type(Mosaic_IO io){
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make(3.14);
      return mip.get_type().equals(double.class);
    }

    public Boolean test_string_type(Mosaic_IO io){
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make("hello");
      return mip.get_type().equals(String.class);
    }

    public Boolean test_object_type(Mosaic_IO io){
      Object obj = new Object();
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make(obj);
      return mip.get_type().equals(Object.class);
    }

    public Boolean test_char_type(Mosaic_IO io){
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make('a');
      return mip.get_type().equals(char.class);
    }

    public Boolean test_null_value(Mosaic_IO io){
      try{
        Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make(null);
        return mip.get_type() == null; // Should handle gracefully or throw
      } catch (Exception e){
        return false;
      }
    }

    public Boolean test_empty_string(Mosaic_IO io){
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make("");
      return mip.get_type().equals(String.class);
    }

    public Boolean test_blank_string(Mosaic_IO io){
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make("   ");
      return mip.get_type().equals(String.class);
    }

    // When passing arguments through Object types, there is no way
    // for the callee to know if the caller sent a primitive type or a
    // boxed value.  This is the point of having IsPrimitive.
    // IsPrimitive indicates that we really mean to send the primitive
    // type, though it appears in the box.
    public Boolean test_primitive_wrapper(Mosaic_IO io){
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make(Integer.valueOf(42));
      return mip.get_type().equals(int.class); 
    }

    public Boolean test_primitive_array(Mosaic_IO io){
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make(new int[]{1, 2, 3});
      return mip.get_type().equals(int[].class);
    }

    public Boolean test_object_array(Mosaic_IO io){
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make(new String[]{"a", "b", "c"});
      return mip.get_type().equals(String[].class);
    }

    public Boolean test_enum_type(Mosaic_IO io){
      enum TestEnum{ VALUE1, VALUE2 }
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make(TestEnum.VALUE1);
      return mip.get_type().equals(TestEnum.class);
    }

    public Boolean test_collection_type(Mosaic_IO io){
      java.util.List<Integer> list = java.util.Arrays.asList(1, 2, 3);
      Mosaic_IsPrimitive mip = Mosaic_IsPrimitive.make(list);
      return mip.get_type().getName().equals("java.util.Arrays$ArrayList");
    }

    public Boolean test_extreme_primitive_values(Mosaic_IO io){
      Mosaic_IsPrimitive mipMax = Mosaic_IsPrimitive.make(Integer.MAX_VALUE);
      Mosaic_IsPrimitive mipMin = Mosaic_IsPrimitive.make(Integer.MIN_VALUE);
      Mosaic_IsPrimitive mipNaN = Mosaic_IsPrimitive.make(Double.NaN);
      return mipMax.get_type().equals(int.class)
        && mipMin.get_type().equals(int.class)
        && mipNaN.get_type().equals(double.class);
    }
  }

  public static void main(String[] args){
    TestSuite suite = new IsPrimitive().new TestSuite();
    int result = Mosaic_Testbench.run(suite);
    System.exit(result);
  }
}

package tester;

public class TestClasses_2 {
  // Static fields
  public static int i_200;
  private static String s_201;

  // Instance fields
  public Integer i_202;
  private Integer i_203;

  // Nested class
  public static class Class_Nested_21 {
    public static Integer i_210;
    private static String s_211;

    public Integer i_212;
    private Integer i_213;

    public static void initialize_static_data() {
      i_210 = 210;
      s_211 = "Static Nested Private String";
    }

    public void initialize_instance_data() {
      i_212 = 212;
      i_213 = 213;
    }
  }

  public static void initialize_static_data() {
    i_200 = 200;
    s_201 = "Static Private String";
  }

  public void initialize_instance_data() {
    i_202 = 202;
    i_203 = 203;
  }
}

// Default (package-private) class
class DefaultTestClass {
  // Static fields
  public static double d_300;
  private static boolean b_301;

  // Instance fields
  public Float f_302;
  private Long l_303;

  // Nested class
  public static class Class_Nested_31 {
    public static Character c_310;
    private static String s_311;

    public Byte b_312;
    private Short s_313;

    public static void initialize_static_data() {
      c_310 = 'C';
      s_311 = "Default Static Nested Private String";
    }

    public void initialize_instance_data() {
      b_312 = (byte) 12;
      s_313 = (short) 313;
    }
  }

  public static void initialize_static_data() {
    d_300 = 300.5;
    b_301 = true;
  }

  public void initialize_instance_data() {
    f_302 = 302.5f;
    l_303 = 303L;
  }
}

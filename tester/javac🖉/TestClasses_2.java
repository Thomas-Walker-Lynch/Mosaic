package tester;

public class TestClasses_2{

  // Static fields
  public static int i_200;
  private static String s_201;

  // Instance fields
  public Integer i_202;
  private Integer i_203;

  // Nested class
  public static class Class_Nested_21{
    public static Integer i_210;
    private static String s_211;

    public Integer i_212;
    private Integer i_213;

    public static void initialize_static_fields(){
      i_210=210;
      s_211="Static Nested Private String";
    }

    public void initialize_instance_fields(){
      i_212=212;
      i_213=213;
    }
  }

  public static void initialize_static_fields(){
    i_200=200;
    s_201="Static Private String";
  }

  public void initialize_instance_fields(){
    i_202=202;
    i_203=203;
  }
}



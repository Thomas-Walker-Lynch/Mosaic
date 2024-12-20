import com.ReasoningTechnology.Mosaic.Mosaic_Dispatcher;

import tester.TestClasses_2;

public class Dispatcher_2{

  private static Mosaic_Dispatcher dispatcher;

  static{
    TestClasses_2.initialize_static_fields();
    dispatcher=new Mosaic_Dispatcher(TestClasses_2.class);
  }

  public static boolean test_publicStaticField(){
    try{
      Integer value=dispatcher.read("i_200");
      return value != null && value == 200;
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_privateStaticField(){
    try{
      String value=dispatcher.read("s_201");
      return value != null && value.equals("Static Private String");
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_publicInstanceField(){
    try{
      TestClasses_2 instance=dispatcher.make();
      instance.initialize_instance_fields();
      Integer value=dispatcher.read(instance,"i_202");
      return value != null && value == 202;
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_privateInstanceField(){
    try{
      TestClasses_2 instance=dispatcher.make();
      instance.initialize_instance_fields();
      Integer value=dispatcher.read(instance,"i_203");
      return value != null && value == 203;
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_writePublicStaticField(){
    try{
      dispatcher.write("i_200",300);
      Integer value=dispatcher.read("i_200");
      return value != null && value == 300;
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_writePrivateStaticField(){
    try{
      dispatcher.write("s_201","New Static Private String");
      String value=dispatcher.read("s_201");
      return value != null && value.equals("New Static Private String");
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_writePublicInstanceField(){
    try{
      TestClasses_2 instance=dispatcher.make();
      dispatcher.write(instance,"i_202",400);
      Integer value=dispatcher.read(instance,"i_202");
      return value != null && value == 400;
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean test_writePrivateInstanceField(){
    try{
      TestClasses_2 instance=dispatcher.make();
      dispatcher.write(instance,"i_203",500);
      Integer value=dispatcher.read(instance,"i_203");
      return value != null && value == 500;
    }catch(Throwable t){
      t.printStackTrace();
      return false;
    }
  }

  public static boolean run(){
    try{
      boolean result=true;

      // Existing read tests
      System.out.println("");
      System.out.println("running test: publicStaticField");
      if(Boolean.TRUE.equals(test_publicStaticField())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result=false;
      }

      System.out.println("");
      System.out.println("running test: privateStaticField");
      if(Boolean.TRUE.equals(test_privateStaticField())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result=false;
      }

      System.out.println("");
      System.out.println("running test: publicInstanceField");
      if(Boolean.TRUE.equals(test_publicInstanceField())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result=false;
      }

      System.out.println("");
      System.out.println("running test: privateInstanceField");
      if(Boolean.TRUE.equals(test_privateInstanceField())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result=false;
      }

      // New write tests
      System.out.println("");
      System.out.println("running test: writePublicStaticField");
      if(Boolean.TRUE.equals(test_writePublicStaticField())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result=false;
      }

      System.out.println("");
      System.out.println("running test: writePrivateStaticField");
      if(Boolean.TRUE.equals(test_writePrivateStaticField())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result=false;
      }

      System.out.println("");
      System.out.println("running test: writePublicInstanceField");
      if(Boolean.TRUE.equals(test_writePublicInstanceField())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result=false;
      }

      System.out.println("");
      System.out.println("running test: writePrivateInstanceField");
      if(Boolean.TRUE.equals(test_writePrivateInstanceField())){
        System.out.println("passed");
      }else{
        System.out.println("FAILED");
        result=false;
      }

      System.out.println("");
      return result;

    }catch(Exception e){
      System.out.println("Exception in Dispatcher_2 test:");
      e.printStackTrace();
      return false;
    }
  }

  public static void main(String[] args){
    if(run()){
      System.exit(0);
    }else{
      System.exit(1);
    }
  }
}

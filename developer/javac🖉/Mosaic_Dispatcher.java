package com.ReasoningTechnology.Mosaic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/*--------------------------------------------------------------------------------
  Is a signature for a Method

  The envisioned use case is the 'method signature' -> handle map.

  Perhaps the existing method signature in the Reflection library can
  replace this, but most of the work done here is the formatting done
  in the constructors.
*/

class MethodSignature{
  // header
  private Class<?> return_type;
  private String class_name;
  private String method_name;

  // variable length parameter type list
  private Class<?>[] parameter_type_list;

  // field access and strings
  //
    public String get_class_name(){
      return class_name;
    }

    public String get_method_name(){
      return method_name;
    }

    public Class<?> get_return_type(){
      return return_type;
    }

    public Class<?>[] get_parameter_type_list(){
      return parameter_type_list;
    }

    public String to_string_return_type(){
      return get_return_type() != null ? get_return_type().getSimpleName() : "null";
    }

    public String to_string_parameter_type_list(){
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < get_parameter_type_list().length; i++){
        sb.append(get_parameter_type_list()[i] != null ? get_parameter_type_list()[i].getSimpleName() : "null");
        if (i < get_parameter_type_list().length - 1) sb.append(" ,");
      }
      return sb.toString();
    }

    public String to_string_signature_header(){
      return to_string_return_type() + " " + get_class_name() + "." + get_method_name();
    }

  // constructors
  //
    private void init_header
      (
       Class<?> return_type
       ,String class_name 
       ,String method_name 
       ){
      this.return_type = return_type;
      this.class_name = class_name;
      this.method_name = method_name;
    }

    // Signature when given a Method.
    // Used when putting methods into the method signature to handle map.
    public MethodSignature(Method method){
      init_header
        (
         method.getReturnType()
         ,method.getDeclaringClass().getName()
         ,method.getName()
         );
      this.parameter_type_list = method.getParameterTypes();
    }

    // Signature when given a parameter type list.
    // Used putting constructors into the signature to handle map.
    public MethodSignature
      (
       Class<?> return_type 
       ,String class_name 
       ,String method_name 
       ,Class<?>[] parameter_type_list 
       ){
      init_header(return_type ,class_name ,method_name);
      this.parameter_type_list = parameter_type_list;
    }

    // Signature when given an argument value list.
    // Used by `invoke`.
    public MethodSignature
      ( 
       Class<?> return_type 
       ,String class_name 
       ,String method_name 
       ,Object[] arg_list
        ){
      init_header(return_type ,class_name ,method_name);

      // Set the signature parameter type to the argument type.
      // No automatic conversions are applied.
      this.parameter_type_list = new Class<?>[arg_list.length]; // Initialize the array
      for(int i = 0; i < arg_list.length; i++){
        if(arg_list[i] instanceof Mosaic_IsPrimitive){
          parameter_type_list[i] =( (Mosaic_IsPrimitive) arg_list[i] ).get_type();
        } else if(arg_list[i] != null){
          parameter_type_list[i] = arg_list[i].getClass();
        } else{
          parameter_type_list[i] = null;
        }
      }
    }

  // standard interface
  //
    @Override
    public String toString(){
      return to_string_signature_header() + "(" + to_string_parameter_type_list() + ")";
    }

    @Override
    public boolean equals(Object o){
      if(this == o) return true;
      if(o == null) return false;
      if(o.getClass() != MethodSignature.class) return false;

      MethodSignature signature = (MethodSignature) o;

      return 
        get_class_name().equals(signature.get_class_name()) 
        && get_method_name().equals(signature.get_method_name())
        && get_return_type().equals(signature.get_return_type())
        && Arrays.equals(get_parameter_type_list() ,signature.get_parameter_type_list());
    }

    @Override
    public int hashCode(){
      int result = get_class_name().hashCode();
      result = 31 * result + get_method_name().hashCode();
      result = 31 * result + get_return_type().hashCode();
      result = 31 * result + Arrays.hashCode(get_parameter_type_list());
      return result;
    }

}

/*--------------------------------------------------------------------------------
This is a method signature to callable method handle dictionary.

In the envisioned use case there is one such dictionary per
Dispatcher instance.

 */
class MethodSignature_To_Handle_Map{

  // Static test messaging
  //
    private static boolean test = false;
    public static void test_switch(boolean test){
      if (MethodSignature_To_Handle_Map.test && !test){
        test_print("MethodSignature_To_Handle_Map:: test messages off");
      }
      if (!MethodSignature_To_Handle_Map.test && test){
        test_print("MethodSignature_To_Handle_Map:: test messages on");
      }
      MethodSignature_To_Handle_Map.test = test;
    }
    private static void test_print(String message){
      if(test){
        System.out.println(message);
      }
    }

  // instance data
  //
    private Map<MethodSignature ,MethodHandle> map;

  // field access and strings
  // 

  // constructors
  //
    public MethodSignature_To_Handle_Map(){
      map = new HashMap<>();
    }

  // methods for adding entries
  //
    public void add_class(Class<?> class_metadata){
      test_print("MethodSignature_To_Handle_Map::add_class adding methods");
      add_methods(class_metadata);

      test_print("MethodSignature_To_Handle_Map::add_class adding constructors");
      add_constructors(class_metadata);

      test_print("MethodSignature_To_Handle_Map::add_class adding fields");
      // add_fields(class_metadata);
    }

    private void add_entry(MethodSignature key ,MethodHandle value){
      test_print
        (
         "(add_entry:: " + "(key " + key + ") " + "(value " + value + ")" + ")"
         );
      map.put(key ,value);
    }

    public void add_methods(Class<?> class_metadata){
      try{
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandles.Lookup private_lookup = MethodHandles.privateLookupIn(class_metadata,lookup);

        for(Method method:class_metadata.getDeclaredMethods()){
          try{
            Class<?>[] parameter_type_list=method.getParameterTypes();
            MethodSignature signature=new MethodSignature(
              method.getReturnType(),
              class_metadata.getName(),
              method.getName(),
              parameter_type_list
            );

            MethodType method_type=MethodType.methodType(method.getReturnType(),parameter_type_list);
            MethodHandle method_handle;

            if((method.getModifiers() & Modifier.STATIC) != 0){
              method_handle = private_lookup.findStatic(class_metadata ,method.getName() ,method_type);
            }else{
              method_handle = private_lookup.findSpecial(class_metadata ,method.getName() ,method_type ,class_metadata);
            }

            add_entry(signature,method_handle);

          }catch(IllegalAccessException|NoSuchMethodException e){
            System.err.println
              (
               "Mosaic_Dispatcher::add_methods unexpectedly failed to register method: " 
               + method.getName() + " in class: " + class_metadata.getName()
               );
            e.printStackTrace();
          }
        }

      }catch(IllegalAccessException e){
        System.err.println("Mosaic_Dispatcher::add_methods unexpectedly failed to initialize lookup for class: "+class_metadata.getName());
        e.printStackTrace();
      }
    }

    public void add_constructors(Class<?> class_metadata){
      try{

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandles.Lookup private_lookup = MethodHandles.privateLookupIn(class_metadata ,lookup);

        for( Constructor<?> constructor : class_metadata.getDeclaredConstructors() ){
          try{

            Class<?>[] parameter_type_list = constructor.getParameterTypes();
            MethodType method_type = MethodType.methodType(void.class ,parameter_type_list);
            MethodHandle constructor_handle = private_lookup.findConstructor(class_metadata ,method_type);

            // Signature for constructors: <init> with parameter types
            MethodSignature signature = new MethodSignature
              (
               void.class
               ,class_metadata.getName() 
               ,"<init>" 
               ,parameter_type_list
               );
            add_entry(signature ,constructor_handle);

          }catch(IllegalAccessException|NoSuchMethodException e){
            System.err.println("Mosaic_Dispatcher::add_constructors unexpectedly failed to register constructor: " + class_metadata.getName());
            e.printStackTrace();
          }
        }

      }catch(IllegalAccessException e){
        System.err.println("Mosaic_Dispatcher::add_constructors unexpectedly failed to initialize lookup for class: " + class_metadata.getName());
        e.printStackTrace();
      }
    }

    public void add_fields(Class<?> class_metadata){
      try{
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandles.Lookup private_lookup = MethodHandles.privateLookupIn(class_metadata ,lookup);

        for(Field field : class_metadata.getDeclaredFields()){
          try{
            // Field Metadata
            String field_name = field.getName();
            Class<?> field_type = field.getType();

            // Create <read> MethodHandle
            MethodHandle read_handle = private_lookup.unreflectGetter(field);
            MethodSignature read_signature = new MethodSignature
              (
               field_type 
               ,class_metadata.getName() 
               ,"<read:" + field_name + ">"
               ,new Class<?>[]{}
               );
            add_entry(read_signature ,read_handle);

            // Create <write> MethodHandle
            MethodHandle write_handle = private_lookup.unreflectSetter(field);
            MethodSignature write_signature = new MethodSignature
              (
               void.class
               ,class_metadata.getName()
               ,"<write:" + field_name + ">"
                ,new Class<?>[]{field_type}
               );
            add_entry(write_signature ,write_handle);

          }catch(IllegalAccessException e){
            System.err.println("Mosaic_Dispatcher::add_fields unexpectedly failed to register field: " + field.getName());
            e.printStackTrace();
          }
        }
      }catch(IllegalAccessException e){
        System.err.println("Mosaic_Dispatcher::add_fields unexpectedly failed to initialize lookup for class: " + class_metadata.getName());
        e.printStackTrace();
      }
    }


  // methods for looking up handles
  //
    public MethodHandle lookup(MethodSignature s){
      return map.get(s);
    }

  // standard interface
  //
    @Override
    public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append("MethodSignature_To_Handle_Map:{").append(System.lineSeparator());

      for(Map.Entry<MethodSignature ,MethodHandle> entry : map.entrySet()){
        sb.append("  ")
          .append(entry.getKey().toString()) // MethodSignature's toString
          .append(" -> ")
          .append(entry.getValue().toString()) // MethodHandle's toString
          .append(System.lineSeparator());
      }

      sb.append("}");
      return sb.toString();
    }

}

/*--------------------------------------------------------------------------------
  Given a class, dispatches calls to methods.

*/
public class Mosaic_Dispatcher{

  // Static test messaging
  //
    private static boolean test = false;
    public static void test_switch(boolean test){
      if(Mosaic_Dispatcher.test && !test){
        test_print("Mosaic_Dispatcher:: test messages off");
      }
      if(!Mosaic_Dispatcher.test && test){
        test_print("Mosaic_Dispatcher:: test messages on");
        MethodSignature_To_Handle_Map.test_switch(true);
      }
      Mosaic_Dispatcher.test = test;
    }
    public static void test_print(String message){
      if(test){
        System.out.println(message);
      }
    }

  // instance data
  //
    private MethodSignature_To_Handle_Map map;
    private Class<?> target;


  // field access and strings
  //
    public Class<?> get_target(){
      return target;
    }

    public MethodSignature_To_Handle_Map get_map(){
      return map;
    }

    public String to_string_target(){
        return target != null ? target.getName() : "null";
    }

  // constructors
  //

    // construct given the class metadata for the target class
    public Mosaic_Dispatcher(Class<?> target){
      this.map = new MethodSignature_To_Handle_Map();
      this.target = target;
      test_print("Mosaic_Dispatcher::<init> mapping methods given class_metadata object: " + to_string_target());
      this.map.add_class(target);
    }

    // Constructor accepting a fully qualified class name of the target class
    public Mosaic_Dispatcher(String fully_qualified_class_name) throws ClassNotFoundException{
      this.map = new MethodSignature_To_Handle_Map();
      this.target = Class.forName(fully_qualified_class_name);
      test_print("Mosaic_Dispatcher::<init> mapping methods from class specified by string: \"" + to_string_target() + "\"");
      this.map.add_class(target);
    }

  // methods unique to the class
  //
    public <T> T read(String field_name){
      try{
        test_print("Call to Mosaic_Dispatcher::read( field_name )");

        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(target ,MethodHandles.lookup());
        Field field = target.getDeclaredField(field_name);
        MethodHandle handle = lookup.unreflectGetter(field);
        return (T) handle.invoke();

      }catch(NoSuchFieldException | IllegalAccessException e){
        System.out.println("Mosaic_Dispatcher::read of static exception:");
        e.printStackTrace();
        return null;
      }catch(Throwable t){
        System.out.println("Mosaic_Dispatcher::read of static exception:");
        t.printStackTrace();
        return null;
      }
    }

    public <T> T read(Object instance ,String field_name){
      try{
        test_print("Call to Mosaic_Dispatcher::read(instance ,field_name)");

        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(target ,MethodHandles.lookup());
        Field field = target.getDeclaredField(field_name);

        if(instance == null || !target.isInstance(instance)){
          throw new IllegalArgumentException
            (
             "Mosaic_Dispatcher::read provided instance is not of target type: "
             + target.getName()
             + ", but received: "
             + (instance == null ? "null" : instance.getClass().getName())
             );
        }
        MethodHandle handle = lookup.unreflectGetter(field);
        return (T) handle.bindTo(instance).invoke();

      }catch(NoSuchFieldException | IllegalAccessException e){
        System.out.println("Mosaic_Dispatcher::read exception:");
        e.printStackTrace();
        return null;
      }catch(Throwable t){
        System.out.println("Mosaic_Dispatcher::read exception:");
        t.printStackTrace();
        return null;
      }
    }

    public <T> void write(String field_name ,T value){
      try{
        test_print("Call to Mosaic_Dispatcher::write(field_name ,value)");

        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(target ,MethodHandles.lookup());
        Field field = target.getDeclaredField(field_name);
        MethodHandle handle = lookup.unreflectSetter(field);
        handle.invoke(value);

      }catch(NoSuchFieldException | IllegalAccessException e){
        System.out.println("Mosaic_Dispatcher::write static field exception:");
        e.printStackTrace();
      }catch(Throwable t){
        System.out.println("Mosaic_Dispatcher::write static field exception:");
        t.printStackTrace();
      }
    }

    public <T> void write(Object instance ,String field_name ,T value){
      try{
        test_print("Call to Mosaic_Dispatcher::write(instance ,field_name ,value)");

        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(target ,MethodHandles.lookup());
        Field field = target.getDeclaredField(field_name);

        if(instance == null || !target.isInstance(instance)){
          throw new IllegalArgumentException
            (
             "Mosaic_Dispatcher::write provided instance is not of target type: "
             + target.getName()
             + ", but received: "
             + (instance == null ? "null" : instance.getClass().getName())
             );
        }
        MethodHandle handle = lookup.unreflectSetter(field);
        handle.bindTo(instance).invoke(value);

      }catch(NoSuchFieldException | IllegalAccessException e){
        System.out.println("Mosaic_Dispatcher::write instance field exception:");
        e.printStackTrace();
      }catch(Throwable t){
        System.out.println("Mosaic_Dispatcher::write instance field exception:");
        t.printStackTrace();
      }
    }

    @SuppressWarnings("unchecked")
    public <T> T make(Object... arg_list){
      test_print("Call to Mosaic_Dispatcher::make");

      // Use dispatch_1 to invoke the constructor
      Object result = dispatch_1(
          null         // no instance for constructor
          ,void.class  // return type for signature matching
          ,"<init>"    // constructors are always named `<init>` in Java
          ,arg_list
      );

      // Cast the result to the target type
      return (T) target.cast(result);
    }

    // dispatch static methods
    public <T> T dispatch
      (
       Class<T> return_type
       ,String method_name
       ,Object... arg_list
       ){
      test_print("Call to Mosaic_Dispatcher::dispatch for a static method.");
      return dispatch_1
        (
         null          // No instance for static methods
         ,return_type   // Return type
         ,method_name   // Method name
         ,arg_list       // Argument list
         );
    }

    // dispatch instance binded methods
    public <T> T dispatch
      (
       Object instance,
       Class<T> return_type,
       String method_name,
       Object... arg_list
       ){
      test_print("Call to Mosaic_Dispatcher::dispatch for a method bound to an instance.");
      if(instance == null || !target.isInstance(instance)){
        throw new IllegalArgumentException
          (
           "Provided instance is not of target type: "
           + target.getName()
           + ", but received: "
           + (instance == null ? "null" : instance.getClass().getName())
           );
      }
      return dispatch_1(instance ,return_type ,method_name ,arg_list);
    }

    @SuppressWarnings("unchecked")
    private <T> T dispatch_1(
        Object instance,
        Class<T> return_type,
        String method_name,
        Object... arg_list
    ){
      try{
        if(arg_list == null){
          arg_list = new Object[0]; // Treat null as an empty argument list
        }

        // Resolve method/constructor signature
        MethodSignature signature = new MethodSignature(
            return_type,
            to_string_target(),
            method_name,
            arg_list
        );
        test_print("dispatch_1:: signature key:" + signature.toString());

        MethodHandle handle = map.lookup(signature);

        if(handle == null){
          throw new NoSuchMethodException("No method or constructor found for signature: " + signature.toString());
        }

        // Strip off any IsPrimitive tags
        Object[] untagged_arg_list = new Object[arg_list.length];
        for(int i = 0; i < arg_list.length; i++){
          if(arg_list[i] instanceof Mosaic_IsPrimitive){
            untagged_arg_list[i] = ((Mosaic_IsPrimitive) arg_list[i]).get_value();
          }else{
            untagged_arg_list[i] = arg_list[i];
          }
        }

        // call the Handle and cast the result
        //
          if("<init>".equals(method_name)){
            // Constructor invocation 
            return (T) target.cast(handle.invokeWithArguments(untagged_arg_list));
          }

          if(return_type == void.class || return_type == null){
            if(instance == null){ 
              // static method call
              handle.invokeWithArguments(untagged_arg_list); 
            }else{
              // method bound to instance call
              handle.bindTo(instance).invokeWithArguments(untagged_arg_list); 
            }
            return null; // generic code void return type must return null
          }

          Object result;
          if(instance == null){ 
            // static method call
            result = handle.invokeWithArguments(untagged_arg_list);
          }else{
            // method bound to instance call
            result = handle.bindTo(instance).invokeWithArguments(untagged_arg_list); // Instance method
          }

          if(result == null) return null;

          // Handle primitive return types explicitly
          if(return_type.isPrimitive()){
            if(return_type == boolean.class) return(T)(Boolean) result;
            if(return_type == int.class) return(T)(Integer) result;
            if(return_type == double.class) return(T)(Double) result;
            if(return_type == float.class) return(T)(Float) result;
            if(return_type == long.class) return(T)(Long) result;
            if(return_type == short.class) return(T)(Short) result;
            if(return_type == byte.class) return(T)(Byte) result;
            if(return_type == char.class) return(T)(Character) result;
          }

          // For non-primitives, cast normally
          return return_type.cast(result);

      }catch(Throwable t){
        System.out.println("Mosaic_Dispatcher::dispatch exception:");
        t.printStackTrace();
        return null;
      }
    }

  // standard interface
  //
    @Override
    public String toString(){
      return 
        "Mosaic_Dispatcher{" 
        + "target=" 
        + to_string_target() 
        + " ,map=" 
        + map.toString() 
        + "}"
        ;
    }

}

package com.ReasoningTechnology.Mosaic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class FunctionSignature {
  private final String method_name;
  private final Class<?>[] parameter_types;

  public FunctionSignature(String method_name ,Class<?>[] parameter_types){
    this.method_name = method_name;
    this.parameter_types = parameter_types;
  }

  public FunctionSignature(String method_name ,Object[] args){
    this.method_name = method_name;
    this.parameter_types = resolve_parameter_types(args);
  }

  public FunctionSignature(Method method){
    this.method_name = method.getName();
    this.parameter_types = method.getParameterTypes();
  }

  private static Class<?>[] resolve_parameter_types(Object[] args){
    Class<?>[] parameter_types = new Class<?>[args.length];
    for( int i = 0; i < args.length; i++ ){
      if( args[i] instanceof isPrimitive ){
        parameter_types[i] = ((isPrimitive) args[i]).get_type();
      }else if( args[i] != null ){
        parameter_types[i] = args[i].getClass();
      }else{
        parameter_types[i] = null;
      }
    }
    return parameter_types;
  }

  public String get_method_name(){
    return method_name;
  }

  public Class<?>[] get_parameter_types(){
    return parameter_types;
  }

  @Override
  public boolean equals(Object o){
    if( this == o ) return true;
    if( o == null || getClass() != o.getClass() ) return false;
    FunctionSignature signature = (FunctionSignature) o;
    return method_name.equals(signature.method_name) &&
           java.util.Arrays.equals(parameter_types ,signature.parameter_types);
  }

  @Override
  public int hashCode(){
    int result = method_name.hashCode();
    result = 31 * result + java.util.Arrays.hashCode(parameter_types);
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(method_name).append("(");
    for (int i = 0; i < parameter_types.length; i++) {
      sb.append(parameter_types[i].getSimpleName());
      if (i < parameter_types.length - 1) sb.append(", ");
    }
    sb.append(")");
    return sb.toString();
  }


}

class FunctionSignature_To_Handle_Map {
  private final Map<FunctionSignature ,MethodHandle> map;

  public FunctionSignature_To_Handle_Map(Class<?> class_metadata){
    this.map = new HashMap<>();
    add_class(class_metadata);
  }

  private void add_class(Class<?> class_metadata){
    try{
      MethodHandles.Lookup lookup = MethodHandles.lookup();
      MethodHandles.Lookup private_lookup = MethodHandles.privateLookupIn(class_metadata ,lookup);

      for( Method method : class_metadata.getDeclaredMethods() ){
        Class<?>[] parameter_types = method.getParameterTypes();
        MethodType method_type = MethodType.methodType(
          method.getReturnType() ,parameter_types
        );
        MethodHandle method_handle;

        if( (method.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0 ){
          method_handle = private_lookup.findSpecial(class_metadata ,method.getName() ,method_type ,class_metadata);
        }else{
          method_handle = lookup.findVirtual(class_metadata ,method.getName() ,method_type);
        }

        FunctionSignature signature = new FunctionSignature(method);
        map.put(signature ,method_handle);
      }
    }catch(Throwable t){
      System.out.println("FunctionSignature_To_Handle_Map::add_class exception:");
      t.printStackTrace();
    }
  }

  public MethodHandle get_handle(FunctionSignature signature){
    return map.get(signature);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("FunctionSignature to MethodHandle Map:\n");
    for (Map.Entry<FunctionSignature, MethodHandle> entry : map.entrySet()) {
      sb.append("  ").append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
    }
    return sb.toString();
  }

}

public class Mosaic_AllMethodsPublicProxy {
  private final FunctionSignature_To_Handle_Map map;
  private final Class<?> target_type;

  public Mosaic_AllMethodsPublicProxy(Class<?> target_type){
    this.target_type = target_type;
    this.map = new FunctionSignature_To_Handle_Map(target_type);
  }

  public Object invoke(Object target_instance, String method_name, Object... arg_list) {
    try {
      if (target_instance == null || !target_type.isInstance(target_instance)) {
        System.out.println("Warning: Instance is not of type " + target_type.getName());
      }

      // Resolve the function signature
      FunctionSignature signature = new FunctionSignature(method_name, arg_list);
      MethodHandle handle = map.get_handle(signature);
      System.out.println( "invoked with signature: " + signature.toString() );

      if (handle == null) {
        throw new NoSuchMethodException("No method found for signature: " + signature.get_method_name());
      }

      // Unwrap isPrimitive instances in arguments
      Object[] unwrapped_args = new Object[arg_list.length];
      for (int i = 0; i < arg_list.length; i++) {
        if (arg_list[i] instanceof isPrimitive) {
          unwrapped_args[i] = ((isPrimitive) arg_list[i]).get_value();
        } else {
          unwrapped_args[i] = arg_list[i];
        }
      }

      return handle.bindTo(target_instance).invokeWithArguments(unwrapped_args);

    } catch (Throwable t) {
      System.out.println("Mosaic_AllMethodsPublicProxy::invoke exception:");
      t.printStackTrace();
      return null;
    }
  }

  public FunctionSignature_To_Handle_Map get_map() {
    return map;
  }

}

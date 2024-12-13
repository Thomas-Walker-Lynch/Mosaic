package com.ReasoningTechnology.Mosaic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;


public class Mosaic_Util{

  // Linear search with a predicate
  public static <T> T find( T[] elements ,Predicate<T> predicate ){
    for( T element : elements ){
      if( predicate.test( element )) return element; // Return the first match
    }
    return null; // Return null if no element satisfies the predicate
  }

  // True when it does a search and finds a true value; otherwise false.
  public static Boolean exists( Object[] elements ){
    return elements.length > 0 && find( elements ,element -> (element instanceof Boolean) && (Boolean) element ) != null;
  }

  // True when it does a search and does not find a false value; otherwise false.
  public static Boolean all( Object[] elements ){
    return elements.length > 0 && find( elements ,element -> !(element instanceof Boolean) || !(Boolean) element ) == null;
  }

  public static void all_set_false( Boolean[] condition_list ){
    int i = 0;
    while(i < condition_list.length){
      condition_list[i] = false;
      i++;
    }
  }

  public static void all_set_true( Boolean[] condition_list ){
    int i = 0;
    while(i < condition_list.length){
      condition_list[i] = true;
      i++;
    }
  }

  public static String iso_utc_time(){
    return Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
  }

  public static Object make_all_public_proxy(Class<?> class_metadata) {
    try {
      // Validate that the class implements at least one interface
      Class<?>[] interfaces = class_metadata.getInterfaces();
      if(interfaces.length == 0){
        throw new IllegalArgumentException
          (
           "The class " + class_metadata.getName() + " does not implement any interfaces."
           );}

      // Create a Lookup for the target class that gives us access to private members
      MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(class_metadata, MethodHandles.lookup());

      InvocationHandler handler = (proxy_object, method, args) -> {
        // Find the corresponding method in the target class
        Method target_method = class_metadata.getDeclaredMethod(method.getName(), method.getParameterTypes());

        // Convert the reflective method into a MethodHandle
        MethodHandle mh = lookup.unreflect(target_method);

        // Create an instance of the target class
        Constructor<?> ctor = class_metadata.getDeclaredConstructor();
        MethodHandle ctorHandle = lookup.unreflectConstructor(ctor);
        Object real_instance = ctorHandle.invoke();

        // Invoke the target method via the MethodHandle
        if (args == null) {
          args = new Object[0];
        }


        // replaces former return code:
        Object result;
        if (args == null || args.length == 0) {
          // No arguments, just the instance
          result = mh.invokeWithArguments(real_instance);
        } else {
          // One argument for the instance + all method arguments
          Object[] callArgs = new Object[args.length + 1];
          callArgs[0] = real_instance;
          System.arraycopy(args, 0, callArgs, 1, args.length);
          result = mh.invokeWithArguments(callArgs);
        }
        return result;

        // former return code
        //   return mh.invokeWithArguments(real_instance, args);

      };

      // Create the proxy
      return Proxy.newProxyInstance
        (
         class_metadata.getClassLoader(),
         interfaces,
         handler
         );

    } catch (Throwable e) {
      Mosaic_Logger.message("make_all_public_proxy", "Failed to create proxy: " + e.getMessage());
      throw new RuntimeException("Failed to create proxy for class: " + class_metadata.getName(), e);
    }
  }


}

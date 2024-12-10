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

  public static Object make_all_public_proxy( Class<?> class_metadata ) {
    try {
      // Validate that the class implements at least one interface
      if( class_metadata.getInterfaces().length == 0 ) {
        throw new IllegalArgumentException(
          "The class " + class_metadata.getName() + " does not implement any interfaces."
        );
      }

      Object proxy = Proxy.newProxyInstance(
         class_metadata.getClassLoader()
        ,class_metadata.getInterfaces()
        ,(proxy_object ,method ,args) -> {
            // Ensure the target method is accessible
            Method target_method = class_metadata.getDeclaredMethod(
               method.getName()
              ,method.getParameterTypes()
            );
            target_method.setAccessible( true );

            // Create an instance of the target class
            Object real_instance = class_metadata.getDeclaredConstructor().newInstance();

            // Invoke the target method
            return target_method.invoke( real_instance ,args );
         }
      );

      return proxy;

    } catch( Throwable e ) {
      // Log the error to assist with debugging
      Mosaic_Logger.message( "make_all_public_proxy" ,"Failed to create proxy: " + e.getMessage() );
      throw new RuntimeException(
         "Failed to create proxy for class: " + class_metadata.getName() ,e
      );
    }
  }

}

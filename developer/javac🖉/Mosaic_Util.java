package com.ReasoningTechnology.Mosaic;

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



}

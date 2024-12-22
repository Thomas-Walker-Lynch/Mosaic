package com.ReasoningTechnology.Mosaic;

import java.util.function.Predicate;

public class Mosaic_Quantifier{

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
  // Hence, all true for the empty set is false, which is appropriate for testing.
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

}

package com.ReasoningTechnology.Mosaic;

public class Mosaic_IsPrimitive {
  private final Object value;

  public Mosaic_IsPrimitive(Object value){
    this.value = value;
  }

  public static Mosaic_IsPrimitive make(Object value) {
    return new Mosaic_IsPrimitive(value);
  }

  public Object get_value(){
    return value;
  }

  public Class<?> get_type(){
    if( value instanceof Integer ) return int.class;
    if( value instanceof Boolean ) return boolean.class;
    if( value instanceof Double ) return double.class;
    if( value instanceof Float ) return float.class;
    if( value instanceof Long ) return long.class;
    if( value instanceof Short ) return short.class;
    if( value instanceof Byte ) return byte.class;
    if( value instanceof Character ) return char.class;
    return value.getClass();
  }

}


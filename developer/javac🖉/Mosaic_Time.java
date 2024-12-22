package com.ReasoningTechnology.Mosaic;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Mosaic_Time{

  public static String iso_UTC_time(){
    return Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
  }

}

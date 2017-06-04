package uk.co.sharebear.portfolio.ordermanagement.api.library.sparkjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javaslang.jackson.datatype.JavaslangModule;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class JsonTransformer {
  private static ObjectMapper MAPPER = new ObjectMapper()
      .disable(WRITE_DATES_AS_TIMESTAMPS)
      .registerModule(new JavaTimeModule())
      .registerModule(new JavaslangModule());

  public static String toJson(Object model) throws Exception {
    return MAPPER.writeValueAsString(model);
  }
}

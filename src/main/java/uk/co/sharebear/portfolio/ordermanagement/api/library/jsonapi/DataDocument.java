package uk.co.sharebear.portfolio.ordermanagement.api.library.jsonapi;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DataDocument {
  Object data;

  public static DataDocument data(Object data) {
    return new DataDocument(data);
  }
}

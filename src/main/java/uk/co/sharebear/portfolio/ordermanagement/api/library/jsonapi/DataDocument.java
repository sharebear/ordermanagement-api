package uk.co.sharebear.portfolio.ordermanagement.api.library.jsonapi;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DataDocument<T> {
  T data;

  public static <T> DataDocument<T> data(T data) {
    return new DataDocument<>(data);
  }
}

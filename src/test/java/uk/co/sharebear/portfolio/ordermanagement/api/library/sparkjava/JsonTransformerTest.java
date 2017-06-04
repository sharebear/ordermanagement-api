package uk.co.sharebear.portfolio.ordermanagement.api.library.sparkjava;

import org.junit.Test;

import java.time.LocalDate;

import javaslang.control.Option;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JsonTransformerTest {

  @Test
  public void shouldUnwrapJavaslangOptionValue() throws Exception {
    final String result = JsonTransformer.toJson(Option.of("testvalue"));
    final String expected = "\"testvalue\"";
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void shouldUnwrapJavaslangOptionNone() throws Exception {
    final String result = JsonTransformer.toJson(Option.none());
    final String expected = "null";
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void shouldSerializeLocalDateWithISOFormat() throws Exception {
    final LocalDate testDate = LocalDate.of(2021, 3, 15);
    final String result = JsonTransformer.toJson(testDate);
    final String expected = "\"2021-03-15\"";
    assertThat(result, is(equalTo(expected)));
  }
}

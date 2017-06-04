package uk.co.sharebear.portfolio.ordermanagement.api.library.sparkjava;

import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.Test;

import java.io.IOException;
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
  public void shouldDeserializeNullOptionValueAsOptionNone() throws IOException {
    final Option result = JsonTransformer.fromJson("null", Option.class);

    // replace with assertion library if time to mess with mvn
    assertThat(result.isEmpty(), is(true));
  }

  @Test
  public void shouldDeserializeNonNullOptionValueAsOptionOf() throws IOException {
    final Option<String> result = JsonTransformer.fromJson(
        "\"testvalue\"",
        new TypeReference<Option<String>>() {
        }
    );

    // replace with assertion library if time to mess with mvn
    assertThat(result.isDefined(), is(true));
    assertThat(result.get(), is(equalTo("testvalue")));
  }

  @Test
  public void shouldSerializeLocalDateWithISOFormat() throws Exception {
    final LocalDate testDate = LocalDate.of(2021, 3, 15);
    final String result = JsonTransformer.toJson(testDate);
    final String expected = "\"2021-03-15\"";
    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void shouldDeserializeLocalDateWithISOFormat() throws IOException {
    final LocalDate result = JsonTransformer.fromJson("\"2021-03-15\"", LocalDate.class);
    final LocalDate expected = LocalDate.of(2021, 3, 15);
    assertThat(result, is(equalTo(expected)));
  }
}

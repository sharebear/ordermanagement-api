package uk.co.sharebear.portfolio.ordermanagement.api.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import javaslang.collection.List;
import javaslang.control.Option;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderTest {
  private static final UUID ORDER_ID = UUID.randomUUID();

  @Test
  public void shouldOnlyUpdateVersionField() {
    final Order initial = createOrderWithVersion(1);
    final Order result = initial.withVersion(2);

    final Order expected = createOrderWithVersion(2);
    assertThat(result, is(equalTo(expected)));
  }

  private Order createOrderWithVersion(int version) {
    return new Order(
        ORDER_ID,
        Option.of(version),
        "Customer name",
        "94832345",
        "bob@bobsburgers.com",
        "somewhere",
        "somewhere else",
        List.of(MovingService.CLEANING),
        LocalDate.now(),
        Option.none()
    );
  }

}

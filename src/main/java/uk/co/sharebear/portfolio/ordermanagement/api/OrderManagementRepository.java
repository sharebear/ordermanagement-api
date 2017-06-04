package uk.co.sharebear.portfolio.ordermanagement.api;

import uk.co.sharebear.portfolio.ordermanagement.api.domain.Order;

import java.time.LocalDate;
import java.util.UUID;

import javaslang.collection.List;
import javaslang.control.Option;

import static uk.co.sharebear.portfolio.ordermanagement.api.domain.MovingService.MOVING;

class OrderManagementRepository {

  List<Order> getOrders() {
    return List.of(stubOrder());
  }

  void save(Order order) {

  }

  private static Order stubOrder() {
    return new Order(
        UUID.randomUUID(),
        Option.of(1),
        "Jonathan Share",
        "83212343",
        "jonny@someplace.co.uk",
        "Old address 3, 4532 Nowhere",
        "New address 42, 9843 Anywhere",
        List.of(MOVING),
        LocalDate.of(2017, 5, 25),
        Option.none()
    );
  }
}

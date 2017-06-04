package uk.co.sharebear.portfolio.ordermanagement.api;

import uk.co.sharebear.portfolio.ordermanagement.api.domain.Order;
import uk.co.sharebear.portfolio.ordermanagement.api.library.sparkjava.JsonTransformer;

import java.time.LocalDate;
import java.util.UUID;

import javaslang.collection.List;
import javaslang.control.Option;

import static spark.Spark.before;
import static spark.Spark.get;
import static uk.co.sharebear.portfolio.ordermanagement.api.domain.MovingService.MOVING;
import static uk.co.sharebear.portfolio.ordermanagement.api.library.jsonapi.DataDocument.data;

class OrderManagementEndpoint {
  private static final String JSON_CONTENT_TYPE = "application/json";

  static void configure() {
    before((req, res) -> {
      res.type(JSON_CONTENT_TYPE);
    });

    get("/ordermanagement/orders", (req, res) -> data(List.of(stubOrder())), JsonTransformer::toJson);
  }

  private static Order stubOrder() {
    return new Order(
        UUID.randomUUID(),
        1,
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

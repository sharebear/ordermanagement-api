package uk.co.sharebear.portfolio.ordermanagement.api;

import uk.co.sharebear.portfolio.ordermanagement.api.library.sparkjava.JsonTransformer;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static uk.co.sharebear.portfolio.ordermanagement.api.library.jsonapi.DataDocument.data;
import static uk.co.sharebear.portfolio.ordermanagement.api.library.sparkjava.JsonTransformer.fromJson;

class OrderManagementEndpoint {
  private static final String JSON_CONTENT_TYPE = "application/json";
  private static final int CREATED = 201;

  static void configure(OrderManagementService service) {
    before((req, res) -> {
      res.type(JSON_CONTENT_TYPE);
    });

    path("/ordermanagement/", () -> {
      get("/orders", (req, res) -> data(service.getOrders()), JsonTransformer::toJson);
      post("/orders/create", (req, res) -> {
        final CreateOrderRequest request = fromJson(req.body(), CreateOrderRequest.class);
        final CreateOrderResponse response = service.createOrder(request);
        res.status(CREATED);
        return data(response);
      }, JsonTransformer::toJson);
    });

  }

}

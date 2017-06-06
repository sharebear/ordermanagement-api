package uk.co.sharebear.portfolio.ordermanagement.api;

import com.fasterxml.jackson.core.type.TypeReference;

import uk.co.sharebear.portfolio.ordermanagement.api.library.jsonapi.DataDocument;
import uk.co.sharebear.portfolio.ordermanagement.api.library.sparkjava.JsonTransformer;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.options;
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
      res.header("Access-Control-Allow-Headers", "Authorization, Content-Type");
      res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
      res.header("Access-Control-Allow-Origin", "*");
    });
    options("/*", (req, res) -> "");

    path("/ordermanagement/", () -> {
      get("/orders", (req, res) -> data(service.getOrders()), JsonTransformer::toJson);
      get("/orders/:orderId", (req, res) -> {
        final String orderId = req.params("orderId");
        return data(service.getOrder(orderId));
      }, JsonTransformer::toJson);
      post("/orders/create", (req, res) -> {
        final DataDocument<CreateOrderRequest> request = fromJson(
            req.body(),
            new TypeReference<DataDocument<CreateOrderRequest>>() {
            }
        );
        final CreateOrderResponse response = service.createOrder(request.getData());
        res.status(CREATED);
        return data(response);
      }, JsonTransformer::toJson);
    });

  }

}

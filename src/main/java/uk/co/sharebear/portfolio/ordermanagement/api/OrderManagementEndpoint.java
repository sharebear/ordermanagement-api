package uk.co.sharebear.portfolio.ordermanagement.api;

import static spark.Spark.get;

public class OrderManagementEndpoint {
  public static void configure() {
    get("/ordermanagement/orders", (req, res) -> "Hello world");
  }
}

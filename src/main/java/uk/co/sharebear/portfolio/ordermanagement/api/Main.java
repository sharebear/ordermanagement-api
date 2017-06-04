package uk.co.sharebear.portfolio.ordermanagement.api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String... args) {
    final OrderManagementRepository repository = new OrderManagementRepository();
    final OrderManagementService service = new OrderManagementService(repository);
    OrderManagementEndpoint.configure(service);
    log.info("ordermanagement-api started successfully");
  }
}

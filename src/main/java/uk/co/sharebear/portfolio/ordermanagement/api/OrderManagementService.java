package uk.co.sharebear.portfolio.ordermanagement.api;

import uk.co.sharebear.portfolio.ordermanagement.api.domain.Order;

import java.util.UUID;

import javaslang.collection.List;
import javaslang.control.Option;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class OrderManagementService {
  private final OrderManagementRepository repository;

  CreateOrderResponse createOrder(CreateOrderRequest request) {
    final UUID orderId = UUID.randomUUID(); // this generation means that create is not idempotent

    final Order order = new Order(
        orderId,
        Option.none(), // Issue for discussion, should the Service or Repo maintain this?
        request.getName(),
        request.getPhoneNumber(),
        request.getEmail(),
        request.getFromAddress(),
        request.getToAddress(),
        request.getServices(),
        request.getExecutionDate(),
        request.getComments()
    );

    repository.save(order);

    return new CreateOrderResponse(orderId);
  }

  List<Order> getOrders() {
    return repository.getOrders();
  }

  Order getOrder(String orderId) {
    return repository.findOrder(orderId)
        .getOrElseThrow(IllegalArgumentException::new);
  }

  void updateOrder(String orderId, UpdateOrderRequest request) {
    final Option<Order> orderOption = repository.findOrder(orderId);
    final Order updatedOrder = orderOption
        .map(order -> order.apply(request))
        .getOrElseThrow(IllegalArgumentException::new);
    repository.save(updatedOrder);
  }
}

package uk.co.sharebear.portfolio.ordermanagement.api;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;

import uk.co.sharebear.portfolio.ordermanagement.api.domain.MovingService;
import uk.co.sharebear.portfolio.ordermanagement.api.domain.Order;

import java.time.LocalDate;
import java.util.UUID;

import javaslang.collection.HashMap;
import javaslang.collection.List;
import javaslang.control.Option;

import static java.time.format.DateTimeFormatter.ISO_DATE;

class OrderManagementRepository {

  private final Table ordersTable;

  OrderManagementRepository(DynamoDB dynamoDB) {
    ordersTable = dynamoDB.getTable("orders");
  }

  List<Order> getOrders() {
    final ItemCollection<ScanOutcome> result = ordersTable.scan();
    return List.ofAll(result).map(OrderManagementRepository::createOrderFromItem);
  }

  Option<Order> findOrder(String orderId) {
    final Item item = ordersTable.getItem("orderId", orderId);
    return Option.of(item).map(OrderManagementRepository::createOrderFromItem);
  }

  private static Order createOrderFromItem(Item item) {
    return new Order(
        UUID.fromString(item.getString("orderId")),
        Option.of(item.getInt("version")),
        item.getString("name"),
        item.getString("phoneNumber"),
        item.getString("email"),
        item.getString("fromAddress"),
        item.getString("toAddress"),
        List.ofAll(item.<String>getList("services")).map(MovingService::valueOf),
        LocalDate.parse(item.getString("executionDate")), // Discuss implications of mapping to string instead of number
        Option.of(item.getString("comments"))
    );
  }

  void save(Order order) {
    final PutItemSpec putItemSpec = new PutItemSpec();
    if (order.getVersion().isDefined()) {
      final Integer previousVersion = order.getVersion().get();
      final Integer newVersion = previousVersion + 1;
      putItemSpec.withConditionExpression("version = :version")
          .withValueMap(HashMap.<String, Object>of(":version", previousVersion).toJavaMap())
          .withItem(createItemFromOrder(order.withVersion(newVersion)));
    } else {
      final int newVersion = 1;
      putItemSpec.withConditionExpression("attribute_not_exists(version)")
          .withItem(createItemFromOrder(order.withVersion(newVersion)));
    }
    ordersTable.putItem(putItemSpec);
  }

  private Item createItemFromOrder(Order order) {
    final Item item = new Item()
        .withPrimaryKey("orderId", order.getOrderId().toString())
        .withNumber("version", order.getVersion().get())
        .withString("name", order.getName())
        .withString("phoneNumber", order.getPhoneNumber())
        .withString("email", order.getEmail())
        .withString("fromAddress", order.getFromAddress())
        .withString("toAddress", order.getToAddress())
        .withList("services", order.getServices().map(MovingService::toString).toJavaList())
        .withString("executionDate", order.getExecutionDate().format(ISO_DATE));
    if (order.getComments().isDefined()) {
      return item.withString("comments", order.getComments().get());
    }
    return item;
  }
}

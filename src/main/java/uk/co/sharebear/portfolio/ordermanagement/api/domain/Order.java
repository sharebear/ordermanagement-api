package uk.co.sharebear.portfolio.ordermanagement.api.domain;

import java.time.LocalDate;
import java.util.UUID;

import javaslang.collection.List;
import javaslang.control.Option;
import lombok.Value;

@Value
public class Order {
  UUID orderId;
  Option<Integer> version;
  String name;
  String phoneNumber;
  String email;
  String fromAddress;
  String toAddress;
  List<MovingService> services;
  LocalDate executionDate;
  Option<String> comments;

  public Order withVersion(Integer newVersion) {
    return new Order(
        orderId,
        Option.of(newVersion),
        name,
        phoneNumber,
        email,
        fromAddress,
        toAddress,
        services,
        executionDate,
        comments
    );
  }
}

package uk.co.sharebear.portfolio.ordermanagement.api;

import uk.co.sharebear.portfolio.ordermanagement.api.domain.MovingService;

import java.time.LocalDate;

import javaslang.collection.List;
import javaslang.control.Option;
import lombok.Value;

@Value
public class UpdateOrderRequest {
  Integer version;
  String name;
  String phoneNumber;
  String email;
  String fromAddress;
  String toAddress;
  List<MovingService> services;
  LocalDate executionDate;
  Option<String> comments;
}

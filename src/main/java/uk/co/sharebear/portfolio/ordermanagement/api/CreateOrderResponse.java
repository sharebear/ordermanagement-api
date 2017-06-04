package uk.co.sharebear.portfolio.ordermanagement.api;

import java.util.UUID;

import lombok.Value;

@Value
public class CreateOrderResponse {
  UUID orderId;
}

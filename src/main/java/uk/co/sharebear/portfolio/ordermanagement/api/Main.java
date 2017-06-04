package uk.co.sharebear.portfolio.ordermanagement.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String... args) {
    final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    final OrderManagementRepository repository = new OrderManagementRepository(dynamoDB);
    final OrderManagementService service = new OrderManagementService(repository);
    OrderManagementEndpoint.configure(service);
    log.info("ordermanagement-api started successfully");
  }
}

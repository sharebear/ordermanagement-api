package uk.co.sharebear.portfolio.ordermanagement.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import uk.co.sharebear.portfolio.ordermanagement.api.domain.Order;
import uk.co.sharebear.portfolio.ordermanagement.api.library.sparkjava.SparkjavaRule;

import java.time.LocalDate;
import java.util.UUID;

import javaslang.collection.List;
import javaslang.control.Option;
import spark.Spark;

import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.post;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static uk.co.sharebear.portfolio.ordermanagement.api.domain.MovingService.MOVING;
import static uk.co.sharebear.portfolio.ordermanagement.api.library.sparkjava.JsonTransformer.toJson;

public class OrderManagementEndpointTest {

  @ClassRule
  public static final SparkjavaRule spark = new SparkjavaRule();

  private static final OrderManagementService mockService = mock(OrderManagementService.class);

  @BeforeClass
  public static void configureEndpoint() {
    // consider moving this to the SparkjavaRule by making use of a functional interface. This
    // would enable moving awaitInitialization into common code.
    OrderManagementEndpoint.configure(mockService);
    Spark.awaitInitialization();
  }

  @After
  public void resetMocks() {
    reset(mockService);
  }

  @Test
  public void shouldExposeEndpointForListingAllOrders() throws UnirestException {
    when(mockService.getOrders()).thenReturn(List.of(testOrder()));

    final HttpResponse<JsonNode> response = get(spark.url("/ordermanagement/orders")).asJson();

    assertThat(response.getStatus(), is(equalTo(200)));
    // FIXME: Replace following with deterministic value check once we have DI and mocking in place
    assertThat(getJsonApiDataAsArray(response).length(), is(equalTo(1)));
  }

  @Test
  public void shouldExposeEndpointForCreatingAnOrder() throws Exception {
    when(mockService.createOrder(any(CreateOrderRequest.class))).thenReturn(createOrderResponse());

    final HttpResponse<JsonNode> response =
        post(spark.url("/ordermanagement/orders/create"))
            .body(toJson(createOrderRequest()))
            .asJson();

    assertThat(response.getStatus(), is(equalTo(201)));
    assertThat(getJsonApiDataAsObject(response).has("orderId"), is(true));
  }

  private JSONArray getJsonApiDataAsArray(HttpResponse<JsonNode> response) {
    assertJsonApiSuccessResponse(response);
    return response.getBody().getObject().getJSONArray("data");
  }

  private JSONObject getJsonApiDataAsObject(HttpResponse<JsonNode> response) {
    assertJsonApiSuccessResponse(response);
    return response.getBody().getObject().getJSONObject("data");
  }

  private void assertJsonApiSuccessResponse(HttpResponse<JsonNode> response) {
    assertThat("Response body must be an object", response.getBody().isArray(), is(false));
    assertThat("Response body must contain a data key",
        response.getBody().getObject().has("data"), is(true)
    );
    assertThat("Response body must not contain aan errors key",
        response.getBody().getObject().has("errors"), is(false)
    );
  }

  private Order testOrder() {
    return new Order(
        UUID.randomUUID(),
        Option.of(1),
        "Jonathan Share",
        "83212343",
        "jonny@someplace.co.uk",
        "Old address 3, 4532 Nowhere",
        "New address 42, 9843 Anywhere",
        List.of(MOVING),
        LocalDate.of(2017, 5, 25),
        Option.none()
    );
  }

  private CreateOrderRequest createOrderRequest() {
    return new CreateOrderRequest("", "", "", "", "", List.empty(), LocalDate.now(), Option.none());
  }

  private CreateOrderResponse createOrderResponse() {
    return new CreateOrderResponse(UUID.randomUUID());
  }
}

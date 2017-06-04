package uk.co.sharebear.portfolio.ordermanagement.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import uk.co.sharebear.portfolio.ordermanagement.api.domain.Order;

import java.time.LocalDate;
import java.util.UUID;

import javaslang.collection.List;
import javaslang.control.Option;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.sharebear.portfolio.ordermanagement.api.domain.MovingService.MOVING;

@RunWith(MockitoJUnitRunner.class)
public class OrderManagementServiceTest {

  @Mock
  private OrderManagementRepository mockRepository;
  private OrderManagementService orderManagementService;

  @Before
  public void configureServiceUnderTest() {
    orderManagementService = new OrderManagementService(mockRepository);
  }

  @Test
  public void shouldRetrieveListOfOrdersFromRepository() {
    final List<Order> expected = createListOfOrders();
    when(mockRepository.getOrders()).thenReturn(expected);

    final List<Order> result = orderManagementService.getOrders();

    assertThat(result, is(equalTo(expected)));
  }

  @Test
  public void shouldSetEmptyVersionWhenSavingInitialVersionOfOrder() {
    orderManagementService.createOrder(createOrderRequest());

    final Order savedOrder = verifySavedOrder();
    assertThat(savedOrder.getVersion().isEmpty(), is(true));
  }

  private Order verifySavedOrder() {
    ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
    verify(mockRepository).save(captor.capture());
    return captor.getValue();
  }

  private CreateOrderRequest createOrderRequest() {
    return new CreateOrderRequest(
        "",
        "",
        "",
        "",
        "",
        List.of(MOVING),
        LocalDate.of(20181, 1, 22),
        Option.none()
    );
  }

  private List<Order> createListOfOrders() {
    return List.of(testOrder());
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
}

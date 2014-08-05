package au.com.example.api.contoller;

import au.com.example.api.controller.customer.CustomerController;
import au.com.example.api.model.customer.Customer;
import au.com.example.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:WEB-INF/spring/test-application-context-config.xml",
		"classpath:WEB-INF/spring/test-security-context-config.xml",
        "classpath:WEB-INF/spring/test-persistence-context-config.xml" })
public class CustomerControllerUnitTest {

	@InjectMocks
	private CustomerController customerController;
	
	@Mock
	private CustomerService customerService;
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void getCustomersSuccess() {
		Mockito.when(customerService.getCustomers()).thenReturn(getTestCustomers());
		
		List<Customer> customers = customerController.getCustomers();
		
		Assert.assertNotNull(customers);
		Assert.assertEquals(4, customers.size());
	}
	
	// ========== Helpers ==========
	
	private List<Customer> getTestCustomers() {
		List<Customer> customers = new ArrayList<Customer>();

		customers.add(new Customer(1L, "Test", "User1"));
		customers.add(new Customer(2L, "Test", "User2"));
		customers.add(new Customer(3L, "Test", "User3"));
		customers.add(new Customer(4L, "Test", "User4"));

		return customers;
	}
}

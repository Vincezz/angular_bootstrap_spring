package au.com.example.service;

import au.com.example.api.model.customer.Customer;
import au.com.example.persistence.dao.CustomerDao;
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
public class CustomerServiceUnitTest {

	@InjectMocks
	private CustomerServiceImpl customerServiceImpl;
	
	@Mock
	private CustomerDao customerDao;
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void getCustomersSuccess() {
		Mockito.when(customerDao.getCustomers()).thenReturn(getTestCustomers());
		
		List<Customer> customers = customerServiceImpl.getCustomers();
		
		Assert.assertNotNull(customers);
		Assert.assertEquals(4, customers.size());
	}

    @Test
    public void saveCustomerSuccess() {
        Mockito.when(customerDao.saveCustomer(Mockito.any(Customer.class))).thenReturn(true);

        boolean saved = customerServiceImpl.saveCustomer(new Customer());

        Assert.assertTrue(saved);
    }

    @Test
    public void saveCustomerFailure() {
        Mockito.when(customerDao.saveCustomer(Mockito.any(Customer.class))).thenReturn(false);

        boolean saved = customerServiceImpl.saveCustomer(new Customer());

        Assert.assertFalse(saved);
    }

    @Test
    public void deleteCustomerSuccess() {
        Mockito.when(customerDao.deleteCustomer(Mockito.any(Long.class))).thenReturn(true);

        boolean deleted = customerServiceImpl.deleteCustomer(1L);

        Assert.assertTrue(deleted);
    }

    @Test
    public void deleteCustomerFailure() {
        Mockito.when(customerDao.deleteCustomer(Mockito.any(Long.class))).thenReturn(false);

        boolean deleted = customerServiceImpl.deleteCustomer(1L);

        Assert.assertFalse(deleted);
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

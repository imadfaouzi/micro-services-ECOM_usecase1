package ma.emsi.billingservice;

import ma.emsi.billingservice.entities.Bill;
import ma.emsi.billingservice.entities.ProductItem;
import ma.emsi.billingservice.model.Customer;
import ma.emsi.billingservice.model.Product;
import ma.emsi.billingservice.repository.BillRepository;
import ma.emsi.billingservice.repository.ProductItemRepository;
import ma.emsi.billingservice.services.CustomerRestClient;
import ma.emsi.billingservice.services.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}



	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository productItemRepository,
							CustomerRestClient customerRestClient,
							ProductRestClient productRestClient){
		return args -> {
			Collection<Product> products=productRestClient.getAllProducts().getContent();
			Long customerId=1L;
			Customer customer=customerRestClient.findCustomerById(customerId);
			if(customer==null) throw new RuntimeException("Customer not found");
			Bill bill=new Bill();
			bill.setBillingDate(new Date());
			bill.setCustomerID(customerId);
			Bill savedBill = billRepository.save(bill);
			products.forEach(product -> {
				ProductItem productItem=new ProductItem();
				productItem.setBill(savedBill);
				productItem.setProductId(product.getId());
				productItem.setQuantity(1+new Random().nextInt(10));
				productItem.setPrice(product.getPrice());
				productItem.setDiscount(Math.random());
				productItemRepository.save(productItem);
			});

		};
	}

}

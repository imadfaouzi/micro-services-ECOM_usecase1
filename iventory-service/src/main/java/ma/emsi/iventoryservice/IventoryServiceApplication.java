package ma.emsi.iventoryservice;

import ma.emsi.iventoryservice.entities.Product;
import ma.emsi.iventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class IventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IventoryServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner1( RepositoryRestConfiguration repositoryRestConfiguration){
		return  args -> {
			repositoryRestConfiguration.exposeIdsFor(Product.class);
		};
	}
	// Random Data to db
	private final String[] productNames = {"Laptop", "Smartphone", "Headphones", "Camera", "Tablet"};
	private final double minPrice = 50.0;
	private final double maxPrice = 1000.0;

	@Bean
	public CommandLineRunner productDataLoader(ProductRepository productRepository) {
		return args -> {
			List<Product> products = new ArrayList<>();
			for (String productName : productNames) {
				products.add(generateRandomProduct(productName));
			}
			productRepository.saveAll(products);
		};
	}

	private Product generateRandomProduct(String productName) {
		Random random = new Random();
		double price = minPrice + (maxPrice - minPrice) * random.nextDouble();
		price = roundToTwoDecimalPlaces(price);
		int quantity = random.nextInt(50) + 1; // Quantity between 1 and 50

		return Product.builder()
				.name(productName)
				.price(price)
				.quantity(quantity)
				.build();
	}

	private double roundToTwoDecimalPlaces(double value) {
		BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}

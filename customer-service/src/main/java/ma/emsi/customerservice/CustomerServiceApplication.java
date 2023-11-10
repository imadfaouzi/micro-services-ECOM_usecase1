package ma.emsi.customerservice;

import ma.emsi.customerservice.entities.Customer;
import ma.emsi.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }



    @Bean
    public CommandLineRunner commandLineRunner1( RepositoryRestConfiguration repositoryRestConfiguration){
        return  args -> {
            repositoryRestConfiguration.exposeIdsFor(Customer.class);
        };
    }

    private final String[] names = {"mohamed", "aziz", "tariK", "John", "Alice", "Bob", "Eva", "Charlie"};
    private final String domain = "gmail.com";

    @Bean
    public CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {


            List<Customer> customers = new ArrayList<>();
            for (String name : names) {
                customers.add(Customer.builder().name(name).email(generateUniqueEmail(name)).build());
            }
            customerRepository.saveAll(customers);
        };
    }

    private String generateUniqueEmail(String name) {
        Random random = new Random();
        return name.toLowerCase() + random.nextInt(1000) + "@" + domain;
    }

}

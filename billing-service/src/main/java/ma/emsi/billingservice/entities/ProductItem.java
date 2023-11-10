package ma.emsi.billingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.emsi.billingservice.model.Product;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;


@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class ProductItem{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long productId;
    @ManyToOne
    private Bill bill;
    private double price;
    private double quantity;
    private double discount;
    @Transient private Product product;
}

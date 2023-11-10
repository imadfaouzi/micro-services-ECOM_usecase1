package ma.emsi.billingservice.model;


import lombok.Data;

@Data
public class Customer{
    private Long id;
    private String name;
    private String email;
}

/*
Product{
private Long id;
private String name;
private double price;
 */
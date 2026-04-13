package com.pink.pink_api.sales;

import com.pink.pink_api.customers.Customer;
import com.pink.pink_api.customers.LocationType;
import com.pink.pink_api.customers.ShoppingType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at", insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "location_type")
    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Column(name = "shopping_type")
    @Enumerated(EnumType.STRING)
    private ShoppingType shoppingType;

    @Column(name = "delivery_vendor")
    private String deliveryVendor;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SaleProduct> saleProducts = new ArrayList<>();

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL)
    private Customer customer;
}

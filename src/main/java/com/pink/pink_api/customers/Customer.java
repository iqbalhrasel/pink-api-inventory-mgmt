package com.pink.pink_api.customers;

import com.pink.pink_api.sales.Sale;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Column(name = "created_at", insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "shopping_type")
    @Enumerated(EnumType.STRING)
    private ShoppingType shoppingType;

    @Column(name = "address")
    private String address;

    @Column(name = "remarks")
    private String remarks;
}

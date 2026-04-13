package com.pink.pink_api.sizes;

import com.pink.pink_api.products.ProductSize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "size", fetch = FetchType.LAZY)
    private List<ProductSize> productSizes = new ArrayList<>();
}

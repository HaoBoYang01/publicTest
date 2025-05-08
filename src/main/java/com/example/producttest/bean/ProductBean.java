package com.example.producttest.bean;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

/**
 * @author HaoBoYang
 */
@Data
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class ProductBean {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long productId;

    @Column(name="name",nullable = false)
    private String name;
    @Column(name="stock",nullable = false)
    private int stock;
    @Version
    private Long version;  // 乐观锁版本号

}

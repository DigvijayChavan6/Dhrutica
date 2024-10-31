package com.digvi.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Seller seller;

    private Double totalEarnings = 0.0D;

    private Double totalSales = 0.0D;

    private Double totalRefunds = 0.0D;

    private Double totalTax = 0.0D;

    private Double netEarnings = 0.0;

    private Integer totalOrders = 0;

    private Integer canceledOrders = 0;

    private Double totalTransactions = 0.0D;

}

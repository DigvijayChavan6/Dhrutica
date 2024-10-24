package com.digvi.ecommerce.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateProductRequest {
    private String title;
    private String description;
    private double mrpPrice;
    private double sellingPrice;
    private String color;
    private List<String> images;
    private String category1;
    private String category2;
    private String category3;
    private String sizes;
}

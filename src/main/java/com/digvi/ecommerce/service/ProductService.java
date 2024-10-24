package com.digvi.ecommerce.service;

import com.digvi.ecommerce.exceptions.ProductException;
import com.digvi.ecommerce.model.Product;
import com.digvi.ecommerce.model.Seller;
import com.digvi.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;


import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest req, Seller seller);
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId, Product product) throws ProductException;
    public Product findProductById(Long productId) throws ProductException;
    public List<Product> searchProducts(String query);
    public Page<Product> getAllProducts(
            String category,
            String brand,
            String colors,
            String sizes,
            Double minPrice,
            Double maxPrice,
            Double minDiscount,
            String sort,
            String stock,
            Integer pageNumber
    );
    public List<Product> getProductBySellerId(Long sellerId);
}

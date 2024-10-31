package com.digvi.ecommerce.service;

import com.digvi.ecommerce.model.Seller;
import com.digvi.ecommerce.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
}

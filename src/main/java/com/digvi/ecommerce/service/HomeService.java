package com.digvi.ecommerce.service;

import com.digvi.ecommerce.model.Home;
import com.digvi.ecommerce.model.HomeCategory;

import java.util.List;

public interface HomeService {
    Home createHomePageData(List<HomeCategory> allCategories);
}

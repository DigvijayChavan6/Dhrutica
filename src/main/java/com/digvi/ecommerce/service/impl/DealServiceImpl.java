package com.digvi.ecommerce.service.impl;

import com.digvi.ecommerce.model.Deal;
import com.digvi.ecommerce.model.HomeCategory;
import com.digvi.ecommerce.repository.DealRepository;
import com.digvi.ecommerce.repository.HomeCategoryRepository;
import com.digvi.ecommerce.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final HomeCategoryRepository homeCategoryRepository;

    @Override
    public List<Deal> getDeals() {
        return dealRepository.findAll();
    }

    @Override
    public Deal createDeal(Deal deal) {
        HomeCategory category = homeCategoryRepository.findById(deal.getId()).orElse(null);

        Deal newDeal = dealRepository.save(deal);
        newDeal.setCategory(category);
        newDeal.setDiscount(deal.getDiscount());
        return dealRepository.save(newDeal);
    }

    @Override
    public Deal updateDeal(Deal deal, Long id) throws Exception {
        Deal existingDeal = dealRepository.findById(id).orElseThrow(()->
                new Exception("Deal not found"));

        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);
        if(deal.getDiscount() != null){
            existingDeal.setDiscount(deal.getDiscount());
        }
        if(category != null){
            existingDeal.setCategory(category);
        }
        return dealRepository.save(existingDeal);
    }

    @Override
    public void deleteDeal(Long id) throws Exception {
        Deal deal = dealRepository.findById(id).orElseThrow(()->
                new Exception("Deal not found"));
        dealRepository.delete(deal);
    }
}

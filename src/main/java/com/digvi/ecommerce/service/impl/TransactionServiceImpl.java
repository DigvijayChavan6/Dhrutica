package com.digvi.ecommerce.service.impl;

import com.digvi.ecommerce.model.Order;
import com.digvi.ecommerce.model.Seller;
import com.digvi.ecommerce.model.Transaction;
import com.digvi.ecommerce.repository.SellerRepository;
import com.digvi.ecommerce.repository.TransactionRepository;
import com.digvi.ecommerce.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    @Override
    public void createTransaction(Order order) {
        Seller seller = sellerRepository.findById(order.getSellerId()).get();

        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setUser(order.getUser());
        transaction.setOrder(order);

        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsBySeller(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}

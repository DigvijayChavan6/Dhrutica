package com.digvi.ecommerce.service;

import com.digvi.ecommerce.model.Order;
import com.digvi.ecommerce.model.Seller;
import com.digvi.ecommerce.model.Transaction;

import java.util.List;

public interface TransactionService {

    void createTransaction(Order order);
    List<Transaction> getTransactionsBySeller(Seller seller);
    List<Transaction> getAllTransactions();

}

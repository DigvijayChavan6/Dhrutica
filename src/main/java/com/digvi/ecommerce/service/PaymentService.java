package com.digvi.ecommerce.service;

import com.digvi.ecommerce.model.Order;
import com.digvi.ecommerce.model.PaymentOrder;
import com.digvi.ecommerce.model.User;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

import java.util.Set;

public interface PaymentService {
    PaymentOrder createOrder(User user, Set<Order> orders);
    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder,
                                String paymentId, String paymentLinkId) throws RazorpayException;
    PaymentLink createRazorpayPaymentLink(User user, Double amount, Long orderId) throws RazorpayException;

    String createStripePaymentLink(User user, Double amount, Long orderId) throws StripeException;

}

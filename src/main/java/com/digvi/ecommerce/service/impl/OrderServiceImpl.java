package com.digvi.ecommerce.service.impl;

import com.digvi.ecommerce.domain.OrderStatus;
import com.digvi.ecommerce.domain.PaymentStatus;
import com.digvi.ecommerce.model.*;
import com.digvi.ecommerce.repository.AddressRepository;
import com.digvi.ecommerce.repository.CartItemRepository;
import com.digvi.ecommerce.repository.OrderItemRepository;
import com.digvi.ecommerce.repository.OrderRepository;
import com.digvi.ecommerce.service.CartItemService;
import com.digvi.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {
        if(!user.getAddresses().contains(shippingAddress)){
            user.getAddresses().add(shippingAddress);
        }
        Address address = addressRepository.save(shippingAddress);

        Map<Long, List<CartItems>> itemsBySeller = cart.getCartItems().stream()
                .collect(Collectors.groupingBy(item -> item.getProduct()
                        .getSeller().getId()));

        Set<Order> orders = new HashSet<>();

        for(Map.Entry<Long, List<CartItems>> entry : itemsBySeller.entrySet()){
            Long sellerId = entry.getKey();
            List<CartItems> items = entry.getValue();

            double totalDiscountedOrderPrice = items.stream().mapToDouble(
                    CartItems::getSellingPrice
            ).sum();

            double totalOrderPrice = items.stream().mapToDouble(
                    CartItems::getMrpPrice
            ).sum();
            int totalItem = items.stream().mapToInt(CartItems::getQuantity).sum();

            Order createdOrder = new Order();
            createdOrder.setUser(user);
            createdOrder.setSellerId(sellerId);
            createdOrder.setTotalItem(totalItem);
            createdOrder.setTotalSellingPrice(totalDiscountedOrderPrice);
            createdOrder.setTotalMrpPrice(totalOrderPrice);
            createdOrder.setShippingAddress(address);
            createdOrder.setOrderStatus(OrderStatus.PENDING);
            createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);

            Order savedOrder = orderRepository.save(createdOrder);
            orders.add(savedOrder);

            List<OrderItems> orderItems = new ArrayList<>();

            for(CartItems item : items){
                OrderItems orderItem = new OrderItems();
                orderItem.setOrder(savedOrder);
                orderItem.setMrpPrice(item.getMrpPrice());
                orderItem.setProduct(item.getProduct());
                orderItem.setSize(item.getSize());
                orderItem.setSellingPrice(item.getSellingPrice());
                orderItem.setUserId(item.getUserId());
                orderItem.setId(item.getId());
                orderItem.setQuantity(item.getQuantity());
                savedOrder.getOrderItems().add(orderItem);

                OrderItems savedOrderItems = orderItemRepository.save(orderItem);
                orderItems.add(orderItem);
            }
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long id) throws Exception {
        return orderRepository.findById(id).orElseThrow(()->
               new Exception("Order not found with this id "+id));
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> sellersOrder(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId, User user) throws Exception {
        Order order = findOrderById(orderId);

        if(!user.getId().equals(order.getUser().getId())){
            throw new Exception("You don't have the access of this order");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public OrderItems getOrderItemById(Long id) throws Exception {
        return orderItemRepository.findById(id).orElseThrow(() -> new Exception("Order item not accessed"));
    }
}

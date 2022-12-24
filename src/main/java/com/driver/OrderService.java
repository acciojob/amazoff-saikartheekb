package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        orderRepository.addPartner(partnerId, deliveryPartner);
    }

    public void createOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.createOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderByID(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCount(String partnerId) {
        return orderRepository.getOrderCount(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getUnassignedOrdersCount() {
        return orderRepository.getUnassignedOrdersCount();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(convertDeliveryTimeToMinutes(time), partnerId);
    }
    private int convertDeliveryTimeToMinutes(String deliveryTime) {
        String[] time = deliveryTime.split(":");
        return Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int timeInMinutes = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        return convertDeliveryTimeToString(timeInMinutes);
    }
    private String convertDeliveryTimeToString(int timeInMinutes) {
        return timeInMinutes/60 + ":" + timeInMinutes%60;
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}

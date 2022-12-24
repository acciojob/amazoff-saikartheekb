package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {

    Map<String, Order> orderMap = new HashMap<>();
    List<String> unassignedOrders = new ArrayList<>();
    Map<String, DeliveryPartner> deliveryPartnerMap = new HashMap<>();
    Map<String, List<String>> deliveryPartnerOrderListMap  = new HashMap<>();

    public void addOrder(Order order) {
        orderMap.put(order.getId(), order);
        unassignedOrders.add(order.getId());
    }

    public void addPartner(String partnerId, DeliveryPartner deliveryPartner) {
        deliveryPartnerMap.put(partnerId, deliveryPartner);
    }

    public void createOrderPartnerPair(String orderId, String partnerId) {
        if(orderMap.containsKey(orderId) && deliveryPartnerMap.containsKey(partnerId)) {
            if(!deliveryPartnerOrderListMap.containsKey(partnerId))
                deliveryPartnerOrderListMap.put(partnerId, new ArrayList<>());
            deliveryPartnerOrderListMap.get(partnerId).add(orderId);
            deliveryPartnerMap.get(partnerId).setNumberOfOrders(deliveryPartnerOrderListMap.get(partnerId).size());
            unassignedOrders.remove(orderId);
        }
    }

    public Order getOrderById(String orderId) {
        return orderMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerMap.get(partnerId);
    }

    public Integer getOrderCount(String partnerId) {
        return deliveryPartnerMap.get(partnerId).getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return deliveryPartnerOrderListMap.getOrDefault(partnerId, new ArrayList<>());
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public Integer getUnassignedOrdersCount() {
        return unassignedOrders.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int deliveryTime, String partnerId) {
        int count = 0;
        List<String> orderList = deliveryPartnerOrderListMap.get(partnerId);
        for(String orderId: orderList){
            if(orderMap.get(orderId).getDeliveryTime() > deliveryTime)
                count++;
        }
        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int maxTime = 0;
        List<String> orderList = deliveryPartnerOrderListMap.get(partnerId);
        for(String orderId: orderList){
            maxTime = Math.max(orderMap.get(orderId).getDeliveryTime(), maxTime);
        }
        return maxTime;
    }

    public void deletePartnerById(String partnerId) {
        unassignedOrders.addAll(deliveryPartnerOrderListMap.get(partnerId));
        deliveryPartnerOrderListMap.remove(partnerId);
        deliveryPartnerMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderMap.remove(orderId);
        if(!unassignedOrders.remove(orderId)){
            for(String partnerId: deliveryPartnerOrderListMap.keySet()){
                if(deliveryPartnerOrderListMap.get(partnerId).remove(orderId))
                    break;
            }
        }
    }
}

package Homework1.Services;

import Homework1.Delivery.DeliveryMethod;
import Homework1.Models.Client;
import Homework1.Models.Courier;
import Homework1.Models.CourierStatus;
import Homework1.Models.Order;
import Homework1.Models.OrderItem;
import Homework1.Models.OrderStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderService {

    private final List<Order> orders = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();

    private int nextOrderNumber = 1;
    private int nextCourierId = 1;

    public Order createOrder(Client client, String address) {
        Order order = new Order(nextOrderNumber, client, address);
        nextOrderNumber++;
        orders.add(order);
        return order;
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public Order findOrderByNumber(int orderNumber) {
        for (Order order : orders) {
            if (order.getNumber() == orderNumber) {
                return order;
            }
        }

        throw new IllegalArgumentException(
                "Заказ с номером " + orderNumber + " не найден.");
    }

    public void addItemToOrder(int orderNumber, OrderItem item) {
        Order order = findOrderByNumber(orderNumber);
        order.addItem(item);
    }

    public void setDeliveryMethod(
            int orderNumber,
            DeliveryMethod deliveryMethod) {
        Order order = findOrderByNumber(orderNumber);
        order.setDeliveryMethod(deliveryMethod);
    }

    public Courier createCourier(String name, String phone) {
        Courier courier = new Courier(nextCourierId, name, phone);
        addCourier(courier);
        return courier;
    }

    public void addCourier(Courier courier) {
        if (courier == null) {
            throw new IllegalArgumentException("Курьер не выбран.");
        }

        for (Courier existingCourier : couriers) {
            if (existingCourier.getId() == courier.getId()) {
                throw new IllegalArgumentException("Курьер с таким ID уже добавлен.");
            }
        }

        couriers.add(courier);

        if (courier.getId() >= nextCourierId) {
            nextCourierId = courier.getId() + 1;
        }
    }

    public List<Courier> getAvailableCouriers() {
        List<Courier> availableCouriers = new ArrayList<>();

        for (Courier courier : couriers) {
            if (courier.getStatus() == CourierStatus.AVAILABLE) {
                availableCouriers.add(courier);
            }
        }

        return Collections.unmodifiableList(availableCouriers);
    }

    public void assignCourier(int orderNumber, int courierId) {
        Order order = findOrderByNumber(orderNumber);
        Courier courier = findCourierById(courierId);
        order.assignCourier(courier);
    }

    public void changeOrderStatus(
            int orderNumber,
            OrderStatus newStatus) {
        Order order = findOrderByNumber(orderNumber);
        order.changeStatus(newStatus);
    }

    private Courier findCourierById(int courierId) {
        for (Courier courier : couriers) {
            if (courier.getId() == courierId) {
                return courier;
            }
        }

        throw new IllegalArgumentException("Курьер с ID " + courierId + " не найден.");
    }
}
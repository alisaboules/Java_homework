package Homework1.Models;

import Homework1.Delivery.DeliveryMethod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    private final int number;
    private final Client client;
    private final String address;
    private final List<OrderItem> items;
    private OrderStatus status;
    private DeliveryMethod deliveryMethod;
    private Courier courier;

    public Order(int number, Client client, String address) {
        if (number <= 0) {
            throw new IllegalArgumentException("Номер заказа должен быть больше нуля.");
        }

        if (client == null) {
            throw new IllegalArgumentException("У заказа должен быть клиент.");
        }

        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Адрес не может быть пустым.");
        }

        this.number = number;
        this.client = client;
        this.address = address;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }

    public int getNumber() {
        return number;
    }

    public Client getClient() {
        return client;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public Courier getCourier() {
        return courier;
    }

    public void addItem(OrderItem item) {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Нельзя менять товары после отправки заказа.");
        }

        if (item == null) {
            throw new IllegalArgumentException("Товар не может быть пустым.");
        }

        items.add(item);
    }

    public double getItemsTotal() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }

        return total;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Нельзя изменить доставку после отправки заказа.");
        }

        if (deliveryMethod == null) {
            throw new IllegalArgumentException("Способ доставки не выбран.");
        }

        releaseCourier();

        this.deliveryMethod = deliveryMethod;
    }

    public double getDeliveryCost() {
        if (deliveryMethod == null) {
            throw new IllegalStateException("Сначала выберите способ доставки.");
        }

        return deliveryMethod.calculateCost(getItemsTotal());
    }

    public double getTotalCost() {
        return getItemsTotal() + getDeliveryCost();
    }

    public int getEstimatedDeliveryDays() {
        if (deliveryMethod == null) {
            throw new IllegalStateException("Сначала выберите способ доставки.");
        }

        return deliveryMethod.getEstimatedDays();
    }

    public void assignCourier(Courier courier) {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Курьера можно назначить только созданному заказу.");
        }

        if (deliveryMethod == null) {
            throw new IllegalStateException("Сначала выберите способ доставки.");
        }

        if (!deliveryMethod.requiresCourier()) {
            throw new IllegalStateException("Для этого способа доставки курьер не нужен.");
        }

        if (courier == null) {
            throw new IllegalArgumentException("Курьер не выбран.");
        }

        if (courier.getStatus() != CourierStatus.AVAILABLE) {
            throw new IllegalStateException("Этот курьер уже занят.");
        }

        releaseCourier();

        this.courier = courier;
        courier.setStatus(CourierStatus.BUSY);
    }

    public void changeStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Новый статус не выбран.");
        }

        if (!isAllowedStatusTransition(newStatus)) {
            throw new IllegalStateException("Такой переход статуса заказа невозможен.");
        }

        if (newStatus == OrderStatus.SHIPPED) {
            if (deliveryMethod == null) {
                throw new IllegalStateException("Сначала выберите способ доставки.");
            }

            if (items.isEmpty()) {
                throw new IllegalStateException("Нельзя отправить заказ без товаров.");
            }

            if (deliveryMethod.requiresCourier() && courier == null) {
                throw new IllegalStateException("Для этой доставки сначала назначьте курьера.");
            }
        }

        status = newStatus;

        if (status == OrderStatus.DELIVERED
                || status == OrderStatus.CANCELLED) {
            releaseCourier();
        }
    }

    private boolean isAllowedStatusTransition(OrderStatus newStatus) {
        if (status == OrderStatus.CREATED) {
            return newStatus == OrderStatus.SHIPPED
                    || newStatus == OrderStatus.CANCELLED;
        }

        if (status == OrderStatus.SHIPPED) {
            return newStatus == OrderStatus.DELIVERED
                    || newStatus == OrderStatus.CANCELLED;
        }

        if (status == OrderStatus.DELIVERED) {
            return newStatus == OrderStatus.RETURNED;
        }

        return false;
    }

    private void releaseCourier() {
        if (courier != null) {
            courier.setStatus(CourierStatus.AVAILABLE);
            courier = null;
        }
    }
}
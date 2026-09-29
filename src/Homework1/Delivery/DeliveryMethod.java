package Homework1.Delivery;

public interface DeliveryMethod {

    String getName();

    double calculateDeliveryCost(double orderCost);

    int getEstimateDays();

    boolean requiresCourier();
}

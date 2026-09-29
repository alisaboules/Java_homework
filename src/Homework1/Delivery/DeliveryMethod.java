package Homework1.Delivery;

public interface DeliveryMethod {

    String getName();

    double calculateCost(double orderCost);

    int getEstimatedDays();

    boolean requiresCourier();
}

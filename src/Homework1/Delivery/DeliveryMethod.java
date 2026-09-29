package Homework1.Delivery;

public interface DeliveryMethod {

    String getName();

    double calculateСost(double orderCost);

    int getEstimatedDays();

    boolean requiresCourier();
}

package Homework1.Delivery;

import Homework1.Models.City;

public interface DeliveryMethod {

    String getName();

    double calculateCost(double orderCost, City destination);

    int getEstimatedDays(City destination);

    boolean requiresCourier();
}

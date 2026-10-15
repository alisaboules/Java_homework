package Homework1.Delivery;

import Homework1.Models.City;

public class PickupDelivery implements DeliveryMethod {

    @Override
    public String getName() {
        return "Самовывоз";
    }

    @Override
    public double calculateCost(double orderCost, City destination) {
        return 0;
    }

    @Override
    public int getEstimatedDays(City destination) {
        return 1;
    }

    @Override
    public boolean requiresCourier() {
        return false;
    }
}

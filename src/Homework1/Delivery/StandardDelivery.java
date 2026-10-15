package Homework1.Delivery;

import Homework1.Models.City;

public class StandardDelivery implements DeliveryMethod {

    @Override
    public String getName() {
        return "Стандартная доставка";
    }

    @Override
    public double calculateCost(double orderCost, City destination) {
        return destination.getStandardDeliveryCost();
    }

    @Override
    public int getEstimatedDays(City destination) {
        return destination.getStandardDeliveryDays();
    }

    @Override
    public boolean requiresCourier() {
        return true;
    }
}

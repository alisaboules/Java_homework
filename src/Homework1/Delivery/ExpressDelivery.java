package Homework1.Delivery;

import Homework1.Models.City;

public class ExpressDelivery implements DeliveryMethod {

    @Override
    public String getName() {
        return "Экспресс-доставка";
    }

    @Override
    public double calculateCost(double orderCost, City destination) {
        return destination.getExpressDeliveryCost();
    }

    @Override
    public int getEstimatedDays(City destination) {
        return destination.getExpressDeliveryDays();
    }

    @Override
    public boolean requiresCourier() {
        return true;
    }
}

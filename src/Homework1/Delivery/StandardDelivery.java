package Homework1.Delivery;

public class StandardDelivery implements DeliveryMethod {

    @Override
    public String getName() {
        return "Стандартная доставка";
    }

    @Override
    public double calculateCost(double orderCost) {
        return 250;
    }

    @Override
    public int getEstimatedDays() {
        return 2;
    }

    @Override
    public boolean requiresCourier() {
        return true;
    }
}
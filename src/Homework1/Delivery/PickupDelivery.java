package Homework1.Delivery;

public class PickupDelivery implements DeliveryMethod {

    @Override
    public String getName() {
        return "Самовывоз";
    }

    @Override
    public double calculateCost(double orderCost) {
        return 0;
    }

    @Override
    public int getEstimatedDays() {
        return 1;
    }

    @Override
    public boolean requiresCourier() {
        return false;
    }
}

package Homework1.Delivery;

public class ExpressDelivery implements DeliveryMethod{

    @Override
    public String getName() {
        return "Экспресс-доставка";
    }

    @Override
    public double calculateCost(double orderCost) {
        return 600;
    }

    @Override
    public int getEstimatedDays() {
        return 1;
    }

    @Override
    public boolean requiresCourier() {
        return true;
    }
}

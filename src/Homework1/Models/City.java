package Homework1.Models;

public enum City {
    MOSCOW("Москва", 250, 600, 2, 1),
    SAINT_PETERSBURG("Санкт-Петербург", 885, 1235, 4, 3),
    YEKATERINBURG("Екатеринбург", 1667, 2017, 5, 4),
    KAZAN("Казань", 971, 1321, 4, 3),
    NOVOSIBIRSK("Новосибирск", 3061, 3411, 8, 7),
    VLADIVOSTOK("Владивосток", 6684, 7034, 15, 14),
    KAMCHATKA("Камчатка", 7043, 7393, 16, 15),
    KALININGRAD("Калининград", 1342, 1692, 5, 4),
    SOCHI("Сочи", 1610, 1960, 5, 4),
    YAKUTSK("Якутск", 5151, 5501, 12, 11);

    private final String name;
    private final double standardDeliveryCost;
    private final double expressDeliveryCost;
    private final int standardDeliveryDays;
    private final int expressDeliveryDays;

    City(
          String name,
          double standardDeliveryCost,
          double expressDeliveryCost,
          int standardDeliveryDays,
          int expressDeliveryDays
    ) {
        this.name = name;
        this.standardDeliveryCost = standardDeliveryCost;
        this.expressDeliveryCost = expressDeliveryCost;
        this.standardDeliveryDays = standardDeliveryDays;
        this.expressDeliveryDays = expressDeliveryDays;
    }

    public String getName() {
        return name;
    }

    public double getStandardDeliveryCost() {
        return standardDeliveryCost;
    }

    public double getExpressDeliveryCost() {
        return expressDeliveryCost;
    }

    public int getStandardDeliveryDays() {
        return standardDeliveryDays;
    }

    public int getExpressDeliveryDays() {
        return expressDeliveryDays;
    }
}

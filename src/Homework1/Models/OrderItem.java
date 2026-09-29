package Homework1.Models;

public class OrderItem {

    private final String name;
    private final int quantity;
    private final double price;

    public OrderItem(String name, int quantity, double price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название товара не может быть пустым.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество товаров не может быть меньше нуля.");
        }

        if (price < 0) {
            throw new IllegalArgumentException("Цена товара не может быть отрицательной.");
        }

        this.name = name;
        this.quantity = quantity;
        this.price = price;

    }

    public double getTotalPice() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return name + ", " + quantity + "шт" + " (" + price + "руб/шт" + ")";
    }
}

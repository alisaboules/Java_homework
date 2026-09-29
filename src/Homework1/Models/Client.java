package Homework1.Models;

public class Client {

    private final String name;
    private final String phone;

    public Client(String name, String phone) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя клиента не может быть пустым.");
        }

        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Номер телефона клиента не может быть пустым.");
        }

        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Клиент: " + name
                + "\nТелефон: " + phone;
    }
}

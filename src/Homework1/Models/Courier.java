package Homework1.Models;

public class Courier {

    private final int id;
    private final String name;
    private final String phone;
    private CourierStatus status;

    public Courier(int id, String name, String phone) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID курьера не может быть меньше нуля.");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя курьера не может быть пустым.");
        }

        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Номер телефона курьера не модет быть пустым.");
        }

        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = CourierStatus.AVAILABLE;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public CourierStatus getStatus() {
        return status;
    }

    public void setStatus(CourierStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Курьер №" + id
                + "\nИмя: " + name
                + "\nТелефон: " + phone
                + "\nСтатус: " + status;
    }
}

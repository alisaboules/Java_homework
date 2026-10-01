package Homework1.ui;

import Homework1.Delivery.DeliveryMethod;
import Homework1.Delivery.ExpressDelivery;
import Homework1.Delivery.PickupDelivery;
import Homework1.Delivery.StandardDelivery;
import Homework1.Models.Client;
import Homework1.Models.Courier;
import Homework1.Models.Order;
import Homework1.Models.OrderItem;
import Homework1.Models.OrderStatus;
import Homework1.Services.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final OrderService orderService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(OrderService orderService) {
        this.orderService = orderService;
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Выберите пункт меню: ");

            try {
                switch (choice) {
                    case 1:
                        createOrder();
                        break;
                    case 2:
                        showOrders();
                        break;
                    case 3:
                        chooseDeliveryMethod();
                        break;
                    case 4:
                        assignCourier();
                        break;
                    case 5:
                        changeOrderStatus();
                        break;
                    case 6:
                        addCourier();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Такого пункта меню нет.");
                }
            } catch (RuntimeException exception) {
                System.out.println("Ошибка: " + exception.getMessage());
            }
        }

        System.out.println("Программа завершена.");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Служба доставки ===");
        System.out.println("1. Создать заказ");
        System.out.println("2. Показать заказы");
        System.out.println("3. Выбрать способ доставки");
        System.out.println("4. Назначить курьера");
        System.out.println("5. Изменить статус заказа");
        System.out.println("6. Добавить курьера");
        System.out.println("0. Завершить работу");
    }

    private void createOrder() {
        String name = readNonBlank("Имя клиента: ");
        String phone = readNonBlank("Телефон клиента: ");
        String address = readNonBlank("Адрес доставки: ");

        Client client = new Client(name, phone);
        List<OrderItem> items = new ArrayList<>();

        boolean addAnotherItem = true;

        while (addAnotherItem) {
            String itemName = readNonBlank("Название товара: ");
            int quantity = readPositiveInt("Количество: ");
            double price = readNonNegativeDouble("Цена за штуку: ");

            items.add(new OrderItem(itemName, quantity, price));
            addAnotherItem = readYesNo("Добавить ещё один товар? (да/нет): ");
        }

        Order order = orderService.createOrder(client, address);

        for (OrderItem item : items) {
            orderService.addItemToOrder(order.getNumber(), item);
        }

        System.out.println("Создан заказ №" + order.getNumber() + ".");
        printOrder(order);
    }

    private void showOrders() {
        List<Order> orders = orderService.getOrders();

        if (orders.isEmpty()) {
            System.out.println("Заказов пока нет.");
            return;
        }

        for (Order order : orders) {
            printOrder(order);
        }
    }

    private void chooseDeliveryMethod() {
        int orderNumber = readPositiveInt("Номер заказа: ");

        System.out.println("1. Стандартная доставка");
        System.out.println("2. Экспресс-доставка");
        System.out.println("3. Самовывоз");

        int choice = readInt("Выберите способ доставки: ");
        DeliveryMethod deliveryMethod;

        switch (choice) {
            case 1:
                deliveryMethod = new StandardDelivery();
                break;
            case 2:
                deliveryMethod = new ExpressDelivery();
                break;
            case 3:
                deliveryMethod = new PickupDelivery();
                break;
            default:
                System.out.println("Такого способа доставки нет.");
                return;
        }

        orderService.setDeliveryMethod(orderNumber, deliveryMethod);

        Order order = orderService.findOrderByNumber(orderNumber);
        System.out.println("Выбрано: " + deliveryMethod.getName());
        System.out.println("Стоимость доставки: " + order.getDeliveryCost() + " руб.");
        System.out.println("Ориентировочный срок: " + order.getEstimatedDeliveryDays() + " дн.");
    }

    private void assignCourier() {
        int orderNumber = readPositiveInt("Номер заказа: ");
        List<Courier> couriers = orderService.getAvailableCouriers();

        if (couriers.isEmpty()) {
            System.out.println("Нет свободных курьеров.");
            return;
        }

        System.out.println("Свободные курьеры:");

        for (Courier courier : couriers) {
            System.out.println("ID: " + courier.getId() + ", имя: " + courier.getName());
        }

        int courierId = readPositiveInt("Введите ID курьера: ");
        orderService.assignCourier(orderNumber, courierId);
        System.out.println("Курьер назначен.");
    }

    private void changeOrderStatus() {
        int orderNumber = readPositiveInt("Номер заказа: ");

        System.out.println("1. Отправлен");
        System.out.println("2. Доставлен");
        System.out.println("3. Отменён");
        System.out.println("4. Возвращён");

        int choice = readInt("Выберите новый статус: ");
        OrderStatus newStatus;

        switch (choice) {
            case 1:
                newStatus = OrderStatus.SHIPPED;
                break;
            case 2:
                newStatus = OrderStatus.DELIVERED;
                break;
            case 3:
                newStatus = OrderStatus.CANCELLED;
                break;
            case 4:
                newStatus = OrderStatus.RETURNED;
                break;
            default:
                System.out.println("Такого статуса нет.");
                return;
        }

        orderService.changeOrderStatus(orderNumber, newStatus);
        printOrder(orderService.findOrderByNumber(orderNumber));
    }

    private void addCourier() {
        String name = readNonBlank("Имя курьера: ");
        String phone = readNonBlank("Телефон курьера: ");

        Courier courier = orderService.createCourier(name, phone);
        System.out.println("Добавлен курьер №" + courier.getId() + ": " + courier.getName());
    }

    private void printOrder(Order order) {
        System.out.println();
        System.out.println("Заказ №" + order.getNumber());
        System.out.println("Клиент: " + order.getClient().getName());
        System.out.println("Телефон: " + order.getClient().getPhone());
        System.out.println("Адрес: " + order.getAddress());
        System.out.println("Статус: " + order.getStatus());
        System.out.println("Товары:");

        for (OrderItem item : order.getItems()) {
            System.out.println("- " + item + ", всего: " + item.getTotalPrice() + " руб.");
        }

        System.out.println("Стоимость товаров: " + order.getItemsTotal() + " руб.");

        double totalCost = order.getItemsTotal();

        if (order.getDeliveryMethod() == null) {
            System.out.println("Способ доставки пока не выбран.");
        } else {
            System.out.println("Способ доставки: " + order.getDeliveryMethod().getName());
            System.out.println("Стоимость доставки: " + order.getDeliveryCost() + " руб.");
            System.out.println("Срок: " + order.getEstimatedDeliveryDays() + " дн.");
            totalCost += order.getDeliveryCost();
        }

        if (order.getCourier() == null) {
            System.out.println("Курьер не назначен.");
        } else {
            System.out.println("Курьер: " + order.getCourier().getName());
        }

        System.out.println("Итоговая стоимость: " + totalCost + " руб.");
    }

    private String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isBlank()) {
                return value;
            }

            System.out.println("Поле не должно быть пустым.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Введите целое число.");
            }
        }
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);

            if (value > 0) {
                return value;
            }

            System.out.println("Число должно быть больше нуля.");
        }
    }

    private double readNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().replace(',', '.');

            try {
                double value = Double.parseDouble(input);

                if (value >= 0) {
                    return value;
                }
            } catch (NumberFormatException exception) {
                
            }

            System.out.println("Введите число, равное нулю или больше.");
        }
    }

    private boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("да") || answer.equalsIgnoreCase("д")) {
                return true;
            }

            if (answer.equalsIgnoreCase("нет") || answer.equalsIgnoreCase("н")) {
                return false;
            }

            System.out.println("Введите «да» или «нет».");
        }
    }
}
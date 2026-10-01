package Homework1;

import Homework1.Services.OrderService;
import Homework1.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        ConsoleUI consoleUI = new ConsoleUI(orderService);
        consoleUI.run();
    }
}
package ru.logging;

public class Main {
    public static void main(String[] args) {
        Test test = Ioc.loggingProxy();
        test.calculate();
        test.calculate(2, 3);
        test.print("a");
        test.print("a", "b", "c");
    }

}

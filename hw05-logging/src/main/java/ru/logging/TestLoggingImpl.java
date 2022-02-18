package ru.logging;

public class TestLoggingImpl implements TestLogging {

    @Override
    @Log
    public void calculation(int param1) {

        System.out.println("calculation 1");
    }


    @Override
    @Log
    public void calculation(int param1, int param2) {
        System.out.println("calculation 2");

    }
    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("calculation 1" + " " + param3);

    }

    @Deprecated
    public void testov(){}
}

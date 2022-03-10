package ru.logging;

public class TestImpl implements Test {

    @Log
    @Override
    public void print(String s) {
        System.out.println("Hi from print. (1 params)");

    }

    @Log
    @Override
    public void calculate(int a, int b) {
        System.out.println("Hi from calculate. (2 params) " + a * b);

    }

    @Log
    @Override
    public void calculate() {
        System.out.println("Hi from calculate. (0 params) " + 0);

    }

    @Log
    @Override
    public void print(String s, String b, String c) {
        System.out.println("Hi from print. (3 params)");

    }
}

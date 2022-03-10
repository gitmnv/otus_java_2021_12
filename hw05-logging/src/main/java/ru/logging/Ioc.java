package ru.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

class Ioc {
    static Test loggingProxy() {
        InvocationHandler invocationHandler = new MyInvocationHandler(new TestImpl());
        return (Test) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{Test.class}, invocationHandler);
    }
}

package ru.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public class Ioc {
    private Ioc() {
    }

    static TestLogging createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging myClass;

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //TestLogging c = createMyClass();
            // System.out.println(myClass.getClass().getAnnotations().length);
            // Object object =
            // System.out.println(object);
            Object o = myClass.getClass().newInstance();


            //  Arrays.stream(o.getClass().getMethods()).toList().forEach(v -> System.out.println(Arrays.stream(v.getDeclaredAnnotations()).toList()));


            //System.out.println(o.getClass().getMethod("calculation",Integer.class));
            List<Method> methods = Arrays.stream(o.getClass().getMethods()).toList();

            for (int i = 0; i < methods.size(); i++) {
                System.out.println(methods.get(i).getName());
                System.out.println(method.getName());
            }
            // System.out.println(Arrays.stream(o.getClass().getMethods()).toList());

            // o.getClass().getMethod(method.getName());
            // System.out.println(o.getClass().getClassLoader().getMethod("calculation"));
            // System.out.println(method.invoke(myClass, args));
            // System.out.println(method.getDeclaredAnnotations().length);
            //System.out.println("executed method: "+method.getName());
            return method.invoke(myClass, args);
        }
    }
}

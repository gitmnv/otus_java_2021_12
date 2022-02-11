
import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestUnit {

    public static void startTest(Class clazz) {

        List<Method> methods = Arrays.stream(clazz.getDeclaredMethods()).toList();
        List<Method> before = new ArrayList<>();
        List<Method> test = new ArrayList<>();
        List<Method> after = new ArrayList<>();
        HashMap<String, Boolean> stat = new HashMap<>();

        for (Method inspectedMethod : methods) {
            if (inspectedMethod.getDeclaredAnnotations().length > 0) {
                if (inspectedMethod.getDeclaredAnnotations()[0].annotationType().equals(Before.class)) {
                    inspectedMethod.setAccessible(true);
                    before.add(inspectedMethod);


                } else if (inspectedMethod.getDeclaredAnnotations()[0].annotationType().equals(Test.class)) {
                    inspectedMethod.setAccessible(true);
                    test.add(inspectedMethod);


                } else if (inspectedMethod.getDeclaredAnnotations()[0].annotationType().equals(After.class)) {
                    inspectedMethod.setAccessible(true);
                    after.add(inspectedMethod);

                }
            }

        }
        for (Method method : test) {

            Object newInstance = generateInstance(clazz);

            if (invokeBefore(before, newInstance)) {
                stat.putAll(invokeTest(method, newInstance));
                if (!invokeAfter(after, newInstance)){
                    stat.put(method.getName(),false);
                }
            } else {
                stat.put(method.getName(), false);
            }
        }
        System.out.println(stat);

    }

    private static Object generateInstance(Class clazz) {
        Object object = new Object();
        try {
            object = clazz.getDeclaredConstructor().newInstance();

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exception) {
            System.err.println(Arrays.stream(exception.getStackTrace()).toList());
        }
        return object;
    }

    private static boolean invokeBefore(List<Method> beforeMethodList, Object obj) {
        if (beforeMethodList.size() > 1) {
            throw new RuntimeException("Должна быть 1 @Before");
        } else {
            for (Method method : beforeMethodList) {
                try {
                    method.invoke(obj);
                } catch (InvocationTargetException | IllegalAccessException exception) {
                    System.err.println(Arrays.stream(exception.getStackTrace()).toList());
                    return false;
                }
            }
            return true;
        }
    }

    private static HashMap<String, Boolean> invokeTest(Method testMethod, Object obj) {
        if (testMethod == null) {
            throw new RuntimeException("Должно быть больше @Test");
        } else {
            HashMap<String, Boolean> stat = new HashMap<>();
            try {
                testMethod.invoke(obj);
                stat.put(testMethod.getName(), true);
            } catch (InvocationTargetException | IllegalAccessException exception) {
                System.err.println(Arrays.stream(exception.getStackTrace()).toList());
                stat.put(testMethod.getName(), false);
            }
            return stat;
        }
    }

    private static boolean invokeAfter(List<Method> afterMethodList, Object obj) {
        if (afterMethodList.size() > 1) {
            throw new RuntimeException("Должна быть 1 @After");
        } else {
            for (Method method : afterMethodList) {
                try {
                    method.invoke(obj);
                } catch (InvocationTargetException | IllegalAccessException exception) {
                    System.err.println(Arrays.stream(exception.getStackTrace()).toList());
                    return false;
                }
            }
            return true;
        }
    }
}

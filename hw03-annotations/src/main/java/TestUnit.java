import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestUnit {

    public static void startTest(Class clazz) {

        List<Method> methods = Arrays.stream(clazz.getDeclaredMethods()).toList();
        List<Method> before = new ArrayList<>();
        List<Method> test = new ArrayList<>();
        List<Method> after = new ArrayList<>();
        HashMap<String, String> stat = new HashMap<>();

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

            invokeBefore(before, newInstance);
            stat.putAll(invokeTest(method, newInstance));
            invokeAfter(after, newInstance);
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

    private static void invokeBefore(List<Method> beforeMethodList, Object obj) {
        if (beforeMethodList.size() > 1) {
            throw new RuntimeException("Должна быть 1 @Before");
        } else {
            for (Method method : beforeMethodList) {
                try {
                    method.invoke(obj);
                } catch (InvocationTargetException | IllegalAccessException exception) {
                    System.err.println(Arrays.stream(exception.getStackTrace()).toList());
                }
            }
        }
    }

    private static HashMap<String, String> invokeTest(Method testMethod, Object obj) {
        if (testMethod == null) {
            throw new RuntimeException("Должно быть больше @Test");
        } else {
            HashMap<String, String> stat = new HashMap<>();
            try {
                testMethod.invoke(obj);
                stat.put(testMethod.getName(), "OK");
            } catch (InvocationTargetException | IllegalAccessException exception) {
                System.err.println(Arrays.stream(exception.getStackTrace()).toList());
                stat.put(testMethod.getName(), "X");
            }
            return stat;
        }
    }

    private static void invokeAfter(List<Method> afterMethodList, Object obj) {
        if (afterMethodList.size() > 1) {
            throw new RuntimeException("Должна быть 1 @After");
        } else {
            for (Method method : afterMethodList) {
                try {
                    method.invoke(obj);
                } catch (InvocationTargetException | IllegalAccessException exception) {
                    System.err.println(Arrays.stream(exception.getStackTrace()).toList());
                }
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Before {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Test {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface After {
    }
}

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUnit {

    public static void startTest(Object obj, Class clazz) {
        List<Method> methods = Arrays.stream(clazz.getDeclaredMethods()).toList();
        List<Method> before = new ArrayList<>();
        List<Method> test = new ArrayList<>();
        List<Method> after = new ArrayList<>();

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
        invokeBefore(before, obj);
        invokeTest(test, obj);
        invokeAfter(after, obj);
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

    private static void invokeTest(List<Method> testMethodList, Object obj) {
        if (testMethodList.size() <= 0) {
            throw new RuntimeException("Должно быть больше @Test");
        } else {
            for (Method method : testMethodList) {
                try {
                    method.invoke(obj);
                } catch (InvocationTargetException | IllegalAccessException exception) {
                    System.err.println(Arrays.stream(exception.getStackTrace()).toList());
                }
            }
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

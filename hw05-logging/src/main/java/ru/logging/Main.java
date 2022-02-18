package ru.logging;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
          TestLogging logUnit = Ioc.createMyClass();
          logUnit.calculation(1);

       //    TestLogging testLogging = new TestLoggingImpl();
      //  Arrays.stream(testLogging.getClass().getMethods()).forEach(v -> System.out.println(Arrays.stream(v.getDeclaredAnnotations()).toList()));
     //   List<Method> ye = Arrays.stream(testLogging.getClass().getMethods()).toList();
     ////   for (int i = 0; i < ye.size(); i++) {
     ///       System.out.println();
     //   }
      //  System.out.println(testLogging.getClass().getGenericInterfaces()[0].getClass().getAnnotations().length);

        // logUnit.calculation(1,2);
        // logUnit.calculation(1,3,"sdsd");

        //new TestLoggingImpl().calculation(1);
    }

}

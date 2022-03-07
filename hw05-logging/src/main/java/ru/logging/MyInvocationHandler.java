package ru.logging;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyInvocationHandler implements InvocationHandler {
    private final Test test;
    private final List<MethodLogDataBuilder> methodsWithAnnotation;
    private final Class<?> thisAnnotation = Log.class;

    MyInvocationHandler(Test myClass) {
        this.test = myClass;
        methodsWithAnnotation = getMethodsWithAnnotation();
        System.out.println(methodsWithAnnotation);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        MethodLogDataBuilder methodLogDataBuilder = new MethodLogDataBuilder(method);
        if (methodsWithAnnotation.contains(methodLogDataBuilder)){
            System.out.println(methodLogDataBuilder);
        }
        try {
            return method.invoke(test,args);
        }catch (InvocationTargetException | IllegalAccessException invokeException){
            throw new RuntimeException(invokeException.getMessage());
        }

    }

    private List<MethodLogDataBuilder> getMethodsWithAnnotation() {
        Class<?> inspectClass = test.getClass();
        List<Method> allMethods = Arrays.stream(inspectClass.getMethods()).toList();
        List<MethodLogDataBuilder> methodLogDataBuilderList = new ArrayList<>();

        for (Method checkThisMethod : allMethods) {
            if (checkThisMethod.isAnnotationPresent((Class<? extends Annotation>) thisAnnotation)) {
                methodLogDataBuilderList.add(new MethodLogDataBuilder(checkThisMethod));
            }
        }


        return methodLogDataBuilderList;
    }
}

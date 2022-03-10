package ru.logging;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class MethodLogDataBuilder {
    private final String methodName;
    private final Class[] methodParameters;
    private final Method method;

    public MethodLogDataBuilder(Method method) {
        this.methodName = method.getName();
        this.methodParameters = method.getParameterTypes();
        this.method = method;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getMethodParameters() {
        return methodParameters;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodLogDataBuilder that = (MethodLogDataBuilder) o;
        return Objects.equals(methodName, that.methodName) && Arrays.equals(methodParameters, that.methodParameters);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(methodName);
        result = 31 * result + Arrays.hashCode(methodParameters);
        return result;
    }

    @Override
    public String toString() {
        return "Invoked method: {" +
                "methodName='" + methodName + '\'' +
                ",withParameters=" + Arrays.toString(methodParameters) +
                '}';
    }
}


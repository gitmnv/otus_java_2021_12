package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final static Class<AppComponent> component = AppComponent.class;
    private final static Class<AppComponentsContainerConfig> componentConfig = AppComponentsContainerConfig.class;

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws Exception {
        processConfig(initialConfigClass);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object appComponent : appComponents) {
            if (appComponent.getClass().getName().equals(componentClass.getName())
                    || Arrays.stream(appComponent.getClass().getInterfaces())
                    .allMatch(v -> v.getName().equalsIgnoreCase(componentClass.getName()))
            ) {
                return (C) appComponent;
            }
        }
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private void processConfig(Class<?> configClass) throws Exception {
        checkConfigClass(configClass);

        var instance = configClass.getConstructor().newInstance();
        var methods = getComponentMethods(configClass);
        var bean = new Object();

        for (Method inspectedMethod : methods) {
            if (inspectedMethod.getParameterCount() == 0) {
                bean = inspectedMethod.invoke(instance);
            } else {
                bean = inspectedMethod.invoke(instance, getParams(inspectedMethod));
            }
            appComponents.add(bean);
            appComponentsByName.put(inspectedMethod.getName(), bean);
        }

    }

    private Object[] getParams(Method method) {
        return Arrays.stream(method.getParameterTypes()).map(
                value -> Optional.ofNullable(getAppComponent(value))
                        .orElseThrow(() -> new RuntimeException("method not found."))
        ).toArray();
    }

    private List<Method> getComponentMethods(Class<?> configClass) {
        return Arrays.stream(configClass.getMethods()).filter
                (v -> v.isAnnotationPresent(component))
                .sorted(Comparator.comparingInt(value ->
                        value.getAnnotation(component).order()))
                .collect(Collectors.toList());
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(componentConfig)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }
}

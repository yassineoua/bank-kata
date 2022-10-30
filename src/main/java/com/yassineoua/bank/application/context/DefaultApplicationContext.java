package com.yassineoua.bank.application.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultApplicationContext implements ApplicationContext {

    private Map<Class<?>, Object> componentsMap = new ConcurrentHashMap<>();

    public <T> void registerComponent(Class<?> componentType, T componentInstance) {
        componentsMap.put(componentType, componentInstance);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<?> aClass) {
        Object o = componentsMap.get(aClass);
        if (o == null) {
            throw new RuntimeException(String.format("No component found for this token <%s>", aClass.toString()));
        }
        return (T) o;
    }
}

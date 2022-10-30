package com.yassineoua.bank.application.context;

public interface ApplicationContext {

    public <T> T getComponent(Class<?> aClass);
}

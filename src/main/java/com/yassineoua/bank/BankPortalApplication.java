package com.yassineoua.bank;

import com.yassineoua.bank.application.boot.ApplicationBootstrap;
import com.yassineoua.bank.application.boot.ConsoleApplicationBootstrap;
import com.yassineoua.bank.application.context.ApplicationContext;
import com.yassineoua.bank.application.context.DefaultApplicationContext;
import com.yassineoua.bank.application.initializer.ApplicationInitializer;
import com.yassineoua.bank.application.initializer.ContextInitializer;
import com.yassineoua.bank.application.initializer.DataInitializer;

import java.util.Arrays;

public class BankPortalApplication {

    private static ApplicationContext initialize(ApplicationInitializer... initializers) {
        ApplicationContext applicationContext = new DefaultApplicationContext();
        Arrays.stream(initializers).forEach(initializer -> initializer.init(applicationContext));
        return applicationContext;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = initialize(new ContextInitializer(), new DataInitializer());
        ApplicationBootstrap applicationBootstrap = new ConsoleApplicationBootstrap();
        applicationBootstrap.start(applicationContext);
    }
}

package com.yassineoua.bank.application;

import com.yassineoua.bank.application.context.DefaultApplicationContext;
import com.yassineoua.bank.helpers.DummyComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultApplicationContextTest {

    private DefaultApplicationContext applicationContext;

    @BeforeEach
    void setup() {
        applicationContext = new DefaultApplicationContext();
    }

    @Test
    void testRegisterComponent() {
        DummyComponent component1 = new DummyComponent("component1");

        applicationContext.registerComponent(DummyComponent.class, component1);
        Object resolvedComponent = applicationContext.getComponent(DummyComponent.class);

        Assertions.assertNotNull(resolvedComponent);
        Assertions.assertInstanceOf(DummyComponent.class, resolvedComponent);
        Assertions.assertEquals(resolvedComponent, component1);

        DummyComponent castedResolvedComponent = (DummyComponent) resolvedComponent;
        Assertions.assertEquals(castedResolvedComponent.getTag(), component1.getTag());
    }
}

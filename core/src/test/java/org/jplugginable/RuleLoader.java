package org.jplugginable;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodCall;

import java.lang.reflect.Method;
import java.util.Arrays;

import static net.bytebuddy.matcher.ElementMatchers.named;

class RuleLoader {

    RuleInterface loadFromClass(Class<?> clazz) {
        try {

            Method method = getRunMethod(clazz);

            return (RuleInterface) new ByteBuddy()
                    .subclass(clazz)
                    .implement(RuleInterface.class)
                    .method(named("run"))
                    .intercept(MethodCall.invoke(method)
                            .andThen(FixedValue.value(true)))
                    .make()
                    .load(ClassLoader.getSystemClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Method getRunMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .filter(this::filterRunAnnotated)
                .findFirst().orElse(null);
    }

    private boolean filterRunAnnotated(Method method) {
        return method.isAnnotationPresent(Run.class);
    }
}

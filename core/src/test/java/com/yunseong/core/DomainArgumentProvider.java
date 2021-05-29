package com.yunseong.core;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class DomainArgumentProvider implements ArgumentsProvider, AnnotationConsumer<DomainArgumentSource> {

    @Override
    public void accept(DomainArgumentSource domainArgumentSource) {

    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Method method = context.getRequiredTestMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] arguments = new Object[parameterTypes.length];
        DomainArgumentResolver argumentResolver = DomainArgumentResolver.INSTANCE;
        for(int i=0;i<arguments.length;i++) {
            Optional<Object> argument = argumentResolver.tryResolve(parameterTypes[i]);
            arguments[i] = argument.orElse(null);
        }
        return Arrays.stream(new Arguments[] { Arguments.of(arguments) });
    }
}

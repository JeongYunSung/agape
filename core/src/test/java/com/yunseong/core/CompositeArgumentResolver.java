package com.yunseong.core;

import java.util.Optional;

public class CompositeArgumentResolver implements DomainArgumentResolver {

    private final DomainArgumentResolver[] domainArgumentResolvers;

    public CompositeArgumentResolver(DomainArgumentResolver...domainArgumentResolvers) {
        this.domainArgumentResolvers = domainArgumentResolvers;
    }

    @Override
    public Optional<Object> tryResolve(Class<?> parameterType) {
        for(var domainArgumentResolver : this.domainArgumentResolvers) {
            Optional<Object> argument = domainArgumentResolver.tryResolve(parameterType);
            if(argument.isPresent()) {
                return argument;
            }
        }
        return Optional.empty();
    }
}

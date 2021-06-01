package com.yunseong.gateway.config;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CoreLoadBalanceConfiguration {

    private static final String serviceId = "core";

    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context, LoadBalanceServerConfiguration configuration) {
        return ServiceInstanceListSupplier.builder()
                .withBase(new ServiceInstanceListSupplier() {
                    @Override
                    public String getServiceId() {
                        return serviceId;
                    }

                    @Override
                    public Flux<List<ServiceInstance>> get() {
                        AtomicInteger integer = new AtomicInteger(1);
                        return Flux.just(configuration.getServers().stream().map(a ->
                                new DefaultServiceInstance(serviceId + integer.getAndIncrement(), serviceId, a.getHost(), a.getPort(), false)).collect(Collectors.toList()));
                    }
                })
                .withRetryAwareness()
                .withHealthChecks(WebClient.builder().build())
                .build(context);
    }
}

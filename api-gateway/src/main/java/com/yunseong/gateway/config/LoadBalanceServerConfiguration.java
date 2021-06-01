package com.yunseong.gateway.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ConfigurationProperties(value = "spring.cloud.loadbalancer")
public class LoadBalanceServerConfiguration {

    private List<Address> servers;

    public void setServers(String servers) {
        this.servers = Arrays.asList(servers.replaceAll(" ", "")
                .split(","))
                .stream().map(s -> {
                    String[] split = s.split(":");
                    return new Address(split[0], Integer.parseInt(split[1]));
                }).collect(Collectors.toList());
    }

    public List<Address> getServers() {
        return this.servers;
    }
}

@Getter
@Setter
@AllArgsConstructor
class Address {

    private String host;
    private int port;
}
package com.smartosc.userservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(Credential.class)
public class UserServiceApplication implements CommandLineRunner {

    private final Credential credential;

    public UserServiceApplication(Credential credential) {
        this.credential = credential;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("========properties========");
        System.out.println("username=" + credential.getUsername());
        System.out.println("password=" + credential.getPassword());
    }
}

package com.smartosc.userservice;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app")
public class Credential {

    private String username;
    private String password;
}

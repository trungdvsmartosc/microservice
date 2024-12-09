package com.smartosc.accountqueryservice.model.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {

    @Column(nullable = false, length = 50)
    private String holder;

    @Column(nullable = false, length = 15)
    private String number;

    private double balance;
    private boolean status;
}

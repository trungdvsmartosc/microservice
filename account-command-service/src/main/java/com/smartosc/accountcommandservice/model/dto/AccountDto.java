package com.smartosc.accountcommandservice.model.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {

    @Column(nullable = false, length = 15)
    private String number;

    private String holder;
    private double balance;
    private boolean status;
}

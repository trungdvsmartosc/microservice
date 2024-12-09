package com.smartosc.transaction.model.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @Column(nullable = false)
    private String senderNumber;

    @Column(nullable = false)
    private String receiverNumber;

    @Column(nullable = false)
    private double amount;

    private String remarks;

    /**
     * see {@link Transaction.TYPE}
     */
    private String type;
}

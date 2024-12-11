package com.smartosc.transactionqueryservice.model.mapper;

import com.smartosc.transactionqueryservice.model.dto.TransactionDto;
import com.smartosc.transactionqueryservice.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(source = "sender.number", target = "senderNumber")
    @Mapping(source = "receiver.number", target = "receiverNumber")
    TransactionDto convertToTransactionDto(Transaction transaction);
}

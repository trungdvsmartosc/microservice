package com.smartosc.transactioncommandservice.model.mapper;

import com.smartosc.transactioncommandservice.model.dto.TransactionDto;
import com.smartosc.transactioncommandservice.model.entity.Transaction;
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

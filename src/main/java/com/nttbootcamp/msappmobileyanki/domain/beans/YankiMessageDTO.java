package com.nttbootcamp.msappmobileyanki.domain.beans;

import com.nttbootcamp.msappmobileyanki.domain.enums.YankiTransactionType;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YankiMessageDTO {

    private String debitCardNumber;
    private String businessPartnerId;
    private String linkedAccount;
    private BigDecimal amount;
    private YankiTransactionType transactionType;
}

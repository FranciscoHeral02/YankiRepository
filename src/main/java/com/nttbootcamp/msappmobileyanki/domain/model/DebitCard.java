package com.nttbootcamp.msappmobileyanki.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class DebitCard {
    @Id
    private String debitCardNumber;
    private String linkedAccount;
    private String businessPartnerId;
    private BigDecimal amount;
}

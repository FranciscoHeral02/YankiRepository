/**
 * 
 */
package com.nttbootcamp.msappmobileyanki.domain.model;


import com.nttbootcamp.msappmobileyanki.domain.enums.YankiTransactionType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document
public class Transaction {
	
	@Id
    private String transactionId;
    @NotNull
    private YankiTransactionType transactiontype;
    private String fromCellphoneAccount;
    private String toCellphoneAccount;
    private LocalDateTime createDateTime;
    private LocalDate createDate;
    @NotNull
    @Digits(integer =20, fraction=6)
    private BigDecimal amount;
    @Digits(integer =20, fraction=6)
    private BigDecimal debit;
    @Digits(integer =20, fraction=6)
    private BigDecimal credit;
    @Digits(integer =20, fraction=6)
    private BigDecimal balance;
    private String debitCardId;

}

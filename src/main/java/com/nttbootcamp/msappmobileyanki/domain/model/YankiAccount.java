package com.nttbootcamp.msappmobileyanki.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@ToString
public class YankiAccount {

	//Yanki account field
	@Id
	private String cellphoneNumber;
	@Digits(integer =20, fraction=6)
	private BigDecimal balance;
	@NotNull
	private LocalDateTime createdDateTime;
	@NotNull
	private LocalDate createdDate;
	@NotNull	
	private Boolean valid;
	private String linkedDebitCard;
	private String linkedAccount;
	private String email;
	private String imei;
	private String docIdemType;
	private String docNum;
}
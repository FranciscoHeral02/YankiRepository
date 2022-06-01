package com.nttbootcamp.msappmobileyanki.domain.repository;
import com.nttbootcamp.msappmobileyanki.domain.model.YankiAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collection;

public interface YankiAccountRepository extends ReactiveMongoRepository<YankiAccount, String> {
	Mono<Boolean> existsByCellphoneNumber(String cellphoneNumber);
	Mono<YankiAccount> findByLinkedAccount(String linkedAccount);
}

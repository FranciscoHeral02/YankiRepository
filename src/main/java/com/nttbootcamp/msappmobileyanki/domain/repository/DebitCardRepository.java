package com.nttbootcamp.msappmobileyanki.domain.repository;

import com.nttbootcamp.msappmobileyanki.domain.beans.YankiMessageDTO;
import com.nttbootcamp.msappmobileyanki.domain.model.DebitCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DebitCardRepository extends ReactiveMongoRepository<DebitCard, String> {
    Mono<Boolean> existsByDebitCardNumberAndBusinessPartnerId(String cardNumber,String bdPartnerId);

    Mono<DebitCard> findByDebitCardNumberAndBusinessPartnerId(String debitCardNumber, String docNum);
}

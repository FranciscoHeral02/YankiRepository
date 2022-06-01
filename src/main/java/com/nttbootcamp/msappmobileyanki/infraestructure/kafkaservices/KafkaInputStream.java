package com.nttbootcamp.msappmobileyanki.infraestructure.kafkaservices;

import com.nttbootcamp.msappmobileyanki.domain.beans.YankiMessageDTO;
import com.nttbootcamp.msappmobileyanki.domain.model.DebitCard;
import com.nttbootcamp.msappmobileyanki.domain.repository.DebitCardRepository;
import com.nttbootcamp.msappmobileyanki.domain.repository.YankiAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Component
public class KafkaInputStream {
    @Autowired
    private DebitCardRepository debitCardRepository;
    @Autowired
    private YankiAccountRepository repository;

    @Bean
    Consumer<YankiMessageDTO> input(){
        return message->{
            switch (message.getTransactionType()) {
                case DEPOSIT:
                    System.out.println(message.getLinkedAccount());
                    repository.findByLinkedAccount(message.getLinkedAccount())
                            .flatMap(m->{   System.out.println(m);
                                            BigDecimal bigDecimal=m.getBalance().add(message.getAmount());
                                            m.setBalance(bigDecimal);

                                           return repository.save(m);
                            }).subscribe();

                    break;
                case WITHDRAWAL:
                    System.out.println(message.getLinkedAccount());
                    repository.findByLinkedAccount(message.getLinkedAccount())
                            .flatMap(m->{   System.out.println(m);
                                BigDecimal bigDecimal=m.getBalance().subtract(message.getAmount());
                                m.setBalance(bigDecimal);

                                return repository.save(m);
                            }).subscribe();
                    break;
                case DEBIT_CARD_CREATION:
                    System.out.println(message);
                    DebitCard d= DebitCard.builder()
                                .amount(message.getAmount())
                                .debitCardNumber(message.getDebitCardNumber())
                                .linkedAccount(message.getLinkedAccount())
                                .businessPartnerId(message.getBusinessPartnerId())
                                .build();
                    debitCardRepository.save(d).subscribe();
                    break;
            }
        };
    }
}

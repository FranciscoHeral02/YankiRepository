package com.nttbootcamp.msappmobileyanki.infraestructure.services;


import com.nttbootcamp.msappmobileyanki.application.exception.EntityNotExistsException;
import com.nttbootcamp.msappmobileyanki.application.exception.ResourceNotCreatedException;
import com.nttbootcamp.msappmobileyanki.domain.beans.YankiMessageDTO;
import com.nttbootcamp.msappmobileyanki.domain.beans.YankiOperationDTO;
import com.nttbootcamp.msappmobileyanki.domain.enums.YankiTransactionType;
import com.nttbootcamp.msappmobileyanki.domain.model.Transaction;
import com.nttbootcamp.msappmobileyanki.domain.model.YankiAccount;
import com.nttbootcamp.msappmobileyanki.domain.repository.TransactionRepository;
import com.nttbootcamp.msappmobileyanki.infraestructure.interfaces.IYankiAccountService;
import com.nttbootcamp.msappmobileyanki.infraestructure.interfaces.IYankiAccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;

@Service
public class YankiAccountTransactionService implements IYankiAccountTransactionService {

    //Services and Repositories
    @Autowired
    private TransactionRepository tRepository;
    @Autowired
    private YankiAccountService accountService;
    @Autowired
    private StreamBridge streamBridge;
    //Crud
    @Override
    public Flux<Transaction> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Mono<Transaction> delete(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Mono<Transaction> findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Mono<ResponseEntity<Transaction>> update(String id, Transaction request) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Flux<Transaction> saveAll(List<Transaction> a) {
        // TODO Auto-generated method stub
        return null;
    }

    //Business Logic

    @Override
    public Mono<Transaction> doYankiPayment(YankiOperationDTO dto) {
        Mono<YankiAccount> fromAccount = accountService.findById(dto.getFromCellphoneAccount())
                .switchIfEmpty(Mono.error(new EntityNotExistsException("Origin account doesn't exists")));
        Mono<YankiAccount> toAccount = accountService.findById(dto.getToCellphoneAccount())
                .switchIfEmpty(Mono.error(new EntityNotExistsException("Destiny account doesn't exists")));

        return  Mono.zip(fromAccount,toAccount)
                .filter(a-> !(dto.getFromCellphoneAccount().equals(dto.getToCellphoneAccount())))
                .switchIfEmpty(Mono.error(new ResourceNotCreatedException("Account cannot be the same")))
                .filter(a->a.getT1().getBalance().compareTo(dto.getAmount())>=0)
                .switchIfEmpty(Mono.error(new ResourceNotCreatedException("Yanki account doesn't have sufficient funds")))
                .flatMap(a->saveTransactionPayment.apply(a,dto))
                .switchIfEmpty(Mono.error(new ResourceNotCreatedException("Transaction error")));

    }

    //Functions
    private final BiFunction<Tuple2<YankiAccount,YankiAccount>,YankiOperationDTO, Mono<Transaction>> saveTransactionPayment
            = (tuple2,dto) -> {

           Transaction t = Transaction.builder()
                .debit(dto.getAmount())
                .credit(dto.getAmount())
                .fromCellphoneAccount(tuple2.getT1().getCellphoneNumber())
                .toCellphoneAccount(tuple2.getT2().getCellphoneNumber())
                .transactiontype(YankiTransactionType.YANKI_PAYMENT)
                .createDate(LocalDate.now())
                .createDateTime(LocalDateTime.now())
                .build();

           return Mono.just(tuple2)
                    .flatMap(t1->{
                                    if(t1.getT1().getLinkedDebitCard()!=null){
                                        YankiMessageDTO w= YankiMessageDTO
                                                .builder()
                                                .debitCardNumber(t1.getT1().getLinkedDebitCard())
                                                .businessPartnerId(t1.getT1().getDocNum())
                                                .linkedAccount(t1.getT1().getLinkedAccount())
                                                .amount(dto.getAmount())
                                                .transactionType(YankiTransactionType.ACCOUNT_WITHDRAWAL)
                                                .build();
                                        streamBridge.send("output-out-0",w, MimeTypeUtils.APPLICATION_JSON);
                                    }
                                    return accountService.updateBalanceSend(dto.getFromCellphoneAccount(),
                                            dto.getAmount()).thenReturn(t1);
                    })
                   .flatMap(t2-> {

                                    if (tuple2.getT2().getLinkedDebitCard() != null) {
                                       YankiMessageDTO w = YankiMessageDTO
                                               .builder()
                                               .debitCardNumber(t2.getT1().getLinkedDebitCard())
                                               .businessPartnerId(t2.getT2().getDocNum())
                                               .linkedAccount(t2.getT2().getLinkedAccount())
                                               .amount(dto.getAmount())
                                               .transactionType(YankiTransactionType.ACCOUNT_DEPOSIT)
                                               .build();
                                       streamBridge.send("output-out-0", w, MimeTypeUtils.APPLICATION_JSON);
                                   }
                                   return accountService.updateBalanceReceive(dto.getToCellphoneAccount(),
                                           dto.getAmount()).thenReturn(t2);

                   }).then(tRepository.save(t));
    };



}







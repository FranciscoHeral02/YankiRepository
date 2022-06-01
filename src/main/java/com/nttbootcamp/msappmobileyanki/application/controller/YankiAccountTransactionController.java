package com.nttbootcamp.msappmobileyanki.application.controller;

import com.nttbootcamp.msappmobileyanki.domain.beans.YankiOperationDTO;
import com.nttbootcamp.msappmobileyanki.infraestructure.interfaces.IYankiAccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/YankiMobile/Actions/YankiOperations")
public class YankiAccountTransactionController {
    @Autowired
    private IYankiAccountTransactionService service;
    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> yankiPayment(@Valid @RequestBody Mono<YankiOperationDTO> request) {

        Map<String, Object> response = new HashMap<>();

        return request.flatMap(a -> service.doYankiPayment(a).map(c -> {
            response.put("Yanki Payment Operation", c);
            response.put("message", "Successful Yanki Payment Transaction ");
            return ResponseEntity.created(URI.create("/YankiMobile/Actions/YankiOperations".concat(c.getTransactionId())))
                    .contentType(MediaType.APPLICATION_JSON).body(response);
        }));
    }

}

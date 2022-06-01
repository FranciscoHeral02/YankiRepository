package com.nttbootcamp.msappmobileyanki.application.controller;


import com.nttbootcamp.msappmobileyanki.domain.beans.AvailableAmountDTO;
import com.nttbootcamp.msappmobileyanki.domain.beans.CreateYankiAccountDTO;
import com.nttbootcamp.msappmobileyanki.domain.beans.CreateYankiAccountWithCardDTO;
import com.nttbootcamp.msappmobileyanki.domain.model.YankiAccount;
import com.nttbootcamp.msappmobileyanki.infraestructure.services.YankiAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/YankiMobile/Entities/YankiAccount")
public class YankiAccountController {
	@Autowired
	private YankiAccountService service;
	@GetMapping
	public Mono<ResponseEntity<Flux<YankiAccount>>> findAll() {
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll()));
	}
	@GetMapping("/{id}")
	public Mono<YankiAccount> findById(@PathVariable String id) {
		return service.findById(id);
	}
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> createAccount(@Valid @RequestBody Mono<CreateYankiAccountDTO> request) {

		Map<String, Object> response = new HashMap<>();

		return request.flatMap(a -> service.createYankiAccount(a).map(c -> {
			response.put("Account", c);
			response.put("Message", "Account created Successfully");
			return ResponseEntity.created(URI.create("/Accounts/Entities/Account/".concat(c.getCellphoneNumber())))
					.contentType(MediaType.APPLICATION_JSON).body(response);
		}));
	}
	@PostMapping("/WithCard")
	public Mono<ResponseEntity<Map<String, Object>>> createAccountWithCard(@Valid @RequestBody Mono<CreateYankiAccountWithCardDTO> request) {

		Map<String, Object> response = new HashMap<>();

		return request.flatMap(a -> service.createYankiAccountWithCard(a).map(c -> {
			response.put("Account", c);
			response.put("Message", "Account created Successfully");
			return ResponseEntity.created(URI.create("/Accounts/Entities/Account/".concat(c.getCellphoneNumber())))
					.contentType(MediaType.APPLICATION_JSON).body(response);
		}));
	}
	@PostMapping("/SaveAll")
	public Mono<ResponseEntity<Map<String, Object>>> saveAll(@RequestBody Flux<YankiAccount> businessPartnerList) {

		Map<String, Object> response = new HashMap<>();

		return businessPartnerList.collectList().flatMap(a -> service.saveAll(a).collectList()).map(c -> {
			response.put("Accounts", c);
			response.put("Message", "Accounts Created Successfully");
			return ResponseEntity.created(URI.create("/Accounts/Entities/Account/SaveAll")).contentType(MediaType.APPLICATION_JSON)
					.body(response);
		});
	}
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Map<String, Object> >> delete(@PathVariable String id) {
		 Map<String, Object> response = new HashMap<>();

		return service.delete(id)
				.map(c -> {
					response.put("Account", c);
					response.put("Message", "Accounts deleted successfully");
					return ResponseEntity.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.location( URI.create("/Accounts/Entities/Account".concat(c.getCellphoneNumber())))
							.body(response);
				});
	}

	@GetMapping("/GetAvailableAmount/{id}")
	public Mono<AvailableAmountDTO> getAvailableAmount(@PathVariable String id) {
		return service.getAvailableAmount(id);
	}
}

package com.nttbootcamp.msappmobileyanki;

import com.nttbootcamp.msappmobileyanki.domain.repository.YankiAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(SpringRunner.class)
@DataMongoTest
public class AccountRepositoryTest {

	@Autowired
	YankiAccountRepository repository;


	@Test
	public void shouldBeNotEmpty() {

		System.out.println(repository.findByLinkedAccount("81345420523459")

				.block().toString());

	}
}

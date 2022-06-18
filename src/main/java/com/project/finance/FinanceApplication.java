package com.project.finance;

import com.project.finance.dbServices.ClientService;
import com.project.finance.entities.Client;
import com.project.finance.entities.RefreshToken;
import com.project.finance.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Optional;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)

public class FinanceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);

	}

	@Autowired
	ClientService clientService;

	@Autowired
	RefreshTokenRepository tokenRepository;

	@Override
	public void run(String... args) throws Exception {
		/*Instant now = Instant.now();
		Optional<Client> loken16 = clientService.findClientByLogin("Loken16");
		Client client = loken16.get();
		System.out.println(client);
		RefreshToken token = RefreshToken.builder().token("abaa").client(client).expiryDate(now).build();
		tokenRepository.saveToken(token);*/
		//new RefreshToken(Long.valueOf(1),client,"trafaf", now )
		/*Optional<Client> loken16 = clientService.findClientByLogin("Loken16");
		Client client = loken16.get();
		tokenRepository.deleteByClientId(client);*/
		Stock stock = YahooFinance.get("TSLA");
		System.out.println(stock.getHistory());


	}
}

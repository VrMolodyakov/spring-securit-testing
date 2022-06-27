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
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;


import java.io.*;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Optional;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableFeignClients
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


		/*BufferedWriter writer = new BufferedWriter(new FileWriter("codes.txt"));
		URL url = new URL("https://financialmodelingprep.com/api/v3/stock/list?apikey=a5d3f26d021264b83814d40972c12275");
		int counter  = 0;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			for (String line; (line = reader.readLine()) != null;) {
				if(counter>=1 && counter<=2) {
					writer.write(line);
					writer.write(System.lineSeparator());
				}
				++counter;
				if(counter == 7){
					counter = 0;
				}
			}
		}*/
		/*int counter = 0;
		BufferedWriter writer = new BufferedWriter(new FileWriter("codes2.json"));
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("codes1.json")))) {
			for (String line; (line = reader.readLine()) != null;) {
				if(counter>=0 && counter<=3) {
					if(counter == 3 ){
						line = line.substring(0, line.length()-1);
						writer.write(line);
						writer.write(System.lineSeparator());
					}else{
						writer.write(line);
						writer.write(System.lineSeparator());
					}

				}else if(counter>=4 && counter<=5){

				}else if(counter == 6){
					counter = -1;
				}
				++counter;
			}
		}*/

	}
}

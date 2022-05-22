package com.project.finance;

import com.project.finance.clientDBService.ClientService;
import com.project.finance.repository.ClientRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class FinanceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);
	}

	@Autowired
	ClientService clientService;

	@Override
	public void run(String... args) throws Exception {
		//System.out.println(clientService.findClientByLogin("Loken15"));
	}
}

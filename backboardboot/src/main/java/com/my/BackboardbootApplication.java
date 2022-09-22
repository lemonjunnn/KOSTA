package com.my;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.my.service.BankService;

@SpringBootApplication
public class BackboardbootApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context =
        SpringApplication.run(BackboardbootApplication.class, args);

    // Fetching the employee object from the application context.
    BankService bank = context.getBean(BankService.class);
    // Displaying balance in the account
    String accnumber = "12345";
    bank.displayBalance(accnumber);
    // Closing the context object
    context.close();
  }

}

package io.jmerta.tau.repository;

import io.jmerta.tau.TauApplication;
import io.jmerta.tau.config.DataConfig;
import io.jmerta.tau.domain.accountManagment.entity.Account;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest(excludeAutoConfiguration = {AutoConfigureTestDatabase.class, SpringBootTest.class})
@SpringBootTest(classes = {TauApplication.class, DataConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FlywayTest
public class AccountRepositoryTests {


    @Autowired
    AccountRepository accountRepository;

    @Test
    public void getAccountByUsername(){
        Account account = accountRepository.getAccountByUsername("Admin");
        assertThat(account).isNotNull();
        assertThat(account.getUsername()).isEqualToIgnoringCase("Admin");

    }

    @Ignore
    @Test
    public void getAccountByToken(){
        Account account = accountRepository.getAccountByToken(null);
        assertThat(account).isNotNull();
        assertThat(account.getUsername()).isEqualToIgnoringCase("Admin");
    }

    @Test
    public void getAccountList(){
        List<Account> accountList = accountRepository.getAllAccounts();

        assertThat(accountList).isNotEmpty();
    }
}

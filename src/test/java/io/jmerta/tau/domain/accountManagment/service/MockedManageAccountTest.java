package io.jmerta.tau.domain.accountManagment.service;

import io.jmerta.tau.TauApplication;
import io.jmerta.tau.config.DataConfig;
import io.jmerta.tau.domain.accountManagment.entity.Account;
import io.jmerta.tau.repository.AccountRepository;
import io.jmerta.tau.repository.SessionRepository;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@MybatisTest(excludeAutoConfiguration = {AutoConfigureTestDatabase.class, SpringBootTest.class})
@SpringBootTest(classes = {TauApplication.class, DataConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FlywayTest
public class MockedManageAccountTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    SessionRepository sessionRepository;

    @InjectMocks
    ManageAccount manageAccount;

    public MockedManageAccountTest() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void getUserByUsername(){
        when(accountRepository.getAccountByUsername("Admin")).thenReturn(new Account("Admin","password",null,1l));

        Account account = manageAccount.loadUserByUsername("Admin");
        assertThat(account).isNotNull();
        assertThat(account.getUsername()).isEqualToIgnoringCase("Admin");
    }

    @Test
    public void getAllUsers(){
        Account account1 = new Account("User 1", null,null,null);
        Account account2 = new Account("User 2", null,null,null);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);

        when(accountRepository.getAllAccounts()).thenReturn(accountList);

        List<Account> accounts = manageAccount.getAllAccounts();
        assertThat(accounts).isNotEmpty();
        assertThat(accounts).hasSize(2);
    }
}


package io.jmerta.tau.domain.accountManagment.service;

import io.jmerta.tau.TauApplication;
import io.jmerta.tau.config.DataConfig;
import io.jmerta.tau.domain.accountManagment.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest(excludeAutoConfiguration = {AutoConfigureTestDatabase.class, SpringBootTest.class})
@SpringBootTest(classes = {TauApplication.class, DataConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AccountService.class)
public class AccountServiceTest {


    @Autowired
    AccountService accountService;


    @Test
    public void getUserByUsername(){
        Account account = accountService.loadUserByUsername("Admin");
        assertThat(account).isNotNull();
        assertThat(account.getUsername()).isEqualToIgnoringCase("Admin");
    }

    @Test
    public void getAllUsers(){
        List<Account> accountList = accountService.getAllAccounts();

        System.out.println(accountList);
        assertThat(accountList).isNotEmpty();
        assertThat(accountList).allMatch(account -> account.getPassword()==null);
    }

    @Test
    public void createNewuser(){
        Account account = new Account("Nowy user", "haslo1234",null,null);

        accountService.createNewAccount(account);

        Account accountFromDb = accountService.loadUserByUsername("Nowy user");
        System.out.println(account);
        System.out.println(accountFromDb);
        assertThat(accountFromDb).isNotNull();
        assertThat(accountFromDb.getPassword()).isEqualTo(null);
    }


    @Test
    public void testSessionMechanism(){
        Account account = new Account("User testowy","password",null,null);

        Account accountFromDb = accountService.createNewAccount(account);
        Account accountFromDbOnUsername = accountService.loadUserByUsername("User testowy");

        assertThat(accountFromDb).isNotNull();
        assertThat(accountFromDbOnUsername).isNotNull();

        String uuid = UUID.randomUUID().toString();
        accountService.saveSession(accountFromDbOnUsername, uuid);

        System.out.println(accountFromDb);
        Account accountFromSession = accountService.loadUserByToken(uuid);

        assertThat(accountFromSession).isNotNull();
        assertThat(accountFromSession.getUsername()).isEqualToIgnoringCase(accountFromDb.getUsername());
    }
}

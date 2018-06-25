package io.jmerta.tau.domain.accountManagment.service;

import io.jmerta.tau.TauApplication;
import io.jmerta.tau.config.DataConfig;
import io.jmerta.tau.domain.accountManagment.entity.Account;
import org.flywaydb.test.annotation.FlywayTest;
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
@Import(ManageAccount.class)
@FlywayTest
public class ManageAccountTest {


    @Autowired
    ManageAccount manageAccount;


    @Test
    public void getUserByUsername(){
        Account account = manageAccount.loadUserByUsername("Admin");
        assertThat(account).isNotNull();
        assertThat(account.getUsername()).isEqualToIgnoringCase("Admin");
    }

    @Test
    public void getAllUsers(){
        List<Account> accountList = manageAccount.getAllAccounts();

        System.out.println(accountList);
        assertThat(accountList).isNotEmpty();
        assertThat(accountList).allMatch(account -> account.getPassword()==null);
    }

    @Test
    public void createNewuser(){
        Account account = new Account("Nowy user", "haslo1234",null,null);
        manageAccount.createNewAccount(account);

        Account accountFromDb = manageAccount.loadUserByUsername("Nowy user");
        System.out.println(account);
        System.out.println(accountFromDb);
        assertThat(accountFromDb).isNotNull();
        assertThat(accountFromDb.checkPassword(account.getPassword()));
    }


    @Test
    public void testSessionMechanism(){
        Account account = new Account("User testowy","password",null,null);

        Account accountFromDb = manageAccount.createNewAccount(account);
        Account accountFromDbOnUsername = manageAccount.loadUserByUsername("User testowy");

        assertThat(accountFromDb).isNotNull();
        assertThat(accountFromDbOnUsername).isNotNull();

        String uuid = UUID.randomUUID().toString();
        manageAccount.saveSession(accountFromDbOnUsername, uuid);

        System.out.println(accountFromDb);
        Account accountFromSession = manageAccount.loadUserByToken(uuid);

        assertThat(accountFromSession).isNotNull();
        assertThat(accountFromSession.getUsername()).isEqualToIgnoringCase(accountFromDb.getUsername());
    }
}

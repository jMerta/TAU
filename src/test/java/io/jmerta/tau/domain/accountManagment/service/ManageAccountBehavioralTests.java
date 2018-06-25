package io.jmerta.tau.domain.accountManagment.service;

import io.jmerta.tau.TauApplication;
import io.jmerta.tau.config.DataConfig;
import io.jmerta.tau.domain.accountManagment.entity.Account;
import io.jmerta.tau.domain.accountManagment.util.AuthManager;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest(excludeAutoConfiguration = {AutoConfigureTestDatabase.class, SpringBootTest.class})
@SpringBootTest(classes = {TauApplication.class, DataConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ManageAccount.class, AuthManager.class})
@FlywayTest
public class ManageAccountBehavioralTests {

    @Autowired
    private ManageAccount manageAccount;

    @Autowired
    private AuthManager authManager;


    @Before
    public void setUp() throws Exception {
        System.out.println("Given account with username: testUser and password: password");

        Account account = new Account("testUser");
        account.setPassword("password");

        manageAccount.createNewAccount(account);
    }

    @Test
    public void Test() {
        System.out.println("When I try to login");

        Account account = new Account("testUser");
        account.setPassword("password");

        System.out.println(manageAccount.getAllAccounts());

        Authentication authentication = authManager.authentication(account);

        String token =UUID.randomUUID().toString();
        authManager.persistSession(authentication, token);

        System.out.println("Then securityContext and session should be set");
        Account accountFromContext = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account accountFromSession = manageAccount.loadUserByToken(token);

        assertThat(accountFromSession).isNotNull();
        assertThat(accountFromContext).isNotNull();
        assertThat(accountFromContext.getUsername()).isEqualToIgnoringCase(account.getUsername());
        assertThat(accountFromSession.getUsername()).isEqualToIgnoringCase(account.getUsername());
    }
}

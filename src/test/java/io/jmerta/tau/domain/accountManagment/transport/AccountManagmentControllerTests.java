package io.jmerta.tau.domain.accountManagment.transport;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmerta.tau.domain.accountManagment.entity.Account;
import io.jmerta.tau.domain.accountManagment.service.ManageAccount;
import io.jmerta.tau.domain.accountManagment.util.AuthManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountManagmentController.class, secure = false)
public class AccountManagmentControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ManageAccount manageAccount;

    @MockBean
    AuthManager authManager;

    public AccountManagmentControllerTests() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllUsers()
            throws Exception {
        Account account = new Account("account");

        List<Account> allAccounts = Arrays.asList(account);
        given(manageAccount.getAllAccounts()).willReturn(allAccounts);
        mockMvc.perform(get("/api/account/allUsers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    public void insertNewUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account account = new Account("nowy user");

        given(manageAccount.createNewAccount(account)).willReturn(account);
        String accountJson = mapper.writeValueAsString(account);
        System.out.println(accountJson);
        mockMvc.perform(post("/api/account/register").contentType(MediaType.APPLICATION_JSON).content(accountJson))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().json(accountJson));
    }

}

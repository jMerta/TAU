package io.jmerta.tau.domain.accountManagment.service;

import io.jmerta.tau.domain.accountManagment.entity.Account;
import io.jmerta.tau.domain.accountManagment.entity.Session;
import io.jmerta.tau.repository.AccountRepository;
import io.jmerta.tau.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    private AccountRepository accountRepository;
    private SessionRepository sessionRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, SessionRepository sessionRepository) {
        this.accountRepository = accountRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account =  accountRepository.getAccountByUsername(username);
        account.setPassword(null);
        account.setPasswordSalt(null);
        return account;
    }

    public Account loadUserByToken(String token) {
        long id = sessionRepository.getAccountId(token);
        return accountRepository.getAccount(id);
    }

    public Account createNewAccount(Account account) {
        account.encryptPassword(account.getPassword());
        accountRepository.createAccount(account);
        account.setPassword(null);
        account.setPasswordSalt(null);

        return account;
    }

    public void saveSession(Account account, String token) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Session session = new Session();
        session.setToken(token);
        session.setValidUntil(localDateTime.plusHours(1));
        account.setSession(session);
        sessionRepository.saveSession(account);
    }

    public void destroySession(Account account) {
        sessionRepository.destroySession(account);
    }


    public List<Account> getAllAccounts(){
        List<Account> accountList = accountRepository.getAllAccounts();
        accountList.stream().forEach(account -> account.setPassword(null));
        return accountList;
    }

}

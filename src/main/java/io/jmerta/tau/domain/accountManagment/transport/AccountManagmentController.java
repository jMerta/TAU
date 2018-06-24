package io.jmerta.tau.domain.accountManagment.transport;


import io.jmerta.tau.domain.accountManagment.service.ManageAccount;
import io.jmerta.tau.domain.accountManagment.util.AuthManager;
import io.jmerta.tau.domain.accountManagment.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class AccountManagmentController {


    private ManageAccount manageAccount;
    private AuthManager authManager;


    @Autowired
    public AccountManagmentController(ManageAccount accountService, AuthManager authManager) {
        this.manageAccount = accountService;
        this.authManager = authManager;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Account> createNewAccount(@RequestBody Account account){
        if (account.getUsername() == null || account.getUsername().trim().equalsIgnoreCase("")) return new ResponseEntity<>((Account) null, HttpStatus.BAD_REQUEST);
        if (account.getPassword() == null || account.getPassword().trim().equalsIgnoreCase("")) return new ResponseEntity<>((Account) null, HttpStatus.BAD_REQUEST);
        Account frontAccount = manageAccount.createNewAccount(account);

        return new ResponseEntity<>(frontAccount, HttpStatus.OK);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Account> login(@RequestBody Account frontAccount, HttpServletResponse response){
        if (frontAccount.getPassword() == null || frontAccount.getPassword().equalsIgnoreCase("")){
            throw new BadCredentialsException("password cannot be empty");
        }
        Authentication authentication = null;
        try {
            authentication = authManager.authentication(frontAccount);

        } catch (Exception ex){
            return new ResponseEntity<>((Account) null, HttpStatus.BAD_REQUEST);
        }
        Account account = (Account) authentication.getPrincipal();


        String token = UUID.randomUUID().toString();

        Cookie cookie = new Cookie(this.authManager.tokenName, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60);
        cookie.setPath("/");
        response.addCookie(cookie);

        authManager.persistSession(authentication,token);


        account.setPassword(null);
        account.setPasswordSalt(null);
        return new ResponseEntity<>(account,HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        manageAccount.destroySession(account);

        Cookie cookie = new Cookie(this.authManager.tokenName, "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        request.logout();
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value ="/allUsers", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAllAccounts(){
        List<Account> accountList = manageAccount.getAllAccounts();

        return new ResponseEntity<>(accountList,HttpStatus.OK);
    }


    @RequestMapping(value = "/refreshSession", method = RequestMethod.GET)
    public ResponseEntity keepSession() {
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

}

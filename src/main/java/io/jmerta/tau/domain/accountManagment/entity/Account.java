package io.jmerta.tau.domain.accountManagment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class Account implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String passwordSalt;
    private List<Authority> grantedAuthorities;
    private Session session;
    private Long roleId;

    public Account() {
        grantedAuthorities = new ArrayList<>();
    }

    public Account(String username, String password, String passwordSalt, Long roleId) {
        this.username = username;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.roleId = roleId;
    }

    public Account(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    @JsonIgnore
    public void encryptPassword(String unhashedPassword){
        this.passwordSalt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(unhashedPassword,passwordSalt);

    }

    public boolean checkPassword(String unhashedPassword){
        return BCrypt.checkpw(unhashedPassword,this.password);
    }

}

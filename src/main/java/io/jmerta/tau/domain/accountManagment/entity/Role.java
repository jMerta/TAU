package io.jmerta.tau.domain.accountManagment.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Role implements GrantedAuthority {
    private long id;
    private String authority;
    private String code;
}

package io.jmerta.tau.domain.accountManagment.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Authority implements GrantedAuthority {
    private String authority;
}

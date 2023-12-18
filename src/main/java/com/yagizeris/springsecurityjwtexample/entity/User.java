package com.yagizeris.springsecurityjwtexample.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.yagizeris.springsecurityjwtexample.entity.enums.RoleEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name= "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String surname;

    private String email;

    private String username;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //Kullanıcılarının rollerini döndürmemizi sağlar.
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() { //Kullanıcının hesap süresinin dolup dolmadığını temsil eder
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //Kullanıcının hesabınını kilitlli olup olmadığını temsil eder.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //Kullanıcı kimlik bilgilerinin süresinin dolup dolmadığını temsil eder.
        return true;
    }

    @Override
    public boolean isEnabled() { //Kullanıcı hesabının aktif mi yoksa pasif mi olduğunu temsil eder.
        return true; 
    }
}

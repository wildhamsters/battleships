package org.wildhamsters.battleships;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;

/**
 * @author Piotr Chowaniec
 */
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "authority")
    private GrantedAuthority authority;
    @Column(name = "acc_expired")
    private Boolean accountExpired;
    @Column(name = "acc_locked")
    private Boolean accountLocked;
    @Column(name = "cred_expired")
    private Boolean credentialsExpired;
    @Column(name = "disabled")
    private Boolean disabled;

    private UserEntity(String name,
                       String email,
                       String password,
                       GrantedAuthority authority,
                       Boolean accountExpired,
                       Boolean accountLocked,
                       Boolean credentialsExpired,
                       Boolean disabled) {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public GrantedAuthority getAuthority() {
        return authority;
    }

    public Boolean isAccountExpired() {
        return accountExpired;
    }

    public Boolean isAccountLocked() {
        return accountLocked;
    }

    public Boolean areCredentialsExpired() {
        return credentialsExpired;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    static UserEntityBuilder builder() {
        return new UserEntityBuilder();
    }

    static class UserEntityBuilder {

        private String name;
        private String email;
        private String password;

        UserEntityBuilder setName(String name) {
            this.name = name;
            return this;
        }

        UserEntityBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        UserEntityBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        UserEntity build() {
            return new UserEntity(name, email, password, new SimpleGrantedAuthority("USER"),
                    false, false,false,false);
        }
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", authority=" + authority +
                ", accountExpired=" + accountExpired +
                ", accountLocked=" + accountLocked +
                ", credentialsExpired=" + credentialsExpired +
                ", disabled=" + disabled +
                '}';
    }
}

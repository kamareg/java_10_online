package ua.com.alevel.final_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.final_project.entity.BaseEntity;
import ua.com.alevel.final_project.enums.Role;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity
//        implements UserDetails
{

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

//    private Integer age;

//    @Column(name = "account_non_expired")
//    private Boolean accountNonExpired;
//
//    @Column(name = "account_non_locked")
//    private Boolean accountNonLocked;
//
//    @Column(name = "credentials_non_expired")
//    private Boolean credentialsNonExpired;
//
//    private Boolean enabled;

//    public User() {
//        accountNonExpired = true;
//        accountNonLocked = true;
//        credentialsNonExpired = true;
//        enabled = true;
//    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }

//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return accountNonExpired;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return accountNonLocked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return credentialsNonExpired;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }
}

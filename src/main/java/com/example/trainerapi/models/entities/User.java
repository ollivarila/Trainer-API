package com.example.trainerapi.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 * Represents a user. User has a username and a password. <br>
 * <b>User</b> has a <b>OneToMany</b> relation to <b>Workout</b>, meaning that one user can have many workouts. <br>
 * <b>User</b> has a <b>OneToMany</b> relation to <b>Exercise</b>, meaning that one user can have many exercises. <br>
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    /**
     * User id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Username
     */
    private String username;

    /**
     * Password (hashed)
     */
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @Override
    public String toString(){
        return String.format("TrainerUser[id=%s, username='%s']", id, username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

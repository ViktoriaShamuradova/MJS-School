package com.epam.esm.dto;

import com.epam.esm.Status;
import com.epam.esm.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends RepresentationModel<UserDTO> implements UserDetails {

    private Long id;
    @NotBlank(message = "name must not be blank")
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё \\-]{1,45}", message = "name should contain from 1 to 45 characters")
    private String name;
    @NotBlank(message = "name must not be blank")
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё \\-]{1,45}", message = "surname should contain from 1 to 45 characters")
    private String surname;
    @Null(message = "user create date cannot be defined by users")
    private Instant createDate;
    @Null(message = "user update date cannot be defined by users")
    private Instant lastUpdateDate;

    @NotBlank(message = "password must not be empty")
    private String password;

    private String username;

    @Null(message = "user authorities cannot be defined by users")
    private List<SimpleGrantedAuthority> authorities;

    @Null
    private boolean isActive;

    public UserDTO(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUser(User user) {

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getRole().getAuthorities()
        );
    }
}

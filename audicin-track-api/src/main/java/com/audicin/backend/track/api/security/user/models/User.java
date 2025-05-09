package com.audicin.backend.track.api.security.user.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name="users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, name="user_id")
    private Integer id;

    @Column(nullable=false)
    private String fullName;

    @Column(unique=true, length=100, nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @CreationTimestamp
    @Column(updatable=false, name="created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private Date updatedAt;

    @ManyToOne(cascade=CascadeType.REMOVE)
    @JoinColumn(name="role_id", referencedColumnName="id", nullable=false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
        return List.of(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' + ", password='" + password + '\'' +
                ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

}
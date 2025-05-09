package com.audicin.backend.track.api.security.user.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Table(name="roles")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false)
    private Integer id;

    @Column(unique=true, nullable=false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Column(nullable=false)
    private String description;

    @CreationTimestamp
    @Column(updatable=false, name="created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private Date updatedAt;

}
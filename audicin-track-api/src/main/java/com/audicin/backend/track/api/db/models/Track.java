package com.audicin.backend.track.api.db.models;

import com.audicin.backend.track.api.db.models.enums.MusicGenre;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name="tracks")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude="license")
public class Track {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, name="track_id")
    private Integer Id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private MusicGenre genre;

    @OneToOne(mappedBy="track", cascade=CascadeType.ALL)
    @JoinColumn(nullable=true)
    private License license;

    @Column(nullable=true, unique=true, length=64)
    private String licenseHash;

}

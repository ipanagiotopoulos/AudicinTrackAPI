package com.audicin.backend.track.api.db.models;

import com.audicin.backend.track.api.security.helpers.LicenseHasher;
import com.audicin.backend.track.api.security.user.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="licenses")
@Data
@NoArgsConstructor
public class License {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, name="license_id")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User partner;

    @OneToOne
    @JoinColumn(name="track_id")
    private Track track;

    @CreationTimestamp
    @Column(updatable=false)
    private Date licenseDate;

    @Column(nullable=true, unique=true, length=64)
    private String licenseHash;

    public License(User partner, Track track) {
        this.partner = partner;
        this.track = track;
        this.licenseHash = LicenseHasher.hashLicense(
                partner.getEmail() + partner.getId() + track.getId());
        this.licenseDate = new Date();
    }

    public void setLicenseHash(String licenseHash) {
        this.licenseHash = LicenseHasher.hashLicense(
                partner.getEmail() + partner.getId() + track.getId());
    }
}

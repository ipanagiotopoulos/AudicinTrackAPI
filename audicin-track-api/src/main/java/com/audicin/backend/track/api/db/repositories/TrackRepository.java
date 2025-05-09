package com.audicin.backend.track.api.db.repositories;

import com.audicin.backend.track.api.db.models.Track;
import com.audicin.backend.track.api.security.user.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrackRepository extends JpaRepository<Track,Integer> {

    @Query("SELECT t FROM Track t WHERE t.license.partner = :partner")
    List<Track> findAllByLicensesByPartner(User partner);
}

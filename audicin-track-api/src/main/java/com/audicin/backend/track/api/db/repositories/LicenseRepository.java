package com.audicin.backend.track.api.db.repositories;

import com.audicin.backend.track.api.db.models.License;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, Integer> {
    List<License> findByTrackId(Long trackId);
}

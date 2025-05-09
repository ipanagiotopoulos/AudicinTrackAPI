package com.audicin.backend.track.api.db.repositories;

import com.audicin.backend.track.api.db.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track,Long> {
}

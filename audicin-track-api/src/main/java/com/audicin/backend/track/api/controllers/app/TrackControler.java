package com.audicin.backend.track.api.controllers.app;

import com.audicin.backend.track.api.db.models.Track;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tracks")
@RestController
public class TrackControler {

    @GetMapping
    @PreAuthorize("hasRole('PARTNER')")
    public List<Track> getTrackPerPartner(){
        return null;
    }
    // not imeplemented yet
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String addTrack(String licenseId){
        return null;
    }
}

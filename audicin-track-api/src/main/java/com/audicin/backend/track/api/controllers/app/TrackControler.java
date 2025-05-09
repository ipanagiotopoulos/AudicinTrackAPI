package com.audicin.backend.track.api.controllers.app;

import com.audicin.backend.track.api.db.models.Track;
import com.audicin.backend.track.api.db.repositories.TrackRepository;
import com.audicin.backend.track.api.db.service.TrackService;
import com.audicin.backend.track.api.dtos.request.TrackDto;
import com.audicin.backend.track.api.dtos.response.TrackResponseDTO;
import com.audicin.backend.track.api.security.services.JwtService;
import com.audicin.backend.track.api.security.services.UserService;
import com.audicin.backend.track.api.security.user.models.User;
import com.audicin.backend.track.api.security.user.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tracks")
@RestController
public class TrackControler {
    @Autowired
    private TrackService trackService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrackRepository trackRepository;

    @GetMapping("/partner-tracks")
    @PreAuthorize("hasRole('PARTNER')")
    public List<TrackResponseDTO> getTrackPerPartner(Principal principal) {
        List<Track> tracks = trackRepository.findAll();
        List<TrackResponseDTO> trackDtos = new ArrayList<>();
        for (Track track: tracks){
            trackDtos.add(trackService.toDto(track));
        }
        return trackDtos;
    }


    // not imeplemented yet
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TrackResponseDTO addTrack(TrackDto trackRequest) {

        Track track = trackService.createTrack(trackRequest);
        TrackResponseDTO trackResponse =
                TrackResponseDTO.builder().title(track.getTitle())
                        .id(track.getId()).genre(track.getGenre())
                        .description(track.getDescription()).build();

        return trackResponse;
    }
}

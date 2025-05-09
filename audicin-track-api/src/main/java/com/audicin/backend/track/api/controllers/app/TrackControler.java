package com.audicin.backend.track.api.controllers.app;

import com.audicin.backend.track.api.db.models.Track;
import com.audicin.backend.track.api.db.repositories.TrackRepository;
import com.audicin.backend.track.api.db.service.TrackService;
import com.audicin.backend.track.api.dtos.request.TrackDto;
import com.audicin.backend.track.api.dtos.response.TrackResponseDTO;
import com.audicin.backend.track.api.exceptions.controller.ResourceNotFoundException;
import com.audicin.backend.track.api.exceptions.controller.UnauthorizedAccessException;
import com.audicin.backend.track.api.exceptions.controller.BadRequestException;
import com.audicin.backend.track.api.security.user.repositories.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        // Here we can check if the user has permission or other conditions
        if (principal == null) {
            throw new UnauthorizedAccessException("User is not authorized to access these tracks.");
        }

        List<Track> tracks = trackRepository.findAll();
        if (tracks == null || tracks.isEmpty()) {
            throw new ResourceNotFoundException("No tracks found for the partner.");
        }

        List<TrackResponseDTO> trackDtos = new ArrayList<>();
        for (Track track : tracks) {
            trackDtos.add(trackService.toDto(track));
        }
        return trackDtos;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TrackResponseDTO addTrack(TrackDto trackRequest) {
        if (trackRequest == null || trackRequest.getTitle() == null || trackRequest.getTitle().isEmpty()) {
            throw new BadRequestException("Track title is required.");
        }

        Track track = trackService.createTrack(trackRequest);
        if (track == null) {
            throw new ResourceNotFoundException("Failed to create the track.");
        }

        TrackResponseDTO trackResponse = TrackResponseDTO.builder()
                .title(track.getTitle())
                .id(track.getId())
                .genre(track.getGenre())
                .description(track.getDescription())
                .build();

        return trackResponse;
    }
}

package com.audicin.backend.track.api.db.service;

import com.audicin.backend.track.api.db.models.Track;
import com.audicin.backend.track.api.db.repositories.TrackRepository;
import com.audicin.backend.track.api.dtos.request.TrackDto;
import com.audicin.backend.track.api.dtos.response.TrackResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public Track createTrack(TrackDto trackDto) {

        Track track = new Track();
        track.setTitle(trackDto.getTitle());
        track.setDescription(trackDto.getDescription());
        track.setGenre(trackDto.getGenre());
        return trackRepository.save(track);
    }

    public Optional<Track> getTrackById(Integer trackId) {
        return trackRepository.findById(trackId);
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public TrackResponseDTO toDto(Track track) {
        return TrackResponseDTO.builder()
                .id(track.getId())
                .title(track.getTitle())
                .description(track.getDescription())
                .genre(track.getGenre())
                .build();
    }
}


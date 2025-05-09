package com.audicin.backend.track.api.dtos.response;

import com.audicin.backend.track.api.db.models.Track;
import com.audicin.backend.track.api.db.models.enums.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackResponseDTO {

    private Integer id;

    private String title;

    private String description;

    private MusicGenre genre;


    private TrackResponseDTO toDto(Track track) {
        return TrackResponseDTO.builder()
                .id(track.getId())
                .title(track.getTitle())
                .description(track.getDescription())
                .genre(track.getGenre())
                .build();
    }

}

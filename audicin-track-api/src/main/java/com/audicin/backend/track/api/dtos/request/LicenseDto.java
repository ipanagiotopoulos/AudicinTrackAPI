package com.audicin.backend.track.api.dtos.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicenseDto {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Track ID is required")
    private Integer trackId;
}

package com.audicin.backend.track.api.dtos.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LicenseResponseDTO {

    private Integer id;

    private Integer userId;

    private Integer trackId;

    private Date licenseDate;

    private String licenseHash;
}

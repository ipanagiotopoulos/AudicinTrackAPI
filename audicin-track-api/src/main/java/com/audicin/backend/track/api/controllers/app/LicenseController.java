package com.audicin.backend.track.api.controllers.app;

import com.audicin.backend.track.api.db.models.License;
import com.audicin.backend.track.api.db.service.LicenseService;
import com.audicin.backend.track.api.dtos.response.LicenseResponseDTO;
import com.audicin.backend.track.api.security.user.models.User;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/license")
@RestController
public class LicenseController {
    @Autowired
    private LicenseService licenseService;

    @GetMapping
    @PreAuthorize("hasRole('PARTNER')")
    public List<LicenseResponseDTO> getLicesnsesPerPartner() {
        User user =
                (User) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        System.out.println("user id" + user.getId());
        List<License> licenses = licenseService.getAllLicenses(user.getId());
        System.out.println("licenses" + licenses);
        List<LicenseResponseDTO> licenseDtos = new ArrayList<>();
        for (License license: licenses){
            licenseDtos.add(licenseService.toDto(license));
        }
        return licenseDtos;
    }

    // not imeplemented yet
    @PostMapping
    @PreAuthorize("hasRole('PARTNER')")
    public LicenseResponseDTO addLicensePerPartner(
            @RequestParam @NotNull Integer trackId) {
        User user =
                (User) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();

        License license = licenseService.createLicense(user, trackId);
        LicenseResponseDTO licenseResponse = LicenseResponseDTO.builder()
                .licenseDate(license.getLicenseDate())
                .trackId(license.getTrack().getId())
                .userId(license.getPartner().getId())
                .licenseHash(license.getLicenseHash()).id(license.getId())
                .build();

        return licenseResponse;
    }

}

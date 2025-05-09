package com.audicin.backend.track.api.controllers.app;

import com.audicin.backend.track.api.db.models.License;
import com.audicin.backend.track.api.db.service.LicenseService;
import com.audicin.backend.track.api.dtos.response.LicenseResponseDTO;
import com.audicin.backend.track.api.exceptions.controller.ResourceNotFoundException;
import com.audicin.backend.track.api.exceptions.controller.UnauthorizedAccessException;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new UnauthorizedAccessException("User is not authorized to access these licenses.");
        }

        List<License> licenses = licenseService.getAllLicenses(user.getId());
        if (licenses == null || licenses.isEmpty()) {
            throw new ResourceNotFoundException("No licenses found for the user.");
        }

        List<LicenseResponseDTO> licenseDtos = new ArrayList<>();
        for (License license : licenses) {
            licenseDtos.add(licenseService.toDto(license));
        }
        return licenseDtos;
    }

    @PostMapping
    @PreAuthorize("hasRole('PARTNER')")
    public LicenseResponseDTO addLicensePerPartner(@RequestParam @NotNull Integer trackId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new UnauthorizedAccessException("User is not authorized to add a license.");
        }

        License license = licenseService.createLicense(user, trackId);
        if (license == null) {
            throw new ResourceNotFoundException("Failed to create a license for track ID: " + trackId);
        }

        LicenseResponseDTO licenseResponse = LicenseResponseDTO.builder()
                .licenseDate(license.getLicenseDate())
                .trackId(license.getTrack().getId())
                .userId(license.getPartner().getId())
                .licenseHash(license.getLicenseHash())
                .id(license.getId())
                .build();

        return licenseResponse;
    }
}

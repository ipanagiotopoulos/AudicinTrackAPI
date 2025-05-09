package com.audicin.backend.track.api.db.service;

import com.audicin.backend.track.api.db.models.License;
import com.audicin.backend.track.api.db.models.Track;
import com.audicin.backend.track.api.db.repositories.LicenseRepository;
import com.audicin.backend.track.api.db.repositories.TrackRepository;
import com.audicin.backend.track.api.dtos.response.LicenseResponseDTO;
import com.audicin.backend.track.api.security.user.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {

    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private LicenseRepository licenseRepository;

    public License createLicense(User partner, Integer trackId) {

        Track track = trackRepository.findById(trackId).orElseThrow(
                ()->new RuntimeException(
                        "Track not found with ID: " + trackId));
        License license = new License(partner, track);
        license = licenseRepository.save(license);

        return license;

    }

    public Optional<License> getLicenseById(Integer trackId, String hash) {
        License license = licenseRepository.findById(trackId).orElseThrow(
                ()->new RuntimeException(
                        "Track not found with ID: " + trackId));
        if (!license.getLicenseHash().equals(hash)) {
            new RuntimeException("Track with ID: " + trackId +
                    "does not have an equal hash with the one registered in " +
                    "the database");
        }
        return licenseRepository.findById(trackId);
    }

    public List<License> getAllLicenses(Integer partnerId) {
        return licenseRepository.findAll().stream()
                .filter(license->license.getPartner().getId().toString()
                        .equals(partnerId.toString())).toList();
    }

    public LicenseResponseDTO toDto(License license) {
        return LicenseResponseDTO.builder().id(license.getId())
                .licenseDate(license.getLicenseDate())
                .userId(license.getPartner().getId())
                .trackId(license.getTrack().getId())
                .licenseHash(license.getLicenseHash()).build();
    }
}

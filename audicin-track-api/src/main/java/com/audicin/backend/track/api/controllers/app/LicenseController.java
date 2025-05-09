package com.audicin.backend.track.api.controllers.app;

import com.audicin.backend.track.api.db.models.License;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/license")
@RestController
public class LicenseController {
    // not implemented yet
    @GetMapping
    @PreAuthorize("hasRole('PARTNER')")
    public List<License> getLicesnsesPerPartner(){
        return null;
    }
    // not imeplemented yet
    @PostMapping
    @PreAuthorize("hasRole('PARTNER')")
    public String addLicensePerPartner(String licenseId){
      return null;
    }

}

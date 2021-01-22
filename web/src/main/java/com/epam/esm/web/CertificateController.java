package com.epam.esm.web;

import com.epam.esm.service.CertificateService;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.web.exceptionHandling.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping
    public List<CertificateDTO> showAllCertificate() {
        return certificateService.getAllCertificates();
    }

    @PostMapping("/partOfCertificate")
    public List<CertificateDTO> showCertificateByPartOfDescription(@RequestBody CertificateDTO certificateDTO) {
        return certificateService.getCertificatesByPartOfNameOrDescription(certificateDTO);
    }

    @PostMapping
    public void addNewCertificate(@RequestBody CertificateDTO certificateDTO) {
        certificateService.saveCertificate(certificateDTO);
    }

    @DeleteMapping("/{certificateId}")
    public String deleteCertificate(@PathVariable int certificateId) {
        CertificateDTO certificate = certificateService.getCertificate(certificateId);
        if (certificate == null) {
            throw new NoSuchResourceException("There is no certificate with this id = " + certificateId + " in dataBase");
        }
        certificateService.deleteCertificate(certificateId);
        return "Certificate with id= " + certificateId + " was deleted";
    }

    @PutMapping("/{certificateId}")
    public void updateCertificate(@RequestBody CertificateDTO certificate, @PathVariable int certificateId) {
        certificateService.update(certificate, certificateId);
    }

    @GetMapping("/{certificateId}")
    public CertificateDTO certificate(@PathVariable int certificateId) {
        CertificateDTO certificate = certificateService.getCertificate(certificateId);
        if (certificate == null) {
            throw new NoSuchResourceException("There is no certificate with this id = " + certificateId + " in dataBase");
        }
        return certificate;
    }
}

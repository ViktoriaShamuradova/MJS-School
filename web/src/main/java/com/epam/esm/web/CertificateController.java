package com.epam.esm.web;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * a class which performs REST's CRUD operations on a resource called "Certificates"
 */
@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }
    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @return a collection of CertificatesDTO, which represents a resource "certificate"
     */
    @GetMapping
    public List<CertificateDTO> findAll() {
        return certificateService.findAll();
    }

    @GetMapping("/find/{partOfNameOrDescription}")
    public List<CertificateDTO> findByPartOfNameOrDescription(@PathVariable("partOfNameOrDescription") String partOfNameOrDescription) {
        return certificateService.findByPartOfNameOrDescription(partOfNameOrDescription);
    }

    /**
     * a method which realizes REST's CREATE operation
     *
     * @param certificateDTO an object which represents a resource "certificate" that must be created
     *                       in the data source
     */
    @PostMapping
    public void create(@RequestBody CertificateDTO certificateDTO) {
        certificateService.create(certificateDTO);
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<String> delete(@PathVariable long certificateId) {
        certificateService.delete(certificateId);
        return ResponseEntity.ok("Certificate with id= " + certificateId + " was deleted");
    }

    @PutMapping
    public void update(@RequestBody CertificateDTO certificate) {
        certificateService.update(certificate);
    }

    @GetMapping("/{certificateId}")
    public CertificateDTO find(@PathVariable long certificateId) {
        return certificateService.find(certificateId);
    }
}

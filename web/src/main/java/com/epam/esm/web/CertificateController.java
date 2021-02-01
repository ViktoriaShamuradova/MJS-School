package com.epam.esm.web;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * a class which performs REST's CRUD operations on a resource called "Certificates"
 */
@RestController
@RequestMapping(value = "/certificates", produces = "application/json; charset=utf-8")
public class CertificateController {

    private final CertificateService certificateService;
    @Autowired
    private ReloadableResourceBundleMessageSource resourceBundle;

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
    public ResponseEntity<List<CertificateDTO>> findAll() {

        return new ResponseEntity<>(certificateService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/find/{partOfNameOrDescription}")
    public ResponseEntity<List<CertificateDTO>> findByPartOfNameOrDescription(@PathVariable("partOfNameOrDescription") String partOfNameOrDescription) {
        return new ResponseEntity<>(certificateService.findByPartOfNameOrDescription(partOfNameOrDescription), HttpStatus.OK);
    }

    /**
     * a method which realizes REST's CREATE operation
     *
     * @param certificateDTO an object which represents a resource "certificate" that must be created
     *                       in the data source
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody CertificateDTO certificateDTO) {
        certificateService.create(certificateDTO);
        return ResponseEntity.ok("Certificate was created");
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
    public ResponseEntity<CertificateDTO> find(@PathVariable long certificateId) {
        return new ResponseEntity<>(certificateService.find(certificateId), HttpStatus.OK);
    }

}

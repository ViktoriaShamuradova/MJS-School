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
     * @return a collection of CertificatesDTO, which represents a resource "certificates" from data base
     */
    @GetMapping
    public List<CertificateDTO> findAll() {

        return certificateService.findAll();
    }

    @GetMapping("/find/{partOfNameOrDescription}")
    public ResponseEntity<List<CertificateDTO>> findByPartOfNameOrDescription(@PathVariable("partOfNameOrDescription") String partOfNameOrDescription) {
        return new ResponseEntity<>(certificateService.findByPartOfNameOrDescription(partOfNameOrDescription), HttpStatus.OK);
    }

    /**
     * a method which realizes REST's CREATE operation
     *
     * @param certificateDTO an object which represents a resource "certificates" that must be created
     *                       in the data source
     * @return an object which represents Http response of CREATE operation,
     * which body contains an information about successful creature
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody CertificateDTO certificateDTO) {
        certificateService.create(certificateDTO);
        return ResponseEntity.ok("Certificate was created");
    }
    /**
     * a method which realizes REST's DELETE operation of a specific resource with ID stored in a request path
     *
     * @param id an identification number of a resource which should be deleted
     * @return an object which represents Http response of DELETE operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        certificateService.delete(id);
        return ResponseEntity.ok("Certificate with id= " + id + " was deleted");
    }

    /**
     * a method which realizes REST's UPDATE operation of a specific resource
     *
     * @param certificate an object with new fields of a specified resource
     * @return an object which represents Http response of UPDATE operation,     *
     */
    @PutMapping
    public ResponseEntity<String> update(@RequestBody CertificateDTO certificate) {
        certificateService.update(certificate);
        return new ResponseEntity<>("The certificate wa updated", HttpStatus.OK);
    }

    /**
     * a method which realizes REST's READ operation of a specific resource with id stored in a request path
     *
     * @param id an identification number of a requested resource
     * @return an object which represents a target resource
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificateDTO> find(@PathVariable long id) {
        return new ResponseEntity<>(certificateService.find(id), HttpStatus.OK);
    }

}

package com.epam.esm.web;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping
    public List<CertificateDTO> findAll() {
        return certificateService.findAll();
    }

    @GetMapping("/find/{partOfNameOrDescription}")
    public List<CertificateDTO> findByPartOfNameOrDescription(@PathVariable("partOfNameOrDescription") String partOfNameOrDescription) {
        return certificateService.findByPartOfNameOrDescription(partOfNameOrDescription);
    }

    @PostMapping
    public void create(@RequestBody CertificateDTO certificateDTO) {
        certificateService.create(certificateDTO);
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<String> delete(@PathVariable int certificateId) {
        certificateService.delete(certificateId);
        return ResponseEntity.ok("Certificate with id= " + certificateId + " was deleted");
    }

    @PutMapping
    public void update(@RequestBody CertificateDTO certificate) {
        certificateService.update(certificate);
    }

    @GetMapping("/{certificateId}")
    public CertificateDTO find(@PathVariable int certificateId) {
        return certificateService.find(certificateId);
    }
}

package com.epam.esm.web.controller;

import com.epam.esm.service.PageInfo;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.util.HateoasBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/certificates", produces = "application/json; charset=utf-8")
public class CertificateController {

    private final CertificateService certificateService;
    private final HateoasBuilder hateoasBuilder;

    @Autowired
    public CertificateController(CertificateService certificateService,
                                 HateoasBuilder builder) {
        this.certificateService = certificateService;
        this.hateoasBuilder = builder;
    }

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @param pageInfo - object witch contains information about pagination
     * @return a collection of CertificatesDTO, which represents a resource "certificates" from data base with links
    */

    @GetMapping()
    public ResponseEntity<RepresentationModel<?>> findAll(@Valid PageInfo pageInfo, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//           throw new ValidationException(ExceptionCode.NOT_VALID_PAGE_INFO.getErrorCode());
//        }
        List<CertificateDTO> certificates = certificateService.findAll(pageInfo);
        long certificateCount = certificateService.getCount();
        return new ResponseEntity<>(hateoasBuilder.addLinksForListOfCertificates(certificates, pageInfo, certificateCount),
                HttpStatus.OK);
    }


    @GetMapping("/find")
    public ResponseEntity<List<CertificateDTO>> findByTags(@RequestParam List<String> tagNames) {
        return new ResponseEntity<>(certificateService.findByTags(tagNames), HttpStatus.OK);
    }

    @GetMapping("/find/{partOfNameOrDescription}")
    public ResponseEntity<List<CertificateDTO>> findByPartOfNameOrDescription(@PathVariable("partOfNameOrDescription") String partOfNameOrDescription) {
        List<CertificateDTO> certificates = certificateService.findByPartOfNameOrDescription(partOfNameOrDescription);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
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
    public ResponseEntity<CertificateDTO> create(@RequestBody CertificateDTO certificateDTO) {
        CertificateDTO certificate = certificateService.create(certificateDTO);
        return new ResponseEntity<>(hateoasBuilder.addLinkForCertificate(certificate), HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<CertificateDTO> create(@RequestBody CertificateDTO certificateDTO, UriComponentsBuilder builder) {
//
//        CertificateDTO certificate = certificateService.create(certificateDTO);
//
//        UriComponents uriComponents =
//                builder.path("/{id}").buildAndExpand(certificate.getId());
//
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.setLocation(uriComponents.toUri());
//        return new ResponseEntity<>(certificate, responseHeaders, HttpStatus.CREATED);
//    }

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
     * a method which realizes REST's UPDATE operation of a specific resource with ID stored in a request path
     * and CertificateUpdateDto
     *
     * @param id                   an identification number of a resource which should be updated
     * @param certificateUpdateDto contains information for updating certificate in data base
     * @return an object which represents Http response of certificate with links
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CertificateDTO> update(@PathVariable("id") long id,
                                                 @Valid @RequestBody CertificateUpdateDto certificateUpdateDto) {
        CertificateDTO certificate = certificateService.update(certificateUpdateDto, id);
        return new ResponseEntity<>(hateoasBuilder.addLinkForCertificate(certificate), HttpStatus.OK);
    }

    /**
     * a method which realizes REST's READ operation of a specific resource with id stored in a request path
     *
     * @param id an identification number of a requested resource
     * @return an ResponseEntity with CertificateDTO with links
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificateDTO> find(@PathVariable long id) {
        CertificateDTO certificate = certificateService.find(id);
        return new ResponseEntity<>(hateoasBuilder.addLinkForCertificate(certificate), HttpStatus.OK);
    }
}

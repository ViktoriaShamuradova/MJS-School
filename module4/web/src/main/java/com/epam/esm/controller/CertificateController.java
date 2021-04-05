package com.epam.esm.controller;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.CertificateHateoasAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * a class which performs CRUD operations on a resource called "certificate"
 */
@RestController
@RequestMapping(value = "/certificates")
@RequiredArgsConstructor
@Validated
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateHateoasAssembler certificateAssembler;

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @param criteriaInfo - object with information about certificate to search
     * @return a collection of CertificatesDTO, which represents a resource "certificates" from database with links
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('certificate:read')")
    public ResponseEntity<CollectionModel<CertificateDTO>> find(@Valid PageInfo pageInfo, @Valid CertificateCriteriaInfo criteriaInfo) {
        List<CertificateDTO> certificates = certificateService.find(pageInfo, criteriaInfo);
        long count = certificateService.getCount();
        return ResponseEntity.ok(certificateAssembler.toHateoasCollectionOfEntities(certificates, pageInfo, count));
    }

    /**
     * a method which realizes REST's CREATE operation
     *
     * @param certificateDTO an object which represents a resource "certificates" that should be created
     *                       in the database
     * @return a responseEntity with status code and target resource certificate from database
     * with links
     */
    @PostMapping
    @PreAuthorize("hasAuthority('certificate:write')")
    public ResponseEntity<CertificateDTO> create(@RequestBody @Valid CertificateDTO certificateDTO) {
        CertificateDTO certificateDTOCreated = certificateService.create(certificateDTO);
        certificateAssembler.appendAsForMainEntity(certificateDTOCreated);
        return new ResponseEntity<>(certificateDTOCreated, HttpStatus.CREATED);
    }

    /**
     * a method which realizes REST's DELETE operation of a specific resource with ID stored in a request path
     *
     * @param id an identification number of a resource which should be deleted
     * @return an object which represents Http response of DELETE operation
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('certificate:write')")
    public ResponseEntity<RepresentationModel> delete(@PathVariable @Min(1) long id) {
        RepresentationModel representationModel = new RepresentationModel();
        certificateAssembler.appendGenericCertificateHateoasActions(representationModel);
        if (certificateService.delete(id)) {
            return new ResponseEntity<>(representationModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(representationModel, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * a method which realizes REST's UPDATE operation of a specific resource with ID stored in a request path
     * and CertificateUpdateDto
     *
     * @param certificateUpdateDto contains information for updating certificate in data base
     * @return an object which represents Http response of certificate with links
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('certificate:write')")
    public ResponseEntity<CertificateDTO> update(@PathVariable("id") @Min(1)  Long id, @Valid @RequestBody CertificateUpdateDto certificateUpdateDto) {
        CertificateDTO certificate = certificateService.update(certificateUpdateDto, id);
        certificateAssembler.appendAsForMainEntity(certificate);
        return ResponseEntity.ok(certificate);
    }

    /**
     * a method which realizes REST's READ operation of a specific resource with id stored in a request path
     *
     * @param id an identification number of a requested resource
     * @return an ResponseEntity with CertificateDTO with links
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('certificate:read')")
    public ResponseEntity<CertificateDTO> find(@PathVariable("id")
                                                   @Min(1) long id) {
        CertificateDTO certificate = certificateService.findById(id);
        certificateAssembler.appendAsForMainEntity(certificate);
        return ResponseEntity.ok(certificate);
    }
}

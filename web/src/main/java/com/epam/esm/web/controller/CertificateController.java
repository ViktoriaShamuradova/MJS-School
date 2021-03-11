package com.epam.esm.web.controller;

import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.CertificateHateoasAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * a class which performs CRUD operations on a resource called "certificate"
 */
@RestController
@RequestMapping(value = "/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateHateoasAssembler certificateAssembler;

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @param pageInfo     - object witch contains information about pagination
     * @param criteriaInfo - object with information about certificate to search
     * @return a collection of CertificatesDTO, which represents a resource "certificates" from database with links
     */
    @GetMapping()
    public ResponseEntity<CollectionModel<CertificateDTO>> find(PageInfo pageInfo, CertificateCriteriaInfo criteriaInfo) {
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
    public ResponseEntity<CertificateDTO> create(@RequestBody CertificateDTO certificateDTO) {
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
    public ResponseEntity<RepresentationModel> delete(@PathVariable long id) {
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
    public ResponseEntity<CertificateDTO> update(@PathVariable("id") Long id, @Valid @RequestBody CertificateUpdateDto certificateUpdateDto) {
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
    public ResponseEntity<CertificateDTO> find(@PathVariable long id) {
        CertificateDTO certificate = certificateService.findById(id);
        certificateAssembler.appendAsForMainEntity(certificate);
        return ResponseEntity.ok(certificate);
    }
}

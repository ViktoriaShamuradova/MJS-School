package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.criteria_info.CertificateCriteriaInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface CertificateService extends CrudService<CertificateDTO, Long, CertificateCriteriaInfo> {

    CertificateDTO update(CertificateUpdateDto certificateUpdateDto, Long id);

}


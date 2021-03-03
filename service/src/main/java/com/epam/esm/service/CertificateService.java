package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.criteria_info.CertificateCriteriaInfo;

public interface CertificateService extends CrudService<CertificateDTO, Long, CertificateCriteriaInfo> {

    CertificateDTO update(CertificateUpdateDto certificateUpdateDto, Long id);

    long getCount();

}


package com.praca.inzynierska.gardenservicemanagement.webFront.service;

import com.praca.inzynierska.gardenservicemanagement.webFront.controller.apiModel.warnings.*;

import java.util.List;

public interface WarningsService {

    void addNewWarnings(WarningsAddRequest request);

    StationWarningsListResponse getWarningsForStation(Long id);

    void deleteWarning(Long id);

    WarningDetailResponse getWarningDetail(Long id, Long warningId);

    void editWarning(WarningsEditRequest request);

    WarningsPageResponse getWarningsOccurrencesListById(Long warningId, Integer page);
}

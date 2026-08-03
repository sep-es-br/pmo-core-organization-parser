package br.gov.es.pmo.organization_parser.pmo_base.model;

import java.util.Optional;

/**
 * Provider-independent contract for resolving a work location by its GUID.
 */
public interface IWorkLocationParser {

    Optional<WorkLocationDto> findByGuid(String guid, String token);
}

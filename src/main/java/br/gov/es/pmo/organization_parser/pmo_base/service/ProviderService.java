/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gov.es.pmo.organization_parser.pmo_base.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import br.gov.es.pmo.organization_parser.pmo_base.model.IOrganizationParser;
import br.gov.es.pmo.organization_parser.pmo_base.model.OrganizationDto;



@Service
public class ProviderService {

    private final Map<String, IOrganizationParser<?>> providers;

    public ProviderService(Map<String, IOrganizationParser<?>> providers) {
        this.providers = providers;
    }

    public List<OrganizationDto> getOrganizations(String registrationId, String token) {

        IOrganizationParser<?> provider = providers.get(registrationId);

        if (provider == null) {
            return Collections.emptyList();
        }

        return provider.getOrganizations(token);
    }
}
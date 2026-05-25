/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gov.es.pmo.organization_parser.pmo_base.service;

import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import br.gov.es.pmo.organization_parser.pmo_base.model.IOrganizationParser;
import br.gov.es.pmo.organization_parser.pmo_base.model.OrganizationDto;


/**
 *
 * @author gean.carneiro
 */
@Service
public class ProviderService {
    
    private final Map<String, IOrganizationParser<?>> providers;
    
    private final ClientCredentialService clientCredentialService;
        
    public ProviderService(
        final Map<String, IOrganizationParser<?>> providers,
        final ClientCredentialService clientCredentialService
    ){
        this.providers = providers;
        this.clientCredentialService = clientCredentialService;
    }
    
    private OAuth2AuthenticationToken getToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof OAuth2AuthenticationToken) {
            return (OAuth2AuthenticationToken) auth;
        }

        throw new IllegalStateException("Usuario não autenticado");
    }

    private String getRegistrationId() {
        return getToken().getAuthorizedClientRegistrationId();
    }
    
    private String getClientToken() {
        return clientCredentialService.getClientToken(this.getRegistrationId());
    }
       
    
    
    public IOrganizationParser<?> getProvider() {
        
        IOrganizationParser<?> provider = this.providers.get(this.getRegistrationId());
        
        if(provider == null) {
            throw new IllegalStateException("Provider não implementado: " + getRegistrationId() );
        }

        
        return provider;
                    
    }
    
    public List<OrganizationDto> getOrganizations() {
        return this.getProvider().getOrganizations(getClientToken());
    }
    
}
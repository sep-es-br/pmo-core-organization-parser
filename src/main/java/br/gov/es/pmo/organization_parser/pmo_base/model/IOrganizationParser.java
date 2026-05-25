package br.gov.es.pmo.organization_parser.pmo_base.model;

import java.util.List;

/**
 *
 * Parser responsável por extrair dados de Organização a partir do OAuth2User
 *
 * @param <ID> tipo do identificador da organização (ex: guid)
 *
 */
public interface IOrganizationParser<ID> {

        /**
        * Extrai o identificador da organização a partir do OAuth2User
        *
        * @param token token de acesso do usuário
        * @return identificador das organizações
        */
        List<OrganizationDto> getOrganizations(String token);

}
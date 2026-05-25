package br.gov.es.pmo.organization_parser.pmo_base.model;

import net.minidev.json.JSONArray;
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
        JSONArray getOrganizations(String token);

}
package br.gov.es.pmo.organization_parser.pmo_base.model;

/**
 *
 * Parser responsável por extrair dados de Organização a partir do OAuth2User
 *
 * @param <ID> tipo do identificador da organização (ex: guid)
 *
 */
public interface IOrganizationParser<ID> {

    /**
     * Retorna o identificador único da organização
     * Ex: guid
     */
    ID getId(String clientToken);

    /**
     * Retorna o nome curto da organização
     * Ex: sigla
     */
    String getNome(String clientToken);

    /**
     * Retorna o nome completo / razão social da organização
     * Ex: razaoSocial
     */
    String getFullName(String clientToken);

}
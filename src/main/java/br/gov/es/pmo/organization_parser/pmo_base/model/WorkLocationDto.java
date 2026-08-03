package br.gov.es.pmo.organization_parser.pmo_base.model;

public final class WorkLocationDto {

    private final String guid;
    private final String name;
    private final String abbreviation;
    private final String organizationGuid;

    public WorkLocationDto(
        final String guid,
        final String name,
        final String abbreviation,
        final String organizationGuid
    ) {
        this.guid = guid;
        this.name = name;
        this.abbreviation = abbreviation;
        this.organizationGuid = organizationGuid;
    }

    public String getGuid() {
        return this.guid;
    }

    public String getName() {
        return this.name;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public String getOrganizationGuid() {
        return this.organizationGuid;
    }
}

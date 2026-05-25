package br.gov.es.pmo.organization_parser.pmo_base.model;

public class OrganizationDto {

    private String guid;
    private String name;
    private String fullName;
    private String integration;
    private String suffix;

    public OrganizationDto() {}

    public OrganizationDto(
        String guid,
        String name,
        String fullName,
        String integration,
        String suffix
    ) {
        this.guid = guid;
        this.name = name;
        this.fullName = fullName;
        this.integration = integration;
        this.suffix = suffix;
    }

    public String getGuid() { return guid; }
    public String getName() { return name; }
    public String getFullName() { return fullName; }
    public String getIntegration() { return integration; }
    public String getSuffix() { return suffix; }

    public void setGuid(String guid) { this.guid = guid; }
    public void setName(String name) { this.name = name; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setIntegration(String integration) { this.integration = integration; }
    public void setSuffix(String suffix) { this.suffix = suffix; }
}
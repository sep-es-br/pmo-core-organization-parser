# pmo-core-organization-parser

Biblioteca base para integrações do OpenPMO com serviços de organizações e lotações.

## Objetivo

Este projeto define contratos e tipos compartilhados por diferentes implementações de Organograma. O código consumidor depende das interfaces do core, enquanto cada ambiente fornece o parser concreto responsável por consultar sua fonte de dados.

O core também fornece um cliente HTTP com Bearer Token, propriedades auxiliares para OAuth 2.0 Client Credentials e auto-configuração Spring Boot.

## Componentes

| Componente | Finalidade |
| --- | --- |
| `IOrganizationParser<ID>` | Consulta organizações e resolve a sigla do órgão responsável por uma unidade. |
| `IWorkLocationParser` | Localiza uma lotação pelo GUID. |
| `OrganizationDto` | Representa uma organização normalizada para o OpenPMO. |
| `WorkLocationDto` | Representa unidade, nome, sigla e organização vinculada. |
| `ApiClient` | Executa requisições GET, POST e PUT com Bearer Token. |
| `ClientCredentialProperties` | Lê registros OAuth de `spring.security.oauth2.client`. |
| `OrganizationParserAutoConfig` | Habilita o component scan dos parsers no classpath. |

## Requisitos

- Java 8 ou superior;
- Spring Boot 2.2.12;
- repositório JitPack configurado no projeto consumidor.

## Instalação

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.sep-es-br:pmo-core-organization-parser:1.1.2'
}
```

## Contrato de organizações

```java
public interface IOrganizationParser<ID> {

    List<OrganizationDto> getOrganizations(String token);

    default Optional<String> findAbbreviationByUnit(ID unitId, String token) {
        return Optional.empty();
    }

    default void clearCache() {
    }
}
```

| Método | Comportamento esperado |
| --- | --- |
| `getOrganizations(token)` | Retorna as organizações visíveis para o token informado. |
| `findAbbreviationByUnit(unitId, token)` | Resolve a sigla da organização de uma unidade. Por padrão, retorna vazio. |
| `clearCache()` | Limpa caches mantidos pela implementação. Por padrão, não executa nenhuma ação. |

O tipo genérico `ID` permite que cada provedor escolha o formato do identificador, por exemplo `String` para GUIDs.

## Contrato de lotação

```java
public interface IWorkLocationParser {

    Optional<WorkLocationDto> findByGuid(String guid, String token);
}
```

O retorno vazio representa uma lotação inexistente ou não localizada. Validação de GUID e tratamento de erros HTTP pertencem à implementação concreta.

## DTOs

### `OrganizationDto`

| Campo | Descrição |
| --- | --- |
| `guid` | Identificador externo da organização. |
| `name` | Nome curto ou sigla. |
| `fullName` | Nome completo. |
| `integration` | Origem da integração. |
| `suffix` | Sufixo territorial ou institucional. |
| `sector` | Classificação do setor. |

### `WorkLocationDto`

| Campo | Descrição |
| --- | --- |
| `guid` | Identificador da unidade ou lotação. |
| `name` | Nome da unidade. |
| `abbreviation` | Sigla da unidade. |
| `organizationGuid` | GUID da organização responsável. |

`WorkLocationDto` é imutável e deve ser construído com todos os valores disponíveis.

## Cliente HTTP

`ApiClient` recebe uma URL base e oferece operações reativas que retornam `Mono<String>`:

```java
ApiClient client = new ApiClient("https://api.exemplo.gov.br");

Mono<String> response = client.doGetRequest(
    "/organizacoes",
    accessToken
);
```

O token é enviado no cabeçalho `Authorization: Bearer <token>`. Respostas HTTP não bem-sucedidas são propagadas pelo `WebClient`.

## Client Credentials

`ClientCredentialProperties` utiliza o prefixo `spring.security.oauth2.client`. Ao solicitar o registro `org`, a biblioteca procura as chaves com o sufixo `-client`, ou seja, `org-client`.

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          org-client:
            client-id: ${ORGANIZATION_CLIENT_ID}
            client-secret: ${ORGANIZATION_CLIENT_SECRET}
            authorization-grant-type: client_credentials
            scope: ApiOrganograma
        provider:
          org-client:
            token-uri: https://identity.example/token
            webapi: https://api.example
```

Quando o registro ou provider não existe, `getRegistration(...)` e `getProvider(...)` lançam `IllegalStateException`.

## Auto-configuração Spring Boot

`META-INF/spring.factories` registra `OrganizationParserAutoConfig`, que executa component scan em `br.gov.es.pmo.organization_parser`. Dessa forma, implementações presentes no classpath podem ser descobertas automaticamente.

## Criando uma implementação

```java
@Component
public class ExampleOrganizationParser implements IOrganizationParser<String> {

    @Override
    public List<OrganizationDto> getOrganizations(final String token) {
        return Collections.emptyList();
    }

    @Override
    public Optional<String> findAbbreviationByUnit(
        final String unitId,
        final String token
    ) {
        return Optional.empty();
    }
}
```

## Build local

```powershell
.\gradlew.bat clean build
```

```bash
./gradlew clean build
```

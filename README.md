# grails4-schema-per-tenant-test (H2, dynamic tenant creation and setup)

Grails 4.0.1 schema per tenant test / tested with grails and  JDK managed by sdkman

sdk use java 11.0.5.j9-adpt
sdk use grails 4.0.1

This project is a trivial example of a grails 4 schema per tenant setup - in paricular tho

* It uses dynamic rather than static configuration of the tenants (IE the integration test issues calls that cause the creation of the tenants at runtime rather than the data being hard-coded into application.yml, the tables for each tenant are configured when the schema is created at runtime).
* It uses an interceptor to map a HTTP header X-TENANT to the session attribute needed by SessionTenantResolver
* It uses a particular setup of a SchemaHandler for H2 (As H2 needs uppercase schema names)
* The integration test uses http-builder-ng as I struggled a little with the G4 http client.

It's primary purpose is to serve as a testbed for the [graphql issue](https://github.com/grails/gorm-graphql/issues/24) demonstrated in the [mt_graphql branch](https://github.com/ianibo/grails4-schema-per-tenant-test/tree/mt_graphql), but kept separate in case it's useful for anyone wanting to try dynamic multi-tenant setup in G4, or to test other MT issues.

The project runs an [integration test](https://github.com/ianibo/grails4-schema-per-tenant-test/blob/master/sptt/src/integration-test/groovy/sptt/LifecycleSpec.groovy) that dynamically creates two tenants and creates [Widget](https://github.com/ianibo/grails4-schema-per-tenant-test/blob/master/sptt/grails-app/domain/sptt/Widget.groovy) entries for each and checks that they are kept separate.

Comments / suggestions welcome.

# Running

checkout and

    grails test-app

Will run the integration test

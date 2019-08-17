# grails4-schema-per-tenant-test (H2, dynamic tenant creation and setup)
Grails 4 schema per tenant test

This project is a trivial example of a grails 4 schema per tenant setup - in paricular tho

* It uses dynamic rather than static configuration of the tenants
* It uses an interceptor to map a HTTP header X-TENANT to the session attribute needed by SessionTenantResolver
* It uses a particular setup of a SchemaHandler for H2 (As H2 needs uppercase schema names)

It's primary purpose is to serve as a testbed for the graphql issue documented in the mt_graphql branch, but kept
separate in case it's useful for anyone wanting to try dynamic multi-tenant setup in G4.

The project runs an [integration test](https://github.com/ianibo/grails4-schema-per-tenant-test/blob/master/sptt/src/integration-test/groovy/sptt/LifecycleSpec.groovy) that dynamically creates two tenants and creates [Widget](https://github.com/ianibo/grails4-schema-per-tenant-test/blob/master/sptt/grails-app/domain/sptt/Widget.groovy) entries for each and checks that they are kept separate.

I had an issue with needing to force withTransaction in my controller - of course IRL you would probably move
this functionality to a service anyway - but it's worth highlighting as something that used to work, and seems 
not to in G4.

Comments / suggestions welcome.

# Running

checkout and

    grails test-app

Will run the integration test

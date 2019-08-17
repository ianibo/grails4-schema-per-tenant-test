package sptt;

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.sql.DataSource
import java.sql.Connection
import java.sql.ResultSet
import org.grails.datastore.gorm.jdbc.schema.SchemaHandler

/**
 * Resolves the schema names
 *
 * @See https://github.com/grails/gorm-hibernate5/blob/master/grails-datastore-gorm-hibernate5/src/main/groovy/org/grails/orm/hibernate/HibernateDatastore.java
 * @See https://github.com/grails/gorm-hibernate5/blob/master/docs/src/docs/asciidoc/multiTenancy/schemaPerTenant.adoc
 */
@CompileStatic
@Slf4j
class SpttSchemaHandler implements SchemaHandler {

    final String useSchemaStatement
    final String createSchemaStatement
    final String defaultSchemaName

    SpttSchemaHandler() {
        // We use the search_path variant as folio installs some extensions in the public schema and 
        // we need to be able to access them.
        // useSchemaStatement = "SET SCHEMA '%s'"
        useSchemaStatement = "SET SCHEMA %s"
        createSchemaStatement = "CREATE SCHEMA %s IF NOT EXISTS"
        defaultSchemaName = "PUBLIC"
    }

    SpttSchemaHandler(String useSchemaStatement, String createSchemaStatement, String defaultSchemaName) {
        this.useSchemaStatement = useSchemaStatement
        this.createSchemaStatement = createSchemaStatement
        this.defaultSchemaName = defaultSchemaName
    }

    @Override
    void useSchema(Connection connection, String name) {
        // log.debug("useSchema");
        String useStatement = String.format(useSchemaStatement, name.toUpperCase())
        // log.debug("Executing SQL Set Schema Statement: ${useStatement}")

        try {
          connection
            .createStatement()
            .execute(useStatement)
        }
        catch ( Exception e ) {
          log.error("problem trying to use schema - \"${useStatement}\"",e)
          // Rethrow
          throw e
        }
    }

    @Override
    void useDefaultSchema(Connection connection) {
      useSchema(connection, defaultSchemaName)
    }

    @Override
    void createSchema(Connection connection, String name) {
        String schemaCreateStatement = String.format(createSchemaStatement, name)
        log.debug("Executing SQL Create Schema Statement: ${schemaCreateStatement}")
        connection
                .createStatement()
                .execute(schemaCreateStatement)
    }

    @Override
    Collection<String> resolveSchemaNames(DataSource dataSource) {
        // If this is called by HibernateDatastore.java then the next step will be for the
        // addTenantForSchemaInternal method to be called for this db
        // log.debug("OkapiSchemaHandler::resolveSchemaNames called")
        Collection<String> schemaNames = []
        Connection connection = dataSource.getConnection()
        try {

          // Iterate through all schemas, ignore any that don't end OkapiTenantResolver.getSchemaSuffix(), add those that do to the result.
          // This may be the place to run migrations, or it may be better to do that in bootstrap.
          ResultSet schemas = connection.getMetaData().getSchemas()
          while(schemas.next()) {
            String schema_name = schemas.getString("TABLE_SCHEM")
            if ( [ 'INFORMATION_SCHEMA', 'PUBLIC' ].contains(schema_name) ) {
              log.debug("Skip ${schema_name}");
            }
            else {
              log.debug('resolveSchemaNames adding schema for '+schema_name);
              schemaNames.add(schema_name)
            }
          }
        } finally {
            try {
                connection.createStatement().execute('set schema PUBLIC')
                connection?.close()
            } catch (Throwable e) {
                log.debug("Error closing SQL connection: $e.message", e)
            }
        }
        log.debug("resolveSchemaNames called - returning ${schemaNames}")
        return schemaNames
    }
}

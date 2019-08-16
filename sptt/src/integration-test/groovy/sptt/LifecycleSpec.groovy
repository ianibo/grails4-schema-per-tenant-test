package sptt

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.*
import spock.lang.Specification
import spock.lang.Stepwise
import geb.spock.GebSpec
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import groovyx.net.http.HttpBuilder
import groovyx.net.http.FromServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value


// See http://grails.github.io/grails-http/latest/

@Integration
@Rollback
@Stepwise
class LifecycleSpec extends Specification {

  final static String baseUrl = 'http://localhost:8080';
  final static Logger logger = LoggerFactory.getLogger(LifecycleSpec.class);

 @Value('${local.server.port}')
 Integer serverPort

  def setup() {
  }

  def cleanup() {
  }

  void "test tenant creation"(tenantid,name) {

    when:"We post a new tenant request to the admin controller"

      logger.debug("Post new tenant request for ${tenantid} to ${baseUrl}/admin/createTenant");

      String status = null;

      def httpBin = HttpBuilder.configure {
        request.uri = 'http://localhost:'+serverPort
      }

      def result = httpBin.get {
        request.uri.path = "/admin/createTenant?tenantId=${tenantid}"
        response.when(200) { FromServer fs, Object body ->
          logger.debug("createTenant returns 200 ${body}");
          status='OK'
        }
      }
      logger.debug("Result: ${result}");

    then:"The response is correct"
      status=='OK'

    where:
      tenantid | name
      'TestTenantG' | 'TestTenantG'


  }

  void "test widget creation"(tenantid, widgetName) {
    when:"We post a new tenant request to the admin controller"

      logger.debug("Post new widget (${widgetName}) for tenant ${tenantid}");

      String status = null;

      def httpBin = HttpBuilder.configure {
        request.uri = 'http://localhost:'+serverPort
         request.headers['X-TENANT'] = tenantid
      }

      def result = httpBin.get {
        request.uri.path = "/admin/createWidget?name=${widgetName}"
        response.when(200) { FromServer fs, Object body ->
          logger.debug("createWidget returns 200 ${body}");
          status='OK'
        }
      }
      logger.debug("Result: ${result}");

    then:"The response is correct"
      status=='OK'

    where:
      tenantid | widgetName
      'TestTenantG' | 'Test Widget A Tenant G'
      'TestTenantG' | 'Test Widget B Tenant G'
      'TestTenantG' | 'Test Widget C Tenant G'
      'TestTenantG' | 'Test Widget D Tenant G'
      'TestTenantG' | 'Test Widget E Tenant G'
      'TestTenantF' | 'Test Widget A Tenant F'
  }
 
}

package sptt

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.*
import spock.lang.Specification

import grails.plugins.rest.client.RestBuilder
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Integration
@Rollback
class LifecycleSpec extends Specification {

  final static Logger logger = LoggerFactory.getLogger(LifecycleSpec.class);

  def setup() {
  }

  def cleanup() {
  }

  void "test tenant creation"(tenantid,name) {

    when:"We post a new tenant request to the admin controller"

      logger.debug("Post new tenant request for ${tenantid} to ${baseUrl}/admin/createTenant");

      def resp = restBuilder().post("${baseUrl}/admin/createTenant?tenantId=${tenantid}") {
        header 'X-Tenant-Id', tenantid
      }

    then:"The response is correct"
      resp.status == OK.value()

    where:
      tenantid | name
      'TestTenantG' | 'TestTenantG'


  }
 
}

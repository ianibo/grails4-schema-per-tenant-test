package sptt

import grails.rest.*
import grails.converters.*

class AdminController {

  def createTenant(String tenantId) {
    log.debug("createTenant(${tenantId})");
    def result=[:]
    render result as JSON
  }
}

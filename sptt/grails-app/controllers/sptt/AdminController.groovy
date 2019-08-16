package sptt

import grails.rest.*
import grails.converters.*

class AdminController {

  def createTenant(String tenantId) {
    log.debug("createTenant(${tenantId})");
    def result=[:]
    render result as JSON
  }

  def createWidget(String widgetName) {
    log.debug("createWidget(${widgetName}) ${request.getAttribute('gorm.tenantId')}");
  }
}

package sptt

import grails.rest.*
import grails.converters.*

class AdminController {

  def createTenant(String tenantId) {
    def result=[status:'SURE']
    System.out.println("***");
    log.debug("createTenant(${tenantId})");
    render result as JSON
  }

  def createWidget(String name) {
    def result=[status:'SURE']
    log.debug("createWidget(${name}) ${request.getAttribute('gorm.tenantId')}");
    Widget w = new Widget(name:name).save(flush:true, failOnError:true);
    log.debug(Widget.list());
    render result as JSON
  }
}

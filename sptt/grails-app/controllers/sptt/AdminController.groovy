package sptt

import grails.rest.*
import grails.converters.*
import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.transactions.Transactional


class AdminController {

  def createTenant(String tenantId) {
    def result=[status:'SURE']
    System.out.println("***");
    log.debug("createTenant(${tenantId})");
    render result as JSON
  }

}

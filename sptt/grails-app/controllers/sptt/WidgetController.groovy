package sptt

import grails.rest.*
import grails.converters.*
import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.transactions.Transactional


@CurrentTenant
class WidgetController {

  def createWidget(String name) {
    def result=[status:'SURE']

    log.debug("createWidget(${params}) ${request.getAttribute('gorm.tenantId')}");
    Widget w = new Widget(widgetName:name).save(flush:true, failOnError:true);
    log.debug(Widget.list());
    render result as JSON
  }
}

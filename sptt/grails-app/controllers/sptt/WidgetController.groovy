package sptt

import grails.rest.*
import grails.converters.*
import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.transactions.Transactional

class WidgetController {

  @CurrentTenant
  @Transactional
  def createWidget(String name) {
    def result=[status:'SURE']

    Widget.withTransaction {
      log.debug("createWidget(${params})");
      Widget w = new Widget(widgetName:name).save(flush:true, failOnError:true);
      log.debug("${Widget.list()}");
    }

    render result as JSON
  }
}

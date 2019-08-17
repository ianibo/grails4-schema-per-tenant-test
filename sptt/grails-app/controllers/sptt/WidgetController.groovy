package sptt

import grails.rest.*
import grails.converters.*
import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.transactions.Transactional

@CurrentTenant
class WidgetController {

  @Transactional
  def createWidget(String name) {
    def result=[status:'SURE']

    
    log.debug("createWidget(${params})");
    Widget w = new Widget(widgetName:name).save(flush:true, failOnError:true);
    log.debug(Widget.list());
    render result as JSON
  }
}

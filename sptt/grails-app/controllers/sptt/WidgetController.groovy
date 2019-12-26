package sptt

import grails.rest.*
import grails.converters.*
import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.transactions.Transactional

@CurrentTenant
@Transactional
class WidgetController {

  @CurrentTenant
  def index() {
    Widget.withTransaction {
      render Widget.list() as JSON
    }
  }

  @CurrentTenant
  def createWidget(String name) {
    def result=[status:'SURE']

    Widget.withTransaction {
      log.debug("createWidget(${params})");

      Widget w = new Widget()
      w.widgetName = name;

      log.debug("Save widget");
      w.save(flush:true, failOnError:true);

      log.debug("List widgets");
      log.debug("${Widget.list()}");
    }

    render result as JSON
  }
}
